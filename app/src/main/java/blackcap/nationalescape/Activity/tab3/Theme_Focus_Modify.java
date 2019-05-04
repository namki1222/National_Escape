package blackcap.nationalescape.Activity.tab3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import me.drakeet.materialdialog.MaterialDialog;

public class Theme_Focus_Modify extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성
    private String User_Pk = "";

    private ImageView Img_Back;
    private ImageView Img_Review_Star1, Img_Review_Star2, Img_Review_Star3, Img_Review_Star4, Img_Review_Star5;
    private TextView Txt_StarCount;
    private ImageView Img_Level_1, Img_Level_2, Img_Level_3;
    private TextView Txt_level_1,Txt_level_2,Txt_level_3;
    private ImageView Img_Level_Easy_Radio, Img_Level_Normal_Radio, Img_Level_Hard_Radio;
    private ImageView Img_Escape_Succed, Img_Escape_Failed;
    private ImageView Img_Escape_Succed_Radio, Img_Escape_Failed_Radio;
    private EditText Edit_Time_Minute, Edit_Time_Second, Edit_Hint, Edit_Memo;
    private TextView Txt_Save;

    private String str_theme_pk = "", str_level = "", str_escape = "", str_exp = "", str_grade = "";
    private String str_time = "", str_hint = "", str_memo = "";
    private double startcount = 5;
    boolean choice_5 = true, choice_4 = true, choice_3 = true, choice_2 = true, choice_1 = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3_focus_write);

        init();
        //뒤로가기
        setImgBack();
        setLevel_Event();
        setEscape_Event();
        setHint_Event();
        setTime_Event();
        setTxt_Save();
    }
    public void init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");;

        Intent intent1 = getIntent();
        str_theme_pk = intent1.getStringExtra("Theme_Pk");
        str_grade = intent1.getStringExtra("Grade");
        str_level = intent1.getStringExtra("Level");
        str_escape = intent1.getStringExtra("Escape");
        str_time = intent1.getStringExtra("Time");
        str_hint = intent1.getStringExtra("Hint");
        str_memo = intent1.getStringExtra("Memo");

        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Review_Star1 = (ImageView)findViewById(R.id.img_review_star1);
        Img_Review_Star2 = (ImageView)findViewById(R.id.img_review_star2);
        Img_Review_Star3 = (ImageView)findViewById(R.id.img_review_star3);
        Img_Review_Star4 = (ImageView)findViewById(R.id.img_review_star4);
        Img_Review_Star5 = (ImageView)findViewById(R.id.img_review_star5);
        Txt_StarCount = (TextView)findViewById(R.id.txt_starcount);
        setStar_Event();
        setStar(str_grade);

        Img_Level_1 = (ImageView)findViewById(R.id.img_level_1);
        Img_Level_2 = (ImageView)findViewById(R.id.img_level_2);
        Img_Level_3 = (ImageView)findViewById(R.id.img_level_3);
        Txt_level_1 = (TextView)findViewById(R.id.txt_level_1);
        Txt_level_2 = (TextView)findViewById(R.id.txt_level_2);
        Txt_level_3 = (TextView)findViewById(R.id.txt_level_3);
        Img_Level_Easy_Radio = (ImageView)findViewById(R.id.img_level_easy_radio);
        Img_Level_Normal_Radio = (ImageView)findViewById(R.id.img_level_normal_radio);
        Img_Level_Hard_Radio = (ImageView)findViewById(R.id.img_level_hard_radio);
        setLevel(str_level);

        Img_Escape_Succed = (ImageView)findViewById(R.id.img_escape_succed);
        Img_Escape_Failed = (ImageView)findViewById(R.id.img_escape_failed);
        Img_Escape_Succed_Radio = (ImageView)findViewById(R.id.img_escape_succed_radio);
        Img_Escape_Failed_Radio = (ImageView)findViewById(R.id.img_escape_failed_radio);
        setEscape(str_escape);

        Edit_Time_Minute = (EditText)findViewById(R.id.edit_time_minute);
        Edit_Time_Second = (EditText)findViewById(R.id.edit_time_second);
        if(str_time.equals(".")){
            Edit_Time_Minute.setText("");
            Edit_Time_Second.setText("");
        }
        else{
            int minute_position = str_time.indexOf("분");
            int second_position = str_time.indexOf("초");
            if(str_time.contains("분")){
                Edit_Time_Minute.setText(str_time.substring(0, minute_position));
            }
            if(str_time.contains("초")){
                if(str_time.contains("분")){
                    Edit_Time_Second.setText(str_time.substring(minute_position+1, second_position));
                }
                else{
                    Edit_Time_Second.setText(str_time.substring(0, second_position));
                }
            }
        }

        Edit_Hint = (EditText)findViewById(R.id.edit_hint);
        Edit_Hint.setInputType(0);
        if(str_hint.equals(".")){
            Edit_Hint.setText("");
        }
        else{
            Edit_Hint.setText(str_hint);
        }

        Edit_Memo = (EditText)findViewById(R.id.edit_memo);
        Edit_Memo.setText(str_memo);

        Txt_Save = (TextView)findViewById(R.id.txt_save);
    }
    public void setStar_Event(){
        Img_Review_Star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice_5 == true){
                    setStar("4.5");
                    choice_5 = false;
                }
                else{
                    setStar("5.0");
                    choice_5 = true;
                }
            }
        });
        Img_Review_Star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice_4 == true){
                    setStar("3.5");
                    choice_4 = false;
                }
                else{
                    setStar("4.0");
                    choice_4 = true;
                }
            }
        });
        Img_Review_Star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice_3 == true){
                    setStar("2.5");
                    choice_3 = false;
                }
                else{
                    setStar("3.0");
                    choice_3 = true;
                }
            }
        });
        Img_Review_Star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice_2 == true){
                    setStar("1.5");
                    choice_2 = false;
                }
                else{
                    setStar("2.0");
                    choice_2 = true;
                }
            }
        });
        Img_Review_Star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice_1 == true){
                    setStar("0.5");
                    choice_1 = false;
                }
                else{
                    setStar("1.0");
                    choice_1 = true;
                }
            }
        });
    }
    public void setStar(String count){
        if(count.equals("0.5")){
            Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
            startcount = 0.5;
            Txt_StarCount.setText(startcount+"");
        }
        else if(count.equals("1.0")){
            Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            startcount = 1;
            Txt_StarCount.setText(startcount+"");
        }
        else if(count.equals("1.5")){
            Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
            Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            startcount = 1.5;
            Txt_StarCount.setText(startcount+"");
        }
        else if(count.equals("2.0")){
            Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            startcount = 2;
            Txt_StarCount.setText(startcount+"");
        }
        else if(count.equals("2.5")){
            Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
            Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            startcount = 2.5;
            Txt_StarCount.setText(startcount+"");
        }
        else if(count.equals("3.0")){
            Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            startcount = 3;
            Txt_StarCount.setText(startcount+"");
        }
        else if(count.equals("3.5")){
            Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
            Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            startcount = 3.5;
            Txt_StarCount.setText(startcount+"");
        }
        else if(count.equals("4.0")){
            Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
            Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            startcount = 4;
            Txt_StarCount.setText(startcount+"");
        }
        else if(count.equals("4.5")){
            Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
            Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            startcount = 4.5;
            Txt_StarCount.setText(startcount+"");
        }
        else{
            Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
            Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
            startcount = 5;
            Txt_StarCount.setText(startcount+"");
        }
    }
    public void setLevel_Event(){
        Img_Level_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLevel("easy");
            }
        });
        Txt_level_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLevel("easy");
            }
        });
        Img_Level_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLevel("normal");
            }
        });
        Txt_level_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLevel("normal");
            }
        });
        Img_Level_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLevel("hard");
            }
        });
        Txt_level_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLevel("hard");
            }
        });
    }
    public void setLevel(String level){
        if(level.equals("easy")){
            Img_Level_Easy_Radio.setImageResource(R.drawable.theme_write_radio_check);
            Img_Level_Normal_Radio.setImageResource(R.drawable.theme_write_radio);
            Img_Level_Hard_Radio.setImageResource(R.drawable.theme_write_radio);
            str_level = "easy";
        }
        else if(level.equals("normal")){
            Img_Level_Easy_Radio.setImageResource(R.drawable.theme_write_radio);
            Img_Level_Normal_Radio.setImageResource(R.drawable.theme_write_radio_check);
            Img_Level_Hard_Radio.setImageResource(R.drawable.theme_write_radio);
            str_level = "normal";
        }
        else{
            Img_Level_Easy_Radio.setImageResource(R.drawable.theme_write_radio);
            Img_Level_Normal_Radio.setImageResource(R.drawable.theme_write_radio);
            Img_Level_Hard_Radio.setImageResource(R.drawable.theme_write_radio_check);
            str_level = "hard";
        }
    }
    public void setEscape_Event(){
        Img_Escape_Succed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEscape("success");
            }
        });
        Img_Escape_Failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEscape("failed");
            }
        });
    }
    public void setTime_Event(){
        Edit_Time_Minute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Edit_Time_Minute.getText().toString().length() != 0)
                {
                    int int_text = Integer.parseInt(Edit_Time_Minute.getText().toString());
                    if(int_text >= 60 ){
                        Toast.makeText(Theme_Focus_Modify.this, "0 ~ 59 사이로 입력해주세요", Toast.LENGTH_SHORT).show();
                        s.delete(s.length() - 1,s.length());
                    }
                }
            }
        });
        Edit_Time_Second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Edit_Time_Second.getText().toString().length() != 0)
                {
                    int int_text = Integer.parseInt(Edit_Time_Second.getText().toString());
                    if(int_text >= 60 ){
                        Toast.makeText(Theme_Focus_Modify.this, "0 ~ 59 사이로 입력해주세요", Toast.LENGTH_SHORT).show();
                        s.delete(s.length() - 1,s.length());
                    }
                }
            }
        });
    }
    public void setHint_Event(){
        Edit_Hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog_Hint();
            }
        });
    }
    public void setEscape(String escape){
        if(escape.equals("success")){
            Img_Escape_Succed_Radio.setImageResource(R.drawable.theme_write_radio_check);
            Img_Escape_Failed_Radio.setImageResource(R.drawable.theme_write_radio);
            str_escape = "success";
        }
        else{
            Img_Escape_Succed_Radio.setImageResource(R.drawable.theme_write_radio);
            Img_Escape_Failed_Radio.setImageResource(R.drawable.theme_write_radio_check);
            str_escape = "failed";
        }
    }
    public void setTxt_Save(){
        Txt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edit_Memo.getText().toString().equals("")){
                    Toast.makeText(Theme_Focus_Modify.this, "메모를 작성해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    str_time = "";
                    if(Edit_Time_Minute.getText().toString().equals("")){
                        str_time = "00분";
                    }
                    else if(Edit_Time_Minute.length() == 1){
                        str_time =  "0"+Edit_Time_Minute.getText().toString()+"분";
                    }
                    else{
                        str_time =  ""+Edit_Time_Minute.getText().toString()+"분";
                    }

                    if(Edit_Time_Second.getText().toString().equals("")){
                        str_time =  str_time + "00초";
                    }
                    else if(Edit_Time_Second.length() == 1){
                        str_time =  str_time + "0"+Edit_Time_Second.getText().toString()+"초";
                    }
                    else{
                        str_time =  str_time +Edit_Time_Second.getText().toString()+"초";
                    }

                    Async_Modify async_save = new Async_Modify();
                    async_save.execute(str_theme_pk, User_Pk, Txt_StarCount.getText().toString(), Edit_Memo.getText().toString(), setNowTime(), str_level, str_escape, str_time, Edit_Hint.getText().toString());
                }
            }
        });
    }
    public class Async_Modify extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;

        private String http_result = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Theme_Focus_Modify.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                HttpClient http = new HttpClient();
                http_result = http.HttpClient("Web_Escape", "Theme_Focus_Review_Modify.jsp",params);
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            finish();
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
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
    public void setDialog_Hint(){
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_hint, (ViewGroup)findViewById(R.id.root));
        final TextView Txt_0 = (TextView)layout.findViewById(R.id.txt_0);
        final TextView Txt_1  = (TextView) layout.findViewById(R.id.txt_1);
        final TextView Txt_2  = (TextView) layout.findViewById(R.id.txt_2);
        final TextView Txt_3  = (TextView) layout.findViewById(R.id.txt_3);
        final TextView Txt_4  = (TextView) layout.findViewById(R.id.txt_4);
        final TextView Txt_5  = (TextView) layout.findViewById(R.id.txt_5);
        final ImageView Img_Cancel = (ImageView)layout.findViewById(R.id.img_cancel);
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Theme_Focus_Modify.this);
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();
        Txt_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_Hint.setText("0개");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Hint.setText("1개");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Hint.setText("2개");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Hint.setText("3개");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Hint.setText("4개");
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit_Hint.setText("5개 이상");
                TeamInfo_Dialog.dismiss();
            }
        });
        Img_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamInfo_Dialog.dismiss();
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        super.onBackPressed();
    }
    private String setNowTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        return sdf.format(date)+"";
    }
}




