package blackcap.nationalescape.Activity.tab4;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Setting_ChangePass extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성

    private ImageView Img_Back;
    private EditText Edit_Pass1, Edit_Pass2;
    private TextView Txt_Save;

    private String User_Pk = "", Str_Nickname = "", Str_Pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_setting_changepass);

        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");

        init();
        setImgBack();
        //저장 이벤트
        setSave();
    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Edit_Pass1 = (EditText)findViewById(R.id.edit_pass1);
        Edit_Pass2 = (EditText)findViewById(R.id.edit_pass2);
        Txt_Save = (TextView)findViewById(R.id.txt_save);
    }
    public void setSave(){
        Txt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edit_Pass1.getText().toString().equals("")){
                    Toast.makeText(Setting_ChangePass.this, "변경할 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Edit_Pass1.getText().toString().length() < 8){
                        Toast.makeText(Setting_ChangePass.this, "8자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(Edit_Pass1.getText().toString().equals(Edit_Pass2.getText().toString())){
                            Async_Update_Pass async_user_update = new Async_Update_Pass();
                            async_user_update.execute();
                        }else{
                            Toast.makeText(Setting_ChangePass.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    public class Async_Update_Pass extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String result_pass = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Setting_ChangePass.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //유저 정보 response
                HttpClient http = new HttpClient();
                result_pass = http.HttpClient("Web_Escape", "User_Modify_Pass_v2.jsp",User_Pk, Edit_Pass1.getText().toString());

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result_pass.equals("succed")){
                Toast.makeText(Setting_ChangePass.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
            else{
                Toast.makeText(Setting_ChangePass.this, "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
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
}



