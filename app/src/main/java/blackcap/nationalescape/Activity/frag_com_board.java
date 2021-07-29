package blackcap.nationalescape.Activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blackcap.nationalescape.Adapter.Com_Board_Adapter;
import blackcap.nationalescape.Adapter.Com_Free_Adapter;
import blackcap.nationalescape.Model.Com_Board_Model;
import blackcap.nationalescape.Model.Com_Comment_Model;
import blackcap.nationalescape.Model.Com_Free_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.Http_Get;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

import static blackcap.nationalescape.Activity.Fragmetn_community.str_status;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;

public class frag_com_board extends Fragment {
    private SharedPreferences preferences; //캐쉬 데이터 생성
    private SharedPreferences.Editor editor;

    boolean view = false;
    public static boolean tab1_refresh = false;
    ImageView Img_Search, Img_Search_Refresh, Img_Filter;
    EditText EditText_Search;

    public static ArrayList<Com_Board_Model> com_free_models;
    public static Com_Board_Adapter com_free_adapters;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView List_Free;
    public static String str_search = "";
    public static int page = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.frag_com_board, container, false);

        //변수 초기화
        init(rootView);

        return rootView;
    }

    public void init(View rootView){
        List_Free = (RecyclerView)rootView.findViewById(R.id.list_free);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Async async = new Async();
                async.execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        //서버 값 리스트 뷰 전달 및 뷰
        Async async = new Async();
        async.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(str_status.equals("board_re")){
            Async async = new Async();
            async.execute();
        }
        str_status = "board";

        Log.i("테스트12", "테스트2");
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
                //홈 업체 리스트 데이터 셋팅
                Http_Get http = new Http_Get();
                String result = http.Http_Get(User_Pk, "v1/board/list?diff=보드판%20갤러리&sort=최신순");
                parseredData = jsonParserList(result);

                com_free_models = new ArrayList<Com_Board_Model>();
                com_free_models.add(new Com_Board_Model(getActivity(),"", "", "", "", "", "", "", "", "", "","","","",""));
                com_free_models.add(new Com_Board_Model(getActivity(),"", "", "", "", "", "", "", "", "", "","","","",""));
                for (int i = 0; i < parseredData.length; i++) {
                    String id = parseredData[i][0];
                    String user_pk = parseredData[i][1];
                    String nickname = parseredData[i][2];
                    String grade = parseredData[i][3];
                    String title = parseredData[i][4];
                    String category = parseredData[i][5];
                    String commentCount = parseredData[i][6];
                    String isLike = parseredData[i][7];
                    String diff = parseredData[i][8];
                    String createdAt = parseredData[i][9];
                    String thumbnail = parseredData[i][10];
                    String company_name = parseredData[i][11];
                    String theme_name = parseredData[i][12];
                    String like_count = parseredData[i][13];

                    com_free_models.add(new Com_Board_Model(getActivity(),id, user_pk, nickname, grade, title, category, commentCount, isLike, diff, createdAt, thumbnail, company_name, theme_name, like_count));

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

            //StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == 0 || position == 1)
                        return 4;
                    else
                        return 2;
                }
            });
            if(getContext() != null){
                //베스트 무료체험 어댑터 셋팅
                com_free_adapters = new Com_Board_Adapter(getActivity(), com_free_models);
                List_Free.setLayoutManager(layoutManager);
                List_Free.setAdapter(com_free_adapters);
            }


            progressDialog.dismiss();
        }
    }
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("list");
            String[] jsonName = {"id", "user_pk", "nickname","grade", "title", "category","commentCount", "isLike", "diff", "createdAt", "thumbnail", "company_name", "theme_name", "likeCount"};
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


