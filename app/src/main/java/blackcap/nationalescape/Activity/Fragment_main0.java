package blackcap.nationalescape.Activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import blackcap.nationalescape.Activity.tab0.Fragment_event;
import blackcap.nationalescape.Adapter.Tab0_Company_Adapter;
import blackcap.nationalescape.Adapter.Tab0_Theme_Adapter;
import blackcap.nationalescape.Model.Company_Model;
import blackcap.nationalescape.Model.Main_Event_Model;
import blackcap.nationalescape.Model.Theme_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.GpsInfo;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.PaddingItemDecoration;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import blackcap.nationalescape.Uitility.ViewPager_Wrap;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;

public class Fragment_main0 extends android.support.v4.app.Fragment {
    private SharedPreferences preferences; //캐쉬 데이터 생성
    private SharedPreferences.Editor editor;

    private GpsInfo gps;
    String gps_x = "", gps_y = "";

    boolean view = false;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager_Wrap ViewPager_Event;
    private DotIndicator Indicator_Event;
    private RecyclerView List_Company;
    private RecyclerView List_Theme;

    public static TimerTask main_event_myTask;
    public static Timer main_event_timer;

    private ArrayList<Company_Model> company_models;
    private Tab0_Company_Adapter tab0_company_adapter;

    private ArrayList<Theme_Model> theme_models;
    private Tab0_Theme_Adapter tab0_theme_adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main0, container, false);

        //변수 초기화
        init(rootView);
        //서버 값 리스트 뷰 전달 및 뷰
        Async async = new Async();
        async.execute();

        return rootView;
    }

    public void init(View rootView){
        Indicator_Event = (DotIndicator)rootView.findViewById( R.id.dot_event);
        ViewPager_Event = (ViewPager_Wrap)rootView.findViewById(R.id.viewpage_event);
        List_Company = (RecyclerView)rootView.findViewById(R.id.list_company);
        List_Theme = (RecyclerView)rootView.findViewById(R.id.list_theme);
        gps = new GpsInfo(getActivity());
    }
    public void setViewPager(String[][] data) {
        //프래그먼트 정의

        //프래그먼트 정의
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager() ,data);
        // 도트 색 지정
        Indicator_Event.setSelectedDotColor( Color.parseColor( "#f6b400" ) );
        Indicator_Event.setUnselectedDotColor( Color.parseColor( "#e4e4e4" ) );

        // Set up the ViewPager with the sections adapter.

        ViewPager_Event.setAdapter(mSectionsPagerAdapter);
        ViewPager_Event.setOffscreenPageLimit(3);

        final int pageCount = data.length;
        Indicator_Event.setNumberOfItems(pageCount);

        main_event_myTask = new TimerTask() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int currentPage = ViewPager_Event.getCurrentItem();
                        if( currentPage >= pageCount - 1 ) ViewPager_Event.setCurrentItem( 0, true );
                        else ViewPager_Event.setCurrentItem( currentPage + 1, true );
                        Indicator_Event.setSelectedItem( ( currentPage + 1 == pageCount ) ? 0 : currentPage + 1, true );
                    }
                });
            }
        };
        main_event_timer = new Timer();
        //timer.schedule(myTask, 5000);  // 5초후 실행하고 종료
        main_event_timer.schedule(main_event_myTask, 500, 5000); // 5초후 첫실행, 3초마다 계속실행
        ViewPager_Event.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Indicator_Event.setSelectedItem( ViewPager_Event.getCurrentItem(), true );
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        String[][] data;

        public SectionsPagerAdapter(FragmentManager fm, String[][] data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = new Fragment_event();
            Bundle bundle = new Bundle();

            //이미지 URL 동적 전송 ex) 1_1
            String Image_txt = data[position][1];
            String category = data[position][2];
            String contents = data[position][3];
            bundle.putString("Image", Image_txt);
            bundle.putString("category", category);
            bundle.putString("contents", contents);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.

            return data.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData, parseredData_company, parseredData_theme;

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
                if (gps.isGetLocation()) {
                    gps_x = Double.toString(gps.getLatitude());
                    gps_y = Double.toString(gps.getLongitude());
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }

                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();

                //이벤트 데이터 셋팅
                String result = http.HttpClient("Web_Escape", "Main_Event.jsp",params);
                parseredData = jsonParserList.jsonParserList_Data4(result);

                //회사 데이터 셋팅
                String result_company = http.HttpClient("Web_Escape", "Main_Company.jsp",gps_x, gps_y, User_Pk);
                parseredData_company = jsonParserList.jsonParserList_Data11(result_company);

                //테마 데이터 셋팅
                String result_theme = http.HttpClient("Web_Escape", "Main_Theme.jsp");
                parseredData_theme = jsonParserList.jsonParserList_Data11(result_theme);

                company_models = new ArrayList<Company_Model>();
                for (int i = 0; i < parseredData_company.length; i++) {
                    String company_pk = parseredData_company[i][0];
                    String owner_pk = parseredData_company[i][1];
                    String title = parseredData_company[i][2];
                    String intro = parseredData_company[i][3];
                    String grade_avg = parseredData_company[i][4];
                    String recommend_count = parseredData_company[i][5];
                    String address = parseredData_company[i][6];
                    String distance = parseredData_company[i][7];
                    String favorite = parseredData_company[i][8];
                    company_models.add(new Company_Model(getActivity(),company_pk, owner_pk, title, intro, grade_avg, recommend_count, address, distance, favorite));
                }

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
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //이벤트 뷰페이지 셋팅
            setViewPager(parseredData);

            //회사 리스트 셋팅
            gps.stopUsingGPS();
            //리스트 뷰 세로 방향으로
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            layoutManager.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            tab0_company_adapter = new Tab0_Company_Adapter(getActivity(), company_models);
            List_Company.setLayoutManager(layoutManager);
            List_Company.setAdapter(tab0_company_adapter);

            //리스트 뷰 세로 방향으로
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
            layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            layoutManager1.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            tab0_theme_adapter = new Tab0_Theme_Adapter(getActivity(), theme_models);
            List_Theme.setLayoutManager(layoutManager1);
            List_Theme.setAdapter(tab0_theme_adapter);

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

        }

        view = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        view = false;
    }
}



