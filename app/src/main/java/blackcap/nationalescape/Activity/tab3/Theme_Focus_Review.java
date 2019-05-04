package blackcap.nationalescape.Activity.tab3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import blackcap.nationalescape.Adapter.Theme_Focus_Review_Adapter;
import blackcap.nationalescape.Model.Theme_Review_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Theme_Focus_Review extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성
    private String User_Pk = "" ,str_theme_pk = "";

    private ImageView Img_Back;

    private RecyclerView List_Review;
    private ArrayList<Theme_Review_Model> theme_review_models;
    private Theme_Focus_Review_Adapter theme_review_adapter;
    private int line_count = 0;

    private Async async;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3_focus_review);

        init();
        setImgBack();

        async = new Async();
        async.execute(str_theme_pk, User_Pk);
    }
    public void init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");

        Intent intent1 = getIntent();
        str_theme_pk = intent1.getStringExtra("Theme_Pk");

        Img_Back = (ImageView)findViewById(R.id.img_back);
        List_Review = (RecyclerView)findViewById(R.id.list_review);
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
                String result = http.HttpClient("Web_Escape", "Theme_Focus_Review_v2.jsp",params);
                parseredData_reivew = jsonParserList.jsonParserList_Data11(result);

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
                    String line = parseredData_reivew[i][10];
                    theme_review_models.add(new Theme_Review_Model(Theme_Focus_Review.this, User_Pk, review_user_pk, nickname, grage, content, date, str_theme_pk, level, status, time, hint, experience, line));
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
            List_Review.setLayoutManager(layoutManager1);
            List_Review.setAdapter(theme_review_adapter);

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
}

