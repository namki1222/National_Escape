package blackcap.nationalescape.Activity.tab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import blackcap.nationalescape.Adapter.Tab4_ReviewLog_Search_Adapter;
import blackcap.nationalescape.Model.Review_Log_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Review_Search extends AppCompatActivity {
    SharedPreferences preferences;

    private ImageView Img_Back;
    private RecyclerView List_Review;
    public static EditText Review_Search_Edit_Search;
    private ImageView Img_Search;
    public static ArrayList<Review_Log_Model> review_models;

    public static Tab4_ReviewLog_Search_Adapter reviewSearch_adapter;

    String[][] parseredData, parseredData_total;

    private Async async;
    private String User_Pk = "", str_timeview= "";

    public static String str_allplay = "";
    public static String str_successplay = "";
    public static String str_nohintplay = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_reviewsearch);

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
        List_Review.setNestedScrollingEnabled(false);

        Review_Search_Edit_Search = (EditText)findViewById(R.id.edit_search);
        Img_Search = (ImageView)findViewById(R.id.img_search);
        Img_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                async = new Async();
                async.execute(Review_Search_Edit_Search.getText().toString(),"desc", User_Pk);
            }
        });
        Review_Search_Edit_Search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter??????????????? ??????
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(Review_Search_Edit_Search.getText().toString().equals("")){
                        Toast.makeText(Review_Search.this,"???????????? ??????????????????", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        async = new Async();
                        async.execute(Review_Search_Edit_Search.getText().toString(),"desc", User_Pk);
                    }
                    return true;
                }
                return false;
            }
        });

        Intent intent= getIntent();
        String nickname;
        if(intent.hasExtra("Nickname")){
            nickname = intent.getStringExtra("Nickname");
            Review_Search_Edit_Search.setText(nickname);
            Async async = new Async();
            async.execute(nickname,"desc", User_Pk);
        }
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
            //?????? ?????? ????????????
            try {
                //???????????? ????????? ????????? ??????
                HttpClient http = new HttpClient();
                str_result = http.HttpClient("Web_Escape", "Review_Search_v2.jsp", params);
               /* str_result_desc = http.HttpClient("Web_Escape", "Review_Search_v2.jsp", Edit_Search.getText().toString(), "desc", User_Pk);
                str_result_review = http.HttpClient("Web_Escape", "Review_Search_v2.jsp", Edit_Search.getText().toString(), "review", User_Pk);
               */
               str_result_total = http.HttpClient("Web_Escape", "Review_Total.jsp", params);
               Log.i("??????", str_result_total);
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
                Toast.makeText(Review_Search.this, "???????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
            }
            else if(str_result_total.contains("notview")){
                Toast.makeText(Review_Search.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            }
            else{
                JsonParserList jsonParserList = new JsonParserList();
                parseredData = jsonParserList.jsonParserList_Data15(str_result);
       /*         parseredData_desc = jsonParserList.jsonParserList_Data11(str_result_desc);
                parseredData_review = jsonParserList.jsonParserList_Data11(str_result_review);*/
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
               /* Txt_Total.setText("??? ????????? ??? : "+str_total);
                Txt_Success.setText("?????? ??????(?????????) : "+str_success+"("+success_per+"%)");
                Txt_Nohint.setText("????????? ??????(????????? ?????????) : "+ str_nohint+"("+nohint_per+"%)");*/

                //Layout_total.setVisibility(View.VISIBLE);

                str_allplay = "??? ????????? ??? : "+str_total;
                str_successplay = "?????? ??????(?????????) : "+str_success+"("+success_per+"%)";
                str_nohintplay = "????????? ??????(????????? ?????????) : "+ str_nohint+"("+nohint_per+"%)";

                review_models = new ArrayList<Review_Log_Model>();
                review_models.add(new Review_Log_Model(Review_Search.this, str_allplay, str_successplay, str_nohintplay,"*", "*", "*", "*", "*", str_timeview, "*", "*", "*","*","*"));
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
                    review_models.add(new Review_Log_Model(Review_Search.this, company_name, theme_name, grade, content, level, status, time, hint, str_timeview, theme_pk, str_timeview, deadtime, date, grade_flag));
                }

                //????????? ??? ?????? ????????????
                LinearLayoutManager layoutManager = new LinearLayoutManager(Review_Search.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                layoutManager.scrollToPosition(0);

                //????????? ???????????? ????????? ??????
                reviewSearch_adapter = new Tab4_ReviewLog_Search_Adapter(Review_Search.this, review_models);
                List_Review.setLayoutManager(layoutManager);
                List_Review.setAdapter(reviewSearch_adapter);

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


