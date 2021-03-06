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
    SharedPreferences preferences; //?????? ????????? ??????
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
        //???????????? ??????
        setImg_Homepage();
        //?????? ????????????
        setAddress_Copy_Event();
        //????????????
        setCall_Event();
        //??????????????? ??????
        setBook_Event();
        //????????????
        setImgBack();
        setStar_Event();
        //?????? ?????????
        setReview_Focus();
        //?????? ??????
        setReview_Write();
        //????????? ??????
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
                //???????????? ??????????????? ????????? ?????? ??????????????? ????????? ??????
                ClipData clip = ClipData.newPlainText("text", str_address);
                //???????????? ????????? ????????? ?????????
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(clip);//??????????????? ???????????? ??????
                Toast.makeText(Home_Focus.this,"????????? ??????????????????",Toast.LENGTH_SHORT).show();
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
                Log.i("?????????", "xxx");
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
                Log.i("?????????", "xxx");
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
                Log.i("?????????", "xxx");
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
                        Toast.makeText(Home_Focus.this, "????????? ??????????????????", Toast.LENGTH_SHORT).show();
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
            //?????? ?????? ????????????
            try {
                //???????????? ????????? ????????? ??????
                HttpClient http = new HttpClient();
                http_result = http.HttpClient("Web_Escape", "Home_Focus_Review_Write_v3.jsp",params);
                Log.i("?????????", http_result);
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
                //???????????? ????????????
                setting_view = false;
                Toast.makeText(Home_Focus.this, "????????? ?????????????????????", Toast.LENGTH_SHORT).show();
                Edit_Review_Content.setText("");
                //?????? 3??? ????????? ?????????
                Async_Review3 async_review3 = new Async_Review3();
                async_review3.execute(str_company_pk, User_Pk);
            }
            else if(http_result.equals("exist")){
                Toast.makeText(Home_Focus.this, "?????? ????????? ????????? ???????????????", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(Home_Focus.this, "?????? ??? ?????? ??????????????????", Toast.LENGTH_SHORT).show();
                Log.i("????????? ??????", http_result);
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
            //?????? ?????? ????????????
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

            //?????? ??????
            Txt_Review_GradeAvg.setText(str_grade_avg+"???");
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
            //?????? ?????? ????????????
            try {
                //???????????? ????????? ????????? ??????
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();

                //?????? ?????? ?????????
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

                //?????? ????????? ??????
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

            //?????? ???????????? ??????
            setViewPager(parseredData);

            //?????? ??????
            setMap(str_title, str_x, str_y);
            Txt_Address.setText(str_address);

            //?????? ??????
            Txt_IntroFocus.setText(str_introfocus);

            Txt_Title1.setText(str_title);
            Txt_Distance.setText(str_distance);
            Txt_Intro.setText(str_intro);
            Rating_Range(Double.parseDouble(str_grade_avg));

            //?????? ??????
            LinearLayoutManager layoutManager = new LinearLayoutManager(Home_Focus.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);

            price_adapter = new Price_Adapter(Home_Focus.this, price_models);
            List_Price.setLayoutManager(layoutManager);
            List_Price.setAdapter(price_adapter);

            //?????? ??????
            Txt_Review_GradeAvg.setText(str_grade_avg+"???");
            Txt_Review_Nickname.setText(str_nickname);
            StartRange startRange = new StartRange();
            startRange.rangestart(Home_Focus.this, Double.parseDouble(str_grade_avg), Img_Review_Star1, Img_Review_Star2, Img_Review_Star3, Img_Review_Star4, Img_Review_Star5);

            LinearLayoutManager layoutManager1 = new LinearLayoutManager(Home_Focus.this);
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager1.scrollToPosition(0);
            review_adapter = new Review_Adapter(Home_Focus.this, review_models);
            List_Review.setLayoutManager(layoutManager1);
            List_Review.setAdapter(review_adapter);

            //????????? ??? ?????? ????????????
            layoutManager2 = new LinearLayoutManager(Home_Focus.this);
            layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
            layoutManager2.scrollToPosition(0);

            //????????? ???????????? ????????? ??????
            tab2_focus_theme_adapter = new Tab2_Focus_Theme_Adapter(Home_Focus.this, theme_models);
            List_Theme.setLayoutManager(layoutManager2);
            List_Theme.setAdapter(tab2_focus_theme_adapter);


            progressDialog.dismiss();
        }
    }
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("???????????? ?????? ?????? ??????", pRecvServerPage);
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
        Log.i("???????????? ?????? ?????? ??????", pRecvServerPage);
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
        Log.i("???????????? ?????? ?????? ??????", pRecvServerPage);
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
        Log.i("???????????? ?????? ?????? ??????", pRecvServerPage);
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
        Log.i("???????????? ?????? ?????? ??????", pRecvServerPage);
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
        //??????????????? ??????

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

        //????????? ??????
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(x), Double.parseDouble(y)), true);
        //??? ?????? ??????
        //mapView.setZoomLevel(-5, true);
        //??? ???
        mapView.zoomIn(true);
        //??? ??????
        mapView.zoomOut(true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(title);
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(x), Double.parseDouble(y)));
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // ??????????????? ????????? ????????? ??????.
        marker.setCustomImageResourceId(R.drawable.map_marker); // ?????? ?????????.
        marker.setCustomImageAutoscale(false); // hdpi, xhdpi ??? ??????????????? ???????????? ???????????? ????????? ?????? ?????? ?????????????????? ????????? ????????? ??????.
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
            //????????? ?????? ??????
            if(position == 0 ){
                f = new Home_Focus_Fragment();
            }
            else{
                f = new Home_Focus_Fragment();
            }

            Bundle bundle = new Bundle();

            //????????? URL ?????? ?????? ex) 1_1
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



