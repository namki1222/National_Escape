package blackcap.nationalescape.Activity.tab1;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.Adapter.Price_Adapter;
import blackcap.nationalescape.Adapter.Review_Adapter;
import blackcap.nationalescape.Adapter.Tab0_Theme_Adapter;
import blackcap.nationalescape.Adapter.Tab2_Focus_Theme_Adapter;
import blackcap.nationalescape.Model.Price_Model;
import blackcap.nationalescape.Model.Review_Model;
import blackcap.nationalescape.Model.Theme_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import blackcap.nationalescape.Uitility.StartRange;
import blackcap.nationalescape.Uitility.ViewPager_Wrap;
import me.drakeet.materialdialog.MaterialDialog;

import static blackcap.nationalescape.Activity.Fragment_main4.setting_view;
import static blackcap.nationalescape.Activity.MainActivity.bol_main;
import static blackcap.nationalescape.Activity.MainActivity.mInterstitialAd;

public class Home_Focus extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성
    private String User_Pk = "";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager_Wrap mViewPager;

    private ImageView Img_Back, Img_Homepage;
    private TextView Txt_Title, Txt_ViewpagerCount, Txt_IntroFocus, Txt_Address;
    private ImageView Img_Address_Copy;

    private String str_company_pk = "", str_title = "", str_grade_avg = "", str_recommend_count = "", str_distance = "", str_intro = "", str_address = "";
    private int pageCount = 0;

    private RecyclerView List_Theme;
    private ArrayList<Theme_Model> theme_models;
    private Tab2_Focus_Theme_Adapter tab2_focus_theme_adapter;

    private RecyclerView List_Price;
    private ArrayList<Price_Model> price_models;
    private Price_Adapter price_adapter;

    private TextView Txt_Review_GradeAvg;int startcount = 5;
    private ImageView Img_Review_Star1, Img_Review_Star2, Img_Review_Star3, Img_Review_Star4, Img_Review_Star5;
    private RecyclerView List_Review;
    public static ImageView Img_ReviewWrite_Star1, Img_ReviewWrite_Star2, Img_ReviewWrite_Star3, Img_ReviewWrite_Star4, Img_ReviewWrite_Star5;
    private TextView Txt_Review_Write;
    public static EditText Edit_Review_Content;
    public static ArrayList<Review_Model> review_models;
    public static Review_Adapter review_adapter;
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private TextView Txt_Review_Nickname, Txt_Review_Focus;
    private LinearLayout Layout_Call, Layout_Book;
    private NestedScrollView Nes_Scroll;

    private String str_phone = "";String str_bookpage = "";String str_homepage = "", str_bookflag = "";
    private Async async;

    private LinearLayoutManager layoutManager2;

    private ImageView Img;
    private ImageView Img_top_star1, Img_top_star2, Img_top_star3, Img_top_star4, Img_top_star5;
    private TextView Txt_Title1, Txt_Distance, Txt_Intro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_focus);

        init();
        //홈페이지 이동
        setImg_Homepage();
        //주소 복사하기
        setAddress_Copy_Event();
        //전화걸기
        setCall_Event();
        //예약페이지 이동
        setBook_Event();
        //뒤로가기
        setImgBack();
        setStar_Event();
        //리뷰 더보기
        setReview_Focus();
        //리뷰 쓰기
        setReview_Write();
        //데이터 셋팅
        async = new Async();
        async.execute(str_company_pk);

    }
    public void init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");

        Intent intent1 = getIntent();
        str_company_pk = intent1.getStringExtra("Company_Pk");
        str_title = intent1.getStringExtra("Title");
        str_grade_avg = intent1.getStringExtra("Grade_Avg");
        str_recommend_count = intent1.getStringExtra("Recommend_Count");
        str_distance = intent1.getStringExtra("Distance");
        str_intro = intent1.getStringExtra("Intro");
        str_address = intent1.getStringExtra("Address");

        if(bol_main == false){
            if(mInterstitialAd != null){
                if(mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                }
                bol_main = true;
            }
        }

        Nes_Scroll = (NestedScrollView)findViewById(R.id.scroll);

        Txt_Title = (TextView)findViewById(R.id.txt_title);
        Txt_Title.setText(str_title);
        Txt_IntroFocus = (TextView)findViewById(R.id.txt_introfocus);
        List_Price = (RecyclerView)findViewById(R.id.list_price);
        Txt_Address = (TextView)findViewById(R.id.txt_address);
        Img_Address_Copy = (ImageView)findViewById(R.id.img_address_copy);
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Homepage = (ImageView)findViewById(R.id.img_homepage);
        Txt_ViewpagerCount = (TextView) findViewById(R.id.txt_viewpagercount);

        Txt_Review_GradeAvg = (TextView)findViewById(R.id.txt_point);
        Txt_Review_Nickname = (TextView)findViewById(R.id.txt_review_nickname);
        Img_Review_Star1 = (ImageView) findViewById(R.id.img_review_star1);
        Img_Review_Star2 = (ImageView) findViewById(R.id.img_review_star2);
        Img_Review_Star3 = (ImageView) findViewById(R.id.img_review_star3);
        Img_Review_Star4 = (ImageView) findViewById(R.id.img_review_star4);
        Img_Review_Star5 = (ImageView) findViewById(R.id.img_review_star5);
        List_Review = (RecyclerView)findViewById(R.id.list_review);
        Img_ReviewWrite_Star1 = (ImageView) findViewById(R.id.img_star1);
        Img_ReviewWrite_Star2 = (ImageView) findViewById(R.id.img_star2);
        Img_ReviewWrite_Star3 = (ImageView) findViewById(R.id.img_star3);
        Img_ReviewWrite_Star4 = (ImageView) findViewById(R.id.img_star4);
        Img_ReviewWrite_Star5 = (ImageView) findViewById(R.id.img_star5);

        Txt_Review_Focus = (TextView) findViewById(R.id.txt_review_focus);
        Txt_Review_Write = (TextView) findViewById(R.id.txt_review_write);
        Edit_Review_Content = (EditText) findViewById(R.id.edit_review_content);
        //Edit_Review_Content.setFilters(new InputFilter[]{specialCharacterFilter});

        Layout_Call = (LinearLayout)findViewById(R.id.layout_call);
        Layout_Book = (LinearLayout)findViewById(R.id.layout_book);

        List_Theme = (RecyclerView)findViewById(R.id.list_theme);

        Txt_Title1 = (TextView) findViewById(R.id.txt_title1);
        Txt_Distance = (TextView) findViewById(R.id.txt_distance);
        Txt_Intro = (TextView) findViewById(R.id.txt_intro);

        Img_top_star1 = (ImageView)findViewById(R.id.img_top_star1);
        Img_top_star2 = (ImageView)findViewById(R.id.img_top_star2);
        Img_top_star3 = (ImageView)findViewById(R.id.img_top_star3);
        Img_top_star4 = (ImageView)findViewById(R.id.img_top_star4);
        Img_top_star5 = (ImageView)findViewById(R.id.img_top_star5);

        setScroll();
    }

    public void setScroll(){
        Nes_Scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                layoutManager2.setSmoothScrollbarEnabled(true);
                List_Theme.dispatchTouchEvent(event);
                return false;
            }
        });
    }
    public void setImg_Homepage(){
        Img_Homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient http = new HttpClient();
                http.HttpClient("Web_Escape", "Home_Focus_Visit.jsp",str_company_pk, "home", setNowTime());
                Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(str_homepage));
                startActivity(newIntent);
            }
        });
    }
    public void setAddress_Copy_Event(){
        Img_Address_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //문자열을 클립보드에 넣는수 있는 클립데이터 형태로 포장
                ClipData clip = ClipData.newPlainText("text", str_address);
                //클립보드 관리자 객체를 가져옴
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(clip);//클립보드에 저장하는 부분
                Toast.makeText(Home_Focus.this,"주소가 복사되었어요",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setStar_Event(){
        Img_ReviewWrite_Star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_ReviewWrite_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star));
                Img_ReviewWrite_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
                Img_ReviewWrite_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                Img_ReviewWrite_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                Img_ReviewWrite_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                startcount = 5;

            }
        });
        Img_ReviewWrite_Star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_ReviewWrite_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                Img_ReviewWrite_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
                Img_ReviewWrite_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                Img_ReviewWrite_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                Img_ReviewWrite_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                startcount = 4;
            }
        });
        Img_ReviewWrite_Star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_ReviewWrite_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                Img_ReviewWrite_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                Img_ReviewWrite_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                Img_ReviewWrite_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                Img_ReviewWrite_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                startcount = 3;
                Log.i("테스트", "xxx");
            }
        });
        Img_ReviewWrite_Star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_ReviewWrite_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                Img_ReviewWrite_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                Img_ReviewWrite_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                Img_ReviewWrite_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                Img_ReviewWrite_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                startcount = 2;
                Log.i("테스트", "xxx");
            }
        });
        Img_ReviewWrite_Star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_ReviewWrite_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                Img_ReviewWrite_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                Img_ReviewWrite_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                Img_ReviewWrite_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                Img_ReviewWrite_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                startcount = 1;
                Log.i("테스트", "xxx");
            }
        });
    }
    public void setCall_Event(){
        Layout_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient http = new HttpClient();
                http.HttpClient("Web_Escape", "Home_Focus_Visit.jsp",str_company_pk, "phone", setNowTime());
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:"+str_phone)));
            }
        });
    }
    public void setBook_Event(){
        Layout_Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient http = new HttpClient();
                http.HttpClient("Web_Escape", "Home_Focus_Visit.jsp",str_company_pk, "book", setNowTime());
                if(str_bookflag.equals("true")){
                    Intent intent = new Intent(Home_Focus.this, Book_Chapter1.class);
                    intent.putExtra("Company_Pk", str_company_pk);
                    intent.putExtra("Company_Title", str_title);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    setDialog_check();
                }
            }
        });
    }
    public void setReview_Write(){
        Txt_Review_Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_Pk.equals(".")){
                    Intent intent = new Intent(Home_Focus.this, Login.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    if(Edit_Review_Content.getText().toString().equals("")){
                        Toast.makeText(Home_Focus.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Async_write async_write = new Async_write();
                        async_write.execute(str_company_pk, User_Pk, Integer.toString(startcount), Edit_Review_Content.getText().toString(), setNowTime());
                    }
                }
            }
        });
    }
    public void setReview_Focus(){
        Txt_Review_Focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Focus.this, Home_Focus_Review.class);
                intent.putExtra("Company_Pk", str_company_pk);
                intent.putExtra("Company_Title", str_title);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }
    public class Async_write extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;
        private String http_result = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Home_Focus.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //공지사항 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                http_result = http.HttpClient("Web_Escape", "Home_Focus_Review_Write_v3.jsp",params);
                Log.i("테스트", http_result);
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(http_result.equals("succed")){
                //셋팅화면 리프레쉬
                setting_view = false;
                Toast.makeText(Home_Focus.this, "리뷰가 작성되었습니다", Toast.LENGTH_SHORT).show();
                Edit_Review_Content.setText("");
                //리뷰 3개 데이터 리셋팅
                Async_Review3 async_review3 = new Async_Review3();
                async_review3.execute(str_company_pk, User_Pk);
            }
            else if(http_result.equals("exist")){
                Toast.makeText(Home_Focus.this, "이미 리뷰를 작성한 업체입니다", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(Home_Focus.this, "잠시 후 다시 이용바랍니다", Toast.LENGTH_SHORT).show();
                Log.i("테스트 결과", http_result);
            }

            progressDialog.dismiss();
        }
    }
    public class Async_Review3 extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;

        private String[][] parseredData_reivew, parseredData_extra;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Home_Focus.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();
                String result1 = http.HttpClient("Web_Escape", "Home_Focus_Extradate.jsp",params);
                parseredData_extra = jsonParserList_Extra(result1);

                String result3 = http.HttpClient("Web_Escape", "Home_Focus_Review_3_v2.jsp",params);
                parseredData_reivew = jsonParserList.jsonParserList_Data8(result3);


                review_models = new ArrayList<Review_Model>();
                for (int i = 0; i < parseredData_reivew.length; i++) {
                    String nickname = parseredData_reivew[i][0];
                    String grage = parseredData_reivew[i][1];
                    String content = parseredData_reivew[i][2];
                    String date = parseredData_reivew[i][3];
                    String review_user_pk = parseredData_reivew[i][4];
                    String bol_owner = parseredData_reivew[i][5];
                    String owner_date = parseredData_reivew[i][6];
                    String owner_memo = parseredData_reivew[i][7];
                    review_models.add(new Review_Model(Home_Focus.this, User_Pk, review_user_pk, nickname, grage, content, date, str_company_pk, bol_owner, str_title, owner_date, owner_memo));
                }
                str_grade_avg = parseredData_extra[0][3];
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //리뷰 셋팅
            Txt_Review_GradeAvg.setText(str_grade_avg+"점");
            StartRange startRange = new StartRange();
            startRange.rangestart(Home_Focus.this, Double.parseDouble(str_grade_avg), Img_Review_Star1, Img_Review_Star2, Img_Review_Star3, Img_Review_Star4, Img_Review_Star5);

            LinearLayoutManager layoutManager1 = new LinearLayoutManager(Home_Focus.this);
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager1.scrollToPosition(0);
            review_adapter = new Review_Adapter(Home_Focus.this, review_models);
            List_Review.setLayoutManager(layoutManager1);
            List_Review.setAdapter(review_adapter);

            progressDialog.dismiss();
        }
    }
    public class Async extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;

        private String[][] parseredData, parseredData_extra, parseredData_price, parseredData_reivew, parseredData_User, parseredData_theme;
        private String str_introfocus = "", str_x = "", str_y = "",str_nickname = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Home_Focus.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //공지사항 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();

                //업체 클릭 카운트
                http.HttpClient("Web_Escape", "Home_Focus_Visit.jsp",str_company_pk, "company", setNowTime());

                String result = http.HttpClient("Web_Escape", "Home_Focus_Image.jsp",params);
                parseredData = jsonParserList(result);

                String result1 = http.HttpClient("Web_Escape", "Home_Focus_Extradate.jsp",params);
                parseredData_extra = jsonParserList.jsonParserList_Data7(result1);

                String result2 = http.HttpClient("Web_Escape", "Home_Focus_Price.jsp",params);
                parseredData_price = jsonParserList_price(result2);

                String result3 = http.HttpClient("Web_Escape", "Home_Focus_Review_3_v2.jsp",params[0], User_Pk);
                parseredData_reivew = jsonParserList.jsonParserList_Data8(result3);

                String result4 = http.HttpClient("Web_Escape", "User_v3.jsp",User_Pk, "yologuys12");
                parseredData_User = jsonParserList.jsonParserList_Data8(result4);

                //테마 데이터 셋팅
                String result_theme = http.HttpClient("Web_Escape", "Home_Focus_Theme.jsp", str_company_pk);
                parseredData_theme = jsonParserList.jsonParserList_Data12(result_theme);

                str_introfocus = parseredData_extra[0][0];
                str_x = parseredData_extra[0][1];
                str_y = parseredData_extra[0][2];
                str_phone = parseredData_extra[0][4];
                str_bookpage = parseredData_extra[0][5];
                str_homepage = parseredData_extra[0][6];
                str_bookflag = parseredData_extra[0][7];
                price_models = new ArrayList<Price_Model>();
                for (int i = 0; i < parseredData_price.length; i++) {
                    String title = parseredData_price[i][0];
                    String price = parseredData_price[i][1];
                    price_models.add(new Price_Model(Home_Focus.this, title, price));
                }

                review_models = new ArrayList<Review_Model>();
                for (int i = 0; i < parseredData_reivew.length; i++) {
                    String nickname = parseredData_reivew[i][0];
                    String grage = parseredData_reivew[i][1];
                    String content = parseredData_reivew[i][2];
                    String date = parseredData_reivew[i][3];
                    String review_user_pk = parseredData_reivew[i][4];
                    String bol_owner = parseredData_reivew[i][5];
                    String owner_date = parseredData_reivew[i][6];
                    String owner_memo = parseredData_reivew[i][7];
                    review_models.add(new Review_Model(Home_Focus.this, User_Pk, review_user_pk, nickname, grage, content, date, str_company_pk, bol_owner, str_title, owner_date, owner_memo));
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
                    String deadtime = parseredData_theme[i][11];
                    theme_models.add(new Theme_Model(Home_Focus.this,theme_pk, company_pk, img, title, intro, category, grade, level, person, tool, activity, deadtime));
                }
                str_nickname = parseredData_User[0][2];
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //상단 뷰페이지 셋팅
            setViewPager(parseredData);

            //지도 셋팅
            setMap(str_title, str_x, str_y);
            Txt_Address.setText(str_address);

            //소개 셋팅
            Txt_IntroFocus.setText(str_introfocus);

            Txt_Title1.setText(str_title);
            Txt_Distance.setText(str_distance);
            Txt_Intro.setText(str_intro);
            Rating_Range(Double.parseDouble(str_grade_avg));

            //요금 셋팅
            LinearLayoutManager layoutManager = new LinearLayoutManager(Home_Focus.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);

            price_adapter = new Price_Adapter(Home_Focus.this, price_models);
            List_Price.setLayoutManager(layoutManager);
            List_Price.setAdapter(price_adapter);

            //리뷰 셋팅
            Txt_Review_GradeAvg.setText(str_grade_avg+"점");
            Txt_Review_Nickname.setText(str_nickname);
            StartRange startRange = new StartRange();
            startRange.rangestart(Home_Focus.this, Double.parseDouble(str_grade_avg), Img_Review_Star1, Img_Review_Star2, Img_Review_Star3, Img_Review_Star4, Img_Review_Star5);

            LinearLayoutManager layoutManager1 = new LinearLayoutManager(Home_Focus.this);
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager1.scrollToPosition(0);
            review_adapter = new Review_Adapter(Home_Focus.this, review_models);
            List_Review.setLayoutManager(layoutManager1);
            List_Review.setAdapter(review_adapter);

            //리스트 뷰 세로 방향으로
            layoutManager2 = new LinearLayoutManager(Home_Focus.this);
            layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
            layoutManager2.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            tab2_focus_theme_adapter = new Tab2_Focus_Theme_Adapter(Home_Focus.this, theme_models);
            List_Theme.setLayoutManager(layoutManager2);
            List_Theme.setAdapter(tab2_focus_theme_adapter);


            progressDialog.dismiss();
        }
    }
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3"};
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
    public void Rating_Range(double Rating){
        StartRange startRange = new StartRange();
        startRange.rangestart(Home_Focus.this, Rating, Img_top_star1, Img_top_star2, Img_top_star3, Img_top_star4, Img_top_star5);
    }
    public String[][] jsonParserList_Extra(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg8"};
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
    public String[][] jsonParserList_price(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2"};
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
    public String[][] jsonParserList_Review(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2","msg3", "msg4"};
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
    public String[][] jsonParserList_User(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2","msg3", "msg4", "msg5", "msg6"};
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
    public void setViewPager(String[][] data) {
        //프래그먼트 정의

        pageCount = data.length;
        Txt_ViewpagerCount.setText("1/"+pageCount);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), data);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager_Wrap) findViewById(R.id.viewpager_homefocus_img);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOffscreenPageLimit(data.length);
        ////////////

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Txt_ViewpagerCount.setText((position+1)+"/"+pageCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void setMap(String title, String x, String y){
        mapView = new MapView(Home_Focus.this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        //중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(x), Double.parseDouble(y)), true);
        //줌 레벨 변경
        //mapView.setZoomLevel(-5, true);
        //줌 인
        mapView.zoomIn(true);
        //줌 아웃
        mapView.zoomOut(true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(title);
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(x), Double.parseDouble(y)));
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        marker.setCustomImageResourceId(R.drawable.map_marker); // 마커 이미지.
        marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        marker.setCustomImageAnchor(0.5f, 1.0f);

        mapView.addPOIItem(marker);
        mapView.setClickable(false);
    }
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        String[][] data;
        int pagercount;

        public SectionsPagerAdapter(FragmentManager fm, String[][] data) {
            super(fm);
            this.data = data;
            if (data.length == 0) {
                pagercount = 1;
            } else {
                pagercount = data.length;
            }
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f;
            //프래그 먼트 생성
            if(position == 0 ){
                f = new Home_Focus_Fragment();
            }
            else{
                f = new Home_Focus_Fragment();
            }

            Bundle bundle = new Bundle();

            //이미지 URL 동적 전송 ex) 1_1
            String Image_txt;
            if (data.length == 0) {
                Image_txt = "";
            } else {
                Image_txt = data[position][2];
            }

            if(position == 0 ){
                bundle.putString("title", str_title);
                bundle.putString("grage_avg", str_grade_avg);
                bundle.putString("recommend_count", str_recommend_count);
                bundle.putString("distance", str_distance);
                bundle.putString("intro", str_intro);
                bundle.putString("Image", Image_txt);
            }
            else{
                bundle.putString("Image", Image_txt);
            }


            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.

            return pagercount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
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
                    else
                    {
                    }
                    if(mapViewContainer != null){
                        mapViewContainer.removeAllViews();
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
            else
            {
            }
            if(mapViewContainer != null){
                mapViewContainer.removeAllViews();
            }
        }
        catch (Exception e)
        {
        }


        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        super.onBackPressed();
    }
    private String setNowTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        return sdf.format(date)+"";
    }
    public void setDialog_check(){
        LayoutInflater inflater = (LayoutInflater)Home_Focus.this.getSystemService(Home_Focus.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_bookflag, (ViewGroup)findViewById(R.id.root));
        final TextView Txt_Ok = (TextView) layout.findViewById(R.id.btn_ok);
        final TextView Txt_Cancel = (TextView) layout.findViewById(R.id.btn_cancel);
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Home_Focus.this);
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();

        Txt_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(str_bookpage));
                startActivity(newIntent);
                TeamInfo_Dialog.dismiss();
            }
        });
    }
}



