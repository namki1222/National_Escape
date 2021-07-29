package blackcap.nationalescape.Activity.Community;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

import com.matthewtamlin.sliding_intro_screen_library.DotIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.tab1.Home_Focus;
import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.Adapter.Com_Comment_Adapter;
import blackcap.nationalescape.Adapter.Com_Free_Adapter;
import blackcap.nationalescape.Adapter.Img_Adapter;
import blackcap.nationalescape.Model.Com_Comment_Model;
import blackcap.nationalescape.Model.Com_Free_Model;
import blackcap.nationalescape.Model.Img_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Http_Del;
import blackcap.nationalescape.Uitility.Http_Get;
import blackcap.nationalescape.Uitility.Http_Post;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import blackcap.nationalescape.Uitility.ViewPager_Wrap;
import blackcap.nationalescape.frag_img;
import me.drakeet.materialdialog.MaterialDialog;

import static blackcap.nationalescape.Activity.Fragmetn_community.str_status;
import static blackcap.nationalescape.Activity.MainActivity.Img_Tab1;
import static blackcap.nationalescape.Activity.MainActivity.Img_Tab2;
import static blackcap.nationalescape.Activity.MainActivity.Img_Tab3;
import static blackcap.nationalescape.Activity.MainActivity.Img_Tab4;
import static blackcap.nationalescape.Activity.MainActivity.Img_Tab5;
import static blackcap.nationalescape.Activity.MainActivity.Txt_Tab1;
import static blackcap.nationalescape.Activity.MainActivity.Txt_Tab2;
import static blackcap.nationalescape.Activity.MainActivity.Txt_Tab3;
import static blackcap.nationalescape.Activity.MainActivity.Txt_Tab4;
import static blackcap.nationalescape.Activity.MainActivity.Txt_Tab5;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;
import static blackcap.nationalescape.Activity.MainActivity.comuview;
import static blackcap.nationalescape.Activity.MainActivity.mViewPager;

public class Focus_Free extends AppCompatActivity {
    private ImageView Img_Next, Img_Grade;
    private TextView Txt_Comugo;
    private TextView Tx_Toptitle;
    private TextView Txt_Company, Txt_Theme;
    private TextView Txt_Title, Txt_Nick, Txt_Date, Txt_Comment_Count, Txt_Contents;
    private LinearLayout Layout_Title;
    //private SectionsPagerAdapter mSectionsPagerAdapter;
  /*  private ViewPager_Wrap Viewpage_Img;
    private DotIndicator Indicator_Event;*/
    private RelativeLayout Layout_Img;
    private NestedScrollView Scroll;
    private LinearLayout Layout_Setting;
    public static EditText Edit_Review_Content;
    private TextView Txt_Review_Write;
    private ImageView Img_Category;
    private ImageView Img_Del, Img_Modi;

    private String str_my_nickname = "";
    private String str_my_grade = "";

    private String str_user_pk = "";
    private String str_nickname = "";
    private String str_grade = "";
    private String str_title = "";
    private String str_content = "";
    private String str_company_name = "";
    private String str_theme_name = "";
    private String str_diff = "";
    private String str_category = "";
    private String str_commentCount = "";
    private String str_isLike = "";
    private String str_createdAt = "";
    private String str_id = "";

    public static TextView Txt_Comment_Nick;
    public static RelativeLayout Layout_Comment_Nick;

    public ArrayList<Com_Comment_Model> com_comment_model;
    public static Com_Comment_Adapter com_comment_adapters;
    private RecyclerView List_Review;

    private ImageView Img_Rec;

    public static String replay_nick = "";
    public static String replay_id = "";

    public ArrayList<Img_Model> img_model;
    public Img_Adapter img_adapters;
    private RecyclerView List_Img;

    private String[][] parsed_img;

    public static int img_height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comu_info_free);

        setInit();

    }

    public void setInit() {
        Intent intent1 = getIntent();
        str_id = intent1.getStringExtra("id");

        Scroll = (NestedScrollView)findViewById(R.id.scroll);
        Txt_Comugo = (TextView)findViewById(R.id.txt_comugo);
        Tx_Toptitle = (TextView)findViewById(R.id.txt_toptitle);
        Layout_Title = (LinearLayout)findViewById(R.id.layout_title);
        Txt_Company = (TextView)findViewById(R.id.txt_company);
        Txt_Theme = (TextView)findViewById(R.id.txt_theme);
        Txt_Title = (TextView)findViewById(R.id.txt_title);
        Txt_Nick = (TextView)findViewById(R.id.txt_nick);
        Txt_Date = (TextView)findViewById(R.id.txt_date);
        Txt_Comment_Count = (TextView)findViewById(R.id.txt_comment_count);
        Txt_Contents = (TextView)findViewById(R.id.txt_contents);
        Edit_Review_Content = (EditText)findViewById(R.id.edit_review_content);
        Txt_Review_Write = (TextView)findViewById(R.id.txt_review_write);
        List_Review = (RecyclerView)findViewById(R.id.list_review);
        Img_Category = (ImageView)findViewById(R.id.img_category);
        Txt_Comment_Nick = (TextView)findViewById(R.id.txt_comment_nick);
        Layout_Comment_Nick = (RelativeLayout)findViewById(R.id.layout_comment_nick);
        Img_Grade = (ImageView)findViewById(R.id.img_grade);

        Img_Rec = (ImageView)findViewById(R.id.img_rec);

        List_Img =(RecyclerView)findViewById(R.id.list_img);
        Layout_Setting = (LinearLayout)findViewById(R.id.layout_setting);
        Img_Del = (ImageView)findViewById(R.id.img_del);
        Img_Modi = (ImageView)findViewById(R.id.img_modi);

        Async async = new Async();
        async.execute();

        Async_User async_user = new Async_User();
        async_user.execute();

        Img_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //홈 업체 리스트 데이터 셋팅
                Http_Post http = new Http_Post();
                String result = http.Http_Post(User_Pk, "v1/like/register", "boardId", str_id);
                String[] parseredData = jsonParserList_access(result);
                if(parseredData[0].equals("success")){
                    Toast.makeText(Focus_Free.this, "게시글을 추천했습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Focus_Free.this, parseredData[0], Toast.LENGTH_SHORT).show();
                }
            }
        });

        Img_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog_check();
            }
        });

        Txt_Comugo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comuview.equals("main")){
                    mViewPager.setCurrentItem(3);
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

                    comuview = "comu";
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                }
                else{
                    comuview ="comu";
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                }
            }
        });

        Img_Modi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("테스123", str_diff);
                if(str_diff.equals("자유게시판")){
                    Intent intent = new Intent(Focus_Free.this, Write_Free.class);
                    intent.putExtra("category", "수정");
                    intent.putExtra("id", str_id);
                    intent.putExtra("title", str_title);
                    intent.putExtra("contents", str_content);

                    for(int i = 0 ; i < parsed_img.length; i ++){
                        int position = i+1;
                        intent.putExtra("img"+position+"_id",  parsed_img[i][0]);
                        intent.putExtra("img"+position,  parsed_img[i][1]);
                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else if(str_diff.equals("보드판 갤러리")){
                    Intent intent = new Intent(Focus_Free.this, Write_Board.class);
                    intent.putExtra("category", "수정");
                    intent.putExtra("id", str_id);
                    intent.putExtra("title", str_title);
                    intent.putExtra("contents", str_content);
                    intent.putExtra("company", str_company_name);
                    intent.putExtra("theme", str_theme_name);

                    for(int i = 0 ; i < parsed_img.length; i ++){
                        int position = i+1;
                        intent.putExtra("img"+position+"_id",  parsed_img[i][0]);
                        intent.putExtra("img"+position,  parsed_img[i][1]);
                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else if(str_diff.equals("일행구하기")){
                    Intent intent = new Intent(Focus_Free.this, Write_Join.class);
                    intent.putExtra("category", "수정");
                    intent.putExtra("id", str_id);
                    intent.putExtra("title", str_title);
                    intent.putExtra("contents", str_content);
                    intent.putExtra("diff", str_category);

                    for(int i = 0 ; i < parsed_img.length; i ++){
                        int position = i+1;
                        intent.putExtra("img"+position+"_id",  parsed_img[i][0]);
                        intent.putExtra("img"+position,  parsed_img[i][1]);
                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else if(str_diff.equals("방탈출정보")){
                    Intent intent = new Intent(Focus_Free.this, Write_Info.class);
                    intent.putExtra("category", "수정");
                    intent.putExtra("id", str_id);
                    intent.putExtra("title", str_title);
                    intent.putExtra("contents", str_content);
                    intent.putExtra("diff", str_category);

                    for(int i = 0 ; i < parsed_img.length; i ++){
                        int position = i+1;
                        intent.putExtra("img"+position+"_id",  parsed_img[i][0]);
                        intent.putExtra("img"+position,  parsed_img[i][1]);
                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });

        Layout_Comment_Nick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replay_id = "";
                replay_nick = "";
                Edit_Review_Content.setText("");
                Layout_Comment_Nick.setVisibility(View.GONE);
            }
        });

        /*Layout_Img = (RelativeLayout)findViewById(R.id.list_img);
        Indicator_Event = (DotIndicator)findViewById( R.id.dot_event);
        Indicator_Event.setSelectedDotColor( Color.parseColor( "#fac836" ) );
        Indicator_Event.setUnselectedDotColor( Color.parseColor( "#e4e4e4" ) );
        Viewpage_Img = (ViewPager_Wrap) findViewById(R.id.viewpage_img);
        Viewpage_Img.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Indicator_Event.setSelectedItem( Viewpage_Img.getCurrentItem(), true );
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

        Img_Next = (ImageView)findViewById(R.id.img_back);
        Img_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });

        Txt_Review_Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_Pk.equals(".")){
                    Intent intent = new Intent(Focus_Free.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    if(Edit_Review_Content.getText().toString().equals("")){
                        Toast.makeText(Focus_Free.this, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Async_Comment async_comment = new Async_Comment();
                        async_comment.execute();
                    }
                }
            }
        });

        Edit_Review_Content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* if(!Edit_Review_Content.getText().toString().equals("")){
                    if(!replay_id.equals("")){
                        if(Edit_Review_Content.getText().toString().length() < replay_nick.length()){
                            replay_id = "";
                            replay_nick = "";
                            Edit_Review_Content.setText("");
                        }
                    }
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Async async = new Async();
        async.execute();
    }

    public class Async_User extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Focus_Free.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //유저 정보 response
                HttpClient http = new HttpClient();
                String result = http.HttpClient("Web_Escape", "User_v4.jsp",User_Pk, "yologuys12");

                JsonParserList jsonParserList = new JsonParserList();
                parseredData = jsonParserList.jsonParserList_Data14(result);

                str_my_nickname = parseredData[0][2];
                str_my_grade = parseredData[0][7];

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[] parseredData;
        String[][] parseredData_list;
        String[] parsed_img_temp;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Focus_Free.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //홈 업체 리스트 데이터 셋팅
                Http_Get http = new Http_Get();
                String result = http.Http_Get(User_Pk, "v1/board/detail?id="+str_id);
                parseredData = jsonParserList_access(result);
                if(parseredData[0].equals("success")){
                    String[] parsed = jsonParserList_access1(result);
                    String[] parsed_info = jsonParserList_info(parsed[1]);
                    parsed_img = jsonParserList_img(parsed[1]);
                    str_user_pk = parsed_info[1];
                    str_nickname = parsed_info[2];
                    str_grade = parsed_info[3];
                    str_title = parsed_info[4];
                    str_content = parsed_info[5];
                    str_company_name = parsed_info[6];
                    str_theme_name = parsed_info[7];
                    str_diff = parsed_info[8];
                    str_category = parsed_info[9];
                    str_commentCount = parsed_info[10];
                    str_isLike = parsed_info[11];
                    str_createdAt = parsed_info[12];

                    parsed_img_temp = new String[parsed_img.length];
                    for(int i = 0 ; i < parsed_img.length; i ++){
                        parsed_img_temp[i] = parsed_img[i][1];
                    }

                    Http_Get http1 = new Http_Get();
                    String result_comment = http1.Http_Get(User_Pk, "v1/boardComment/list?boardId="+str_id);
                    parseredData_list = jsonParserList_comment(result_comment);

                    com_comment_model = new ArrayList<Com_Comment_Model>();
                    for (int i = 0; i < parseredData_list.length; i++) {
                        String id = parseredData_list[i][0];
                        String boardId = parseredData_list[i][1];
                        String user_pk = parseredData_list[i][2];
                        String nickname = parseredData_list[i][3];
                        String grade = parseredData_list[i][4];
                        String content = parseredData_list[i][5];
                        String depth = parseredData_list[i][6];
                        String createdAt = parseredData_list[i][7];

                        com_comment_model.add(new Com_Comment_Model(Focus_Free.this,id, boardId, user_pk, nickname, grade, content, depth, createdAt));
                    }

                    img_model = new ArrayList<Img_Model>();
                    for (int i = 0; i < parsed_img.length; i++) {
                        img_model.add(new Img_Model(Focus_Free.this,parsed_img[i][1]));
                    }
                }

                return "succed";
            } catch (Exception e) {
                Log.i("테스1", e+"");
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if(parseredData[0].equals("success")){
                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(Focus_Free.this);
                    //LinearLayoutManager layoutManager1 = new LinearLayoutManager(Focus_Free.this);
                    layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                    layoutManager1.scrollToPosition(0);

                    if(img_model.size() == 0){
                        List_Img.setVisibility(View.GONE);
                    }
                    else{
                        List_Img.setVisibility(View.VISIBLE);
                        //베스트 무료체험 어댑터 셋팅
                        img_adapters = new Img_Adapter(Focus_Free.this, img_model);
                        List_Img.setLayoutManager(layoutManager1);
                        List_Img.setAdapter(img_adapters);
                    }

                    Tx_Toptitle.setText(str_diff);

                    if(str_category.equals("공지")){
                        Img_Category.setVisibility(View.VISIBLE);
                        Img_Category.setImageResource(R.drawable.comu_info_noti);
                        Txt_Company.setVisibility(View.GONE);
                        Txt_Theme.setVisibility(View.GONE);
                    }
                    else{
                        if(str_diff.equals("자유게시판")){
                            Img_Category.setVisibility(View.GONE);
                            Txt_Company.setVisibility(View.GONE);
                            Txt_Theme.setVisibility(View.GONE);
                        }
                        else if(str_diff.equals("일행구하기")){
                            Txt_Company.setVisibility(View.GONE);
                            Txt_Theme.setVisibility(View.GONE);
                            if(str_category.equals("모집중")){
                                Img_Category.setImageResource(R.drawable.comu_join_ing);
                            }
                            else{
                                Img_Category.setImageResource(R.drawable.comu_join_finish);
                            }
                            Img_Category.setVisibility(View.VISIBLE);
                        }
                        else if(str_diff.equals("방탈출정보")){
                            Txt_Company.setVisibility(View.GONE);
                            Txt_Theme.setVisibility(View.GONE);
                            if(str_category.equals("소식")){
                                Img_Category.setVisibility(View.VISIBLE);
                                Img_Category.setImageResource(R.drawable.comu_info_news);
                            }
                            else if(str_category.equals("공지")){
                                Img_Category.setVisibility(View.VISIBLE);
                                Img_Category.setImageResource(R.drawable.comu_info_info);
                            }
                            else if(str_category.equals("이벤트")){
                                Img_Category.setVisibility(View.VISIBLE);
                                Img_Category.setImageResource(R.drawable.comu_info_event);
                            }
                            else if(str_category.equals("후기")){
                                Img_Category.setVisibility(View.VISIBLE);
                                Img_Category.setImageResource(R.drawable.comu_info_review);
                            }
                            else{
                                Img_Category.setVisibility(View.GONE);
                            }

                        }
                        else{
                            Img_Rec.setVisibility(View.VISIBLE);
                            Txt_Company.setVisibility(View.VISIBLE);
                            Txt_Theme.setVisibility(View.VISIBLE);
                            Layout_Title.setVisibility(View.GONE);
                            Img_Category.setVisibility(View.GONE);

                            Txt_Company.setText("카페명 : "+str_company_name);
                            Txt_Theme.setText("테마명 : "+str_theme_name);
                        }

                    }

                    Txt_Title.setText(str_title);
                    Txt_Nick.setText(str_nickname);
                    Txt_Date.setText(str_createdAt);
                    Txt_Comment_Count.setText(str_commentCount);
                    Txt_Contents.setText(str_content);

                    //내 게시물의 경우
                    if(str_user_pk.equals(User_Pk)){
                        Layout_Setting.setVisibility(View.VISIBLE);
                    }
                    else{
                        Layout_Setting.setVisibility(View.GONE);
                    }

                    if(str_category.equals("공지")){
                        Img_Grade.setVisibility(View.VISIBLE);
                        Img_Grade.setImageResource(R.drawable.comu_master);
                    }
                    else{
                        if(str_grade.equals("0")){
                            Img_Grade.setVisibility(View.INVISIBLE);
                        }
                        else if(str_grade.equals("1")){
                            Img_Grade.setVisibility(View.VISIBLE);
                            Img_Grade.setImageResource(R.drawable.user_medal_1_10);
                        }
                        else if(str_grade.equals("2")){
                            Img_Grade.setVisibility(View.VISIBLE);
                            Img_Grade.setImageResource(R.drawable.user_medal_11_50);
                        }
                        else if(str_grade.equals("3")){
                            Img_Grade.setVisibility(View.VISIBLE);
                            Img_Grade.setImageResource(R.drawable.user_medal_51_100);
                        }
                        else if(str_grade.equals("4")){
                            Img_Grade.setVisibility(View.VISIBLE);
                            Img_Grade.setImageResource(R.drawable.user_medal_101_200);
                        }
                        else if(str_grade.equals("5")){
                            Img_Grade.setVisibility(View.VISIBLE);
                            Img_Grade.setImageResource(R.drawable.user_medal_201_300);
                        }
                        else if(str_grade.equals("6")){
                            Img_Grade.setVisibility(View.VISIBLE);
                            Img_Grade.setImageResource(R.drawable.user_medal_301_500);
                        }
                        else if(str_grade.equals("7")){
                            Img_Grade.setVisibility(View.VISIBLE);
                            Img_Grade.setImageResource(R.drawable.user_medal_501_1000);
                        }
                        else{
                            Img_Grade.setVisibility(View.VISIBLE);
                            Img_Grade.setImageResource(R.drawable.user_medal_1000);
                        }
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(Focus_Free.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    layoutManager.scrollToPosition(0);

                    //베스트 무료체험 어댑터 셋팅
                    com_comment_adapters = new Com_Comment_Adapter(Focus_Free.this, com_comment_model);
                    List_Review.setLayoutManager(layoutManager);
                    List_Review.setAdapter(com_comment_adapters);

                    Log.i("테스1", "123");
                }
                else{
                    Toast.makeText(Focus_Free.this, parseredData[0], Toast.LENGTH_SHORT).show();
                    finish();
                }
            }catch (Exception e){
                Log.i("테스1", e+"");
            }

            progressDialog.dismiss();
        }
    }
    public class Async_Comment extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[] parseredData;
        String[][] parseredData_list;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Focus_Free.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //홈 업체 리스트 데이터 셋팅
                Http_Post http = new Http_Post();

                String result = "";
                if(replay_id.equals("")){
                    result = http.Http_Post(User_Pk, "v1/boardComment/register", "boardId", str_id, "user_pk", User_Pk, "nickname", str_my_nickname, "grade", str_my_grade, "content", Edit_Review_Content.getText().toString());
                }
                else{
                    String comment = Edit_Review_Content.getText().toString();
                    //comment = comment.replace(comment.substring(0, comment.indexOf(" ")), "");
                    result = http.Http_Post(User_Pk, "v1/boardComment/register", "boardId", str_id, "user_pk", User_Pk, "nickname", str_my_nickname, "grade", str_my_grade, "content", comment, "boardCommentId", replay_id);
                }

                parseredData = jsonParserList_access(result);

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(parseredData[0].equals("success")){
                replay_id = "";
                replay_nick = "";
                Edit_Review_Content.setText("");
                Layout_Comment_Nick.setVisibility(View.GONE);

                Http_Get http1 = new Http_Get();
                String result_comment = http1.Http_Get(User_Pk, "v1/boardComment/list?boardId="+str_id);
                parseredData_list = jsonParserList_comment(result_comment);

                com_comment_model = new ArrayList<Com_Comment_Model>();
                for (int i = 0; i < parseredData_list.length; i++) {
                    String id = parseredData_list[i][0];
                    String boardId = parseredData_list[i][1];
                    String user_pk = parseredData_list[i][2];
                    String nickname = parseredData_list[i][3];
                    String grade = parseredData_list[i][4];
                    String content = parseredData_list[i][5];
                    String depth = parseredData_list[i][6];
                    String createdAt = parseredData_list[i][7];

                    com_comment_model.add(new Com_Comment_Model(Focus_Free.this,id, boardId, user_pk, nickname, grade, content, depth, createdAt));
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(Focus_Free.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                layoutManager.scrollToPosition(0);

                //베스트 무료체험 어댑터 셋팅
                com_comment_adapters = new Com_Comment_Adapter(Focus_Free.this, com_comment_model);
                List_Review.setLayoutManager(layoutManager);
                List_Review.setAdapter(com_comment_adapters);
            }
            progressDialog.dismiss();
        }
    }
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        String[] data;

        public SectionsPagerAdapter(FragmentManager fm, String[] data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = new frag_img();
            Bundle bundle = new Bundle();

            //이미지 URL 동적 전송 ex) 1_1
            String Image_txt = data[position];
            bundle.putString("Image", Image_txt);
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

    public void setDialog_check(){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Home_Focus.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_comu_del, (ViewGroup)findViewById(R.id.root));
        final TextView Txt_Ok = (TextView) layout.findViewById(R.id.btn_ok);
        final TextView Txt_Cancel = (TextView) layout.findViewById(R.id.btn_cancel);
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Focus_Free.this);
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
                Async_Del async_del = new Async_Del();
                async_del.execute();
            }
        });
    }
    public class Async_Del extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[] parseredData;
        String[][] parseredData_list;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Focus_Free.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //홈 업체 리스트 데이터 셋팅
                Http_Del http = new Http_Del();

                String result = http.Http_Del(User_Pk, "v1/board/remove?id="+str_id);
                parseredData = jsonParserList_access(result);

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(parseredData[0].equals("success")){
                if(str_diff.equals("자유게시판")){
                    str_status = "free_re";
                }
                else if(str_diff.equals("일행구하기")){
                    str_status = "join_re";
                }
                else if(str_diff.equals("방탈출정보")){
                    str_status = "info_re";
                }
                else{
                    str_status = "board_re";
                }
               Toast.makeText(Focus_Free.this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
               finish();
               overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
            else{
                Toast.makeText(Focus_Free.this, parseredData[0], Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        }
    }
    public String[] jsonParserList_access(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용1212", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);

            String[] jsonName = {"message"};
            String[] parseredData = new String[jsonName.length];
            for (int j = 0; j < jsonName.length; j++) {
                parseredData[j] = json.getString(jsonName[j]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String[] jsonParserList_access1(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);

            String[] jsonName = {"message", "data"};
            String[] parseredData = new String[jsonName.length];
            for (int j = 0; j < jsonName.length; j++) {
                parseredData[j] = json.getString(jsonName[j]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String[] jsonParserList_info(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);

            String[] jsonName = {"id", "user_pk", "nickname", "grade", "title", "content", "company_name", "theme_name", "diff", "category", "commentCount", "isLike", "createdAt"};
            String[] parseredData = new String[jsonName.length];
            for (int j = 0; j < jsonName.length; j++) {
                parseredData[j] = json.getString(jsonName[j]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String[][] jsonParserList_img(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("boardImages");
            String[] jsonName = {"id", "name"};
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
    public String[][] jsonParserList_comment(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("list");
            String[] jsonName = {"id", "boardId", "user_pk", "nickname", "grade", "content", "depth", "createdAt"};
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
