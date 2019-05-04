package blackcap.nationalescape.Activity.tab4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import blackcap.nationalescape.Adapter.Price_Write_Adapter;
import blackcap.nationalescape.Model.Manager_Img_Model;
import blackcap.nationalescape.Model.Price_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import me.drakeet.materialdialog.MaterialDialog;

public class Manager extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성
    SharedPreferences.Editor editor;

    private ImageView Img_Back;
    private EditText Edit_Location, Edit_Title, Edit_Intro, Edit_Introfocus, Edit_Address, Edit_Address_Focus, Edit_Phone, Edit_BookPage, Edit_HomePage;
    private RecyclerView List_Menu;
    private Button Btn_Menuadd;
    private Button Btn_Mainpicture_Add;
    private ImageView Img_Main, Img_Main_Cancel;

    private Button Btn_Extrapicture_Add;
    private ImageView Img_Sub1, Img_Sub2, Img_Sub3, Img_Sub4, Img_Sub5, Img_Sub6;
    private ImageView Img_Sub1_Cancel, Img_Sub2_Cancel, Img_Sub3_Cancel, Img_Sub4_Cancel, Img_Sub5_Cancel, Img_Sub6_Cancel;

    private TextView Txt_Save;Button Btn_Next;

    private ArrayList<Price_Model> price_models;
    private Price_Write_Adapter price_adapter;

    private String User_Pk = "";
    private String str_address_x = "", str_address_y;
    private ArrayList<String> str_img_path;
    private ArrayList<Manager_Img_Model> manager_img_models;

    private String img_choice = "", str_company_pk = "";

    private boolean bool_filter = false, bool_title = false, bool_intro = false, bool_introfocus = false, bool_menu = false, bool_main_img = false , bool_sub_img = false;
    private boolean bool_address = false, bool_address_focus = false, bool_phone = false, bool_bookpage = false, bool_homepage = false;

    public static Activity act_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_manager);

        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");

        init();
        Async_init async_init = new Async_init();
        async_init.execute();

        setImgBack();
        //지역 셋팅
        setEdit_Location();
        //텍스트 입력 이벤트
        setEdit_TextChage_Event();
        //메뉴 추가 이벤트
        setBtn_Menuadd();
        //메인사진 셋팅
        setBtn_Mainpicture_Add();
        //서브사진 셋팅
        setBtn_SubImg_Add();
        //위치 저장
        setEdit_Address();
        //정보 저장
        setSave();
        //널체크, 다음 버튼 활성화
        setNext_Evnet();
//        Async async = new Async();
//        async.execute(User_Pk);
    }

    public void init(){
        act_manager = Manager.this;

        Img_Back = (ImageView)findViewById(R.id.img_back);
        Edit_Location = (EditText)findViewById(R.id.edit_location);
        Edit_Title = (EditText)findViewById(R.id.edit_title);
        Edit_Intro = (EditText)findViewById(R.id.edit_intro);
        Edit_Introfocus = (EditText)findViewById(R.id.edit_introfocus);
        Edit_Address = (EditText)findViewById(R.id.edit_address);
        Edit_Address_Focus = (EditText)findViewById(R.id.edit_address_focus);
        Edit_Phone = (EditText)findViewById(R.id.edit_phone);
        Edit_BookPage = (EditText)findViewById(R.id.edit_bookpage);
        Edit_HomePage = (EditText)findViewById(R.id.edit_homepage);
        List_Menu = (RecyclerView)findViewById(R.id.list_menu);
        Btn_Menuadd = (Button)findViewById(R.id.btn_menuadd);
        //메뉴 초기화
        price_models = new ArrayList<Price_Model>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(Manager.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        price_adapter = new Price_Write_Adapter(Manager.this, price_models);
        List_Menu.setLayoutManager(layoutManager);
        List_Menu.setAdapter(price_adapter);

        Btn_Mainpicture_Add = (Button)findViewById(R.id.btn_mainpicture_add);
        Img_Main = (ImageView)findViewById(R.id.img_main);Img_Main.setVisibility(View.GONE);
        Img_Main_Cancel = (ImageView)findViewById(R.id.img_main_cancel);Img_Main_Cancel.setVisibility(View.GONE);
        str_img_path = new ArrayList<String>();
        str_img_path.add(0, "");
        manager_img_models = new ArrayList<Manager_Img_Model>();
        manager_img_models.add(new Manager_Img_Model(0,"", ""));

        Btn_Extrapicture_Add = (Button)findViewById(R.id.btn_extrapicture_add);
        Img_Sub1 = (ImageView)findViewById(R.id.img_sub1);Img_Sub1.setVisibility(View.GONE);
        Img_Sub2 = (ImageView)findViewById(R.id.img_sub2);Img_Sub2.setVisibility(View.GONE);
        Img_Sub3 = (ImageView)findViewById(R.id.img_sub3);Img_Sub3.setVisibility(View.GONE);
        Img_Sub4 = (ImageView)findViewById(R.id.img_sub4);Img_Sub4.setVisibility(View.GONE);
        Img_Sub5 = (ImageView)findViewById(R.id.img_sub5);Img_Sub5.setVisibility(View.GONE);
        Img_Sub6 = (ImageView)findViewById(R.id.img_sub6);Img_Sub6.setVisibility(View.GONE);

        Img_Sub1_Cancel = (ImageView)findViewById(R.id.img_sub1_cancel);Img_Sub1_Cancel.setVisibility(View.GONE);
        Img_Sub2_Cancel = (ImageView)findViewById(R.id.img_sub2_cancel);Img_Sub2_Cancel.setVisibility(View.GONE);
        Img_Sub3_Cancel = (ImageView)findViewById(R.id.img_sub3_cancel);Img_Sub3_Cancel.setVisibility(View.GONE);
        Img_Sub4_Cancel = (ImageView)findViewById(R.id.img_sub4_cancel);Img_Sub4_Cancel.setVisibility(View.GONE);
        Img_Sub5_Cancel = (ImageView)findViewById(R.id.img_sub5_cancel);Img_Sub5_Cancel.setVisibility(View.GONE);
        Img_Sub6_Cancel = (ImageView)findViewById(R.id.img_sub6_cancel);Img_Sub6_Cancel.setVisibility(View.GONE);

        Txt_Save = (TextView)findViewById(R.id.txt_save);
        Btn_Next = (Button)findViewById(R.id.btn_next);

    }
    public void setEdit_Location(){
        Edit_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog_Location();
            }
        });
        Edit_Location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Edit_Location.getText().toString().equals("")){
                    bool_filter = false;
                    setNext_Evnet();
                }
                else{
                    bool_filter = true;
                    setNext_Evnet();
                }

            }
        });
    }
    public void setDialog_Location(){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Manager.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_manager_location, (ViewGroup)findViewById(R.id.Customdialog_Location_Root));
        final TextView Txt_All  = (TextView) layout.findViewById(R.id.txt_all);
        final TextView Txt_seoulall  = (TextView) layout.findViewById(R.id.txt_seoulall);
        final TextView Txt_hongdae  = (TextView) layout.findViewById(R.id.txt_hongdae);
        final TextView Txt_gangnam  = (TextView) layout.findViewById(R.id.txt_gangnam);
        final TextView Txt_geondae  = (TextView) layout.findViewById(R.id.txt_geondae);
        final TextView Txt_gyeonggi  = (TextView) layout.findViewById(R.id.txt_gyeonggi);
        final TextView Txt_bupyeong  = (TextView) layout.findViewById(R.id.txt_bupyeong);
        final TextView Txt_incheon  = (TextView) layout.findViewById(R.id.txt_incheon);
        final TextView Txt_suwon  = (TextView) layout.findViewById(R.id.txt_suwon);
        final TextView Txt_ansan  = (TextView) layout.findViewById(R.id.txt_ansan);
        final TextView Txt_busan  = (TextView) layout.findViewById(R.id.txt_busan);
        final TextView Txt_daegu  = (TextView) layout.findViewById(R.id.txt_daegu);
        final TextView Txt_ulsan  = (TextView) layout.findViewById(R.id.txt_ulsan);
        final TextView Txt_daejeon  = (TextView) layout.findViewById(R.id.txt_daejeon);
        final TextView Txt_gwangju  = (TextView) layout.findViewById(R.id.txt_gwangju);
        final TextView Txt_gyeongsang  = (TextView) layout.findViewById(R.id.txt_gyeongsang);
        final TextView Txt_jeonla  = (TextView) layout.findViewById(R.id.txt_jeonla);
        final TextView Txt_chungcheong  = (TextView) layout.findViewById(R.id.txt_chungcheong);
        final TextView Txt_gangwon  = (TextView) layout.findViewById(R.id.txt_gangwon);
        final TextView Txt_jeju  = (TextView) layout.findViewById(R.id.txt_jeju);

        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Manager.this);
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();
        Txt_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Location.setText("전체");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_seoulall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("서울");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_hongdae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("홍대");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_gangnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("강남");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_geondae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("건대");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_gyeonggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("경기");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_bupyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("부평");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_incheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("인천");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_suwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("수원");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_ansan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("안산");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("부산");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_daegu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("대구");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_ulsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("울산");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_daejeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("대전");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_gwangju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("광주");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_gyeongsang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("경상");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_jeonla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("전라");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_chungcheong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("충청");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_gangwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("강원");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_jeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Location.setText("제주");
                TeamInfo_Dialog.dismiss();
            }
        });
    }
    public void setBtn_Menuadd(){
        Btn_Menuadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price_models.add(new Price_Model(Manager.this, "", ""));
                price_adapter.notifyDataSetChanged();
            }
        });
    }
    public void setBtn_Mainpicture_Add(){
        Btn_Mainpicture_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사진 읽어오기위한 uri 작성하기.
                Uri uri = Uri.parse("content://media/external/images/media");
                //무언가 보여달라는 암시적 인텐트 객체 생성하기.
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //인텐트에 요청을 덛붙인다.
                intent.setAction(Intent.ACTION_PICK);
                //모든 이미지
                intent.setType("image/*");
                //결과값을 받아오는 액티비티를 실행한다.
                startActivityForResult(intent, 0);
                img_choice = "main";

            }
        });
        Img_Main_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Main.setVisibility(View.GONE);
                Img_Main_Cancel.setVisibility(View.GONE);
                manager_img_models.set(0,new Manager_Img_Model( 0 , "", ""));
            }
        });
    }
    public void setBtn_SubImg_Add(){
        Btn_Extrapicture_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사진 읽어오기위한 uri 작성하기.
                Uri uri = Uri.parse("content://media/external/images/media");
                //무언가 보여달라는 암시적 인텐트 객체 생성하기.
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //인텐트에 요청을 덛붙인다.
                intent.setAction(Intent.ACTION_PICK);
                //모든 이미지
                intent.setType("image/*");
                //결과값을 받아오는 액티비티를 실행한다.
                startActivityForResult(intent, 0);
                img_choice = "sub";
            }
        });
        Img_Sub1_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager_img_models.remove(1);
                setSideImage_Relocation();
            }
        });
        Img_Sub2_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager_img_models.remove(2);
                setSideImage_Relocation();
            }
        });
        Img_Sub3_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager_img_models.remove(3);
                setSideImage_Relocation();
            }
        });
        Img_Sub4_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager_img_models.remove(4);
                setSideImage_Relocation();
            }
        });
        Img_Sub5_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager_img_models.remove(5);
                setSideImage_Relocation();
            }
        });
        Img_Sub6_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager_img_models.remove(6);
                setSideImage_Relocation();
            }
        });
    }
    public void setEdit_Address(){
        Edit_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager.this, Manager_Address.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }
    public void setEdit_TextChage_Event(){
        Edit_Title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Edit_Title.getText().toString().equals("")){
                    bool_title = false;
                }
                else{
                    bool_title = true;
                }

            }
        });
        Edit_Intro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Edit_Intro.getText().toString().equals("")){
                    bool_intro = false;
                }
                else{
                    bool_intro = true;
                }
            }
        });
        Edit_Introfocus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Edit_Introfocus.getText().toString().equals("")){
                    bool_introfocus = false;
                }
                else{
                    bool_introfocus = true;
                }
            }
        });
    }
    public Location findGeoPoint(Context mcontext, String address) {
        Location loc = new Location("");
        Geocoder coder = new Geocoder(mcontext);
        List<Address> addr = null;// 한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 설정

        try {
            addr = coder.getFromLocationName(address, 5);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 몇개 까지의 주소를 원하는지 지정 1~5개 정도가 적당
        if (addr != null) {
            for (int i = 0; i < addr.size(); i++) {
                Address lating = addr.get(i);
                double lat = lating.getLatitude(); // 위도가져오기
                double lon = lating.getLongitude(); // 경도가져오기
                loc.setLatitude(lat);
                loc.setLongitude(lon);
            }
        }
        return loc;
    }
    public void setSave(){
        Txt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async_save async_save = new Async_save();
                async_save.execute();
            }
        });
    }
    public void setNext_Evnet(){
        Btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bool_filter == false){
                    Toast.makeText(Manager.this, "필터를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(bool_title == false){
                        Toast.makeText(Manager.this, "타이틀을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(bool_intro == false){
                            Toast.makeText(Manager.this, "한줄 설명을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(bool_introfocus == false){
                                Toast.makeText(Manager.this, "업체 소개를 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(price_models.get(0).getTitle().equals("") || price_models.get(0).getPrice().equals("")){
                                    Toast.makeText(Manager.this, "메뉴를 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    if(Img_Main.getVisibility() == View.GONE){
                                        Toast.makeText(Manager.this, "메인이미지를 입력해주세요", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        if(Img_Sub1.getVisibility() == View.GONE){
                                            Toast.makeText(Manager.this, "1개 이상의 상세이미지를 입력해주세요", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            if(Edit_Address.getText().toString().equals("")){
                                                Toast.makeText(Manager.this, "주소를 입력해주세요", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                if(Edit_Address_Focus.getText().toString().equals("")){
                                                    Toast.makeText(Manager.this, "상세주소를 입력해주세요", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    if(Edit_Phone.getText().toString().equals("")){
                                                        Toast.makeText(Manager.this, "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{
                                                        if(Edit_BookPage.getText().toString().equals("")){
                                                            Toast.makeText(Manager.this, "예약페이지를 입력해주세요", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else{
                                                            if(Edit_HomePage.getText().toString().equals("")){
                                                                Toast.makeText(Manager.this, "홈페이지를 입력해주세요", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else{
                                                                Async_save async_save = new Async_save();
                                                                async_save.execute();

                                                                Async_next async_next = new Async_next();
                                                                async_next.execute();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        });
    }
    public class Async_init extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData, parseredData_menu, parseredData_img;

        private String filter = "", title = "", intro_focus = "", address = "", address_x = "", address_y = "", phone = "", bookpage = "", homepage = "", address_focus = "", company_pk = "", intro="";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Manager.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                HttpClient http = new HttpClient();

                String result = http.HttpClient("Web_Escape", "Manager_init.jsp", User_Pk);
                JsonParserList jsonParserList = new JsonParserList();
                parseredData = jsonParserList.jsonParserList_Data12(result);

                if(parseredData[0][0].equals("null")) {
                    parseredData_img = new String[1][1];
                    parseredData_img[0][0] = "";
                }
                else{
                    filter = parseredData[0][0];
                    title = parseredData[0][1];
                    intro_focus = parseredData[0][2];
                    address = parseredData[0][3];
                    address_x = parseredData[0][4];
                    address_y = parseredData[0][5];
                    phone = parseredData[0][6];
                    bookpage = parseredData[0][7];
                    homepage = parseredData[0][8];
                    address_focus = parseredData[0][9];
                    company_pk = parseredData[0][10];
                    intro = parseredData[0][11];

                    String result2 = http.HttpClient("Web_Escape", "Manager_init_img.jsp", company_pk);
                    parseredData_img = jsonParserList.jsonParserList_Data1(result2);

                    String result1 = http.HttpClient("Web_Escape", "Manager_init_menu.jsp", company_pk);
                    parseredData_menu = jsonParserList.jsonParserList_Data2(result1);
                    for(int i = 0; i< parseredData_menu.length; i ++){
                        price_models.add(new Price_Model(Manager.this, parseredData_menu[i][0], parseredData_menu[i][1]));
                    }
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

            if(!filter.equals("")){
                Edit_Location.setText(filter);
            }
            if(!title.equals("")){
                Edit_Title.setText(title);
            }
            if(!intro_focus.equals("")){
                Edit_Introfocus.setText(intro_focus);
            }
            if(!intro.equals("")){
                Edit_Intro.setText(intro);
            }
            if(!address.equals("")){
                Edit_Address.setText(address);
                str_address_x = address_x;
                str_address_y = address_y;
            }
            if(!address_focus.equals("")){
                Edit_Address_Focus.setText(address_focus);
            }
            if(!phone.equals("")){
                Edit_Phone.setText(phone);
            }
            if(!bookpage.equals("")){
                Edit_BookPage.setText(bookpage);
            }
            if(!homepage.equals("")){
                Edit_HomePage.setText(homepage);
            }
            //메뉴 셋팅
            price_models.add(new Price_Model(Manager.this, "", ""));
            price_adapter.notifyDataSetChanged();

            if(!parseredData_img[0][0].equals("")){
                if(!parseredData_img[0][0].contains("_")){
                    //메인사진
                    img_choice = "main";
                    //setImage_Location("url", getBitmapFromURL("http://www.yologuys.com/Escape_img/company/"+parseredData_img[0][0]+".jpg"));
                    //str_img_path.set(0, "http://www.yologuys.com/Escape_img/company/"+parseredData_img[0][0]+".jpg");
                    manager_img_models.set(0,new Manager_Img_Model( 0 , "sever", "http://www.yologuys.com/Escape_img/company/"+parseredData_img[0][0]+".jpg"));
                }
                for(int i = 1; i< parseredData_img.length; i ++){
                    if(parseredData_img[i][0].contains("_")){
                        img_choice = "sub";
                        //setImage_Location("url", getBitmapFromURL("http://www.yologuys.com/Escape_img/company_focus/"+parseredData_img[i][0]+".jpg"));
                        //str_img_path.add("http://www.yologuys.com/Escape_img/company_focus/"+parseredData_img[i][0]+".jpg");
                        manager_img_models.add(new Manager_Img_Model(i , "sever", "http://www.yologuys.com/Escape_img/company_focus/"+parseredData_img[i][0]+".jpg"));
                    }
                }
            }

            setSideImage_Relocation();

            progressDialog.dismiss();
        }
    }
    public class Async_save extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Manager.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                HttpClient http = new HttpClient();
                String result_1 = http.HttpClient("Web_Escape", "Manager_Save.jsp", User_Pk, Edit_Location.getText().toString(), Edit_Title.getText().toString(), Edit_Intro.getText().toString(), Edit_Introfocus.getText().toString(), Edit_Address.getText().toString(), Edit_Address_Focus.getText().toString(), str_address_x, str_address_y, Edit_Phone.getText().toString(), Edit_BookPage.getText().toString(), Edit_HomePage.getText().toString());
                JsonParserList jsonParserList = new JsonParserList();
                parseredData = jsonParserList.jsonParserList_Data1(result_1);
                str_company_pk = parseredData[0][0];

                //메뉴 초기화
                http.HttpClient("Web_Escape", "Manager_Save_Menu_init.jsp", str_company_pk);
                //메뉴 추가
                for(int i = 0 ; i< price_models.size(); i++){
                    if(!price_models.get(i).getTitle().equals("") && !price_models.get(i).getPrice().equals("")){
                        http.HttpClient("Web_Escape", "Manager_Save_Menu.jsp", str_company_pk, price_models.get(i).getTitle(), price_models.get(i).getPrice());
                    }
                }

                http.HttpClient("Web_Escape", "Manager_Save_img_init.jsp", str_company_pk);
                if(!manager_img_models.get(0).getPath().equals("")){
                    http.HttpClient("Web_Escape", "Manager_Save_img_main.jsp", str_company_pk);
                }

                for(int i = 1 ; i< manager_img_models.size(); i++){
                    http.HttpClient("Web_Escape", "Manager_Save_img_sub.jsp", str_company_pk, i+"");
                }
                //menu response
                HttpFileUpload("http://www.yologuys.com/Web_Escape/Img_Upload_Main.jsp", 0,manager_img_models.get(0).getPath());
                for(int i = 1 ; i < manager_img_models.size(); i++){
                    //웹 url인 경우, 사진 저장 후 업로드
                    if(manager_img_models.get(i).getCategory().equals("sever")){
                        //서버인경우
                        if(i != manager_img_models.get(i).getSever_Index()){
                            String oldfilename = str_company_pk+"_"+manager_img_models.get(i).getSever_Index();
                            String newfilename = str_company_pk+"_"+i;

                            Log.i("파일1", oldfilename+".jpg");
                            Log.i("파일2", newfilename+".jpg");
                            http.HttpClient("Web_Escape", "Img_Rename.jsp", oldfilename, newfilename);
                        }
                    }
                    //앨범 path인 경우
                    else{
                        HttpFileUpload("http://www.yologuys.com/Web_Escape/Img_Upload_Side.jsp", i,manager_img_models.get(i).getPath());
                    }

                }
                //String result = http.HttpClient("Web_Escape", "Manager_approval.jsp", User_Pk, setNowTime());
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
            Toast.makeText(Manager.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
        }
    }
    public class Async_next extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Manager.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                HttpClient http = new HttpClient();
                http.HttpClient("Web_Escape", "Manager_approval.jsp", User_Pk, setNowTime());
                Intent intent = new Intent(Manager.this, Manager_Succed.class);
                intent.putExtra("user_pk", User_Pk);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
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
    private void setImgBack() {
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        super.onBackPressed();
    }
    public void setSideImage_Relocation(){
        //메인 이미지 초기화
        Img_Main.setVisibility(View.GONE);
        Img_Main_Cancel.setVisibility(View.GONE);
        //서브 이미지 초기화
        for(int i = 1 ; i < 7; i++){
            int resId_img = getResources().getIdentifier("img_sub" + (i), "id", getPackageName());
            int resId_cancel = getResources().getIdentifier("img_sub" + (i)+"_cancel", "id", getPackageName());
            ImageView img = (ImageView)findViewById(resId_img);
            ImageView img_cancel = (ImageView)findViewById(resId_cancel);
            img.setVisibility(View.GONE);
            img_cancel.setVisibility(View.GONE);
        }

        //메인 이미지 셋팅
        if(!manager_img_models.get(0).getPath().equals("")){
            Img_Main.setVisibility(View.VISIBLE);
            Img_Main_Cancel.setVisibility(View.VISIBLE);

            if(manager_img_models.get(0).getCategory().equals("sever")){
                Log.i("테스트123", manager_img_models.get(0).getPath());
                Img_Main.setImageBitmap(getBitmapFromURL(manager_img_models.get(0).getPath()));
            }
            else{
                Bitmap myBitmap = BitmapFactory.decodeFile(manager_img_models.get(0).getPath());
                Img_Main.setImageBitmap(myBitmap);
            }
        }

        for(int i = 1 ;i < manager_img_models.size(); i ++){
            int resId_img = getResources().getIdentifier("img_sub" + (i), "id", getPackageName());
            int resId_cancel = getResources().getIdentifier("img_sub" + (i)+"_cancel", "id", getPackageName());
            ImageView img = (ImageView)findViewById(resId_img);
            ImageView img_cancel = (ImageView)findViewById(resId_cancel);
            img.setVisibility(View.VISIBLE);
            img_cancel.setVisibility(View.VISIBLE);

            if(manager_img_models.get(i).getCategory().equals("sever")){
                Log.i("테스트", manager_img_models.get(i).getPath());
                img.setImageBitmap(getBitmapFromURL(manager_img_models.get(i).getPath()));
            }
            else{
                Bitmap myBitmap = BitmapFactory.decodeFile(manager_img_models.get(i).getPath());
                img.setImageBitmap(myBitmap);
            }
        }
    }
    public void setImage_Location(String absolutePath, Bitmap selPhoto){
        if(img_choice.equals("main")){
            manager_img_models.set(0,new Manager_Img_Model( 0 , "client", absolutePath));
            Img_Main.setVisibility(View.VISIBLE);
            Img_Main_Cancel.setVisibility(View.VISIBLE);
            Img_Main.setImageBitmap(selPhoto);
        }
        else{
            if(Img_Sub1.getVisibility() == View.GONE){
                Img_Sub1.setVisibility(View.VISIBLE);
                Img_Sub1_Cancel.setVisibility(View.VISIBLE);
                Img_Sub1.setImageBitmap(selPhoto);
            }
            else if(Img_Sub2.getVisibility() == View.GONE){
                Img_Sub2.setVisibility(View.VISIBLE);
                Img_Sub2_Cancel.setVisibility(View.VISIBLE);
                Img_Sub2.setImageBitmap(selPhoto);
            }
            else if(Img_Sub3.getVisibility() == View.GONE){
                Img_Sub3.setVisibility(View.VISIBLE);
                Img_Sub3_Cancel.setVisibility(View.VISIBLE);
                Img_Sub3.setImageBitmap(selPhoto);
            }
            else if(Img_Sub4.getVisibility() == View.GONE){
                Img_Sub4.setVisibility(View.VISIBLE);
                Img_Sub4_Cancel.setVisibility(View.VISIBLE);
                Img_Sub4.setImageBitmap(selPhoto);
            }
            else if(Img_Sub5.getVisibility() == View.GONE){
                Img_Sub5.setVisibility(View.VISIBLE);
                Img_Sub5_Cancel.setVisibility(View.VISIBLE);
                Img_Sub5.setImageBitmap(selPhoto);
            }
            else if(Img_Sub6.getVisibility() == View.GONE){
                Img_Sub6.setVisibility(View.VISIBLE);
                Img_Sub6_Cancel.setVisibility(View.VISIBLE);
                Img_Sub6.setImageBitmap(selPhoto);
            }
            manager_img_models.add(new Manager_Img_Model( 0 , "client", absolutePath));
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == 1) {
            String address_num = intent.getStringExtra("Address_Num");
            String address_txt = intent.getStringExtra("Address_Txt");
            Edit_Address.setText(address_txt);

            str_address_x = String.format("%.7f", findGeoPoint(Manager.this, Edit_Address.getText().toString()).getLatitude());
            str_address_y = String.format("%.7f", findGeoPoint(Manager.this, Edit_Address.getText().toString()).getLongitude());
        }
        else{
            try {
                //인텐트에 데이터가 담겨 왔다면
                if (!intent.getData().equals(null)) {
                    //해당경로의 이미지를 intent에 담긴 이미지 uri를 이용해서 Bitmap형태로 읽어온다.
                    Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());

                    //이미지의 크기 조절하기.
                    selPhoto = Bitmap.createScaledBitmap(selPhoto, 100, 100, true);
                    //image_bt.setImageBitmap(selPhoto);//썸네일
                    //화면에 출력해본다.
                    //Profile_ImageVIew_Profile.setImageBitmap(selPhoto);
                    Log.e("선택 된 이미지 ", "selPhoto : " + selPhoto);

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    //선택한 이미지의 uri를 읽어온다.
                    Uri selPhotoUri = intent.getData();
                    Log.e("전송", "시~~작 ~~~~~!");
                    //업로드할 서버의 url 주소
                    String urlString = "";
                    urlString = "http://13.124.32.32/Web_Escape/Img_Upload_Main.jsp";
                    //절대경로를 획득한다!!! 중요~
                    Cursor c = getContentResolver().query(Uri.parse(selPhotoUri.toString()), null, null, null, null);
                    c.moveToNext();
                    //업로드할 파일의 절대경로 얻어오기("_data") 로 해도 된다.
                    String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                    Log.e("###파일의 절대 경로###", absolutePath);

                    setImage_Location(absolutePath, selPhoto);
                }
            } catch (FileNotFoundException e) {
                Log.e("tt",e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("tt",e.toString());
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.e("tt",e.toString());
            }
        }
    }

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    public void HttpFileUpload(String urlString, int params, String fileName) {

        try {
            //선택한 파일의 절대 경로를 이용해서 파일 입력 스트림 객체를 얻어온다.
            FileInputStream mFileInputStream = new FileInputStream(fileName);
            //파일을 업로드할 서버의 url 주소를이용해서 URL 객체 생성하기.
            URL connectUrl = new URL(urlString);
            //Connection 객체 얻어오기.
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);//입력할수 있도록
            conn.setDoOutput(true); //출력할수 있도록
            conn.setUseCaches(false);  //캐쉬 사용하지 않음

            //post 전송
            conn.setRequestMethod("POST");
            //파일 업로드 할수 있도록 설정하기.
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            //DataOutputStream 객체 생성하기.
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            //전송할 데이터의 시작임을 알린다.
            //String En_TeamName = URLEncoder.encode(TeamName, "utf-8");

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            if(params == 0){
                dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + URLEncoder.encode(str_company_pk, "utf-8")+".jpg" + "\"" + lineEnd);
            }
            else{
                dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + URLEncoder.encode(str_company_pk, "utf-8") + "_"+params+".jpg" + "\"" + lineEnd);
            }

            dos.writeBytes(lineEnd);
            //한번에 읽어들일수있는 스트림의 크기를 얻어온다.
            int bytesAvailable = mFileInputStream.available();
            //byte단위로 읽어오기 위하여 byte 배열 객체를 준비한다.
            byte[] buffer = new byte[bytesAvailable];
            int bytesRead = 0;
            // read image
            while (bytesRead != -1) {
                //파일에서 바이트단위로 읽어온다.
                bytesRead = mFileInputStream.read(buffer);
                if (bytesRead == -1) break; //더이상 읽을 데이터가 없다면 빠저나온다.
                Log.d("Test", "image byte is " + bytesRead);
                //읽은만큼 출력한다.
                dos.write(buffer, 0, bytesRead);
                //출력한 데이터 밀어내기
                dos.flush();
            }
            //전송할 데이터의 끝임을 알린다.
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            //flush() 타이밍??
            //dos.flush();
            dos.close();//스트림 닫아주기
            mFileInputStream.close();//스트림 닫아주기.
            // get response
            int ch;
            //입력 스트림 객체를 얻어온다.
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            Log.e("Test", "result = " + s);

        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
        }
    }

    //웹 이미지의 경우 이미지 저장 후 업로드
    private class UrlImage_upload extends AsyncTask<String, Void, Void> {
        /**
         * 파일명
         */
        private String fileName;
        /**
         * 저장할 폴더
         */
        private final String SAVE_FOLDER = "/national_escape";
        @Override
        protected Void doInBackground(String... params) {
            //다운로드 경로를 지정
            String savePath = Environment.getExternalStorageDirectory().toString() + SAVE_FOLDER;
            File dir = new File(savePath);
            //상위 디렉토리가 존재하지 않을 경우 생성
            if (!dir.exists()) {
                dir.mkdirs();
            }


            //파일 이름 :날짜_시간
            Date day = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA);
            fileName = String.valueOf(sdf.format(day));
            //웹 서버 쪽 파일이 있는 경로
            String fileUrl = params[0];
            //다운로드 폴더에 동일한 파일명이 존재하는지 확인
            if (new File(savePath + "/" + fileName).exists() == false) {
            } else {
            }
            String localPath = savePath + "/" + fileName + ".jpg";
            try {
                URL imgUrl = new URL(fileUrl);
                //서버와 접속하는 클라이언트 객체 생성
                HttpURLConnection conn = (HttpURLConnection)imgUrl.openConnection();
                int len = conn.getContentLength();
                byte[] tmpByte = new byte[len];
                //입력 스트림을 구한다
                InputStream is = conn.getInputStream();
                File file = new File(localPath);
                //파일 저장 스트림 생성
                FileOutputStream fos = new FileOutputStream(file);
                int read;
                //입력 스트림을 파일로 저장
                for (;;) {
                    read = is.read(tmpByte);
                    if (read <= 0) {
                        break;
                    }
                    fos.write(tmpByte, 0, read); //file 생성
                }
                is.close();
                fos.close();
                conn.disconnect();

                //저장한 이미지 열기
                String targetDir = Environment.getExternalStorageDirectory().toString() + SAVE_FOLDER;
                String absoul_path = targetDir + "/" + fileName + ".jpg";

                HttpFileUpload("http://www.yologuys.com/Web_Escape/Img_Upload_Side.jsp", Integer.parseInt(params[1]),absoul_path);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);

        }
    }

    public Bitmap getBitmapFromURL(String src) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(src);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true); connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally{
            if(connection!=null)connection.disconnect();
        }
    }
    public String setNowTime(){
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddhhmm", Locale.KOREA);

        return dataFormat.format(date);
    }
}



