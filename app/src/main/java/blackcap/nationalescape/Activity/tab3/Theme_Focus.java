package blackcap.nationalescape.Activity.tab3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import blackcap.nationalescape.Activity.tab1.Book_Chapter1;
import blackcap.nationalescape.Activity.tab1.Home_Focus;
import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.Adapter.Theme_Focus_Review_Adapter;
import blackcap.nationalescape.Adapter.Theme_Review_Adapter;
import blackcap.nationalescape.Model.Theme_Review_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import blackcap.nationalescape.Uitility.StartRange;
import blackcap.nationalescape.Uitility.levelRange;
import me.drakeet.materialdialog.MaterialDialog;

import static blackcap.nationalescape.Activity.MainActivity.bol_main;
import static blackcap.nationalescape.Activity.MainActivity.gps_main;
import static blackcap.nationalescape.Activity.MainActivity.mInterstitialAd;

public class Theme_Focus extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성
    private String User_Pk = "";

    private ImageView Img_Theme, Img_Back;
    private ImageView Img_star1, Img_star2, Img_star3, Img_star4, Img_star5;
    private ImageView Img_level1, Img_level2, Img_level3, Img_level4, Img_level5;
    private TextView Txt_Title, Txt_Title1, Txt_IntroFocus, Txt_Intro, Txt_Date;
    private ImageView Img_Person1, Img_Person2, Img_Person3;
    private ImageView Img_Activity, Img_Tool;

    public static String str_theme_focus_title = "";
    private String str_theme_pk = "",str_company_pk = "", str_img = "", str_title = "", str_intro = "", str_company_title = "", str_deadtme = "", str_time = ".";
    private String str_category = "",str_grade = "", str_level = "", str_person = "", str_tool = "";
    private String str_activity = "",str_intro_focus = "", str_homepage = "", str_favorite = "", str_nickname = "", str_exp = "", str_bookflag = "", str_bookpage = "";
    private String str_comment_nick = "", str_comment_contents = "";

    private TextView Txt_Gradeavg;
    private TextView Txt_Review_GradeAvg, Txt_Review_Nickname;
    private ImageView Img_Experience;
    int startcount = 5;
    private ImageView Img_Review_Star1, Img_Review_Star2, Img_Review_Star3, Img_Review_Star4, Img_Review_Star5;
    private ImageView Img_Favorite;
    private RecyclerView List_Review;
    private TextView Txt_Theme_Focus, Txt_Deadtime;

    private LinearLayout Layout_Comment;
    private TextView Txt_Comment_Nick, Txt_Comment_Contents;

    public static ArrayList<Theme_Review_Model> theme_review_models;
    public static Theme_Review_Adapter theme_review_adapter;
//    private TextView Txt_Review_Nickname, Txt_Review_Focus;
    private LinearLayout Layout_Book, Layout_Company, Layout_Review_Write;

    private boolean favorite = false;
    String gps_x = "", gps_y = "";

    private Async async;
    private Async_Review3 async_review3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3_focus);

        init();
        //뒤로가기
        setImgBack();
        setReview_Focus();
        //리뷰 쓰기
        setReview_Write();

        //테마 찜 이벤트
        setTheme_Event();
        async = new Async();
        async.execute(str_company_pk);

        async_review3 = new Async_Review3();
        async_review3.execute();

        //카페로 이동
        setComapny_Event();
    }
    public void init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");;

        Intent intent1 = getIntent();
        str_theme_pk = intent1.getStringExtra("Theme_Pk");

        Img_Theme = (ImageView)findViewById(R.id.img_theme);

        Img_star1 = (ImageView)findViewById(R.id.img_star1);
        Img_star2 = (ImageView)findViewById(R.id.img_star2);
        Img_star3 = (ImageView)findViewById(R.id.img_star3);
        Img_star4 = (ImageView)findViewById(R.id.img_star4);
        Img_star5 = (ImageView)findViewById(R.id.img_star5);
        Img_level1 = (ImageView)findViewById(R.id.img_level1);
        Img_level2 = (ImageView)findViewById(R.id.img_level2);
        Img_level3 = (ImageView)findViewById(R.id.img_level3);
        Img_level4 = (ImageView)findViewById(R.id.img_level4);
        Img_level5 = (ImageView)findViewById(R.id.img_level5);
        Img_Favorite = (ImageView)findViewById(R.id.img_favorite);

        Txt_Gradeavg = (TextView) findViewById(R.id.txt_gradeavg);
        Txt_Title = (TextView)findViewById(R.id.txt_title);
        Txt_Title1 = (TextView)findViewById(R.id.txt_title1);
        Txt_Intro = (TextView)findViewById(R.id.txt_intro);
        Txt_IntroFocus = (TextView)findViewById(R.id.txt_introfocus);
        Txt_Deadtime = (TextView)findViewById(R.id.txt_deadtime);

        Img_Back = (ImageView)findViewById(R.id.img_back);

        Txt_Review_GradeAvg = (TextView)findViewById(R.id.txt_point);
        Txt_Review_Nickname = (TextView)findViewById(R.id.txt_review_nickname);
        Img_Review_Star1 = (ImageView) findViewById(R.id.img_review_star1);
        Img_Review_Star2 = (ImageView) findViewById(R.id.img_review_star2);
        Img_Review_Star3 = (ImageView) findViewById(R.id.img_review_star3);
        Img_Review_Star4 = (ImageView) findViewById(R.id.img_review_star4);
        Img_Review_Star5 = (ImageView) findViewById(R.id.img_review_star5);

        Img_Person1 = (ImageView)findViewById(R.id.img_person1);
        Img_Person1.setVisibility(View.GONE);
        Img_Person2 = (ImageView)findViewById(R.id.img_person2);
        Img_Person2.setVisibility(View.GONE);
        Img_Person3 = (ImageView)findViewById(R.id.img_person3);
        Img_Person3.setVisibility(View.GONE);
        Img_Activity = (ImageView)findViewById(R.id.img_activity);
        Img_Tool = (ImageView)findViewById(R.id.img_tool);

        Layout_Company = (LinearLayout)findViewById(R.id.layout_company);
        Layout_Book = (LinearLayout)findViewById(R.id.layout_book);
        Layout_Review_Write = (LinearLayout)findViewById(R.id.layout_review_write);
        Txt_Date = (TextView)findViewById(R.id.txt_date);
        Img_Experience = (ImageView)findViewById(R.id.img_experience);

        List_Review = (RecyclerView)findViewById(R.id.list_review);
        Txt_Theme_Focus = (TextView)findViewById(R.id.txt_theme_focus);

        Layout_Comment = (LinearLayout)findViewById(R.id.layout_comment);
        Txt_Comment_Nick = (TextView)findViewById(R.id.txt_comment_nick);
        Txt_Comment_Contents = (TextView)findViewById(R.id.txt_comment_comment);

        if(bol_main == false){
            if(mInterstitialAd != null){
                if(mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                }
                bol_main = true;
            }
        }

        HttpClient http = new HttpClient();
        http.HttpClient("Web_Escape", "Theme_Visit.jsp",str_theme_pk);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Img_Person1.setVisibility(View.GONE);
        Img_Person2.setVisibility(View.GONE);
        Img_Person3.setVisibility(View.GONE);
        Async async = new Async();
        async.execute(str_company_pk);
        Async_Review3 async_review3 = new Async_Review3();
        async_review3.execute();
    }
    public void setComapny_Event(){
        Layout_Company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //카페 바로가기일 경우
                Async_Companygo async_companygo = new Async_Companygo();
                async_companygo.execute();
            }
        });
        Layout_Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient http = new HttpClient();
                http.HttpClient("Web_Escape", "Home_Focus_Visit.jsp",str_company_pk, "book", setNowTime());
                if(str_bookflag.equals("true")){
                    Intent intent = new Intent(Theme_Focus.this, Book_Chapter1.class);
                    intent.putExtra("Company_Pk", str_company_pk);
                    intent.putExtra("Company_Title", str_company_title);
                    intent.putExtra("Theme_Pk", str_theme_pk);
                    intent.putExtra("Theme_Title", str_title);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    setDialog_check();
                }


                //카페 바로가기일 경우
                /*Async_Companygo async_companygo = new Async_Companygo();
                async_companygo.execute();*/
            }
        });
    }
    public void setTheme_Event(){
        Img_Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_Pk.equals(".")){
                    Intent intent = new Intent(Theme_Focus.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    //찜으로 등록하는 경우
                    if(favorite == false){
                        HttpClient http = new HttpClient();
                        String result = http.HttpClient("Web_Escape", "Theme_Favority_Add.jsp", User_Pk, str_theme_pk);
                        if(result.equals("succed")){
                            Glide.with(Theme_Focus.this).load(R.drawable.tab1_favorite_check)
                                    .into(Img_Favorite);
                            favorite = true;
                        }
                        else{
                            Toast.makeText(Theme_Focus.this,R.string.http_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                    //찜을 해제하는 경우
                    else{
                        HttpClient http = new HttpClient();
                        String result = http.HttpClient("Web_Escape", "Theme_Favority_Delete.jsp", User_Pk, str_theme_pk);
                        if(result.equals("succed")){
                            Glide.with(Theme_Focus.this).load(R.drawable.tab1_favorite)
                                    .into(Img_Favorite);
                            favorite = false;
                        }
                        else{
                            Toast.makeText(Theme_Focus.this,R.string.http_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    public void setReview_Write(){
        Layout_Review_Write.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(User_Pk.equals(".")){
                    Intent intent = new Intent(Theme_Focus.this, Login.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    HttpClient http = new HttpClient();
                    String result = http.HttpClient("Web_Escape", "Theme_Focus_Review_Exist.jsp",User_Pk, str_theme_pk);
                    if(result.equals("exist")){
                        Toast.makeText(Theme_Focus.this, "등록된 리뷰가 있습니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(Theme_Focus.this, Theme_Focus_Write.class);
                        intent.putExtra("Theme_Pk", str_theme_pk);
                        intent.putExtra("Experience", str_exp);
                        intent.putExtra("User_Time", str_time);
                        intent.putExtra("Theme_Time", str_deadtme);
                        intent.putExtra("Theme_Title", str_title);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                }
                return false;
            }
        });
    }
    public void setReview_Focus(){
        Txt_Theme_Focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Theme_Focus.this, Theme_Focus_Review.class);
                intent.putExtra("Theme_Pk", str_theme_pk);
                intent.putExtra("User_Time", str_time);
                intent.putExtra("Theme_Time", str_deadtme);
                intent.putExtra("Company_Title", str_company_title);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }
    public class Async_Review3 extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;

        private String[][] parseredData_reivew, parseredData_extra;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Theme_Focus.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();

                String result3 = http.HttpClient("Web_Escape", "Theme_Focus_Review_3_v4.jsp", str_theme_pk, User_Pk);

                parseredData_reivew = jsonParserList.jsonParserList_Data16(result3);

                theme_review_models = new ArrayList<Theme_Review_Model>();
                for (int i = 0; i < parseredData_reivew.length; i++) {
                    String nickname = parseredData_reivew[i][0];
                    String grage = parseredData_reivew[i][1];
                    String content = parseredData_reivew[i][2];
                    String date = parseredData_reivew[i][3];
                    String review_user_pk = parseredData_reivew[i][4];
                    String level = parseredData_reivew[i][5];
                    String status = parseredData_reivew[i][6];
                    String time = parseredData_reivew[i][7];
                    String hint = parseredData_reivew[i][8];
                    String experience = parseredData_reivew[i][9];
                    String recommend_flag = parseredData_reivew[i][10];
                    String recommend_count = parseredData_reivew[i][11];
                    String grade_flag = parseredData_reivew[i][12];
                    String bol_owner = parseredData_reivew[i][13];
                    String owner_date = parseredData_reivew[i][14];
                    String owner_memo = parseredData_reivew[i][15];

                    theme_review_models.add(new Theme_Review_Model(Theme_Focus.this, User_Pk, review_user_pk, nickname, grage, content, date, str_theme_pk, level, status, time, hint, experience, "default", str_time, str_deadtme, recommend_flag, recommend_count, grade_flag, bol_owner, str_company_title, owner_date, owner_memo ,"desc"));

                }
                return "succed";
            } catch (Exception e) {
                Log.i("테스트", e+"에러");
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            LinearLayoutManager layoutManager1 = new LinearLayoutManager(Theme_Focus.this);
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager1.scrollToPosition(0);
            theme_review_adapter = new Theme_Review_Adapter(Theme_Focus.this, theme_review_models);
            List_Review.setLayoutManager(layoutManager1);
            List_Review.setAdapter(theme_review_adapter);

            progressDialog.dismiss();
        }
    }
    public class Async_Companygo extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;

        private String[][] parseredData;
        private String str_company_pk = "", str_title = "", str_gradeavg = "", str_recommendCount = "", str_distance = "", str_intro = "", str_address = "";
        private boolean exist = false;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Theme_Focus.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                if (gps_main.isGetLocation()) {
                    gps_x = Double.toString(gps_main.getLatitude());
                    gps_y = Double.toString(gps_main.getLongitude());
                } else {
                    // GPS 를 사용할수 없으므로
                    gps_main.showSettingsAlert();
                }
                Log.i("테스x", gps_x); Log.i("테스y", gps_y);
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();

                String result = http.HttpClient("Web_Escape", "Home_Theme_Focus.jsp",gps_x,  gps_y, str_theme_pk, User_Pk);
                parseredData = jsonParserList.jsonParserList_Data11(result);
                if(parseredData.length == 0){
                    exist = false;
                }
                else{
                    exist = true;
                    str_company_pk = parseredData[0][0];
                    str_title = parseredData[0][2];
                    str_gradeavg = parseredData[0][4];
                    str_recommendCount = parseredData[0][5];
                    str_distance = parseredData[0][7];
                    str_intro = parseredData[0][3];
                    str_address = parseredData[0][6];
                }

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            gps_main.stopUsingGPS();

            if(exist == true){
                Intent intent = new Intent(Theme_Focus.this, Home_Focus.class);
                intent.putExtra("Company_Pk", str_company_pk);
                intent.putExtra("Title", str_title);
                intent.putExtra("Grade_Avg", str_gradeavg);
                intent.putExtra("Recommend_Count", str_recommendCount);
                intent.putExtra("Distance", str_distance);
                intent.putExtra("Intro", str_intro);
                intent.putExtra("Address", str_address);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);

            }
            else{
                Toast.makeText(Theme_Focus.this, "등록되지 않은 업체입니다.", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }
    public class Async extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;

        private String[][] parseredData, parseredData_reivew, parseredData_User;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Theme_Focus.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //공지사항 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();
                parseredData = jsonParserList.jsonParserList_Data21(http.HttpClient("Web_Escape", "Theme_Focus_v2.jsp", str_theme_pk, User_Pk));

//                String result3 = http.HttpClient("Web_Escape", "Home_Focus_Review_3.jsp",params[0], User_Pk);
//                parseredData_reivew = jsonParserList.jsonParserList_Data5(result3);

                String result4 = http.HttpClient("Web_Escape", "User_v3.jsp",User_Pk, "yologuys12");
                parseredData_User = jsonParserList.jsonParserList_Data13(result4);

                str_company_pk = parseredData[0][1];
                str_img = parseredData[0][2];
                str_title = parseredData[0][3];
                str_theme_focus_title = parseredData[0][3];
                str_intro = parseredData[0][4];
                str_category = parseredData[0][5];
                str_grade = parseredData[0][6];
                str_level = parseredData[0][7];
                str_person = parseredData[0][8];
                str_tool = parseredData[0][9];
                str_activity = parseredData[0][10];
                str_intro_focus = parseredData[0][11];
                str_homepage = parseredData[0][12];
                str_favorite = parseredData[0][13];
                str_company_title = parseredData[0][14];
                str_deadtme = parseredData[0][16];
                str_bookflag = parseredData[0][17];
                str_bookpage = parseredData[0][18];
                str_comment_nick = parseredData[0][19];
                str_comment_contents = parseredData[0][20];

                Log.i("테스2", str_company_title);
//                review_models = new ArrayList<Review_Model>();
//                for (int i = 0; i < parseredData_reivew.length; i++) {
//                    String nickname = parseredData_reivew[i][0];
//                    String grage = parseredData_reivew[i][1];
//                    String content = parseredData_reivew[i][2];
//                    String date = parseredData_reivew[i][3];
//                    String review_user_pk = parseredData_reivew[i][4];
//                    review_models.add(new Review_Model(Home_Focus.this, User_Pk, review_user_pk, nickname, grage, content, date, str_company_pk));
//                }

                str_nickname = parseredData_User[0][2];
                str_exp = parseredData_User[0][7];
                if(User_Pk.equals(".")){
                    str_time = "extra";
                }
                else{
                    str_time = parseredData_User[0][12];
                }

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Glide.with(Theme_Focus.this).load("http://www.yologuys.com/Escape_img/theme/"+str_theme_pk+".jpg")
                    .into(Img_Theme);

            StartRange startRange = new StartRange();
            startRange.rangestart(Theme_Focus.this, Double.parseDouble(str_grade), Img_star1, Img_star2, Img_star3, Img_star4, Img_star5);
            startRange.rangestart(Theme_Focus.this, Double.parseDouble(str_grade), Img_Review_Star1, Img_Review_Star2, Img_Review_Star3, Img_Review_Star4, Img_Review_Star5);
            levelRange levelRange = new levelRange();
            levelRange.levelstart(Theme_Focus.this, Integer.parseInt(str_level), Img_level1, Img_level2, Img_level3, Img_level4, Img_level5);

            //Txt_Title.setText(str_title);
            Txt_Title1.setText(str_title);
            Txt_Gradeavg.setText(str_grade+"점");
            Txt_Review_GradeAvg.setText(str_grade+"점");
            Txt_Intro.setText(str_intro);
            Txt_IntroFocus.setText(str_intro_focus);

            if(str_nickname.equals("")){
                Img_Experience.setVisibility(View.GONE);
                Txt_Review_Nickname.setText("로그인 후 이용해주세요.");
            }
            else{
                Img_Experience.setVisibility(View.VISIBLE);
                Txt_Review_Nickname.setText(str_nickname);
            }

            Txt_Date.setText(setNowTime());
            Txt_Deadtime.setText("제한시간 : "+ str_deadtme+"분");
            //추천 인원
            if(str_person.contains("2")){
                if(Img_Person1.getVisibility() == View.GONE){
                    Img_Person1.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person2)
                            .into(Img_Person1);
                }
                else if(Img_Person2.getVisibility() == View.GONE){
                    Img_Person2.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person2)
                            .into(Img_Person2);
                }
                else{
                    Img_Person3.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person2)
                            .into(Img_Person3);
                }
            }
            //추천 인원
            if(str_person.contains("3")){
                if(Img_Person1.getVisibility() == View.GONE){
                    Img_Person1.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person3)
                            .into(Img_Person1);
                }
                else if(Img_Person2.getVisibility() == View.GONE){
                    Img_Person2.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person3)
                            .into(Img_Person2);
                }
                else{
                    Img_Person3.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person3)
                            .into(Img_Person3);
                }
            }
            if(str_person.contains("4")){
                if(Img_Person1.getVisibility() == View.GONE){
                    Img_Person1.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person4)
                            .into(Img_Person1);
                }
                else if(Img_Person2.getVisibility() == View.GONE){
                    Img_Person2.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person4)
                            .into(Img_Person2);
                }
                else{
                    Img_Person3.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person4)
                            .into(Img_Person3);
                }
            }
            if(str_person.contains("5")){
                if(Img_Person1.getVisibility() == View.GONE){
                    Img_Person1.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person5)
                            .into(Img_Person1);
                }
                else if(Img_Person2.getVisibility() == View.GONE){
                    Img_Person2.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person5)
                            .into(Img_Person2);
                }
                else{
                    Img_Person3.setVisibility(View.VISIBLE);
                    Glide.with(Theme_Focus.this).load(R.drawable.theme_person5)
                            .into(Img_Person3);
                }
            }

            //활동성
            if(str_activity.equals("1")){
                Glide.with(Theme_Focus.this).load(R.drawable.theme_activity0)
                        .into(Img_Activity);
            }
            else if(str_activity.equals("2")){
                Glide.with(Theme_Focus.this).load(R.drawable.theme_activity1)
                        .into(Img_Activity);
            }
            else if(str_activity.equals("3")){
                Glide.with(Theme_Focus.this).load(R.drawable.theme_activity2)
                        .into(Img_Activity);
            }

            //장치
            if(str_tool.equals("rock")){
                Glide.with(Theme_Focus.this).load(R.drawable.theme_tool_rock)
                        .into(Img_Tool);
            }
            else if(str_tool.equals("device")){
                Glide.with(Theme_Focus.this).load(R.drawable.theme_tool_device)
                        .into(Img_Tool);
            }
            else{
                Glide.with(Theme_Focus.this).load(R.drawable.theme_tool_both)
                        .into(Img_Tool);
            }

            if(str_exp.equals("1")){
                Img_Experience.setImageResource(R.drawable.user_medal_1_10);
            }
            else if(str_exp.equals("2")){
                Img_Experience.setImageResource(R.drawable.user_medal_11_50);
            }
            else if(str_exp.equals("3")){
                Img_Experience.setImageResource(R.drawable.user_medal_51_100);
            }
            else if(str_exp.equals("4")){
                Img_Experience.setImageResource(R.drawable.user_medal_101_200);
            }
            else if(str_exp.equals("5")){
                Img_Experience.setImageResource(R.drawable.user_medal_201_300);
            }
            else if(str_exp.equals("6")){
                Img_Experience.setImageResource(R.drawable.user_medal_301_500);
            }
            else if(str_exp.equals("7")){
                Img_Experience.setImageResource(R.drawable.user_medal_501_1000);
            }
            else if(str_exp.equals("8")){
                Img_Experience.setImageResource(R.drawable.user_medal_1000);
            }

            if(str_favorite.equals("true")){
                favorite = true;
                Glide.with(Theme_Focus.this).load(R.drawable.tab1_favorite_check)
                        .into(Img_Favorite);
            }
            else{
                favorite = false;
                Glide.with(Theme_Focus.this).load(R.drawable.tab1_favorite)
                        .into(Img_Favorite);
            }

            if(!str_comment_nick.equals("") && !str_comment_contents.equals("")){
                Layout_Comment.setVisibility(View.VISIBLE);
                Txt_Comment_Nick.setText(str_comment_nick);
                Txt_Comment_Contents.setText(str_comment_contents);
            }
            else{
                Layout_Comment.setVisibility(View.GONE);
            }
            //리뷰 셋팅
//            Txt_Review_GradeAvg.setText(str_grade_avg+"점");
//            Txt_Review_Nickname.setText(str_nickname);
//            StartRange startRange = new StartRange();
//            startRange.rangestart(Home_Focus.this, Double.parseDouble(str_grade_avg), Img_Review_Star1, Img_Review_Star2, Img_Review_Star3, Img_Review_Star4, Img_Review_Star5);
//
//            LinearLayoutManager layoutManager1 = new LinearLayoutManager(Home_Focus.this);
//            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
//            layoutManager1.scrollToPosition(0);
//            review_adapter = new Review_Adapter(Home_Focus.this, review_models);
//            List_Review.setLayoutManager(layoutManager1);
//            List_Review.setAdapter(review_adapter);

            progressDialog.dismiss();
        }
    }
    private void setImgBack() {
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if (async.getStatus() == AsyncTask.Status.RUNNING)
                    {
                        async.cancel(true);
                    }
                    if (async_review3.getStatus() == AsyncTask.Status.RUNNING)
                    {
                        async_review3.cancel(true);
                    }
                }
                catch (Exception e)
                {
                }
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        try
        {
            if (async.getStatus() == AsyncTask.Status.RUNNING)
            {
                async.cancel(true);
            }
            if (async_review3.getStatus() == AsyncTask.Status.RUNNING)
            {
                async_review3.cancel(true);
            }
        }
        catch (Exception e)
        {
        }
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        super.onBackPressed();
    }
    private String setNowTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        return sdf.format(date)+"";
    }
    public void setDialog_check(){
        LayoutInflater inflater = (LayoutInflater)Theme_Focus.this.getSystemService(Home_Focus.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_bookflag, (ViewGroup)findViewById(R.id.root));
        final TextView Txt_Ok = (TextView) layout.findViewById(R.id.btn_ok);
        final TextView Txt_Cancel = (TextView) layout.findViewById(R.id.btn_cancel);
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Theme_Focus.this);
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();

        Txt_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(str_bookpage));
                startActivity(newIntent);
                TeamInfo_Dialog.dismiss();
            }
        });
    }
}



