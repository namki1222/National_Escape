package blackcap.nationalescape.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.tab1.Home_Filter;
import blackcap.nationalescape.Adapter.Home_Adapter;
import blackcap.nationalescape.Model.Company_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.GpsInfo;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

import static android.content.Context.MODE_PRIVATE;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;
import static blackcap.nationalescape.Activity.MainActivity.gps_main;

public class Fragment_main1 extends android.support.v4.app.Fragment {
    private SharedPreferences preferences; //캐쉬 데이터 생성
    private SharedPreferences.Editor editor;

    boolean view = false;
    public static boolean tab1_refresh = false;
    ImageView Img_Search, Img_Search_Refresh, Img_Filter;
    EditText EditText_Search;

    public static ArrayList<Company_Model> company_models;
    public static Home_Adapter home_adpater;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView List_Home;


    String gps_x = "", gps_y = "";

    private String filter_area = "", filter_sort = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main1, container, false);

        //변수 초기화
        init(rootView);
        //서버 값 리스트 뷰 전달 및 뷰
        Async async = new Async();
        async.execute();

        //타이틀 검색 셋팅
        setSearch();
        //타이틀 검색 리프레시
        setSearch_ReFresh();
        //필터 적용 셋팅
        setFilter();

        return rootView;
    }

    public void init(View rootView){
        Img_Search = (ImageView)rootView.findViewById(R.id.img_search);
        Img_Search_Refresh = (ImageView)rootView.findViewById(R.id.img_search_refresh);
        Img_Filter = (ImageView)rootView.findViewById(R.id.img_filter);
        List_Home = (RecyclerView)rootView.findViewById(R.id.list_home);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Async async = new Async();
                async.execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        EditText_Search = (EditText)rootView.findViewById(R.id.edit_search);
        EditText_Search.setMaxLines(1);

    }
    public void setSearch(){
        Img_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditText_Search.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Async_Search async_search = new Async_Search();
                    async_search.execute(EditText_Search.getText().toString(), User_Pk);
                }
            }
        });
        EditText_Search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter키눌렀을떄 처리
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(EditText_Search.getText().toString().equals("")){
                        Toast.makeText(getActivity(),"검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Async_Search async_search = new Async_Search();
                        async_search.execute(EditText_Search.getText().toString(), User_Pk);
                    }
                    return true;
                }
                return false;
            }
        });
    }
    public void setSearch_ReFresh(){
        Img_Search_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async async = new Async();
                async.execute();
                Img_Search_Refresh.setVisibility(View.GONE);
                Img_Search.setVisibility(View.VISIBLE);
            }
        });
    }
    public void setFilter(){
        Img_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Home_Filter.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(getActivity(),"","",true,true,null);
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

                preferences = getActivity().getSharedPreferences("escape", MODE_PRIVATE);
                filter_area = preferences.getString("filter_area", "전국");
                filter_sort = preferences.getString("filter_sort", "distance");

                //홈 업체 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                String result = http.HttpClient("Web_Escape", "Home_List.jsp", gps_x, gps_y, User_Pk, filter_area, filter_sort);
                parseredData = jsonParserList(result);

                company_models = new ArrayList<Company_Model>();
                for (int i = 0; i < parseredData.length; i++) {
                    String company_pk = parseredData[i][0];
                    String owner_pk = parseredData[i][1];
                    String title = parseredData[i][2];
                    String intro = parseredData[i][3];
                    String grade_avg = parseredData[i][4];
                    String recommend_count = parseredData[i][5];
                    String address = parseredData[i][6];
                    String distance = parseredData[i][7];
                    String favorite = parseredData[i][8];
                    company_models.add(new Company_Model(getActivity(),company_pk, owner_pk, title, intro, grade_avg, recommend_count, address, distance, favorite));
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
            //리스트 뷰 세로 방향으로
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            home_adpater = new Home_Adapter(getActivity(), company_models);
            List_Home.setLayoutManager(layoutManager);
            List_Home.setAdapter(home_adpater);

            progressDialog.dismiss();
        }
    }
    public class Async_Search extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(getActivity(),"","",true,true,null);
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

                //홈 업체 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                String result = http.HttpClient("Web_Escape", "Home_List_Search.jsp", gps_x, gps_y, params[0], params[1]);
                parseredData = jsonParserList(result);

                company_models = new ArrayList<Company_Model>();
                for (int i = 0; i < parseredData.length; i++) {
                    String company_pk = parseredData[i][0];
                    String owner_pk = parseredData[i][1];
                    String title = parseredData[i][2];
                    String intro = parseredData[i][3];
                    String grade_avg = parseredData[i][4];
                    String recommend_count = parseredData[i][5];
                    String address = parseredData[i][6];
                    String distance = parseredData[i][7];
                    String favorite = parseredData[i][8];
                    company_models.add(new Company_Model(getActivity(),company_pk, owner_pk, title, intro, grade_avg, recommend_count, address, distance, favorite));
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
            //리스트 뷰 세로 방향으로
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            home_adpater = new Home_Adapter(getActivity(), company_models);
            List_Home.setLayoutManager(layoutManager);
            List_Home.setAdapter(home_adpater);

            Img_Search_Refresh.setVisibility(View.VISIBLE);
            Img_Search.setVisibility(View.INVISIBLE);
            progressDialog.dismiss();
        }
    }
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3","msg4", "msg5", "msg6","msg7", "msg8", "msg9"};
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
    @Override
    public void onResume() {
        super.onResume();
        if(view){

        }
        //뷰가 포커스 갔다 온 경우
        else{
            if(tab1_refresh == true){
                Async async = new Async();
                async.execute();
            }
            else{

            }
        }
        tab1_refresh = false;
        view = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        view = false;
    }
}


