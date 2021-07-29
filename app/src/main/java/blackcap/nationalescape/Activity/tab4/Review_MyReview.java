package blackcap.nationalescape.Activity.tab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import blackcap.nationalescape.Adapter.Tab4_ReviewLog_Adapter;
import blackcap.nationalescape.Adapter.Tab4_ReviewLog_Search_Adapter;
import blackcap.nationalescape.Model.Review_Log_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Review_MyReview extends AppCompatActivity {
    SharedPreferences preferences;
    boolean view = false;
    private ImageView Img_Back;
    private RecyclerView List_Review;
    private ImageView Img_Search;
    private LinearLayout Layout_total;
    public static ArrayList<Review_Log_Model> review_models;

    public static Tab4_ReviewLog_Adapter reviewSearch_adapter;
    String[][] parseredData, parseredData_desc, parseredData_review, parseredData_total;

    public static String str_allplay = "";
    public static String str_successplay = "";
    public static String str_nohintplay = "";

    private String User_Pk = "", str_timeview= "";

    private Async async;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_myreview);

        init();
        setImgBack();
    }
    public void init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");

        Intent intent1 = getIntent();
        str_timeview = intent1.getStringExtra("timeview");

        Img_Back = (ImageView)findViewById(R.id.img_back);
        List_Review = (RecyclerView)findViewById(R.id.list_review);

        async = new Async();
        async.execute(User_Pk, "ascup");

        Layout_total = (LinearLayout)findViewById(R.id.layout_total);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(view){
        }
        //뷰가 포커스 갔다 온 경우
        else{
            async = new Async();
            async.execute(User_Pk, "ascup");
        }
        view = true;
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData, parseredData_total;
        String str_result = "";String str_result_total = "";
        String str_result_desc = "";
        String str_result_review = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Review_MyReview.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //공지사항 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                str_result = http.HttpClient("Web_Escape", "MyReview_Search_v3.jsp", User_Pk, "dateup");


                str_result_total = http.HttpClient("Web_Escape", "MyReview_Total.jsp", params);
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            JsonParserList jsonParserList = new JsonParserList();
            parseredData = jsonParserList.jsonParserList_Data15(str_result);

            parseredData_total = jsonParserList.jsonParserList_Data3(str_result_total);

            String str_total = parseredData_total[0][0];
            String str_success = parseredData_total[0][1];
            String str_nohint = parseredData_total[0][2];

            String success_per = String.format("%.2f", Double.parseDouble(str_success)*100 / Double.parseDouble(str_total));
            String nohint_per = String.format("%.2f", Double.parseDouble(str_nohint)*100 / Double.parseDouble(str_total));
            if(success_per.equals("NaN")){
                success_per = "0";
            }
            if(nohint_per.equals("NaN")){
                nohint_per = "0";
            }
            str_allplay = "총 플레이 수 : "+str_total;
            str_successplay = "성공 횟수(성공률) : "+str_success+"("+success_per+"%)";
            str_nohintplay = "노힌트 횟수(노힌트 성공률) : "+ str_nohint+"("+nohint_per+"%)";

            //Layout_total.setVisibility(View.VISIBLE);
            review_models = new ArrayList<Review_Log_Model>();
            review_models.add(new Review_Log_Model(Review_MyReview.this, str_allplay, str_successplay, str_nohintplay,"*", "*", "*", "*", "*", str_timeview, "*", "*", "*", "*", "*"));
            for (int i = 0; i < parseredData.length; i++) {
                String grade = parseredData[i][0];
                String content = parseredData[i][1];
                String date = parseredData[i][2];
                String user_pk = parseredData[i][3];
                String level = parseredData[i][4];
                String status = parseredData[i][5];
                String time = parseredData[i][6];
                String hint = parseredData[i][7];
                String experience = parseredData[i][8];
                String company_name = parseredData[i][9];
                String theme_name = parseredData[i][10];
                String deadtime = parseredData[i][11];
                String theme_pk = parseredData[i][12];
                String grade_flag = parseredData[i][14];
                review_models.add(new Review_Log_Model(Review_MyReview.this, company_name, theme_name, grade, content, level, status, time, hint, str_timeview, theme_pk, str_timeview, deadtime, date, grade_flag));
            }
            //리스트 뷰 세로 방향으로
            LinearLayoutManager layoutManager = new LinearLayoutManager(Review_MyReview.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            reviewSearch_adapter = new Tab4_ReviewLog_Adapter(Review_MyReview.this, review_models);
            List_Review.setLayoutManager(layoutManager);
            List_Review.setAdapter(reviewSearch_adapter);

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


