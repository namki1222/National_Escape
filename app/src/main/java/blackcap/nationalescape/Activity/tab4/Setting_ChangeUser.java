package blackcap.nationalescape.Activity.tab4;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.Base64Util;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Setting_ChangeUser extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성

    private ImageView Img_Back;
    private EditText Edit_Nickname;
    private TextView Txt_Save;

    private String User_Pk = "", Str_Nickname = "", Str_Pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_setting_changeuser);

        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");

        init();
        setImgBack();
        //저장 이벤트
        setSave();
        Async async = new Async();
        async.execute(User_Pk, "yologuys12");
    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Edit_Nickname = (EditText)findViewById(R.id.edit_nickname);
        Txt_Save = (TextView)findViewById(R.id.txt_save);
    }
    public void setSave(){
        Txt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edit_Nickname.getText().toString().equals(Str_Nickname)){
                    Toast.makeText(Setting_ChangeUser.this, "변경된 정보가 없습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Edit_Nickname.getText().toString().equals("")){
                        Toast.makeText(Setting_ChangeUser.this, "변경할 닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(Edit_Nickname.getText().toString().length() >= 20){
                            Toast.makeText(Setting_ChangeUser.this, "변닉네임은 최대 20자입니다.", Toast.LENGTH_SHORT).show();
                        }else{
                            Async_Update_Nick async_user_update = new Async_Update_Nick();
                            async_user_update.execute();
                        }
                    }
                }
            }
        });
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Setting_ChangeUser.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //유저 정보 response
                HttpClient http = new HttpClient();
                String result = http.HttpClient("Web_Escape", "User_v3.jsp",params);
                parseredData = jsonParserList(result);

                Str_Nickname = parseredData[0][2];

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Edit_Nickname.setText(Str_Nickname);

            progressDialog.dismiss();
        }
    }
    public class Async_Update_Nick extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String result_nick = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Setting_ChangeUser.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //유저 정보 response
                HttpClient http = new HttpClient();
                result_nick = http.HttpClient("Web_Escape", "User_Modify_Nickname.jsp",User_Pk, Edit_Nickname.getText().toString(), Str_Nickname);

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result_nick.equals("exist")){
                Toast.makeText(Setting_ChangeUser.this, "이미 존재하는 닉네임이에요", Toast.LENGTH_SHORT).show();
            }
            else if(result_nick.equals("succed")){
                Toast.makeText(Setting_ChangeUser.this, "닉네임이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
            else{
                Toast.makeText(Setting_ChangeUser.this, "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3","msg4", "msg5","msg6"};
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



