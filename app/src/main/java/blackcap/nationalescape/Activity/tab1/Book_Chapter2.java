package blackcap.nationalescape.Activity.tab1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import blackcap.nationalescape.Adapter.Book_Theme_Adapter;
import blackcap.nationalescape.Adapter.Tab2_Focus_Theme_Adapter;
import blackcap.nationalescape.Model.Book_ThemeTime_Model;
import blackcap.nationalescape.Model.Book_Theme_Model;
import blackcap.nationalescape.Model.Theme_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Book_Chapter2 extends AppCompatActivity {
    public static String str_book_chapter2_date = "20190608";
    private String str_company_pk = "461";

    private RecyclerView List_Theme;
    private ArrayList<Book_Theme_Model> theme_models;
    private Book_Theme_Adapter book_theme_adapter;

    private FlowLayout flowLayout_time;
    public static TagFlowLayout fl_category;

    public static TagAdapter book_chapter2_time_adapter;
    public static LayoutInflater mInflater;
    public static String[] mVals = null;
    public static ArrayList<Book_ThemeTime_Model> book_themeTime_models;

    private TextView Txt_Company;
    private String str_company_title = "", str_theme_pk = "" , str_theme_title = "";

    public static Activity Activity_Book_Chapter2;

    private ImageView Img_Next, Img_Back;
    Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_chapter2);

        fl_category = (TagFlowLayout)findViewById(R.id.flowlaout_time);

        intent1 = getIntent();
        mInflater = LayoutInflater.from(Book_Chapter2.this);
        str_company_pk = intent1.getStringExtra("Company_Pk");
        str_company_title = intent1.getStringExtra("Company_Title");
        if(intent1.hasExtra("Theme_Pk") == true){
            str_theme_pk = intent1.getStringExtra("Theme_Pk");
            str_theme_title = intent1.getStringExtra("Theme_Title");
        }
        str_book_chapter2_date = intent1.getStringExtra("Date");

        init();
        setImgBack();
        Async async = new Async();
        async.execute();
    }
    public void init(){
        Activity_Book_Chapter2 = Book_Chapter2.this;
        List_Theme = (RecyclerView)findViewById(R.id.list_theme);
        Txt_Company = (TextView)findViewById(R.id.txt_company);
        Txt_Company.setText(str_company_title);
        Img_Next = (ImageView)findViewById(R.id.img_next);
        Img_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_theme_pk = "";
                String str_theme_title = "";
                String str_time = "";

                for(int i = 0 ; i < theme_models.size(); i++){
                    if(theme_models.get(i).getFlag_Click().equals("true")){
                        str_theme_pk = theme_models.get(i).getTheme_Pk();
                        str_theme_title = theme_models.get(i).getTitle();
                    }
                }
                for(int i = 0 ; i < book_themeTime_models.size(); i++){
                    if(book_themeTime_models.get(i).getSelect().equals("true")){
                        str_time = book_themeTime_models.get(i).getTime();
                    }
                }

                if(str_time.equals("")){
                    Toast.makeText(Book_Chapter2.this, "시간을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(Book_Chapter2.this, Book_Chapter3.class);
                    intent.putExtra("Company_Pk", str_company_pk);
                    intent.putExtra("Company_Title", str_company_title);
                    intent.putExtra("Theme_Pk", str_theme_pk);
                    intent.putExtra("Theme_Title", str_theme_title);
                    intent.putExtra("Time", str_time);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
        book_themeTime_models = new ArrayList();
    }
    public class Async extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;

        private String[][] parseredData_theme, parseredData_time;
        private String str_introfocus = "", str_x = "", str_y = "",str_nickname = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Book_Chapter2.this,"","",true,true,null);
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

                //테마 데이터 셋팅
                String result_theme = http.HttpClient("Web_Escape", "Home_Focus_Theme.jsp", str_company_pk);
                parseredData_theme = jsonParserList.jsonParserList_Data11(result_theme);

                String first_theme_pk = "";
                theme_models = new ArrayList<Book_Theme_Model>();
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
                    if(intent1.hasExtra("Theme_Pk") == true){
                        if(str_theme_pk.equals(theme_pk)){
                            theme_models.add(new Book_Theme_Model(Book_Chapter2.this,theme_pk, company_pk, img, title, intro, category, grade, level, person, tool, activity, "true"));
                            first_theme_pk = theme_pk;
                        }
                        else{
                            theme_models.add(new Book_Theme_Model(Book_Chapter2.this,theme_pk, company_pk, img, title, intro, category, grade, level, person, tool, activity, "false"));
                        }
                    }else{
                        if(i == 1){
                            theme_models.add(new Book_Theme_Model(Book_Chapter2.this,theme_pk, company_pk, img, title, intro, category, grade, level, person, tool, activity, "true"));
                            first_theme_pk = theme_pk;
                        }
                        else{
                            theme_models.add(new Book_Theme_Model(Book_Chapter2.this,theme_pk, company_pk, img, title, intro, category, grade, level, person, tool, activity, "false"));
                        }
                    }
                }

                //타임 셋팅
                String result_time = http.HttpClient("Web_Escape", "Book_Timelist_v2.jsp", first_theme_pk, str_book_chapter2_date);
                parseredData_time = jsonParserList.jsonParserList_Data2(result_time);
                Log.i("테스트", result_time);
                for (int i = 0; i < parseredData_time.length; i++) {
                    String str_time = parseredData_time[i][0];
                    String str_possible = parseredData_time[i][1];
                    book_themeTime_models.add(new Book_ThemeTime_Model(Book_Chapter2.this,str_time,str_possible,"false"));

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


            //리스트 뷰 세로 방향으로
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(Book_Chapter2.this);
            layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
            layoutManager2.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            book_theme_adapter = new Book_Theme_Adapter(Book_Chapter2.this, theme_models);
            List_Theme.setLayoutManager(layoutManager2);
            List_Theme.setAdapter(book_theme_adapter);

            progressDialog.dismiss();

            initFlowLayout(book_themeTime_models);
        }
    }

    public static void initFlowLayout(ArrayList<Book_ThemeTime_Model> data){
        mVals = new String[data.size()];
        int size = 0;
        for (Book_ThemeTime_Model str : data) {
            mVals[size++] = str.getTime();
        }

        book_chapter2_time_adapter = new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.item_category, fl_category, false);
                tv.setText(s);

                if(book_themeTime_models.get(position).getFlag().equals("true")){
                    tv.setBackground(Activity_Book_Chapter2.getResources().getDrawable(R.drawable.frame_booktime_click));
                    if(book_themeTime_models.get(position).getSelect().equals("true")){
                        tv.setBackground(Activity_Book_Chapter2.getResources().getDrawable(R.drawable.frame_booktime_click));
                    }
                    else{
                        tv.setBackground(Activity_Book_Chapter2.getResources().getDrawable(R.drawable.frame_booktime));
                    }
                    tv.setTextColor(Activity_Book_Chapter2.getResources().getColor(R.color.black));
                    tv.setEnabled(true);
                }
                else{
                    tv.setBackground(Activity_Book_Chapter2.getResources().getDrawable(R.drawable.frame_booktime_already));
                    tv.setTextColor(Color.parseColor("#cbcbcb"));
                    tv.setEnabled(false);
                }
                return tv;
            }

            public void onSelected(int position, View view) {
                super.onSelected(position, view);
//                adapter.setSelected(position, view);

                for(int i = 0 ; i < book_themeTime_models.size(); i ++){
                    if(book_themeTime_models.get(i).getFlag().equals("true")){
                        if(i == position){
                            book_themeTime_models.get(i).setSelect("true");
                        }else{
                            book_themeTime_models.get(i).setSelect("false");
                        }
                    }
                }
                book_chapter2_time_adapter.notifyDataChanged();
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
//                adapter.unSelected(position, view);
               // view.setBackground(Activity_Book_Chapter2.getResources().getDrawable(R.drawable.frame_booktime));
            }
        };
        fl_category.setAdapter(book_chapter2_time_adapter);
    }
    private void setImgBack() {
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }
}

