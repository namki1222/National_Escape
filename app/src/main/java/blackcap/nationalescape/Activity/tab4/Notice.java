package blackcap.nationalescape.Activity.tab4;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blackcap.nationalescape.Adapter.Notice_Adapter;
import blackcap.nationalescape.Model.Notice_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Notice extends AppCompatActivity {
    private ImageView Img_Back;
    private RecyclerView List_Notice;

    private ArrayList<Notice_Model> notice_models;
    private Notice_Adapter notice_adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_notice);

        init();
        setImgBack();

        Async async = new Async();
        async.execute();
    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        List_Notice = (RecyclerView)findViewById(R.id.list_notice);
    }

    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Notice.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //공지사항 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                String result = http.HttpClient("Web_Escape", "Notice_List.jsp");
                parseredData = jsonParserList(result);

                notice_models = new ArrayList<Notice_Model>();
                for (int i = 0; i < parseredData.length; i++) {
                    String notice_pk = parseredData[i][0];
                    String title = parseredData[i][1];
                    String contents = parseredData[i][2];
                    String date = parseredData[i][3];
                    notice_models.add(new Notice_Model(Notice.this,notice_pk, title, contents, date));
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

            //리스트 뷰 세로 방향으로
            LinearLayoutManager layoutManager = new LinearLayoutManager(Notice.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            notice_adpater = new Notice_Adapter(Notice.this, notice_models);
            List_Notice.setLayoutManager(layoutManager);
            List_Notice.setAdapter(notice_adpater);

            progressDialog.dismiss();
        }
    }
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3","msg4"};
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


