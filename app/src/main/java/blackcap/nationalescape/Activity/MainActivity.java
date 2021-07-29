package blackcap.nationalescape.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.GpsInfo;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.SwipeViewPager;

import static blackcap.nationalescape.Activity.Fragment_main0.main_event_timer;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성
    private ViewPagerAdapter mSectionsPagerAdapter;
    public static ViewPager2 mViewPager;
    private GestureDetector gd;

    LinearLayout Layout_Tab1, Layout_Tab2, Layout_Tab3, Layout_Tab4, Layout_Tab5;
    public static ImageView Img_Tab1, Img_Tab2, Img_Tab3, Img_Tab4, Img_Tab5;
    public static TextView Txt_Tab1, Txt_Tab2, Txt_Tab3, Txt_Tab4, Txt_Tab5;

    public static String User_Pk = "";
    public static String filter_area = "", filter_sort = "";
    public static Activity act_main;

    Fragment main1, main2, main3, main4, main5;

    public static GpsInfo gps_main;

    private AdView mAdView;
    public static InterstitialAd mInterstitialAd;

    public static boolean bol_main = false;

    public static String comuview = "main";

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");
        filter_area = preferences.getString("filter_area", "전국");
        filter_sort = preferences.getString("filter_sort", "distance");

        if (shouldAskPermissions()) {
            askPermissions();
        }

        //셋팅 초기값
        init();
        //탭 클릭 이벤트
        setTabEvent();
        //뷰페이저 셋팅
        setViewPager();
        //키 해시
        setKeyHash();
        //토큰 설정
        setToken();
    }

    @Override
    protected void onDestroy() {
        bol_main = true;
        super.onDestroy();
    }

    public void setToken(){
        if(!User_Pk.equals(".")){
            String Token = FirebaseInstanceId.getInstance().getToken();
            HttpClient http_addtoken = new HttpClient();
            http_addtoken.HttpClient("Web_Escape", "User_Fcmadd.jsp", User_Pk, Token);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void setAuthority(){

    }
    public void init(){
        act_main = MainActivity.this;
        gps_main = new GpsInfo(this);
        Layout_Tab1 = (LinearLayout)findViewById(R.id.layout_tab1);
        Layout_Tab2 = (LinearLayout)findViewById(R.id.layout_tab2);
        Layout_Tab3 = (LinearLayout)findViewById(R.id.layout_tab3);
        Layout_Tab4 = (LinearLayout)findViewById(R.id.layout_tab4);
        Layout_Tab5 = (LinearLayout)findViewById(R.id.layout_tab5);
        Img_Tab1 = (ImageView) findViewById(R.id.img_tab1);
        Img_Tab2 = (ImageView) findViewById(R.id.img_tab2);
        Img_Tab3 = (ImageView) findViewById(R.id.img_tab3);
        Img_Tab4 = (ImageView) findViewById(R.id.img_tab4);
        Img_Tab5 = (ImageView) findViewById(R.id.img_tab5);
        Txt_Tab1 = (TextView) findViewById(R.id.txt_tab1);
        Txt_Tab2 = (TextView) findViewById(R.id.txt_tab2);
        Txt_Tab3 = (TextView) findViewById(R.id.txt_tab3);
        Txt_Tab4 = (TextView) findViewById(R.id.txt_tab4);
        Txt_Tab5 = (TextView) findViewById(R.id.txt_tab5);

        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.popup_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                // 광고가 문제 없이 로드시 출력됩니다.
                Log.d("@@@", "onAdLoaded");
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                // 광고 로드에 문제가 있을시 출력됩니다.
                Log.d("@@@", "onAdFailedToLoad " + errorCode);
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.d("@@@", "test");
                mAdView.setVisibility(View.GONE);
            }
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d("@@@", "test1");
                mAdView.setVisibility(View.GONE);
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }
            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

    }
    private void setViewPager(){
        //프래그먼트 정의
        mSectionsPagerAdapter = new ViewPagerAdapter(MainActivity.this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager2) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setUserInputEnabled(false);
        mViewPager.setOffscreenPageLimit(1);
        //Log.i("xxxx", mViewPager.get)
        //mViewPager.setOffscreenPageLimit(5);
        ////////////
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
                    return new Fragment_main0();
                case 1:
                    return new Fragment_main1();
                case 2:
                    return new Fragment_main3();
                case 3:
                    return new Fragmetn_community();
                case 4:
                    return new Fragment_main4();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    //프래그 먼트 생성
                    main1 = new Fragment_main0();
                    Bundle bundle = new Bundle();
                    return main1;
                }
                case 1: {
                    //프래그 먼트 생성
                    main2 = new Fragment_main1();
                    Bundle bundle = new Bundle();
                    return main2;
                }
                case 2: {
                    //프래그 먼트 생성
                    main3 = new Fragment_main3();
                    Bundle bundle = new Bundle();
                    return main3;
                }
                case 3: {
                    //프래그 먼트 생성
                    main4 = new Fragment_main2();
                    Bundle bundle = new Bundle();

                    return main4;
                }
                case 4: {
                    //프래그 먼트 생성
                    main5 = new Fragment_main4();
                    Bundle bundle = new Bundle();

                    return main5;
                }
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }
    public void setTabEvent(){
        Layout_Tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comuview = "main";
                if(bol_main == false){
                    if(mInterstitialAd.isLoaded()){
                        mInterstitialAd.show();
                    }
                    bol_main = true;
                }
                Img_Tab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_1_click));
                Img_Tab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_2));
                Img_Tab3.setImageDrawable(getResources().getDrawable(R.drawable.tab3));
                Img_Tab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_comu));
                Img_Tab5.setImageDrawable(getResources().getDrawable(R.drawable.tab_5));
                Txt_Tab1.setTextColor(getResources().getColor(R.color.tab_text));
                Txt_Tab2.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab3.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab4.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab5.setTextColor(getResources().getColor(R.color.white));
                mViewPager.setCurrentItem(0);

            }
        });
        Layout_Tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comuview = "main";
                if(bol_main == false){
                    if(mInterstitialAd.isLoaded()){
                        mInterstitialAd.show();
                    }
                    bol_main = true;
                }
                Img_Tab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_1));
                Img_Tab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_2_click));
                Img_Tab3.setImageDrawable(getResources().getDrawable(R.drawable.tab3));
                Img_Tab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_comu));
                Img_Tab5.setImageDrawable(getResources().getDrawable(R.drawable.tab_5));
                Txt_Tab1.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab2.setTextColor(getResources().getColor(R.color.tab_text));
                Txt_Tab3.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab4.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab5.setTextColor(getResources().getColor(R.color.white));
                mViewPager.setCurrentItem(1);
            }
        });
        Layout_Tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comuview = "main";
                if(bol_main == false){
                    if(mInterstitialAd.isLoaded()){
                        mInterstitialAd.show();
                    }
                    bol_main = true;
                }
                Img_Tab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_1));
                Img_Tab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_2));
                Img_Tab3.setImageDrawable(getResources().getDrawable(R.drawable.tab3_click));
                Img_Tab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_comu));
                Img_Tab5.setImageDrawable(getResources().getDrawable(R.drawable.tab_5));
                Txt_Tab1.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab2.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab3.setTextColor(getResources().getColor(R.color.tab_text));
                Txt_Tab4.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab5.setTextColor(getResources().getColor(R.color.white));
                mViewPager.setCurrentItem(2);
            }
        });
        Layout_Tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comuview = "comu";
                if(bol_main == false){
                    if(mInterstitialAd.isLoaded()){
                        mInterstitialAd.show();
                    }
                    bol_main = true;
                }
                mViewPager.setCurrentItem(3);
                Log.i("테스트11", mViewPager.getCurrentItem()+"");
                Img_Tab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_1));
                Img_Tab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_2));
                Img_Tab3.setImageDrawable(getResources().getDrawable(R.drawable.tab3));
                Img_Tab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_comu_click));
                Img_Tab5.setImageDrawable(getResources().getDrawable(R.drawable.tab_5));
                Txt_Tab1.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab2.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab3.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab4.setTextColor(getResources().getColor(R.color.tab_text));
                Txt_Tab5.setTextColor(getResources().getColor(R.color.white));
            }
        });
        Layout_Tab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comuview = "main";
                if(bol_main == false){
                    if(mInterstitialAd.isLoaded()){
                        mInterstitialAd.show();
                    }
                    bol_main = true;
                }
                Img_Tab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_1));
                Img_Tab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_2));
                Img_Tab3.setImageDrawable(getResources().getDrawable(R.drawable.tab3));
                Img_Tab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_comu));
                Img_Tab5.setImageDrawable(getResources().getDrawable(R.drawable.tab_5_click));
                Txt_Tab1.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab2.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab3.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab4.setTextColor(getResources().getColor(R.color.white));
                Txt_Tab5.setTextColor(getResources().getColor(R.color.tab_text));
                mViewPager.setCurrentItem(4);
            }
        });
    }
    public void setKeyHash(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "blackcap.nationalescape",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void onBackPressed() {
        if(mViewPager.getCurrentItem() == 0){
            super.onBackPressed();
            main_event_timer.cancel();
            finish();
        }
        else{
            Img_Tab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_1_click));
            Img_Tab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_2));
            Img_Tab3.setImageDrawable(getResources().getDrawable(R.drawable.tab3));
            Img_Tab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_comu));
            Img_Tab5.setImageDrawable(getResources().getDrawable(R.drawable.tab_5));
            Txt_Tab1.setTextColor(getResources().getColor(R.color.tab_text));
            Txt_Tab2.setTextColor(getResources().getColor(R.color.white));
            Txt_Tab3.setTextColor(getResources().getColor(R.color.white));
            Txt_Tab4.setTextColor(getResources().getColor(R.color.white));
            Txt_Tab5.setTextColor(getResources().getColor(R.color.white));

            mViewPager.setCurrentItem(0);
        }
    }
}
