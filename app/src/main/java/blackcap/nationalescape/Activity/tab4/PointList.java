package blackcap.nationalescape.Activity.tab4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import blackcap.nationalescape.Adapter.Point_Adapter;
import blackcap.nationalescape.Model.Point_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import me.drakeet.materialdialog.MaterialDialog;

public class PointList extends AppCompatActivity {
    private SharedPreferences preferences; //캐쉬 데이터 생성
    private SharedPreferences.Editor editor;
    public boolean view = false;
    private ArrayList<Point_Model> point_models;
    private Point_Adapter point_adapter;

    private LinearLayout Layout_Login, Layout_UnLogin;
    private TextView Txt_MyPoint;
    private Button Img_Exchange;
    private RecyclerView List_PointList;
    private TextView Txt_Date;
    private ImageView Img_Date_Left, Img_Date_Right;
    private ImageView Img_Back;
    private ImageView Img_PointUse;

    private String User_Pk = "", str_point = "";
    private String _date_year = "", _date_month = "";

    private String use_code = "", use_point = "", use_title = "";
    private Dialog TeamInfo_Dialog, Check_Dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_user_pointlist);


        //변수 초기화
        init();
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");;

        Async async = new Async();
        async.execute();


        setDate_Event();
        setImgBack();
    }
    public void init(){

        _date_year = setNowTime().substring(0, 4);
        _date_month = setNowTime().substring(4, 6);

        Txt_Date = (TextView)findViewById(R.id.txt_date);
        Img_Date_Left = (ImageView)findViewById(R.id.img_date_left);
        Img_Date_Right = (ImageView)findViewById(R.id.img_date_right);

        Img_PointUse = (ImageView)findViewById(R.id.img_usepoint);
        Img_PointUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog_Hint();
            }
        });
        List_PointList = (RecyclerView)findViewById(R.id.list_pointlist);


        /*Img_Exchange = (Button)rootView.findViewById(R.id.img_exchange);
        Img_Exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Exchange.class);
                intent.putExtra("Point", str_point);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });*/

        Txt_MyPoint = (TextView)findViewById(R.id.txt_mypoint);
    }

    @Override
    public void onResume() {
        super.onResume();

        Async async = new Async();
        async.execute();
    }

    public void setDate_Event(){
        Img_Date_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_date_month.equals("01")){
                    _date_year = (Integer.parseInt(_date_year)-1)+"";
                    _date_month = "12";
                }
                else{
                    int _int_month = (Integer.parseInt(_date_month)-1);
                    if(_int_month < 10){
                        _date_month = "0"+_int_month;
                    }
                    else{
                        _date_month = _int_month+"";
                    }
                }
                Async async = new Async();
                async.execute();
            }
        });
        Img_Date_Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_date_month.equals("12")){
                    _date_year = (Integer.parseInt(_date_year)+1)+"";
                    _date_month = "01";
                }
                else{
                    int _int_month = (Integer.parseInt(_date_month)+1);
                    if(_int_month < 10){
                        _date_month = "0"+_int_month;
                    }
                    else{
                        _date_month = _int_month+"";
                    }
                }
                Async async = new Async();
                async.execute();
            }
        });
    }

    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData, parseredData_user;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(PointList.this,"","",true,true,null);
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

                String result = http.HttpClient("Web_Escape", "User_PointList.jsp", User_Pk, _date_year+_date_month);
                parseredData = jsonParserList.jsonParserList_Data5(result);

                String result1 = http.HttpClient("Web_Escape", "User_v3.jsp",User_Pk, "yologuys12");
                parseredData_user = jsonParserList.jsonParserList_Data11(result1);
                str_point = parseredData_user[0][10];

                point_models = new ArrayList<Point_Model>();
                for (int i = 0; i < parseredData.length; i++) {
                    String pk = parseredData[i][0];
                    String category = parseredData[i][1];
                    String title = parseredData[i][2];
                    String point = parseredData[i][3];
                    String date = parseredData[i][4];

                    point_models.add(new Point_Model(PointList.this,pk, category, title, point, date));
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

            //포인트 설정
            Txt_MyPoint.setText(setPoint_rest(str_point)+"P");
            //날짜 셋팅
            Txt_Date.setText(_date_year +"년 "+_date_month+"월");
            //리스트 뷰 세로 방향으로
            LinearLayoutManager layoutManager = new LinearLayoutManager(PointList.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);

            //베스트 무료체험 어댑터 셋팅
            point_adapter = new Point_Adapter(PointList.this, point_models);
            List_PointList.setLayoutManager(layoutManager);
            List_PointList.setAdapter(point_adapter);

            progressDialog.dismiss();
        }
    }

    private String setNowTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");

        return sdf1.format(date);
    }
    //금액 콤마 표현
    public String setPoint_rest(String point){
        DecimalFormat df = new DecimalFormat("#,##0");

        return df.format(Integer.parseInt(point))+"";
    }

    public void setDialog_Hint(){
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_usepoint, (ViewGroup)findViewById(R.id.root));
        final EditText Edit_Code = (EditText)layout.findViewById(R.id.edit_code);
        final EditText Edit_Point  = (EditText) layout.findViewById(R.id.edit_point);
        final TextView Txt_Cancel  = (TextView) layout.findViewById(R.id.txt_cancel);
        final TextView Txt_Ok  = (TextView) layout.findViewById(R.id.txt_ok);

        TeamInfo_Dialog = new Dialog(PointList.this);
        TeamInfo_Dialog
                .setContentView(layout);

        // Dialog 사이즈 조절 하기
        WindowManager.LayoutParams params = TeamInfo_Dialog.getWindow().getAttributes();
        params.width = convertDpToPixel(300, getApplicationContext());
        params.height = convertDpToPixel(320, getApplicationContext());
        TeamInfo_Dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

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
                if(Edit_Code.getText().toString().equals("")){
                    Toast.makeText(PointList.this, "업체 코드를 입력해주세요." ,Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Edit_Point.getText().toString().equals("")){
                        Toast.makeText(PointList.this, "포인트를 입력해주세요." ,Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(Integer.parseInt(Edit_Point.getText().toString()) > Integer.parseInt(str_point)){
                            Toast.makeText(PointList.this, "포인트가 부족합니다.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(Integer.parseInt(Edit_Point.getText().toString()) >= 1000){
                                if(Integer.parseInt(Edit_Point.getText().toString()) % 1000 == 0){
                                    HttpClient http = new HttpClient();
                                    String result = http.HttpClient("Web_Escape", "User_Point_SearchCompany.jsp", Edit_Code.getText().toString());
                                    use_title = result;
                                    if(result.equals("notexist")){
                                        Toast.makeText(PointList.this, "업체 코드를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        setDialog_check(Edit_Code.getText().toString(), Edit_Point.getText().toString());
                                    }
                                }
                                else{
                                    Toast.makeText(PointList.this, "1,000P 단위로 사용 가능합니다." ,Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(PointList.this, "1,000P 단위로 사용 가능합니다." ,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }

    public void setDialog_check(String code, String point){
        LayoutInflater inflater = (LayoutInflater)PointList.this.getSystemService(PointList.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_usepoint_check, (ViewGroup)findViewById(R.id.root));
        final TextView Txt_Ok = (TextView) layout.findViewById(R.id.txt_ok);
        final TextView Txt_Cancel = (TextView) layout.findViewById(R.id.txt_cancel);
        final TextView Txt_Title = (TextView) layout.findViewById(R.id.txt_title);
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(PointList.this);
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();

        use_code = code;
        use_point = point;
        Txt_Title.setText(use_title+" 업체에 "+setPoint_rest(use_point)+" 포인트를 사용하시겠습니까? ");

        // Dialog 사이즈 조절 하기
       /* WindowManager.LayoutParams params = TeamInfo_Dialog.getWindow().getAttributes();
        params.width = convertDpToPixel(300, getApplicationContext());
        params.height = convertDpToPixel(320, getApplicationContext());
        TeamInfo_Dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);*/

        Txt_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Async_input async_input = new Async_input();
                async_input.execute(User_Pk, use_code, use_point);
                TeamInfo_Dialog.dismiss();
            }
        });
    }

    public static int convertDpToPixel(int dp, Context context){

        Resources resources = context.getResources();

        DisplayMetrics metrics = resources.getDisplayMetrics();

        int px = (int)(dp * (metrics.densityDpi / 160f));

        return px;

    }

    public class Async_input extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String result1 = "";

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(PointList.this,"","",true,true,null);
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

                result1 = http.HttpClient("Web_Escape", "User_PointInput.jsp", params);


                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("ㅌㅌㅌ", result1);
            if(result1.equals("notexist")){
                Toast.makeText(PointList.this, "업체 코드를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
            else{
                Async async = new Async();
                async.execute();
                Toast.makeText(PointList.this, "포인트가 사용되었습니다.", Toast.LENGTH_SHORT).show();
                TeamInfo_Dialog.dismiss();
            }
            progressDialog.dismiss();
        }
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
