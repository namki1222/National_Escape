package blackcap.nationalescape.Activity.tab3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import me.drakeet.materialdialog.MaterialDialog;

import static blackcap.nationalescape.Activity.Fragment_main4.setting_view;

public class Theme_Focus_Write extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성
    private String User_Pk = "";

    private ImageView Img_Back;
    private ImageView Img_Review_Star1, Img_Review_Star2, Img_Review_Star3, Img_Review_Star4, Img_Review_Star5;
    private TextView Txt_StarCount;
    private LinearLayout Layout_Level_1, Layout_Level_2, Layout_Level_3, Layout_Level_4, Layout_Level_5;
    private ImageView Img_Level_1, Img_Level_2, Img_Level_3, Img_Level_4, Img_Level_5;
    private TextView Txt_Level_1, Txt_Level_2, Txt_Level_3, Txt_Level_4, Txt_Level_5;
    private TextView Txt_Time, Txt_Title;
    private ImageView Img_Level_1_Radio, Img_Level_2_Radio, Img_Level_3_Radio, Img_Level_4_Radio, Img_Level_5_Radio;
    private ImageView Img_Escape_Succed, Img_Escape_Failed;
    private ImageView Img_Escape_Succed_Radio, Img_Escape_Failed_Radio;
    private ImageView Img_Grade_Switch;
    private EditText Edit_Date, Edit_Time_Minute, Edit_time_Second, Edit_Hint, Edit_Memo;
    private TextView Txt_Save;
    private LinearLayout Layout_Hint, Layout_Grade_Text, Layout_Grade_Star;
    private TextView Txt_Gradeoff_Title, Txt_Grade_Title;

    boolean bol_grade_switch = true;
    private String str_theme_pk = "", str_level = "", str_escape = "", str_exp = "", str_user_time = "", str_deadtime = "", str_time = "", str_themetitle = "";
    private double startcount = 0.0;
    boolean choice_5 = true, choice_4 = true, choice_3 = true, choice_2 = true, choice_1 = true;

    boolean click_grade = false;
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
        setImg_Grade_Switch_Event();
    }
    public void init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");;

        Intent intent1 = getIntent();
        str_theme_pk = intent1.getStringExtra("Theme_Pk");
        str_exp = intent1.getStringExtra("Experience");
        str_user_time = intent1.getStringExtra("User_Time");
        str_deadtime = intent1.getStringExtra("Theme_Time");
        str_themetitle = intent1.getStringExtra("Theme_Title");

        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Review_Star1 = (ImageView)findViewById(R.id.img_review_star1);
        Img_Review_Star2 = (ImageView)findViewById(R.id.img_review_star2);
        Img_Review_Star3 = (ImageView)findViewById(R.id.img_review_star3);
        Img_Review_Star4 = (ImageView)findViewById(R.id.img_review_star4);
        Img_Review_Star5 = (ImageView)findViewById(R.id.img_review_star5);
        Txt_StarCount = (TextView)findViewById(R.id.txt_starcount);
        setStar_Event();

        Layout_Level_1 = (LinearLayout)findViewById(R.id.layout_level_1);
        Layout_Level_2 = (LinearLayout)findViewById(R.id.layout_level_2);
        Layout_Level_3 = (LinearLayout)findViewById(R.id.layout_level_3);
        Layout_Level_4 = (LinearLayout)findViewById(R.id.layout_level_4);
        Layout_Level_5 = (LinearLayout)findViewById(R.id.layout_level_5);

        Img_Level_1 = (ImageView)findViewById(R.id.img_level_1);
        Img_Level_2 = (ImageView)findViewById(R.id.img_level_2);
        Img_Level_3 = (ImageView)findViewById(R.id.img_level_3);
        Img_Level_4 = (ImageView)findViewById(R.id.img_level_4);
        Img_Level_5 = (ImageView)findViewById(R.id.img_level_5);

        Txt_Level_1 = (TextView)findViewById(R.id.txt_level_1);
        Txt_Level_2 = (TextView)findViewById(R.id.txt_level_2);
        Txt_Level_3 = (TextView)findViewById(R.id.txt_level_3);
        Txt_Level_4 = (TextView)findViewById(R.id.txt_level_4);
        Txt_Level_5 = (TextView)findViewById(R.id.txt_level_5);

        Img_Level_1_Radio = (ImageView)findViewById(R.id.img_level_1_radio);
        Img_Level_2_Radio = (ImageView)findViewById(R.id.img_level_2_radio);
        Img_Level_3_Radio = (ImageView)findViewById(R.id.img_level_3_radio);
        Img_Level_4_Radio = (ImageView)findViewById(R.id.img_level_4_radio);
        Img_Level_5_Radio = (ImageView)findViewById(R.id.img_level_5_radio);

        Img_Escape_Succed = (ImageView)findViewById(R.id.img_escape_succed);
        Img_Escape_Failed = (ImageView)findViewById(R.id.img_escape_failed);
        Img_Escape_Succed_Radio = (ImageView)findViewById(R.id.img_escape_succed_radio);
        Img_Escape_Failed_Radio = (ImageView)findViewById(R.id.img_escape_failed_radio);

        Txt_Time = (TextView)findViewById(R.id.txt_time);
        Edit_Time_Minute = (EditText)findViewById(R.id.edit_time_minute);
        Edit_time_Second = (EditText)findViewById(R.id.edit_time_second);
        Layout_Hint = (LinearLayout)findViewById(R.id.layout_hint);
        Edit_Hint = (EditText)findViewById(R.id.edit_hint);
        Edit_Hint.setInputType(0);
        Edit_Memo = (EditText)findViewById(R.id.edit_memo);
      //  Edit_Memo.setFilters(new InputFilter[]{specialCharacterFilter});

        Edit_Date = (EditText)findViewById(R.id.edit_date);
        Edit_Date.setText(setNowTime());

        Txt_Save = (TextView)findViewById(R.id.txt_save);
        Txt_Title = (TextView)findViewById(R.id.txt_title);
        Txt_Title.setText(str_themetitle);

        Img_Grade_Switch = (ImageView)findViewById(R.id.img_grade_switch);
        Layout_Grade_Text = (LinearLayout)findViewById(R.id.layout_grade_text);
        Layout_Grade_Star = (LinearLayout)findViewById(R.id.layout_grade_star);
        Txt_Gradeoff_Title = (TextView)findViewById(R.id.txt_gradeoff_title);
        Txt_Grade_Title = (TextView)findViewById(R.id.txt_grade_title);

        if(str_user_time.equals("extra")){
            Txt_Time.setText("남은시간");
        }
        else{
            Txt_Time.setText("걸린시간");
        }
    }
    public void setImg_Grade_Switch_Event(){
        Img_Grade_Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bol_grade_switch == true){
                    Img_Grade_Switch.setImageResource(R.drawable.theme_review_grade_on);
                    Layout_Grade_Text.setVisibility(View.INVISIBLE);
                    Layout_Grade_Star.setVisibility(View.INVISIBLE);
                    Txt_Grade_Title.setVisibility(View.INVISIBLE);
                    Txt_Gradeoff_Title.setVisibility(View.VISIBLE);

                    bol_grade_switch= false;

                    startcount = 10.0;
                }
                else{
                    Img_Grade_Switch.setImageResource(R.drawable.theme_review_grade_off);
                    Layout_Grade_Text.setVisibility(View.VISIBLE);
                    Layout_Grade_Star.setVisibility(View.VISIBLE);
                    Txt_Grade_Title.setVisibility(View.VISIBLE);
                    Txt_Gradeoff_Title.setVisibility(View.INVISIBLE);

                    bol_grade_switch = true;

                    startcount = 0.0;
                }
            }
        });
    }
    public void setStar_Event(){
        Img_Review_Star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice_5 == true){
                    Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
                    Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    startcount = 4.5;
                    Txt_StarCount.setText(startcount+"");
                    choice_5 = false;
                }
                else{
                    Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    startcount = 5.0;
                    Txt_StarCount.setText(startcount+"");
                    choice_5 = true;
                }
            }
        });
        Img_Review_Star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice_4 == true){
                    Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
                    Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    startcount = 3.5;
                    Txt_StarCount.setText(startcount+"");
                    choice_4 = false;
                }
                else{
                    Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    startcount = 4.0;
                    Txt_StarCount.setText(startcount+"");
                    choice_4 = true;
                }
            }
        });
        Img_Review_Star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice_3 == true){
                    Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
                    Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    startcount = 2.5;
                    Txt_StarCount.setText(startcount+"");
                    choice_3 = false;
                }
                else{
                    Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    startcount = 3.0;
                    Txt_StarCount.setText(startcount+"");
                    choice_3 = true;
                }
            }
        });
        Img_Review_Star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice_2 == true){
                    Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
                    Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    startcount = 1.5;
                    Txt_StarCount.setText(startcount+"");
                    choice_2 = false;
                }
                else{
                    Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    startcount = 2.0;
                    Txt_StarCount.setText(startcount+"");
                    choice_2 = true;
                }
            }
        });
        Img_Review_Star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice_1 == true){
                    Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
                    startcount = 0.5;
                    Txt_StarCount.setText(startcount+"");
                    choice_1 = false;
                }
                else{
                    Img_Review_Star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star2.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
                    Img_Review_Star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                    startcount = 1.0;
                    Txt_StarCount.setText(startcount+"");
                    choice_1 = true;
                }
            }
        });
    }
    public void setLevel_Event(){
        Layout_Level_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Level_1_Radio.setImageResource(R.drawable.theme_write_radio_check);
                Img_Level_2_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_3_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_4_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_5_Radio.setImageResource(R.drawable.theme_write_radio);

                str_level = "veryEasy";
            }
        });

        Layout_Level_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Level_1_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_2_Radio.setImageResource(R.drawable.theme_write_radio_check);
                Img_Level_3_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_4_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_5_Radio.setImageResource(R.drawable.theme_write_radio);

                str_level = "easy";
            }
        });

        Layout_Level_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Level_1_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_2_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_3_Radio.setImageResource(R.drawable.theme_write_radio_check);
                Img_Level_4_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_5_Radio.setImageResource(R.drawable.theme_write_radio);

                str_level = "normal";
            }
        });

        Layout_Level_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Level_1_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_2_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_3_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_4_Radio.setImageResource(R.drawable.theme_write_radio_check);
                Img_Level_5_Radio.setImageResource(R.drawable.theme_write_radio);

                str_level = "hard";
            }
        });

        Layout_Level_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Level_1_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_2_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_3_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_4_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Level_5_Radio.setImageResource(R.drawable.theme_write_radio_check);

                str_level = "veryHard";
            }
        });

    }
    public void setEscape_Event(){
        Img_Escape_Succed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Escape_Succed_Radio.setImageResource(R.drawable.theme_write_radio_check);
                Img_Escape_Failed_Radio.setImageResource(R.drawable.theme_write_radio);

                str_escape = "success";
            }
        });
        Img_Escape_Succed_Radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Escape_Succed_Radio.setImageResource(R.drawable.theme_write_radio_check);
                Img_Escape_Failed_Radio.setImageResource(R.drawable.theme_write_radio);

                str_escape = "success";
            }
        });
        Img_Escape_Failed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Escape_Succed_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Escape_Failed_Radio.setImageResource(R.drawable.theme_write_radio_check);

                str_escape = "failed";
            }
        });
        Img_Escape_Failed_Radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Img_Escape_Succed_Radio.setImageResource(R.drawable.theme_write_radio);
                Img_Escape_Failed_Radio.setImageResource(R.drawable.theme_write_radio_check);

                str_escape = "failed";
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
                    int int_deadtime = Integer.parseInt(str_deadtime);
                    if(int_text >=  int_deadtime){
                        Toast.makeText(Theme_Focus_Write.this, "0 ~ "+(int_deadtime-1)+" 사이로 입력해주세요", Toast.LENGTH_SHORT).show();
                        s.delete(s.length() - 1,s.length());
                    }
                    else if(Edit_Time_Minute.getText().toString().length() == 2){
                        //Edit_Time_Minute.setFocusable(false);

                        Edit_time_Second.setFocusableInTouchMode(true);
                        Edit_time_Second.requestFocus();

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                        imm.showSoftInput(Edit_time_Second,0);

                        //Edit_time_Second.setFocusable(true);
                    }
                }
            }
        });
        Edit_time_Second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Edit_time_Second.getText().toString().length() != 0)
                {
                    int int_text = Integer.parseInt(Edit_time_Second.getText().toString());
                    if(int_text >= 60 ){
                        Toast.makeText(Theme_Focus_Write.this, "0 ~ 59 사이로 입력해주세요", Toast.LENGTH_SHORT).show();
                        s.delete(s.length() - 1,s.length());
                    }
                }
            }
        });
    }
    public void setTxt_Save(){
        Txt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edit_Memo.getText().toString().equals("")){
                    Toast.makeText(Theme_Focus_Write.this, "메모를 작성해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    str_time = "";

                    if(Edit_Time_Minute.getText().toString().equals("") && Edit_time_Second.getText().toString().equals("")){
                        str_time = ".";
                    }
                    else{
                        if(Edit_Time_Minute.getText().toString().equals("")){
                            str_time = "00분";
                        }
                        else if(Edit_Time_Minute.length() == 1){
                            str_time =  "0"+Edit_Time_Minute.getText().toString()+"분";
                        }
                        else{
                            str_time =  ""+Edit_Time_Minute.getText().toString()+"분";
                        }
                        if(Edit_time_Second.getText().toString().equals("")){
                            str_time =  str_time + "00초";
                        }
                        else if(Edit_time_Second.length() == 1){
                            str_time =  str_time + "0"+Edit_time_Second.getText().toString()+"초";
                        }
                        else{
                            str_time =  str_time +Edit_time_Second.getText().toString()+"초";
                        }
                    }

                    if(startcount == 0.0){
                        Toast.makeText(Theme_Focus_Write.this, "평점을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(str_level.equals("")){
                            Toast.makeText(Theme_Focus_Write.this, "난이도를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(str_escape.equals("")){
                                Toast.makeText(Theme_Focus_Write.this, "성공여부를 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(Edit_Date.getText().toString().length() < 8){
                                    Toast.makeText(Theme_Focus_Write.this, "플레이 날짜 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
                                }else if(Integer.parseInt(Edit_Date.getText().toString()) > Integer.parseInt(setNowTime())){
                                   Toast.makeText(Theme_Focus_Write.this, "플레이 날짜를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    if(Integer.parseInt(Edit_Date.getText().toString().substring(0, 4)) < 2015){
                                        Toast.makeText(Theme_Focus_Write.this, "플레이 날짜를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        if(Integer.parseInt(Edit_Date.getText().toString().substring(4, 6)) > 12){
                                            Toast.makeText(Theme_Focus_Write.this, "플레이 날짜를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            if(Integer.parseInt(Edit_Date.getText().toString().substring(6, 8)) > 31){
                                                Toast.makeText(Theme_Focus_Write.this, "플레이 날짜를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Log.i("ㄷㄴ", Edit_Memo.getText().toString());
                                                Async_Save async_save = new Async_Save();
                                                async_save.execute(str_theme_pk, User_Pk, Txt_StarCount.getText().toString(), Edit_Memo.getText().toString(), Edit_Date.getText().toString(), str_level, str_escape, str_time, Edit_Hint.getText().toString(), str_exp, bol_grade_switch+"");

                                            }
                                        }
                                    }
                               }
                             }
                        }
                    }
               }
            }
        });
    }
    public class Async_Save extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;

        private String http_result = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Theme_Focus_Write.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                HttpClient http = new HttpClient();
                http_result = http.HttpClient("Web_Escape", "Theme_Focus_Review_Write_v4.jsp",params);

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("ㄴㄴㄴㄴ", result);
            setting_view = false;
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
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(Theme_Focus_Write.this);
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
    /** 이모티콘이 있을경우 "" 리턴, 그렇지 않을 경우 null 리턴 **/
  /*  private InputFilter specialCharacterFilter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                // 이모티콘 패턴
                Pattern unicodeOutliers = Pattern.compile("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+");
                // '-' 입력 받고 싶을 경우 : unicodeOutliers.matcher(source).matches() && !source.toString().matches(".*-.*")
                if(unicodeOutliers.matcher(source).matches()) {
                    Toast.makeText(Theme_Focus_Write.this, "사용할 수 없는 이모티콘입니다.", Toast.LENGTH_SHORT).show();
                    return ""; } } return null; } };*/
}



