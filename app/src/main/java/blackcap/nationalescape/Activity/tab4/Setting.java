package blackcap.nationalescape.Activity.tab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.Base64Util;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import me.drakeet.materialdialog.MaterialDialog;

import static blackcap.nationalescape.Activity.Fragment_main0.main_event_myTask;
import static blackcap.nationalescape.Activity.MainActivity.act_main;

public class Setting extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성
    SharedPreferences.Editor editor;

    private ImageView Img_Back;
    private EditText Edit_Nickname, Edit_Pass;
    private Button Btn_UserChange, Btn_ChangePass;
    private TextView Txt_Logout, Txt_Drop;
    private Switch Switch_Push, Switch_Review;
    private ImageView Img_ExtraTime, Img_UseTime;
    private ImageView Img_Category1, Img_Category2, Img_Category3, Img_Category4;
    private ImageView Img_Mymedal;
    private TextView Txt_Mymedal;
    private LinearLayout Layout_RatingFlag;
    private ImageView Img_RatingFlag;

    private String User_Pk = "", User_Time = "", User_GradeFlag;
    private String str_review_filter = "", str_experience = "";
    private boolean bol_ratingflag = true;
    private String str_review_count = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_setting);

        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");

        init();
        setImgBack();
        setLogout();
        setDrop();
        Async async = new Async();
        async.execute(User_Pk, "yologuys12");

        //푸시알림 on / off 이벤트
        setSwitch_Push();
        //리뷰 on /off 이벤트
        setSwitch_Review();
        //시간표기법 변경
        setTimeClick_Event();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Async async = new Async();
        async.execute(User_Pk, "yologuys12");
    }

    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Edit_Nickname = (EditText)findViewById(R.id.edit_nickname);
        Edit_Pass = (EditText)findViewById(R.id.edit_pass);
        Btn_UserChange = (Button)findViewById(R.id.btn_userchange);
        Btn_UserChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, Setting_ChangeUser.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Btn_ChangePass = (Button)findViewById(R.id.btn_changepass);
        Btn_ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, Setting_ChangePass.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });

        Txt_Logout = (TextView)findViewById(R.id.txt_logout);
        Txt_Drop = (TextView)findViewById(R.id.txt_drop);
        Switch_Push = (Switch)findViewById(R.id.switch_push);
        Switch_Review = (Switch)findViewById(R.id.switch_reivew);
        Img_ExtraTime = (ImageView)findViewById(R.id.img_extratime);
        Img_UseTime = (ImageView)findViewById(R.id.img_usetime);

        Img_Category1 = (ImageView) findViewById(R.id.img_category1);
        Img_Category2 = (ImageView) findViewById(R.id.img_category2);
        Img_Category3 = (ImageView) findViewById(R.id.img_category3);
        Img_Category4 = (ImageView) findViewById(R.id.img_category4);

        Img_Mymedal = (ImageView)findViewById(R.id.img_mymedal);
        Txt_Mymedal = (TextView)findViewById(R.id.txt_mymedal);

        Layout_RatingFlag = (LinearLayout)findViewById(R.id.layout_ratingflag);
        Layout_RatingFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bol_ratingflag == true){
                    HttpClient http = new HttpClient();
                    http.HttpClient("Web_Escape", "User_Modify_Exp_v2.jsp",User_Pk, "false", str_review_count);
                    bol_ratingflag = false;
                    Img_RatingFlag.setImageResource(R.drawable.setting_user_rating_uncheck);
                }
                else{
                    HttpClient http = new HttpClient();
                    http.HttpClient("Web_Escape", "User_Modify_Exp_v2.jsp",User_Pk, "true", str_review_count);
                    bol_ratingflag = true;
                    Img_RatingFlag.setImageResource(R.drawable.setting_user_rating_check);
                }
            }
        });
        Img_RatingFlag = (ImageView)findViewById(R.id.img_ratingflag);
    }
    public void setLogout(){
        Txt_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //캐시 초기화
                preferences = getSharedPreferences("escape", MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("pk", ".");
                editor.commit();

                //메인화면 종료
                //메인화면 종료
                main_event_myTask.cancel();
                act_main.finish();
                //로그인 페이지로 이동
                Intent intent = new Intent(Setting.this, Login.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }
    public void setDrop(){
        Txt_Drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //캐시 초기화
                preferences = getSharedPreferences("escape", MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("pk", ".");
                editor.commit();

                Async_Drop async_drop = new Async_Drop();
                async_drop.execute(User_Pk);
            }
        });
    }
    public void setSwitch_Push(){
        Switch_Push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    HttpClient http = new HttpClient();
                    String result = http.HttpClient("Web_Escape", "User_Fcm_On.jsp", User_Pk);
                    if(result.equals("succed")){
                        Switch_Push.setChecked(true);
                    }
                    else{
                        Log.i("테스트", result);
                        Toast.makeText(Setting.this, "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    HttpClient http = new HttpClient();
                    String result = http.HttpClient("Web_Escape", "User_Fcm_Off.jsp", User_Pk);
                    if(result.equals("succed")){
                        Switch_Push.setChecked(false);
                    }
                    else{
                        Log.i("테스트", result);
                        Toast.makeText(Setting.this, "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void setSwitch_Review(){
        Switch_Review.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    HttpClient http = new HttpClient();
                    String result = http.HttpClient("Web_Escape", "User_Review_On.jsp", User_Pk);
                    if(result.equals("succed")){
                        Switch_Review.setChecked(true);
                    }
                    else{
                        Toast.makeText(Setting.this, "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    HttpClient http = new HttpClient();
                    String result = http.HttpClient("Web_Escape", "User_Review_Off.jsp", User_Pk);
                    if(result.equals("succed")){
                        Switch_Review.setChecked(false);
                    }
                    else{
                        Toast.makeText(Setting.this, "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void setTimeClick_Event(){
        Img_ExtraTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_ExtraTime.setImageResource(R.drawable.setting_time_extra_click);
                Img_UseTime.setImageResource(R.drawable.setting_time_use);

                Async_TimeChange async_timeChange = new Async_TimeChange();
                async_timeChange.execute(User_Pk, "extra");
            }
        });
        Img_UseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_ExtraTime.setImageResource(R.drawable.setting_time_extra);
                Img_UseTime.setImageResource(R.drawable.setting_time_use_click);

                Async_TimeChange async_timeChange = new Async_TimeChange();
                async_timeChange.execute(User_Pk, "use");
            }
        });
    }

    public void setCategory_Event(){
        if(str_review_filter.equals("desc")){
            Img_Category1.setImageResource(R.drawable.themelist_new_check);
            Img_Category2.setImageResource(R.drawable.themelist_grade);
            Img_Category3.setImageResource(R.drawable.themelist_rank);
            Img_Category4.setImageResource(R.drawable.themelist_rec);


        }
        else if(str_review_filter.equals("review")){
            Img_Category1.setImageResource(R.drawable.themelist_new);
            Img_Category2.setImageResource(R.drawable.themelist_grade_check);
            Img_Category3.setImageResource(R.drawable.themelist_rank);
            Img_Category4.setImageResource(R.drawable.themelist_rec);
        }
        else if(str_review_filter.equals("record")){
            Img_Category1.setImageResource(R.drawable.themelist_new);
            Img_Category2.setImageResource(R.drawable.themelist_grade);
            Img_Category3.setImageResource(R.drawable.themelist_rank_check);
            Img_Category4.setImageResource(R.drawable.themelist_rec);
        }
        else {
            Img_Category1.setImageResource(R.drawable.themelist_new);
            Img_Category2.setImageResource(R.drawable.themelist_grade);
            Img_Category3.setImageResource(R.drawable.themelist_rank);
            Img_Category4.setImageResource(R.drawable.themelist_rec_click);
        }

        Img_Category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient http = new HttpClient();
                http.HttpClient("Web_Escape", "User_Change_ReviewFilter.jsp",User_Pk, "desc");

                Img_Category1.setImageResource(R.drawable.themelist_new_check);
                Img_Category2.setImageResource(R.drawable.themelist_grade);
                Img_Category3.setImageResource(R.drawable.themelist_rank);
                Img_Category4.setImageResource(R.drawable.themelist_rec);
            }
        });
        Img_Category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient http = new HttpClient();
                http.HttpClient("Web_Escape", "User_Change_ReviewFilter.jsp",User_Pk, "review");

                Img_Category1.setImageResource(R.drawable.themelist_new);
                Img_Category2.setImageResource(R.drawable.themelist_grade_check);
                Img_Category3.setImageResource(R.drawable.themelist_rank);
                Img_Category4.setImageResource(R.drawable.themelist_rec);
            }
        });
        Img_Category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient http = new HttpClient();
                http.HttpClient("Web_Escape", "User_Change_ReviewFilter.jsp",User_Pk, "record");

                Img_Category1.setImageResource(R.drawable.themelist_new);
                Img_Category2.setImageResource(R.drawable.themelist_grade);
                Img_Category3.setImageResource(R.drawable.themelist_rank_check);
                Img_Category4.setImageResource(R.drawable.themelist_rec);
            }
        });
        Img_Category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient http = new HttpClient();
                http.HttpClient("Web_Escape", "User_Change_ReviewFilter.jsp",User_Pk, "recommend");

                Img_Category1.setImageResource(R.drawable.themelist_new);
                Img_Category2.setImageResource(R.drawable.themelist_grade);
                Img_Category3.setImageResource(R.drawable.themelist_rank);
                Img_Category4.setImageResource(R.drawable.themelist_rec_click);
            }
        });
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;
        String str_nickname = "", str_pass = "", str_switch = "", str_review = "", str_time = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Setting.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //유저 정보 response
                HttpClient http = new HttpClient();
                String result = http.HttpClient("Web_Escape", "User_v4.jsp",params);
                str_review_count = http.HttpClient("Web_Escape", "User_Rating.jsp",params);

                JsonParserList jsonParserList = new JsonParserList();
                parseredData = jsonParserList.jsonParserList_Data14(result);

                str_nickname = parseredData[0][2];
                str_pass = Base64Util.decode(parseredData[0][3]);
                str_switch = parseredData[0][6];
                str_experience = parseredData[0][7];
                str_review = parseredData[0][8];
                str_time = parseredData[0][12];
                str_review_filter = parseredData[0][13];
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Edit_Nickname.setText(str_nickname);
            Edit_Pass.setText("**********");

            if(str_switch.equals("on")){
                Switch_Push.setChecked(true);
            }
            else{
                Switch_Push.setChecked(false);
            }

            if(str_review.equals("on")){
                Switch_Review.setChecked(true);
            }
            else{
                Switch_Review.setChecked(false);
            }

            Log.i("테스트13", str_experience);

            if(str_experience.equals("0")){
                Img_Mymedal.setVisibility(View.INVISIBLE);
            }
            else if(str_experience.equals("1")){
                Img_Mymedal.setVisibility(View.VISIBLE);
                Img_Mymedal.setImageResource(R.drawable.user_medal_1_10);
            }
            else if(str_experience.equals("2")){
                Img_Mymedal.setVisibility(View.VISIBLE);
                Img_Mymedal.setImageResource(R.drawable.user_medal_11_50);
            }
            else if(str_experience.equals("3")){
                Img_Mymedal.setVisibility(View.VISIBLE);
                Img_Mymedal.setImageResource(R.drawable.user_medal_51_100);
            }
            else if(str_experience.equals("4")){
                Img_Mymedal.setVisibility(View.VISIBLE);
                Img_Mymedal.setImageResource(R.drawable.user_medal_101_200);
            }
            else if(str_experience.equals("5")){
                Img_Mymedal.setVisibility(View.VISIBLE);
                Img_Mymedal.setImageResource(R.drawable.user_medal_201_300);
            }
            else if(str_experience.equals("6")){
                Img_Mymedal.setVisibility(View.VISIBLE);
                Img_Mymedal.setImageResource(R.drawable.user_medal_301_500);
            }
            else if(str_experience.equals("7")){
                Img_Mymedal.setVisibility(View.VISIBLE);
                Img_Mymedal.setImageResource(R.drawable.user_medal_501_1000);
            }
            else{
                Img_Mymedal.setVisibility(View.VISIBLE);
                Img_Mymedal.setImageResource(R.drawable.user_medal_1000);
            }

            if(str_time.equals("extra")){
                Img_ExtraTime.setImageResource(R.drawable.setting_time_extra_click);
                Img_UseTime.setImageResource(R.drawable.setting_time_use);

            }
            else{
                Img_ExtraTime.setImageResource(R.drawable.setting_time_extra);
                Img_UseTime.setImageResource(R.drawable.setting_time_use_click);

            }

            if(str_experience.equals("0")){
                Img_RatingFlag.setImageResource(R.drawable.setting_user_rating_check);
                bol_ratingflag = true;
            }
            else{
                Img_RatingFlag.setImageResource(R.drawable.setting_user_rating_uncheck);
                bol_ratingflag = false;
            }

            setCategory_Event();
            Txt_Mymedal.setText(str_review_count+"개");
            progressDialog.dismiss();
        }
    }
    public class Async_TimeChange extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Setting.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                HttpClient http = new HttpClient();
                http.HttpClient("Web_Escape", "User_Timeview_Change.jsp",params);
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(Setting.this, "변경되었습니다.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
    public class Async_Drop extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Setting.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                HttpClient http = new HttpClient();
                http.HttpClient("Web_Escape", "User_Drop.jsp",params);
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //메인화면 종료
            main_event_myTask.cancel();
            act_main.finish();
            //로그인 페이지로 이동
            Intent intent = new Intent(Setting.this, Login.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);

            progressDialog.dismiss();
        }
    }
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3","msg4", "msg5","msg6"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void setImgBack() {
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        super.onBackPressed();
    }
}


