package blackcap.nationalescape.Activity.tab4;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import blackcap.nationalescape.Adapter.Tab4_ReviewLog_Adapter;
import blackcap.nationalescape.Model.Review_Log_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Review_Search extends AppCompatActivity {
    private ImageView Img_Back;
    private RecyclerView List_Review, List_Review_Desc, List_Review_Review;
    private EditText Edit_Search;
    private ImageView Img_Search;
    private LinearLayout Layout_total;
    private TextView Txt_Total, Txt_Success, Txt_Nohint;
    private TextView Txt_Category1, Txt_Category2, Txt_Category3;
    private ArrayList<Review_Log_Model> review_models, review_models_desc, review_models_review;

    private Tab4_ReviewLog_Adapter reviewSearch_adapter;
    private Tab4_ReviewLog_Adapter reviewSearch_adapter_desc;
    private Tab4_ReviewLog_Adapter reviewSearch_adapter_review;
    String[][] parseredData, parseredData_desc, parseredData_review, parseredData_total;

    private Async async;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_reviewsearch);

        init();
        setImgBack();
        setCategory_Event();
    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Txt_Total = (TextView)findViewById(R.id.txt_total);
        Txt_Success = (TextView)findViewById(R.id.txt_success);
        Txt_Nohint = (TextView)findViewById(R.id.txt_nohint);

        List_Review = (RecyclerView)findViewById(R.id.list_review);
        List_Review.setNestedScrollingEnabled(false);
        List_Review_Desc = (RecyclerView)findViewById(R.id.list_review_desc);
        List_Review_Desc.setNestedScrollingEnabled(false);
        List_Review_Review = (RecyclerView)findViewById(R.id.list_review_review);
        List_Review_Review.setNestedScrollingEnabled(false);

        Edit_Search = (EditText)findViewById(R.id.edit_search);
        Img_Search = (ImageView)findViewById(R.id.img_search);
        Img_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                async = new Async();
                async.execute(Edit_Search.getText().toString(),"asc");
            }
        });
        Edit_Search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter키눌렀을떄 처리
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(Edit_Search.getText().toString().equals("")){
                        Toast.makeText(Review_Search.this,"검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        async = new Async();
                        async.execute(Edit_Search.getText().toString(),"asc");
                    }
                    return true;
                }
                return false;
            }
        });
        Layout_total = (LinearLayout)findViewById(R.id.layout_total);
        Txt_Category1 = (TextView)findViewById(R.id.txt_category1);
        Txt_Category2 = (TextView)findViewById(R.id.txt_category2);
        Txt_Category3 = (TextView)findViewById(R.id.txt_category3);

        Intent intent= getIntent();
        String nickname;
        if(intent.hasExtra("Nickname")){
            nickname = intent.getStringExtra("Nickname");
            Edit_Search.setText(nickname);
            Async async = new Async();
            async.execute(nickname,"asc");
        }
    }
    public void setCategory_Event(){
        Txt_Category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Async async = new Async();
//                async.execute(Edit_Search.getText().toString(), "asc");
                List_Review.setVisibility(View.VISIBLE);
                List_Review_Desc.setVisibility(View.GONE);
                List_Review_Review.setVisibility(View.GONE);
                Txt_Category1.setTextColor(getResources().getColor(R.color.black));
                Txt_Category2.setTextColor(getResources().getColor(R.color.date_gray));
                Txt_Category3.setTextColor(getResources().getColor(R.color.date_gray));
            }
        });
        Txt_Category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Async async = new Async();
//                async.execute(Edit_Search.getText().toString(), "desc");
                List_Review.setVisibility(View.GONE);
                List_Review_Desc.setVisibility(View.VISIBLE);
                List_Review_Review.setVisibility(View.GONE);
                Txt_Category1.setTextColor(getResources().getColor(R.color.date_gray));
                Txt_Category2.setTextColor(getResources().getColor(R.color.black));
                Txt_Category3.setTextColor(getResources().getColor(R.color.date_gray));
            }
        });
        Txt_Category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Async async = new Async();
//                async.execute(Edit_Search.getText().toString(), "review");
                List_Review.setVisibility(View.GONE);
                List_Review_Desc.setVisibility(View.GONE);
                List_Review_Review.setVisibility(View.VISIBLE);
                Txt_Category1.setTextColor(getResources().getColor(R.color.date_gray));
                Txt_Category2.setTextColor(getResources().getColor(R.color.date_gray));
                Txt_Category3.setTextColor(getResources().getColor(R.color.black));
            }
        });
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;
        String str_result = "";String str_result_total = "";
        String str_result_desc = "";
        String str_result_review = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Review_Search.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //공지사항 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                str_result = http.HttpClient("Web_Escape", "Review_Search.jsp", params);
                str_result_desc = http.HttpClient("Web_Escape", "Review_Search.jsp", Edit_Search.getText().toString(), "desc");
                str_result_review = http.HttpClient("Web_Escape", "Review_Search.jsp", Edit_Search.getText().toString(), "review");
                str_result_total = http.HttpClient("Web_Escape", "Review_Total.jsp", params);
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(str_result_total.contains("notexist")){
                Toast.makeText(Review_Search.this, "닉네임이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
            else if(str_result_total.contains("notview")){
                Toast.makeText(Review_Search.this, "리뷰 비공개 이용자입니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                JsonParserList jsonParserList = new JsonParserList();
                parseredData = jsonParserList.jsonParserList_Data11(str_result);
                parseredData_desc = jsonParserList.jsonParserList_Data11(str_result_desc);
                parseredData_review = jsonParserList.jsonParserList_Data11(str_result_review);
                parseredData_total = jsonParserList.jsonParserList_Data3(str_result_total);

                String str_total = parseredData_total[0][0];
                String str_success = parseredData_total[0][1];
                String str_nohint = parseredData_total[0][2];


                String success_per = String.format("%.2f", Double.parseDouble(str_success)*100 / Double.parseDouble(str_total));
                String nohint_per = String.format("%.2f", Double.parseDouble(str_nohint)*100 / Double.parseDouble(str_total));

                Txt_Total.setText("총 플레이 수 : "+str_total);
                Txt_Success.setText("성공 횟수(성공률) : "+str_success+"("+success_per+"%)");
                Txt_Nohint.setText("노힌트 횟수(노힌트 성공률) : "+ str_nohint+"("+nohint_per+"%)");

                Layout_total.setVisibility(View.VISIBLE);

                review_models = new ArrayList<Review_Log_Model>();
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
                    review_models.add(new Review_Log_Model(Review_Search.this, company_name, theme_name, grade, content, level, status, time, hint));
                }

                //리스트 뷰 세로 방향으로
                LinearLayoutManager layoutManager = new LinearLayoutManager(Review_Search.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                layoutManager.scrollToPosition(0);

                //베스트 무료체험 어댑터 셋팅
                reviewSearch_adapter = new Tab4_ReviewLog_Adapter(Review_Search.this, review_models);
                List_Review.setLayoutManager(layoutManager);
                List_Review.setAdapter(reviewSearch_adapter);

                review_models_desc = new ArrayList<Review_Log_Model>();
                for (int i = 0; i < parseredData_desc.length; i++) {
                    String grade = parseredData_desc[i][0];
                    String content = parseredData_desc[i][1];
                    String date = parseredData_desc[i][2];
                    String user_pk = parseredData_desc[i][3];
                    String level = parseredData_desc[i][4];
                    String status = parseredData_desc[i][5];
                    String time = parseredData_desc[i][6];
                    String hint = parseredData_desc[i][7];
                    String experience = parseredData_desc[i][8];
                    String company_name = parseredData_desc[i][9];
                    String theme_name = parseredData_desc[i][10];
                    review_models_desc.add(new Review_Log_Model(Review_Search.this, company_name, theme_name, grade, content, level, status, time, hint));
                }

                //리스트 뷰 세로 방향으로
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(Review_Search.this);
                layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                layoutManager1.scrollToPosition(0);

                //베스트 무료체험 어댑터 셋팅
                reviewSearch_adapter_desc = new Tab4_ReviewLog_Adapter(Review_Search.this, review_models_desc);
                List_Review_Desc.setLayoutManager(layoutManager1);
                List_Review_Desc.setAdapter(reviewSearch_adapter_desc);

                review_models_review = new ArrayList<Review_Log_Model>();
                for (int i = 0; i < parseredData_review.length; i++) {
                    String grade = parseredData_review[i][0];
                    String content = parseredData_review[i][1];
                    String date = parseredData_review[i][2];
                    String user_pk = parseredData_review[i][3];
                    String level = parseredData_review[i][4];
                    String status = parseredData_review[i][5];
                    String time = parseredData_review[i][6];
                    String hint = parseredData_review[i][7];
                    String experience = parseredData_review[i][8];
                    String company_name = parseredData_review[i][9];
                    String theme_name = parseredData_review[i][10];
                    review_models_review.add(new Review_Log_Model(Review_Search.this, company_name, theme_name, grade, content, level, status, time, hint));
                }

                //리스트 뷰 세로 방향으로
                LinearLayoutManager layoutManager2 = new LinearLayoutManager(Review_Search.this);
                layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                layoutManager2.scrollToPosition(0);

                //베스트 무료체험 어댑터 셋팅
                reviewSearch_adapter_review = new Tab4_ReviewLog_Adapter(Review_Search.this, review_models_review);
                List_Review_Review.setLayoutManager(layoutManager2);
                List_Review_Review.setAdapter(reviewSearch_adapter_review);
            }

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


