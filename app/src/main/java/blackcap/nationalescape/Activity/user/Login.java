package blackcap.nationalescape.Activity.user;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import blackcap.nationalescape.Activity.MainActivity;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;

public class Login extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ImageView Img_Back, Img_Login;
    EditText Edit_Email, Edit_Pass;
    TextView Txt_Passchange;
    LinearLayout Layout_Join;
    public static String Review_User_Pk = "", Review_Goods_Pk = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        editor = preferences.edit();

        init();
        setImgBack();
        setLogin();
        setTxt_Passchange();
        setJoin();
    }
    private void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Login = (ImageView)findViewById(R.id.img_login);
        Edit_Email = (EditText)findViewById(R.id.edit_email);
        Edit_Pass = (EditText)findViewById(R.id.edit_pass);
        Txt_Passchange = (TextView)findViewById(R.id.txt_passchange);
        Layout_Join =(LinearLayout)findViewById(R.id.layout_join);
    }
    private void setLogin(){
        Img_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[][] parsedData;
                HttpClient http_login = new HttpClient();
                String result = http_login.HttpClient("Web_Escape", "Login.jsp", Edit_Email.getText().toString(), Edit_Pass.getText().toString());
                parsedData = jsonParserList(result);
                Log.i("테스트", result);
                if (parsedData[0][0].equals("failed")) {
                    Toast.makeText(Login.this, "입력 정보를 확인해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    preferences = getSharedPreferences("escape", MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString("pk", parsedData[0][0]);
                    editor.commit();


                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
    }
    public void setTxt_Passchange(){
        Txt_Passchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Login_Password.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }
    public String[][] jsonParserList(String pRecvServerPage) {
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
    private void setJoin(){
        Layout_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Login_Join1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
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

