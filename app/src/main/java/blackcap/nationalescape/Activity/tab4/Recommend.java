package blackcap.nationalescape.Activity.tab4;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import me.drakeet.materialdialog.MaterialDialog;

public class Recommend extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성

    private ImageView Img_Back;
    private ImageView Img_Category_User, Img_Category_Company;
    private EditText Edit_Email, Edit_Phone, Edit_Title, Edit_Content;
    private TextView Txt_Save;

    private String User_Pk = "", Str_Nickname = "", Str_Pass = "";
    private String str_category = "user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_recommend);

        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");

        init();
        setImgBack();
        setCategory_Event();
        //저장 이벤트
        setSave();
    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Category_User = (ImageView)findViewById(R.id.img_category_user);
        Img_Category_Company = (ImageView)findViewById(R.id.img_category_company);
        Edit_Email = (EditText)findViewById(R.id.edit_email);
        Edit_Phone = (EditText)findViewById(R.id.edit_phone);
        Edit_Title = (EditText)findViewById(R.id.edit_title);
        Edit_Content = (EditText)findViewById(R.id.edit_content);
        Txt_Save = (TextView)findViewById(R.id.txt_save);
    }
    public void setCategory_Event(){
        Img_Category_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_category = "user";
                Img_Category_User.setImageResource(R.drawable.recommend_check);
                Img_Category_Company.setImageResource(R.drawable.recommend_uncheck);
            }
        });
        Img_Category_Company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_category = "company";
                Img_Category_User.setImageResource(R.drawable.recommend_uncheck);
                Img_Category_Company.setImageResource(R.drawable.recommend_check);
            }
        });
    }
    public void setSave(){
        Txt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edit_Email.getText().toString().equals("")){
                    Toast.makeText(Recommend.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Edit_Phone.getText().toString().equals("")){
                        Toast.makeText(Recommend.this, "휴대폰을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(Edit_Title.getText().toString().equals("")){
                            Toast.makeText(Recommend.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(Edit_Content.getText().toString().equals("")){
                                Toast.makeText(Recommend.this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Async async = new Async();
                                async.execute(str_category, Edit_Email.getText().toString(), Edit_Phone.getText().toString(), Edit_Title.getText().toString(), Edit_Content.getText().toString());
                            }
                        }
                    }
                }
            }
        });
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        private String str_result = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Recommend.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //유저 정보 response
                HttpClient http = new HttpClient();
                str_result = http.HttpClient("Web_Escape", "Recommend_Input.jsp",params);

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(str_result.equals("succed")){
                setDialog_succed();
            }
            else{
                Toast.makeText(Recommend.this, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }
    public void setDialog_succed(){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Recommend.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_recommend, (ViewGroup)findViewById(R.id.Customdialog_Recommend_Root));
        final TextView Customdialog_Recommend_Button_Ok = (TextView)layout.findViewById(R.id.Customdialog_Recommend_Button_Ok);
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Recommend.this);
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();
        Customdialog_Recommend_Button_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
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




