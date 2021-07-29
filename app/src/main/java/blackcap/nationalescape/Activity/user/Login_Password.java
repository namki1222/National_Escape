package blackcap.nationalescape.Activity.user;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;

public class Login_Password extends AppCompatActivity {
    ImageView Back;

    EditText Edit_Phone, Edit_Certi;
    TextView Txt_CertiAgain;
    ImageView Img_Next, Img_Certi, Img_CertiCheck;
    private TextView Txt_Succed;

    boolean cerfi_flag = false, succed_flag = false;

    int certi_num = 0;
    String str_userpk = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pass);

        init();
        //인증번호 받기
        setCerti();
        //인증번호 확인
        setCertiCheck();
        //인증번호 재 전송
        setCerti_Again();
        //회원가입 버튼 이벤트
        setBtnNext();
        //뒤로가기 선언 및 이벤트 셋팅
        setBack();
    }

    public void init() {
        Edit_Phone = (EditText) findViewById(R.id.edit_phone);
        Edit_Certi = (EditText)findViewById(R.id.edit_certi);
        Txt_CertiAgain = (TextView) findViewById(R.id.txt_certiagain);
        Txt_CertiAgain.setText(R.string.join_line);
        Txt_CertiAgain.setVisibility(View.GONE);
        Img_Next = (ImageView)findViewById(R.id.img_next);
        Img_Certi = (ImageView)findViewById(R.id.img_certi);
        Img_CertiCheck = (ImageView)findViewById(R.id.img_certicheck);
        Txt_Succed = (TextView)findViewById(R.id.txt_succed);
    }
    //문자 메시지 전송
    public void Push_Sms(){
        if (Edit_Phone.getText().toString().length() == 11 && Edit_Phone.getText().toString().substring(0, 3).equals("010")) {
            HttpClient user = new HttpClient();
            String result = user.HttpClient("Web_Escape", "Join_PhoneConfirm.jsp", Edit_Phone.getText().toString());
            if (result.equals("not exist")) {
                cerfi_flag = false;
                Toast.makeText(Login_Password.this, "가입된 번호가 없습니다", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("exist")){
                Random random = new Random();
                certi_num = Math.abs(random.nextInt(8999) + 1000);
                String msg = "전국방탈출 인증번호는 [" + String.valueOf(certi_num) + "] 입니다.감사합니다.";
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                Date d = new Date();
                String date = dateFormat.format(d);

                HttpClient http_certi = new HttpClient();
                String result_test = http_certi.HttpClient("InetSMSExample", "example.jsp", msg, Edit_Phone.getText().toString(), "15662649", date);
                Toast.makeText(Login_Password.this, "인증번호가 전송되었습니다", Toast.LENGTH_SHORT).show();
                Txt_CertiAgain.setVisibility(View.VISIBLE);
                Log.i("인증번호", certi_num+"");
                cerfi_flag = true;
            }
            else {
                cerfi_flag = false;
                Toast.makeText(Login_Password.this, "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
        } else {
            cerfi_flag = false;
            Toast.makeText(Login_Password.this, "정확한 휴대전화번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
        }
    }
    public void setCerti(){
        Img_Certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cerfi_flag == true){
                    Toast.makeText(Login_Password.this, "이미 인증번호를 전송했습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    Push_Sms();
                }
            }
        });
    }
    public void setCerti_Again(){
        Txt_CertiAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Txt_Succed.setVisibility(View.GONE);
                Push_Sms();
            }
        });
    }
    public void setCertiCheck(){
        Img_CertiCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cerfi_flag == false){
                    Toast.makeText(Login_Password.this, "인증 번호를 전송해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Edit_Certi.getText().toString() != null && !Edit_Certi.getText().toString().equals("")){
                        if(certi_num == Integer.parseInt(Edit_Certi.getText().toString())){
                            succed_flag = true;
                            Toast.makeText(Login_Password.this, "인증이 완료되었습니다", Toast.LENGTH_SHORT).show();
                            Txt_Succed.setText("인증이 완료되었습니다");
                            Txt_Succed.setVisibility(View.VISIBLE);

                            HttpClient http = new HttpClient();
                            JsonParserList jsonParserList = new JsonParserList();
                            String[][] result = jsonParserList.jsonParserList_Data1(http.HttpClient("Web_Escape", "Login_Find_Userpk.jsp", Edit_Phone.getText().toString()));

                            str_userpk = result[0][0];
                        }else{
                            succed_flag = false;
                        }
                    }
                    else{
                        Toast.makeText(Login_Password.this, "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void setBtnNext() {
        Img_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(succed_flag == true){
                    Intent intent = new Intent(Login_Password.this, Login_Password_Change.class);
                    intent.putExtra("User_Pk", str_userpk);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    Toast.makeText(Login_Password.this, "인증을 완료해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void setBack() {
        Back = (ImageView) findViewById(R.id.img_back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
}
