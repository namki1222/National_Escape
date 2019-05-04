package blackcap.nationalescape.Activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Login_Password_Change extends AppCompatActivity {
    ImageView Back;

    EditText EditPass, EditPassCheck;
    TextView Txt_Warning_Pass, Txt_Warning_PassCheck;
    ImageView Img_Join;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String str_user_pk = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pass_change);

        Intent intent1 = getIntent();
        str_user_pk = intent1.getStringExtra("User_Pk");

        //변수 초기화
        init();
        //회원가입 버튼 이벤트
        setBtnNext();
        //뒤로가기 선언 및 이벤트 셋팅
        setBack();
    }

    public void init() {
        EditPass = (EditText) findViewById(R.id.edit_pass);
        Txt_Warning_Pass = (TextView)findViewById(R.id.txt_warning_pass);
        EditPassCheck = (EditText) findViewById(R.id.edit_passcheck);
        Txt_Warning_PassCheck = (TextView)findViewById(R.id.txt_warning_passcheck);
        Img_Join = (ImageView)findViewById(R.id.img_join);
    }

    public void setBtnNext() {
        Img_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이메일 널 포인트 체크
                //비밀번호 8자리 확인
                if(EditPass.getText().toString().length() < 8){
                    Txt_Warning_Pass.setVisibility(View.VISIBLE);
                    Txt_Warning_PassCheck.setVisibility(View.INVISIBLE);

                    Txt_Warning_Pass.setText("8자리 이상 입력해주세요");
                }
                else{
                    //비밀번호 체크 완료
                    if(!EditPass.getText().toString().equals(EditPassCheck.getText().toString())){
                        Txt_Warning_Pass.setVisibility(View.INVISIBLE);
                        Txt_Warning_PassCheck.setVisibility(View.VISIBLE);

                        Txt_Warning_PassCheck.setText("비밀번호가 일치하지 않습니다");
                    }
                    else{
                        Async_Pass async_join = new Async_Pass();
                        async_join.execute(str_user_pk, EditPass.getText().toString());
                    }
                }
            }
        });
    }

    public class Async_Pass extends AsyncTask<String, Void, String> {
        Progressbar_wheel progressDialog;
        String responsed;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Login_Password_Change.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //프리뷰 이미지 로드
                HttpClient http = new HttpClient();
                responsed = http.HttpClient("Web_Escape", "User_Modify_Pass.jsp", params);
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(responsed.equals("failed")){
                Toast.makeText(Login_Password_Change.this, "잠시 후 다시 이용바랍니다", Toast.LENGTH_SHORT).show();
            }
            else{
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
            super.onPostExecute(result);
            progressDialog.dismiss();
        }
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

