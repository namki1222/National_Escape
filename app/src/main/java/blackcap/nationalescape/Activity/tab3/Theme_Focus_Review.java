package blackcap.nationalescape.Activity.tab3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import blackcap.nationalescape.Adapter.Theme_Focus_Review_Adapter;
import blackcap.nationalescape.Model.Theme_Review_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Theme_Focus_Review extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성
    private String User_Pk = "" ,str_theme_pk = "", str_user_time = "", str_theme_time = "", str_filter_review = "";
    public static String str_theme_focus_Company_Title = "";

    private ImageView Img_Back;

    public static RecyclerView theme_focus_List_Review;
    public static ArrayList<Theme_Review_Model> theme_review_models;
    public static Theme_Focus_Review_Adapter theme_review_adapter;
    private int line_count = 0;

    private Async async;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3_focus_review);

        init();
        setImgBack();



    }

    public void init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");
        Async_User async_user = new Async_User();
        async_user.execute(User_Pk, "yologuys12");

        Intent intent1 = getIntent();
        str_theme_pk = intent1.getStringExtra("Theme_Pk");
        str_user_time = intent1.getStringExtra("User_Time");
        str_theme_time = intent1.getStringExtra("Theme_TIme");
        str_theme_focus_Company_Title = intent1.getStringExtra("Company_Title");

        Img_Back = (ImageView)findViewById(R.id.img_back);
        theme_focus_List_Review = (RecyclerView)findViewById(R.id.list_review);
    }

    public class Async extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;

        private String[][] parseredData_reivew;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Theme_Focus_Review.this,"","",true,true,null);
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
                Log.i("테스", str_filter_review);
                String result = http.HttpClient("Web_Escape", "Theme_Focus_Review_v8.jsp",params);
                parseredData_reivew = jsonParserList.jsonParserList_Data18(result);

                theme_review_models = new ArrayList<Theme_Review_Model>();
                theme_review_models.add(new Theme_Review_Model(Theme_Focus_Review.this, User_Pk, "*", "*", "*", "*", "*", str_theme_pk, "*", "*", "*", "*", "*", "*", str_user_time, str_theme_time, "*", "*", "*", "*", "*", "*", "*", str_filter_review));
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
                    String line = parseredData_reivew[i][10];
                    String recommend_count = parseredData_reivew[i][12];
                    String repoart_count = parseredData_reivew[i][13];
                    String grade_flag = parseredData_reivew[i][14];
                    String bol_owner = parseredData_reivew[i][15];
                    String owner_date = parseredData_reivew[i][16];
                    String owner_memo = parseredData_reivew[i][17];

                    theme_review_models.add(new Theme_Review_Model(Theme_Focus_Review.this, User_Pk, review_user_pk, nickname, grage, content, date, str_theme_pk, level, status, time, hint, experience, line, str_user_time, str_theme_time, recommend_count, repoart_count, grade_flag, bol_owner, str_theme_focus_Company_Title, owner_date, owner_memo, str_filter_review));
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

            //리뷰 셋팅
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(Theme_Focus_Review.this);
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager1.scrollToPosition(0);

            theme_review_adapter = new Theme_Focus_Review_Adapter(Theme_Focus_Review.this, theme_review_models);
            theme_focus_List_Review.setLayoutManager(layoutManager1);
            theme_focus_List_Review.setAdapter(theme_review_adapter);

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
        }
        catch (Exception e)
        {
        }
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        super.onBackPressed();
    }

    public class Async_User extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;
        String str_nickname = "", str_pass = "", str_switch = "", str_review = "", str_time = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Theme_Focus_Review.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                if(User_Pk.equals(".")){
                    str_filter_review = "desc";
                }
                else{
                    //유저 정보 response
                    HttpClient http = new HttpClient();
                    String result = http.HttpClient("Web_Escape", "User_v4.jsp",params);

                    JsonParserList jsonParserList = new JsonParserList();
                    parseredData = jsonParserList.jsonParserList_Data14(result);
                    str_filter_review = parseredData[0][13];
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

            async = new Async();
            async.execute(str_theme_pk, User_Pk, str_filter_review);

            progressDialog.dismiss();
        }
    }
}

