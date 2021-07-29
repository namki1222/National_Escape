package blackcap.nationalescape.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.tab1.Home_Filter;
import blackcap.nationalescape.Adapter.Home_Adapter;
import blackcap.nationalescape.Model.Company_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

import static android.content.Context.MODE_PRIVATE;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;
import static blackcap.nationalescape.Activity.MainActivity.act_main;
import static blackcap.nationalescape.Activity.MainActivity.gps_main;

public class Fragmetn_community extends Fragment {
    private SharedPreferences preferences; //캐쉬 데이터 생성
    private ViewPagerAdapter mSectionsPagerAdapter;
    private ViewPager2 mViewPager;
    private HorizontalScrollView Scroll;

    LinearLayout Layout_Tab1, Layout_Tab2, Layout_Tab3, Layout_Tab4;
    TextView Txt_Tab1, Txt_Tab2, Txt_Tab3, Txt_Tab4;

    public static String str_status = "free";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_community, container, false);

        //변수 초기화
        init(rootView);

        return rootView;
    }

    public void init(View rootView){
        Scroll = (HorizontalScrollView)rootView.findViewById(R.id.scroll);
        Scroll.setHorizontalScrollBarEnabled(false); // 세로 스크롤bar 삭제

        Layout_Tab1 = (LinearLayout)rootView.findViewById(R.id.layout_tab1);
        Layout_Tab2 = (LinearLayout)rootView.findViewById(R.id.layout_tab2);
        Layout_Tab3 = (LinearLayout)rootView.findViewById(R.id.layout_tab3);
        Layout_Tab4 = (LinearLayout)rootView.findViewById(R.id.layout_tab4);
        Txt_Tab1 = (TextView) rootView.findViewById(R.id.txt_tab1_line);
        Txt_Tab2 = (TextView) rootView.findViewById(R.id.txt_tab2_line);
        Txt_Tab3 = (TextView) rootView.findViewById(R.id.txt_tab3_line);
        Txt_Tab4 = (TextView) rootView.findViewById(R.id.txt_tab4_line);

        setTabClick_Event();
        //프래그먼트 정의
        mSectionsPagerAdapter = new ViewPagerAdapter(getActivity());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager2) rootView.findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setUserInputEnabled(false);
        mViewPager.setOffscreenPageLimit(1);
        ////////////

    }
    public void setTabClick_Event(){
        Layout_Tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Txt_Tab1.setVisibility(View.VISIBLE);
                Txt_Tab2.setVisibility(View.INVISIBLE);
                Txt_Tab3.setVisibility(View.INVISIBLE);
                Txt_Tab4.setVisibility(View.INVISIBLE);

                mViewPager.setCurrentItem(0);
            }
        });
        Layout_Tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Txt_Tab1.setVisibility(View.INVISIBLE);
                Txt_Tab2.setVisibility(View.VISIBLE);
                Txt_Tab3.setVisibility(View.INVISIBLE);
                Txt_Tab4.setVisibility(View.INVISIBLE);

                mViewPager.setCurrentItem(1);
            }
        });
        Layout_Tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Txt_Tab1.setVisibility(View.INVISIBLE);
                Txt_Tab2.setVisibility(View.INVISIBLE);
                Txt_Tab3.setVisibility(View.VISIBLE);
                Txt_Tab4.setVisibility(View.INVISIBLE);

                mViewPager.setCurrentItem(2);
            }
        });
        Layout_Tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Txt_Tab1.setVisibility(View.INVISIBLE);
                Txt_Tab2.setVisibility(View.INVISIBLE);
                Txt_Tab3.setVisibility(View.INVISIBLE);
                Txt_Tab4.setVisibility(View.VISIBLE);

                mViewPager.setCurrentItem(3);
            }
        });
    }
    private class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new frag_com_free();
                case 1:
                    return new frag_com_board();
                case 2:
                    return new frag_com_join();
                case 3:
                    return new frag_com_info();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

}


