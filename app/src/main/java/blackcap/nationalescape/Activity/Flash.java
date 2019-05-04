package blackcap.nationalescape.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by 박효근 on 2018-02-26.
 */

public class Flash extends AppCompatActivity {
    static final String Project_version = "1.9.5";
    String[][] ParsedData_Setting;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String User_Pk = "";
    Boolean Guide = true;

    TimerTask myTask;
    Timer timer;
    String strCurToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("Pk", ".");

        currentTime();
        //권한 설정
        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ){
            if(Check_Gps() == true){
                Check_Setting();
            }
            else{
                setDialog_GpsOn();
            }

        }
        else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CALENDAR)){
            } else{
            }
            //사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄운다.
            //만약 사용자가 다시 보지 않기에 체크를 했을 경우엔 권한 설정 다이얼로그가 뜨지 않고,
            //곧바로 OnRequestPermissionResult가 실행된다.
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }

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
    public boolean Check_Gps(){
        boolean gpsEnable = false;
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsEnable = true;
        }
        return gpsEnable;
    }
    public void setDialog_GpsOn(){
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_gpson, (ViewGroup)findViewById(R.id.Customdialog_Gpson_Root));
        final TextView Customdialog_Gpson_Button_Ok = (TextView)layout.findViewById(R.id.Customdialog_Gps_Button_Ok);
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Flash.this);
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();
        Customdialog_Gpson_Button_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 1);
            }
        });
    }
    public void Check_Setting(){
        HttpClient http_setting = new HttpClient();
        String result = http_setting.HttpClient("Web_Escape","Setting.jsp");
        ParsedData_Setting = jsonParserList_Setting(result);
        if(!Project_version.equals(ParsedData_Setting[0][0])){
            LayoutInflater inflater = (LayoutInflater)Flash.this.getSystemService(Flash.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog_update, (ViewGroup)findViewById(R.id.Customdialog_Update_Root));
            final Button Customdialog_Update_Button_Ok = (Button)layout.findViewById(R.id.Customdialog_Update_Button_Ok);
            final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Flash.this);
            TeamInfo_Dialog
                    .setContentView(layout)
                    .setCanceledOnTouchOutside(true);
            TeamInfo_Dialog.show();
            Customdialog_Update_Button_Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=blackcap.nationalescape"));
                    startActivity(intent);
                    finish();
                }
            });
        }
        else{
            myTask = new TimerTask() {
                int i = 2;

                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 해당 작업을 처리함
                            if (i <= 0) {
                                HttpClient http_count = new HttpClient();
                                http_count.HttpClient("Web_Escape","Today_Counting.jsp", strCurToday);
                                timer.cancel();

                                setIntent(User_Pk);
                                finish();
                            }
                        }
                    });
                    i--;
                    //시간이 초과된 경우 game 내 데이터 삭제 및 초기화.

                }
            };
            timer = new Timer();
            timer.schedule(myTask, 1000, 1000); // 5초후 첫실행, 1초마다 계속실행
        }
    }
    public void Check_Setting_notime(){
        HttpClient http_setting = new HttpClient();
        String result = http_setting.HttpClient("Web_Escape","Setting.jsp");
        ParsedData_Setting = jsonParserList_Setting(result);
        if(!Project_version.equals(ParsedData_Setting[0][0])){
            LayoutInflater inflater = (LayoutInflater)Flash.this.getSystemService(Flash.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog_update, (ViewGroup)findViewById(R.id.Customdialog_Update_Root));
            final Button Customdialog_Update_Button_Ok = (Button)layout.findViewById(R.id.Customdialog_Update_Button_Ok);
            final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Flash.this);
            TeamInfo_Dialog
                    .setContentView(layout)
                    .setCanceledOnTouchOutside(true);
            TeamInfo_Dialog.show();
            Customdialog_Update_Button_Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=blackcap.nationalescape"));
                    startActivity(intent);
                    finish();
                }
            });
        }
        else{
            HttpClient http_count = new HttpClient();
            http_count.HttpClient("Web_Escape","Today_Counting.jsp", strCurToday);

            setIntent(User_Pk);
            finish();
        }
    }
    public void setIntent(String pk){
        Intent intent = new Intent(Flash.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);
    }
    public String[][] jsonParserList_Setting(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1"};
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
    public String[][] jsonParserList_login(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1"};
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
    public void currentTime(){
        long now = System.currentTimeMillis();
// 현재 시간을 저장 한다.
        Date date = new Date(now);
// 시간 포맷 지정
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyyMMdd");
// 지정된 포맷으로 String 타입 리턴
        strCurToday = CurDateFormat.format(date);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult){
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        //위 예시에서 requestPermission 메서드를 썼을시 , 마지막 매개변수에 0을 넣어 줬으므로, 매칭
        if(requestCode == 0){
            // requestPermission의 두번째 매개변수는 배열이므로 아이템이 여러개 있을 수 있기 때문에 결과를 배열로 받는다.
            // 해당 예시는 요청 퍼미션이 한개 이므로 i=0 만 호출한다.
            if(grantResult[0] == 0){
                //해당 권한이 승낙된 경우.
                if(Check_Gps() == true){
                    Check_Setting();
                }
                else{
                    setDialog_GpsOn();
                }

            }else{
                //해당 권한이 거절된 경우.
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("test123", requestCode+"");
        if(requestCode == 1){
            if(Check_Gps() == true){
                Check_Setting_notime();
            }
            else{
                setDialog_GpsOn();
            }
        }
    }
}
