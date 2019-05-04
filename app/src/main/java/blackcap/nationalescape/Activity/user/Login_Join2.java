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

import blackcap.nationalescape.Activity.MainActivity;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Login_Join2 extends AppCompatActivity {
    ImageView Back;

    EditText Edit_Email, Edit_NickName, EditPass, EditPassCheck;
    TextView Txt_Warning_Email, Txt_Warning_Nick, Txt_Warning_Pass, Txt_Warning_PassCheck;
    ImageView Img_Join;
    ImageView Img_Exp1, Img_Exp2, Img_Exp3, Img_Exp4, Img_Exp5, Img_Exp6;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String str_phone = "", str_exp = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

        Intent intent1 = getIntent();
        str_phone = intent1.getStringExtra("Phone");

        //변수 초기화
        init();
        //회원가입 버튼 이벤트
        setBtnNext();
        //뒤로가기 선언 및 이벤트 셋팅
        setBack();
    }

    public void init() {
        Edit_Email = (EditText) findViewById(R.id.edit_email);
        Txt_Warning_Email = (TextView)findViewById(R.id.txt_warning_email);
        Edit_NickName = (EditText) findViewById(R.id.edit_nickname);
        Txt_Warning_Nick = (TextView)findViewById(R.id.txt_warning_nick);
        EditPass = (EditText) findViewById(R.id.edit_pass);
        Txt_Warning_Pass = (TextView)findViewById(R.id.txt_warning_pass);
        EditPassCheck = (EditText) findViewById(R.id.edit_passcheck);
        Txt_Warning_PassCheck = (TextView)findViewById(R.id.txt_warning_passcheck);
        Img_Join = (ImageView)findViewById(R.id.img_join);

        Img_Exp1 = (ImageView)findViewById(R.id.img_exp1);
        Img_Exp1.setImageResource(R.drawable.user_join_exp1_click);
        Img_Exp2 = (ImageView)findViewById(R.id.img_exp2);
        Img_Exp3 = (ImageView)findViewById(R.id.img_exp3);
        Img_Exp4 = (ImageView)findViewById(R.id.img_exp4);
        Img_Exp5 = (ImageView)findViewById(R.id.img_exp5);
        Img_Exp6 = (ImageView)findViewById(R.id.img_exp6);

        setImg_exp_clickEvent();
    }
    public void setImg_exp_clickEvent(){
        Img_Exp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Exp1.setImageResource(R.drawable.user_join_exp1_click);
                Img_Exp2.setImageResource(R.drawable.user_join_exp2);
                Img_Exp3.setImageResource(R.drawable.user_join_exp3);
                Img_Exp4.setImageResource(R.drawable.user_join_exp4);
                Img_Exp5.setImageResource(R.drawable.user_join_exp5);
                Img_Exp6.setImageResource(R.drawable.user_join_exp6);

                str_exp = "1";
            }
        });

        Img_Exp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Exp1.setImageResource(R.drawable.user_join_exp1);
                Img_Exp2.setImageResource(R.drawable.user_join_exp2_click);
                Img_Exp3.setImageResource(R.drawable.user_join_exp3);
                Img_Exp4.setImageResource(R.drawable.user_join_exp4);
                Img_Exp5.setImageResource(R.drawable.user_join_exp5);
                Img_Exp6.setImageResource(R.drawable.user_join_exp6);

                str_exp = "2";
            }
        });

        Img_Exp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Exp1.setImageResource(R.drawable.user_join_exp1);
                Img_Exp2.setImageResource(R.drawable.user_join_exp2);
                Img_Exp3.setImageResource(R.drawable.user_join_exp3_click);
                Img_Exp4.setImageResource(R.drawable.user_join_exp4);
                Img_Exp5.setImageResource(R.drawable.user_join_exp5);
                Img_Exp6.setImageResource(R.drawable.user_join_exp6);

                str_exp = "3";
            }
        });

        Img_Exp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Exp1.setImageResource(R.drawable.user_join_exp1);
                Img_Exp2.setImageResource(R.drawable.user_join_exp2);
                Img_Exp3.setImageResource(R.drawable.user_join_exp3);
                Img_Exp4.setImageResource(R.drawable.user_join_exp4_click);
                Img_Exp5.setImageResource(R.drawable.user_join_exp5);
                Img_Exp6.setImageResource(R.drawable.user_join_exp6);

                str_exp = "4";
            }
        });

        Img_Exp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Exp1.setImageResource(R.drawable.user_join_exp1);
                Img_Exp2.setImageResource(R.drawable.user_join_exp2);
                Img_Exp3.setImageResource(R.drawable.user_join_exp3);
                Img_Exp4.setImageResource(R.drawable.user_join_exp4);
                Img_Exp5.setImageResource(R.drawable.user_join_exp5_click);
                Img_Exp6.setImageResource(R.drawable.user_join_exp6);

                str_exp = "5";
            }
        });

        Img_Exp6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Exp1.setImageResource(R.drawable.user_join_exp1);
                Img_Exp2.setImageResource(R.drawable.user_join_exp2);
                Img_Exp3.setImageResource(R.drawable.user_join_exp3);
                Img_Exp4.setImageResource(R.drawable.user_join_exp4);
                Img_Exp5.setImageResource(R.drawable.user_join_exp5);
                Img_Exp6.setImageResource(R.drawable.user_join_exp6_click);

                str_exp = "6";
            }
        });
    }
    public void setBtnNext() {
        Img_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이메일 널 포인트 체크
                if(Edit_Email.getText().toString().equals("")){
                    Txt_Warning_Email.setVisibility(View.VISIBLE);
                    Txt_Warning_Nick.setVisibility(View.INVISIBLE);
                    Txt_Warning_Pass.setVisibility(View.INVISIBLE);
                    Txt_Warning_PassCheck.setVisibility(View.INVISIBLE);

                    Txt_Warning_Email.setText("이메일을 입력해주세요");
                }
                else{
                    //닉네임 널포인트 체크
                    if(Edit_NickName.getText().toString().equals("")){
                        Txt_Warning_Email.setVisibility(View.INVISIBLE);
                        Txt_Warning_Nick.setVisibility(View.VISIBLE);
                        Txt_Warning_Pass.setVisibility(View.INVISIBLE);
                        Txt_Warning_PassCheck.setVisibility(View.INVISIBLE);

                        Txt_Warning_Nick.setText("닉네임을 입력해주세요");
                    }
                    else{
                        //비밀번호 8자리 확인
                        if(EditPass.getText().toString().length() < 8){
                            Txt_Warning_Email.setVisibility(View.INVISIBLE);
                            Txt_Warning_Nick.setVisibility(View.INVISIBLE);
                            Txt_Warning_Pass.setVisibility(View.VISIBLE);
                            Txt_Warning_PassCheck.setVisibility(View.INVISIBLE);

                            Txt_Warning_Pass.setText("8자리 이상 입력해주세요");
                        }
                        else{
                            //비밀번호 체크 완료
                            if(!EditPass.getText().toString().equals(EditPassCheck.getText().toString())){
                                Txt_Warning_Email.setVisibility(View.INVISIBLE);
                                Txt_Warning_Nick.setVisibility(View.INVISIBLE);
                                Txt_Warning_Pass.setVisibility(View.INVISIBLE);
                                Txt_Warning_PassCheck.setVisibility(View.VISIBLE);

                                Txt_Warning_PassCheck.setText("비밀번호가 일치하지 않습니다");
                            }
                            else{
                                Async_Join async_join = new Async_Join();
                                async_join.execute(str_phone, Edit_Email.getText().toString(), Edit_NickName.getText().toString(), EditPass.getText().toString(), str_exp);
                            }
                        }
                    }
                }
            }
        });
    }

    public class Async_Join extends AsyncTask<String, Void, String> {
        Progressbar_wheel progressDialog;
        String responsed;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Login_Join2.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //프리뷰 이미지 로드
                HttpClient http = new HttpClient();
                responsed = http.HttpClient("Web_Escape", "Join_Basic_v2.jsp", params);
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(responsed.equals("failed")){
                Toast.makeText(Login_Join2.this, "잠시 후 다시 이용바랍니다", Toast.LENGTH_SHORT).show();
            }
            else{
                //이메일 중복
                if(responsed.equals("exist_email")){
                    Txt_Warning_Email.setVisibility(View.VISIBLE);
                    Txt_Warning_Nick.setVisibility(View.INVISIBLE);
                    Txt_Warning_Pass.setVisibility(View.INVISIBLE);
                    Txt_Warning_PassCheck.setVisibility(View.INVISIBLE);

                    Txt_Warning_Email.setText("이미 가입된 이메일이 있습니다");
                }
                else{
                    //닉네임 중복
                    if(responsed.equals("exist_nickname")){
                        Txt_Warning_Email.setVisibility(View.INVISIBLE);
                        Txt_Warning_Nick.setVisibility(View.VISIBLE);
                        Txt_Warning_Pass.setVisibility(View.INVISIBLE);
                        Txt_Warning_PassCheck.setVisibility(View.INVISIBLE);

                        Txt_Warning_Nick.setText("이미 가입된 닉네임이 있습니다");
                    }
                    else{
                        preferences = getSharedPreferences("escape", MODE_PRIVATE);
                        editor = preferences.edit();
                        editor.putString("pk", responsed);
                        editor.commit();

                        //가입 요건이 성사된 경우 메인으로 이동
                        Intent intent = new Intent(Login_Join2.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
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
