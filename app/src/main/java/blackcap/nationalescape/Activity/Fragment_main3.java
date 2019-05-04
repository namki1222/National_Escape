package blackcap.nationalescape.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import blackcap.nationalescape.Activity.tab3.Theme_Filter;
import blackcap.nationalescape.Adapter.Home_Adapter;
import blackcap.nationalescape.Adapter.Tab0_Theme_Adapter;
import blackcap.nationalescape.Adapter.Tab3_Theme_Adapter;
import blackcap.nationalescape.Model.Company_Model;
import blackcap.nationalescape.Model.Theme_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.GpsInfo;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

import static android.content.Context.MODE_PRIVATE;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;

public class Fragment_main3 extends android.support.v4.app.Fragment {
    private SharedPreferences preferences; //캐쉬 데이터 생성
    private SharedPreferences.Editor editor;

    public static boolean tab3_refresh = false;
    boolean view = false;
    ImageView Img_Search, Img_Search_Refresh, Img_Filter;
    EditText EditText_Search;

    private ArrayList<Theme_Model> theme_models;
    private Tab3_Theme_Adapter tab0_theme_adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView List_Theme;

    private String filter_category = "", filter_tools = "", filter_person = "", filter_level = "", filter_activity = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_theme, container, false);

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
        List_Theme = (RecyclerView)rootView.findViewById(R.id.list_theme);
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
                Intent intent = new Intent(getActivity(), Theme_Filter.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData_theme;

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

                preferences = getActivity().getSharedPreferences("escape", MODE_PRIVATE);
                filter_category = preferences.getString("filter_theme_category", "전체");
                filter_tools = preferences.getString("filter_theme_tools", "rockdeviceboth");
                filter_person = preferences.getString("filter_theme_person", "2345");
                filter_level = preferences.getString("filter_theme_level", "12345");
                filter_activity = preferences.getString("filter_theme_activity", "123");

                Log.i("필터1", filter_category);
                Log.i("필터2", filter_tools);
                Log.i("필터3", filter_person);
                Log.i("필터4", filter_level);
                Log.i("필터5", filter_activity);
                //홈 업체 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();

                String result_theme = http.HttpClient("Web_Escape", "Theme_List.jsp", filter_category, filter_tools, filter_person, filter_level, filter_activity);
                parseredData_theme = jsonParserList.jsonParserList_Data11(result_theme);

                Log.i("테스트3", parseredData_theme.length+"");
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
                    theme_models.add(new Theme_Model(getActivity(),theme_pk, company_pk, img, title, intro, category, grade, level, person, tool, activity));
                }
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("테스트3", e+"");
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//            LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
//            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
//            layoutManager1.scrollToPosition(0);
            //베스트 무료체험 어댑터 셋팅
            tab0_theme_adapter = new Tab3_Theme_Adapter(getActivity(), theme_models);
            List_Theme.setLayoutManager(layoutManager1);
            List_Theme.setAdapter(tab0_theme_adapter);

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
                //홈 업체 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();
                String result = http.HttpClient("Web_Escape", "Theme_List_Search.jsp", params);
                parseredData = jsonParserList.jsonParserList_Data11(result);

                theme_models = new ArrayList<Theme_Model>();
                for (int i = 0; i < parseredData.length; i++) {
                    String theme_pk = parseredData[i][0];
                    String company_pk = parseredData[i][1];
                    String img = parseredData[i][2];
                    String title = parseredData[i][3];
                    String intro = parseredData[i][4];
                    String category = parseredData[i][5];
                    String grade = parseredData[i][6];
                    String level = parseredData[i][7];
                    String person = parseredData[i][8];
                    String tool = parseredData[i][9];
                    String activity = parseredData[i][10];
                    theme_models.add(new Theme_Model(getActivity(),theme_pk, company_pk, img, title, intro, category, grade, level, person, tool, activity));
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

            StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            //베스트 무료체험 어댑터 셋팅
            tab0_theme_adapter = new Tab3_Theme_Adapter(getActivity(), theme_models);
            List_Theme.setLayoutManager(layoutManager1);
            List_Theme.setAdapter(tab0_theme_adapter);

            Img_Search_Refresh.setVisibility(View.VISIBLE);
            Img_Search.setVisibility(View.INVISIBLE);
            progressDialog.dismiss();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(view){
        }
        //뷰가 포커스 갔다 온 경우
        else{
            if(tab3_refresh == true){
                Async async = new Async();
                async.execute();
            }
            else{

            }
        }
        tab3_refresh = false;
        view = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        view = false;
    }
}
