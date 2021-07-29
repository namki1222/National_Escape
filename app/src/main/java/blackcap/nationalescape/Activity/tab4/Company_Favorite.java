package blackcap.nationalescape.Activity.tab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.Adapter.Favorite_Adapter;
import blackcap.nationalescape.Model.Favorite_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.GpsInfo;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import me.drakeet.materialdialog.MaterialDialog;

public class Company_Favorite extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private LinearLayout Layout_Notfavorite, Layout_Favorite;
    private ImageView img_login, Img_Back;

    public static ArrayList<Favorite_Model> favorite_models;
    public static Favorite_Adapter favorite_adpater;
    private RecyclerView List_Favorite;
    private LinearLayout Layout_Filter;
    private TextView Txt_Filter;

    private GpsInfo gps;
    private String gps_x = "", gps_y = "", filter = "";
    private Async tab3_async;
    private String User_Pk = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_company_favorite);

        init();
        setImgBack();
        //로그인 정보 있는 경우 없는 경우
        setLayout();
        //로그인 이벤트
        setLogin();
        //필터 셋팅
        setFilter();
    }
    public void init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");

        Img_Back = (ImageView)findViewById(R.id.img_back);
        Layout_Notfavorite = (LinearLayout)findViewById(R.id.layout_notfavorite);
        Layout_Favorite = (LinearLayout)findViewById(R.id.layout_favorite);

        img_login = (ImageView)findViewById(R.id.img_login);
        List_Favorite = (RecyclerView)findViewById(R.id.list_favorite);
        gps = new GpsInfo(Company_Favorite.this);

        Layout_Filter = (LinearLayout)findViewById(R.id.layout_filter);
        Txt_Filter = (TextView)findViewById(R.id.txt_filter);

        setData_Setting();
    }
    public void setData_Setting(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        filter = preferences.getString("favorite_filter_sort", "distance");

        if(filter.equals("distance")){
            Txt_Filter.setText("근거리 순");
        }
        else{
            Txt_Filter.setText("평점 순");
        }
    }
    public void setFilter(){
        Layout_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog_Filter();
            }
        });
    }
    public void setDialog_Filter(){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Company_Favorite.this.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_filter_sort, (ViewGroup)findViewById(R.id.Customdialog_Filter_Root));
        final TextView Txt_Distance  = (TextView) layout.findViewById(R.id.txt_distance);
        final TextView Txt_Grade  = (TextView) layout.findViewById(R.id.txt_grade);
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Company_Favorite.this);
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();
        Txt_Distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = preferences.edit();
                editor.putString("favorite_filter_sort", "distance");
                editor.commit();

                setData_Setting();
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_Grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = preferences.edit();
                editor.putString("favorite_filter_sort", "grade");
                editor.commit();

                setData_Setting();
                TeamInfo_Dialog.dismiss();
            }
        });
    }
    public void setLayout(){
        if(User_Pk.equals(".")){
            Layout_Notfavorite.setVisibility(View.VISIBLE);
            Layout_Favorite.setVisibility(View.GONE);
        }
        else{
            Layout_Favorite.setVisibility(View.VISIBLE);
            Layout_Notfavorite.setVisibility(View.GONE);

            tab3_async = new Async();
            tab3_async.execute();
        }
    }
    public void setLogin(){
        img_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Company_Favorite.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Company_Favorite.this,"","",true,true,null);
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
                    gps.showSettingsAlert();
                }
                Log.i("테스", User_Pk);
                Log.i("테스", gps_x);
                Log.i("테스", gps_y);
                Log.i("테스", filter);
                //찜한 업체 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                String result = http.HttpClient("Web_Escape", "Favority_List.jsp", gps_x, gps_y, User_Pk, filter);
                parseredData = jsonParserList(result);

                favorite_models = new ArrayList<Favorite_Model>();
                for (int i = 0; i < parseredData.length; i++) {
                    String company_pk = parseredData[i][0];
                    String owner_pk = parseredData[i][1];
                    String title = parseredData[i][2];
                    String intro = parseredData[i][3];
                    String grade_avg = parseredData[i][4];
                    String recommend_count = parseredData[i][5];
                    String address = parseredData[i][6];
                    String distance = parseredData[i][7];

                    favorite_models.add(new Favorite_Model(Company_Favorite.this,company_pk, owner_pk, title, intro, grade_avg, recommend_count, address, distance));
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

            gps.stopUsingGPS();
            //리스트 뷰 세로 방향으로
            LinearLayoutManager layoutManager = new LinearLayoutManager(Company_Favorite.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            favorite_adpater = new Favorite_Adapter(Company_Favorite.this, favorite_models);
            List_Favorite.setLayoutManager(layoutManager);
            List_Favorite.setAdapter(favorite_adpater);

            progressDialog.dismiss();
        }
        public String[][] jsonParserList(String pRecvServerPage) {
            Log.i("서버에서 받은 전체 내용", pRecvServerPage);
            try {
                JSONObject json = new JSONObject(pRecvServerPage);
                JSONArray jArr = json.getJSONArray("List");
                String[] jsonName = {"msg1", "msg2", "msg3","msg4", "msg5", "msg6","msg7", "msg8"};
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



