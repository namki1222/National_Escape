package blackcap.nationalescape.Activity.tab4;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.tab3.Theme_Focus;
import blackcap.nationalescape.Adapter.Tab3_Theme_Adapter;
import blackcap.nationalescape.Model.Theme_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.GpsInfo;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Theme_Favority extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private LinearLayout Layout_Notfavorite, Layout_Favorite;
    private ImageView img_login, Img_Back;

    private ArrayList<Theme_Model> theme_models;
    private Tab3_Theme_Adapter tab0_theme_adapter;
    private RecyclerView List_Favorite;
    private GpsInfo gps;
    private String User_Pk = "";  String gps_x = "", gps_y = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_theme_favorite);

        init();
        setImgBack();

        Async async = new Async();
        async.execute();
    }
    public void init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");

        Img_Back = (ImageView)findViewById(R.id.img_back);
        Layout_Notfavorite = (LinearLayout)findViewById(R.id.layout_notfavorite);
        Layout_Favorite = (LinearLayout)findViewById(R.id.layout_favorite);

        img_login = (ImageView)findViewById(R.id.img_login);
        List_Favorite = (RecyclerView)findViewById(R.id.list_favorite);

        gps = new GpsInfo(Theme_Favority.this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Async async = new Async();
        async.execute();
    }

    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData_theme;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Theme_Favority.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                if (gps.isGetLocation()) {
                    gps_x = Double.toString(gps.getLatitude());
                    gps_y = Double.toString(gps.getLongitude());
                } else {
                    // GPS 를 사용할수 없으므로
                    //gps.showSettingsAlert();
                    gps_x = 37.497942+"";
                    gps_y = 127.0254323+"";
                }

                //홈 업체 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();

                String result_theme = http.HttpClient("Web_Escape", "Theme_Favority_List_v2.jsp", User_Pk, gps_x, gps_y);
                parseredData_theme = jsonParserList.jsonParserList_Data12(result_theme);

                theme_models = new ArrayList<Theme_Model>();
                for (int i = 0; i < parseredData_theme.length; i++) {
                    String theme_pk = parseredData_theme[i][0];
                    String company_pk = parseredData_theme[i][1];
                    String img = parseredData_theme[i][2];
                    String title = parseredData_theme[i][3];
                    String intro = parseredData_theme[i][4];
                    String category = parseredData_theme[i][5];
                    String grade = parseredData_theme[i][6];
                    String level = parseredData_theme[i][7];
                    String person = parseredData_theme[i][8];
                    String tool = parseredData_theme[i][9];
                    String activity = parseredData_theme[i][10];
                    String deadtime = parseredData_theme[i][11];
                    theme_models.add(new Theme_Model(Theme_Favority.this,theme_pk, company_pk, img, title, intro, category, grade, level, person, tool, activity, deadtime));
                }
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("테스트", e+"");
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

            tab0_theme_adapter = new Tab3_Theme_Adapter(Theme_Favority.this, theme_models);
            List_Favorite.setLayoutManager(layoutManager1);
            List_Favorite.setAdapter(tab0_theme_adapter);

            progressDialog.dismiss();
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




