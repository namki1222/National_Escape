package blackcap.nationalescape.Activity.tab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private Button Btn_UserChange;
    private LinearLayout Layout_Experience1, Layout_Experience2, Layout_Experience3, Layout_Experience4, Layout_Experience5, Layout_Experience6;
    private ImageView Img_Experience1, Img_Experience2, Img_Experience3, Img_Experience4, Img_Experience5, Img_Experience6;
    private TextView Txt_Logout, Txt_Drop;
    private Switch Switch_Push, Switch_Review;

    private String User_Pk = "";
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
        async.execute(User_Pk);

        //푸시알림 on / off 이벤트
        setSwitch_Push();
        //리뷰 on /off 이벤트
        setSwitch_Review();
        //내 방탈출 횟수
        setExperience_Event();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Async async = new Async();
        async.execute(User_Pk);
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
        Layout_Experience1 = (LinearLayout)findViewById(R.id.layout_experience1);
        Layout_Experience2 = (LinearLayout)findViewById(R.id.layout_experience2);
        Layout_Experience3 = (LinearLayout)findViewById(R.id.layout_experience3);
        Layout_Experience4 = (LinearLayout)findViewById(R.id.layout_experience4);
        Layout_Experience5 = (LinearLayout)findViewById(R.id.layout_experience5);
        Layout_Experience6 = (LinearLayout)findViewById(R.id.layout_experience6);
        Img_Experience1 = (ImageView)findViewById(R.id.img_experience1);
        Img_Experience2 = (ImageView)findViewById(R.id.img_experience2);
        Img_Experience3 = (ImageView)findViewById(R.id.img_experience3);
        Img_Experience4 = (ImageView)findViewById(R.id.img_experience4);
        Img_Experience5 = (ImageView)findViewById(R.id.img_experience5);
        Img_Experience6 = (ImageView)findViewById(R.id.img_experience6);
        Txt_Logout = (TextView)findViewById(R.id.txt_logout);
        Txt_Drop = (TextView)findViewById(R.id.txt_drop);
        Switch_Push = (Switch)findViewById(R.id.switch_push);
        Switch_Review = (Switch)findViewById(R.id.switch_reivew);
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
    public void setExperience_Event(){
        Layout_Experience1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog_succed(0);
            }
        });
        Layout_Experience2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog_succed(1);
            }
        });
        Layout_Experience3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog_succed(2);
            }
        });
        Layout_Experience4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog_succed(3);
            }
        });
        Layout_Experience5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog_succed(4);
            }
        });
        Layout_Experience6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog_succed(5);
            }
        });
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;
        String str_nickname = "", str_pass = "", str_switch = "", str_experience = "", str_review = "";
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
                String result = http.HttpClient("Web_Escape", "User.jsp",params);
                JsonParserList jsonParserList = new JsonParserList();
                parseredData = jsonParserList.jsonParserList_Data9(result);

                str_nickname = parseredData[0][2];
                str_pass = parseredData[0][3];
                str_switch = parseredData[0][6];
                str_experience = parseredData[0][7];
                str_review = parseredData[0][8];
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
            Edit_Pass.setText(str_pass);

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

            if(str_experience.equals("1")){
                Img_Experience1.setImageResource(R.drawable.theme_write_radio_check);
            }
            else if(str_experience.equals("2")){
                Img_Experience2.setImageResource(R.drawable.theme_write_radio_check);
            }
            else if(str_experience.equals("3")){
                Img_Experience3.setImageResource(R.drawable.theme_write_radio_check);
            }
            else if(str_experience.equals("4")){
                Img_Experience4.setImageResource(R.drawable.theme_write_radio_check);
            }
            else if(str_experience.equals("5")){
                Img_Experience5.setImageResource(R.drawable.theme_write_radio_check);
            }
            else{
                Img_Experience6.setImageResource(R.drawable.theme_write_radio_check);
            }
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
    public void setDialog_succed(final int position){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Recommend.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_user_experience, (ViewGroup)findViewById(R.id.root));
        final TextView Btn_Ok = (TextView)layout.findViewById(R.id.btn_ok);
        final TextView Btn_Cancel = (TextView)layout.findViewById(R.id.btn_cancel);
        final MaterialDialog Dialog = new MaterialDialog(Setting.this);
        Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        Dialog.show();
        Btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient http = new HttpClient();
                if(position == 0){
                    http.HttpClient("Web_Escape", "User_Modify_Exp.jsp",User_Pk, "1");
                    Img_Experience1.setImageResource(R.drawable.theme_write_radio_check);
                    Img_Experience2.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience3.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience4.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience5.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience6.setImageResource(R.drawable.theme_write_radio);
                }
                else if(position == 1){
                    http.HttpClient("Web_Escape", "User_Modify_Exp.jsp",User_Pk, "2");
                    Img_Experience1.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience2.setImageResource(R.drawable.theme_write_radio_check);
                    Img_Experience3.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience4.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience5.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience6.setImageResource(R.drawable.theme_write_radio);
                }
                else if(position == 2){
                    http.HttpClient("Web_Escape", "User_Modify_Exp.jsp",User_Pk, "3");
                    Img_Experience1.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience2.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience3.setImageResource(R.drawable.theme_write_radio_check);
                    Img_Experience4.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience5.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience6.setImageResource(R.drawable.theme_write_radio);
                }
                else if(position == 3){
                    http.HttpClient("Web_Escape", "User_Modify_Exp.jsp",User_Pk, "4");
                    Img_Experience1.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience2.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience3.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience4.setImageResource(R.drawable.theme_write_radio_check);
                    Img_Experience5.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience6.setImageResource(R.drawable.theme_write_radio);
                }
                else if(position == 4){
                    http.HttpClient("Web_Escape", "User_Modify_Exp.jsp",User_Pk, "5");
                    Img_Experience1.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience2.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience3.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience4.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience5.setImageResource(R.drawable.theme_write_radio_check);
                    Img_Experience6.setImageResource(R.drawable.theme_write_radio);
                }
                else if(position == 5){
                    http.HttpClient("Web_Escape", "User_Modify_Exp.jsp",User_Pk, "6");
                    Img_Experience1.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience2.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience3.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience4.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience5.setImageResource(R.drawable.theme_write_radio);
                    Img_Experience6.setImageResource(R.drawable.theme_write_radio_check);
                }
                Dialog.dismiss();
            }
        });
        Btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog.dismiss();
            }
        });
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


