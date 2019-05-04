package blackcap.nationalescape.Activity.tab3;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import blackcap.nationalescape.R;

import static blackcap.nationalescape.Activity.Fragment_main3.tab3_refresh;

public class Theme_Filter extends AppCompatActivity {
    private SharedPreferences preferences; //캐쉬 데이터 생성
    private SharedPreferences.Editor editor;

    private ImageView Img_Back;
    private TextView Txt_1, Txt_2, Txt_3, Txt_4, Txt_5, Txt_6;
    private TextView Txt_7, Txt_8, Txt_9, Txt_10, Txt_11, Txt_12, Txt_13, Txt_14;

    private LinearLayout Layout_Tool_Rock, Layout_Tool_Device, Layout_Tool_Both;
    private ImageView Img_Tool_Rock, Img_Tool_Device, Img_Tool_Both;
    private TextView Txt_Tool_Rock, Txt_Tool_Device, Txt_Tool_Both;

    private TextView Txt_Person2, Txt_Person3, Txt_Person4, Txt_Person5;

    private LinearLayout Layout_Level1, Layout_Level2, Layout_Level3, Layout_Level4, Layout_Level5;
    private ImageView Img_Level1_1;
    private ImageView Img_Level2_1, Img_Level2_2;
    private ImageView Img_Level3_1, Img_Level3_2, Img_Level3_3;
    private ImageView Img_Level4_1, Img_Level4_2, Img_Level4_3, Img_Level4_4;
    private ImageView Img_Level5_1, Img_Level5_2, Img_Level5_3, Img_Level5_4, Img_Level5_5;

    private LinearLayout Layout_Activity1, Layout_Activity2, Layout_Activity3;
    private ImageView Img_Activity1, Img_Activity2, Img_Activity3;

    private String filter_category = "", filter_tool = "", filter_person = "", filter_level = "", filter_activity = "";

    public boolean Theme_refresh = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3_filter);

        init();
        setFilter_init();
        //장르 선택
        setArea_Event();
        //장치/자물쇠 선택
        setTool_Event();
        //추천인원 선택
        setPerson_Event();
        //난이도 선택
        setLevel_Event();
        //활동성 선택
        setActivity_Event();
        setImgBack();
        tab3_refresh = true;
    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Txt_1 = (TextView)findViewById(R.id.txt_1);
        Txt_1.setTag("전체");
        Txt_2 = (TextView)findViewById(R.id.txt_2);
        Txt_2.setTag("추리");
        Txt_3 = (TextView)findViewById(R.id.txt_3);
        Txt_3.setTag("스릴러");
        Txt_4 = (TextView)findViewById(R.id.txt_4);
        Txt_4.setTag("감성");
        Txt_5 = (TextView)findViewById(R.id.txt_5);
        Txt_5.setTag("로맨스");
        Txt_6 = (TextView)findViewById(R.id.txt_6);
        Txt_6.setTag("잠입");
        Txt_7 = (TextView)findViewById(R.id.txt_7);
        Txt_7.setTag("범죄");
        Txt_8 = (TextView)findViewById(R.id.txt_8);
        Txt_8.setTag("코미디");
        Txt_9 = (TextView)findViewById(R.id.txt_9);
        Txt_9.setTag("모험");
        Txt_10 = (TextView)findViewById(R.id.txt_10);
        Txt_10.setTag("판타지");
        Txt_11 = (TextView)findViewById(R.id.txt_11);
        Txt_11.setTag("19금");
        Txt_12 = (TextView)findViewById(R.id.txt_12);
        Txt_12.setTag("역사");
        Txt_13 = (TextView)findViewById(R.id.txt_13);
        Txt_13.setTag("SF");
        Txt_14 = (TextView)findViewById(R.id.txt_14);
        Txt_14.setTag("음악");
        Layout_Tool_Rock = (LinearLayout)findViewById(R.id.layout_tool_rock);
        Layout_Tool_Device = (LinearLayout)findViewById(R.id.layout_tool_device);
        Layout_Tool_Both = (LinearLayout)findViewById(R.id.layout_tool_both);
        Img_Tool_Rock = (ImageView)findViewById(R.id.img_tool_rock);
        Img_Tool_Device = (ImageView)findViewById(R.id.img_tool_device);
        Img_Tool_Both = (ImageView)findViewById(R.id.img_tool_both);
        Txt_Tool_Rock = (TextView)findViewById(R.id.txt_tool_rock);
        Txt_Tool_Device = (TextView)findViewById(R.id.txt_tool_device);
        Txt_Tool_Both = (TextView)findViewById(R.id.txt_tool_both);

        Txt_Person2 = (TextView)findViewById(R.id.txt_person2);
        Txt_Person3 = (TextView)findViewById(R.id.txt_person3);
        Txt_Person4 = (TextView)findViewById(R.id.txt_person4);
        Txt_Person5 = (TextView)findViewById(R.id.txt_person5);

        Layout_Level1 = (LinearLayout)findViewById(R.id.layout_level1);
        Layout_Level2 = (LinearLayout)findViewById(R.id.layout_level2);
        Layout_Level3 = (LinearLayout)findViewById(R.id.layout_level3);
        Layout_Level4 = (LinearLayout)findViewById(R.id.layout_level4);
        Layout_Level5 = (LinearLayout)findViewById(R.id.layout_level5);
        Img_Level1_1 = (ImageView)findViewById(R.id.img_level1_1);
        Img_Level2_1 = (ImageView)findViewById(R.id.img_level2_1);
        Img_Level2_2 = (ImageView)findViewById(R.id.img_level2_2);
        Img_Level3_1 = (ImageView)findViewById(R.id.img_level3_1);
        Img_Level3_2 = (ImageView)findViewById(R.id.img_level3_2);
        Img_Level3_3 = (ImageView)findViewById(R.id.img_level3_3);
        Img_Level4_1 = (ImageView)findViewById(R.id.img_level4_1);
        Img_Level4_2 = (ImageView)findViewById(R.id.img_level4_2);
        Img_Level4_3 = (ImageView)findViewById(R.id.img_level4_3);
        Img_Level4_4 = (ImageView)findViewById(R.id.img_level4_4);
        Img_Level5_1 = (ImageView)findViewById(R.id.img_level5_1);
        Img_Level5_2 = (ImageView)findViewById(R.id.img_level5_2);
        Img_Level5_3 = (ImageView)findViewById(R.id.img_level5_3);
        Img_Level5_4 = (ImageView)findViewById(R.id.img_level5_4);
        Img_Level5_5 = (ImageView)findViewById(R.id.img_level5_5);

        Layout_Activity1 = (LinearLayout)findViewById(R.id.layout_activity1);
        Layout_Activity2 = (LinearLayout)findViewById(R.id.layout_activity2);
        Layout_Activity3 = (LinearLayout)findViewById(R.id.layout_activity3);
        Img_Activity1 = (ImageView)findViewById(R.id.img_activity1);
        Img_Activity2 = (ImageView)findViewById(R.id.img_activity2);
        Img_Activity3 = (ImageView)findViewById(R.id.img_activity3);

        tab3_refresh = true;
    }
    public void setFilter_init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        filter_category = preferences.getString("filter_theme_category", "전체");
        filter_tool = preferences.getString("filter_theme_tools", "rockdeviceboth");
        filter_person = preferences.getString("filter_theme_person", "2345");
        filter_level = preferences.getString("filter_theme_level", "12345");
        filter_activity = preferences.getString("filter_theme_activity", "123");
        if(filter_category.contains(Txt_1.getTag().toString())){
            Txt_1.setTextColor(getResources().getColor(R.color.white));
            Txt_1.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        //서울
        if(filter_category.contains(Txt_2.getTag().toString())){
            Txt_2.setTextColor(getResources().getColor(R.color.white));
            Txt_2.setBackgroundColor(getResources().getColor(R.color.point3));

        }

        if(filter_category.contains(Txt_3.getTag().toString())){
            Txt_3.setTextColor(getResources().getColor(R.color.white));
            Txt_3.setBackgroundColor(getResources().getColor(R.color.point3));

        }

        if(filter_category.contains(Txt_4.getTag().toString())){
            Txt_4.setTextColor(getResources().getColor(R.color.white));
            Txt_4.setBackgroundColor(getResources().getColor(R.color.point3));

        }

        if(filter_category.contains(Txt_5.getTag().toString())){
            Txt_5.setTextColor(getResources().getColor(R.color.white));
            Txt_5.setBackgroundColor(getResources().getColor(R.color.point3));
        }

        if(filter_category.contains(Txt_6.getTag().toString())){
            Txt_6.setTextColor(getResources().getColor(R.color.white));
            Txt_6.setBackgroundColor(getResources().getColor(R.color.point3));
        }

        if(filter_category.contains(Txt_7.getTag().toString())){
            Txt_7.setTextColor(getResources().getColor(R.color.white));
            Txt_7.setBackgroundColor(getResources().getColor(R.color.point3));
        }

        if(filter_category.contains(Txt_8.getTag().toString())){
            Txt_8.setTextColor(getResources().getColor(R.color.white));
            Txt_8.setBackgroundColor(getResources().getColor(R.color.point3));
        }

        if(filter_category.contains(Txt_9.getTag().toString())){
            Txt_9.setTextColor(getResources().getColor(R.color.white));
            Txt_9.setBackgroundColor(getResources().getColor(R.color.point3));
        }

        if(filter_category.contains(Txt_10.getTag().toString())){
            Txt_10.setTextColor(getResources().getColor(R.color.white));
            Txt_10.setBackgroundColor(getResources().getColor(R.color.point3));
        }

        if(filter_category.contains(Txt_11.getTag().toString())){
            Txt_11.setTextColor(getResources().getColor(R.color.white));
            Txt_11.setBackgroundColor(getResources().getColor(R.color.point3));
        }

        if(filter_category.contains(Txt_12.getTag().toString())){
            Txt_12.setTextColor(getResources().getColor(R.color.white));
            Txt_12.setBackgroundColor(getResources().getColor(R.color.point3));
        }

        if(filter_category.contains(Txt_13.getTag().toString())){
            Txt_13.setTextColor(getResources().getColor(R.color.white));
            Txt_13.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_category.contains(Txt_14.getTag().toString())){
            Txt_14.setTextColor(getResources().getColor(R.color.white));
            Txt_14.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_tool.contains("rock")){
            Layout_Tool_Rock.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Tool_Rock.setImageResource(R.drawable.theme_filter_rock_white);
            Txt_Tool_Rock.setTextColor(getResources().getColor(R.color.white));
        }
        if(filter_tool.contains("device")){
            Layout_Tool_Device.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Tool_Device.setImageResource(R.drawable.theme_filter_rock_white);
            Txt_Tool_Device.setTextColor(getResources().getColor(R.color.white));
        }
        if(filter_tool.contains("both")){
            Layout_Tool_Both.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Tool_Both.setImageResource(R.drawable.theme_filter_rock_white);
            Txt_Tool_Both.setTextColor(getResources().getColor(R.color.white));
        }

        if(filter_person.contains("2")){
            Txt_Person2.setTextColor(getResources().getColor(R.color.white));
            Txt_Person2.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_person.contains("3")){
            Txt_Person3.setTextColor(getResources().getColor(R.color.white));
            Txt_Person3.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_person.contains("4")){
            Txt_Person4.setTextColor(getResources().getColor(R.color.white));
            Txt_Person4.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_person.contains("5")){
            Txt_Person5.setTextColor(getResources().getColor(R.color.white));
            Txt_Person5.setBackgroundColor(getResources().getColor(R.color.point3));
        }

        if(filter_level.contains("1")){
            Layout_Level1.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Level1_1.setImageResource(R.drawable.theme_level_white);
        }
        if(filter_level.contains("2")){
            Layout_Level2.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Level2_1.setImageResource(R.drawable.theme_level_white);
            Img_Level2_2.setImageResource(R.drawable.theme_level_white);
        }
        if(filter_level.contains("3")){
            Layout_Level3.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Level3_1.setImageResource(R.drawable.theme_level_white);
            Img_Level3_2.setImageResource(R.drawable.theme_level_white);
            Img_Level3_3.setImageResource(R.drawable.theme_level_white);
        }
        if(filter_level.contains("4")){
            Layout_Level4.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Level4_1.setImageResource(R.drawable.theme_level_white);
            Img_Level4_2.setImageResource(R.drawable.theme_level_white);
            Img_Level4_3.setImageResource(R.drawable.theme_level_white);
            Img_Level4_4.setImageResource(R.drawable.theme_level_white);
        }
        if(filter_level.contains("5")){
            Layout_Level5.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Level5_1.setImageResource(R.drawable.theme_level_white);
            Img_Level5_2.setImageResource(R.drawable.theme_level_white);
            Img_Level5_3.setImageResource(R.drawable.theme_level_white);
            Img_Level5_4.setImageResource(R.drawable.theme_level_white);
            Img_Level5_5.setImageResource(R.drawable.theme_level_white);
        }

        if(filter_activity.contains("1")){
            Layout_Activity1.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Activity1.setImageResource(R.drawable.theme_filter_activity1_white);
        }
        if(filter_activity.contains("2")){
            Layout_Activity2.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Activity2.setImageResource(R.drawable.theme_filter_activity2_white);
        }
        if(filter_activity.contains("3")){
            Layout_Activity3.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Activity3.setImageResource(R.drawable.theme_filter_activity3_white);
        }
    }
    public void setAllArea_Event(){
        Txt_2.setTextColor(getResources().getColor(R.color.black));
        Txt_2.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_3.setTextColor(getResources().getColor(R.color.black));
        Txt_3.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_4.setTextColor(getResources().getColor(R.color.black));
        Txt_4.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_5.setTextColor(getResources().getColor(R.color.black));
        Txt_5.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_6.setTextColor(getResources().getColor(R.color.black));
        Txt_6.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_7.setTextColor(getResources().getColor(R.color.black));
        Txt_7.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_8.setTextColor(getResources().getColor(R.color.black));
        Txt_8.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_9.setTextColor(getResources().getColor(R.color.black));
        Txt_9.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_10.setTextColor(getResources().getColor(R.color.black));
        Txt_10.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_11.setTextColor(getResources().getColor(R.color.black));
        Txt_11.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_12.setTextColor(getResources().getColor(R.color.black));
        Txt_12.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_13.setTextColor(getResources().getColor(R.color.black));
        Txt_13.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_14.setTextColor(getResources().getColor(R.color.black));
        Txt_14.setBackgroundColor(getResources().getColor(R.color.white));
    }
    public void setOtherArea_Event(){
        Txt_1.setTextColor(getResources().getColor(R.color.black));
        Txt_1.setBackgroundColor(getResources().getColor(R.color.white));

        filter_category = filter_category.replaceAll(Txt_1.getTag().toString(), "");
        editor = preferences.edit();
        editor.putString("filter_theme_category", filter_category);
        editor.commit();
    }
    public void setArea_Event(){
        Txt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_1.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_1.setTextColor(getResources().getColor(R.color.black));
                        Txt_1.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_1.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_1.setTextColor(getResources().getColor(R.color.white));
                    Txt_1.setBackgroundColor(getResources().getColor(R.color.point3));
                    setAllArea_Event();
                    filter_category = Txt_1.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_2.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_2.setTextColor(getResources().getColor(R.color.black));
                        Txt_2.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_2.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_2.setTextColor(getResources().getColor(R.color.white));
                    Txt_2.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_2.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_3.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_3.setTextColor(getResources().getColor(R.color.black));
                        Txt_3.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_3.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_3.setTextColor(getResources().getColor(R.color.white));
                    Txt_3.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_3.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_4.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_4.setTextColor(getResources().getColor(R.color.black));
                        Txt_4.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_4.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_4.setTextColor(getResources().getColor(R.color.white));
                    Txt_4.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_4.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_5.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_5.setTextColor(getResources().getColor(R.color.black));
                        Txt_5.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_5.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_5.setTextColor(getResources().getColor(R.color.white));
                    Txt_5.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_5.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_6.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_6.setTextColor(getResources().getColor(R.color.black));
                        Txt_6.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_6.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_6.setTextColor(getResources().getColor(R.color.white));
                    Txt_6.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_6.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_7.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_7.setTextColor(getResources().getColor(R.color.black));
                        Txt_7.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_7.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_7.setTextColor(getResources().getColor(R.color.white));
                    Txt_7.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_7.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_8.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_8.setTextColor(getResources().getColor(R.color.black));
                        Txt_8.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_8.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_8.setTextColor(getResources().getColor(R.color.white));
                    Txt_8.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_8.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_9.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_9.setTextColor(getResources().getColor(R.color.black));
                        Txt_9.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_9.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_9.setTextColor(getResources().getColor(R.color.white));
                    Txt_9.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_9.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_10.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_10.setTextColor(getResources().getColor(R.color.black));
                        Txt_10.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_10.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_10.setTextColor(getResources().getColor(R.color.white));
                    Txt_10.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_10.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_11.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_11.setTextColor(getResources().getColor(R.color.black));
                        Txt_11.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_11.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_11.setTextColor(getResources().getColor(R.color.white));
                    Txt_11.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_11.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_12.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_12.setTextColor(getResources().getColor(R.color.black));
                        Txt_12.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_12.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_12.setTextColor(getResources().getColor(R.color.white));
                    Txt_12.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_12.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_13.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_13.setTextColor(getResources().getColor(R.color.black));
                        Txt_13.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_13.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_13.setTextColor(getResources().getColor(R.color.white));
                    Txt_13.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_13.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
        Txt_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_14.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_14.setTextColor(getResources().getColor(R.color.black));
                        Txt_14.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replaceAll(Txt_14.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_14.setTextColor(getResources().getColor(R.color.white));
                    Txt_14.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    filter_category = filter_category+Txt_14.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_category", filter_category);
                    editor.commit();
                }
            }
        });
    }
    public void setTool_Event(){
        Layout_Tool_Rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Tool_Rock.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_tool.length() == 4 || filter_tool.length() == 6){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장치를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Layout_Tool_Rock.setBackgroundColor(getResources().getColor(R.color.white));
                        Img_Tool_Rock.setImageResource(R.drawable.theme_filter_rock);
                        Txt_Tool_Rock.setTextColor(getResources().getColor(R.color.black));

                        filter_tool = filter_tool.replaceAll("rock", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_tools", filter_tool);
                        editor.commit();
                    }
                }
                else{
                    Layout_Tool_Rock.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Tool_Rock.setImageResource(R.drawable.theme_filter_rock_white);
                    Txt_Tool_Rock.setTextColor(getResources().getColor(R.color.white));

                    filter_tool = filter_tool +"rock";
                    editor = preferences.edit();
                    editor.putString("filter_theme_tools", filter_tool);
                    editor.commit();
                }
            }
        });

        Layout_Tool_Device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Tool_Device.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_tool.length() == 4 || filter_tool.length() == 6){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장치를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Layout_Tool_Device.setBackgroundColor(getResources().getColor(R.color.white));
                        Img_Tool_Device.setImageResource(R.drawable.theme_filter_device);
                        Txt_Tool_Device.setTextColor(getResources().getColor(R.color.black));

                        filter_tool = filter_tool.replaceAll("device", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_tools", filter_tool);
                        editor.commit();
                    }
                }
                else{
                    Layout_Tool_Device.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Tool_Device.setImageResource(R.drawable.theme_filter_device_white);
                    Txt_Tool_Device.setTextColor(getResources().getColor(R.color.white));

                    filter_tool = filter_tool +"device";
                    editor = preferences.edit();
                    editor.putString("filter_theme_tools", filter_tool);
                    editor.commit();
                }
            }
        });

        Layout_Tool_Both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Tool_Both.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_tool.length() == 4 || filter_tool.length() == 6){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장치를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Layout_Tool_Both.setBackgroundColor(getResources().getColor(R.color.white));
                        Img_Tool_Both.setImageResource(R.drawable.theme_filter_both);
                        Txt_Tool_Both.setTextColor(getResources().getColor(R.color.black));

                        filter_tool = filter_tool.replaceAll("both", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_tools", filter_tool);
                        editor.commit();
                    }
                }
                else{
                    Layout_Tool_Both.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Tool_Both.setImageResource(R.drawable.theme_filter_device_white);
                    Txt_Tool_Both.setTextColor(getResources().getColor(R.color.white));

                    filter_tool = filter_tool +"both";
                    editor = preferences.edit();
                    editor.putString("filter_theme_tools", filter_tool);
                    editor.commit();
                }
            }
        });
    }
    public void setPerson_Event(){
        Txt_Person2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Person2.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_person.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 인원을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Person2.setTextColor(getResources().getColor(R.color.black));
                        Txt_Person2.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_person = filter_person.replaceAll("2", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_person", filter_person);
                        editor.commit();
                    }
                }
                else{
                    Txt_Person2.setTextColor(getResources().getColor(R.color.white));
                    Txt_Person2.setBackgroundColor(getResources().getColor(R.color.point3));

                    filter_person = filter_person +"2";
                    editor = preferences.edit();
                    editor.putString("filter_theme_person", filter_person);
                    editor.commit();
                }
            }
        });
        Txt_Person3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Person3.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_person.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 인원을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Person3.setTextColor(getResources().getColor(R.color.black));
                        Txt_Person3.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_person = filter_person.replaceAll("3", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_person", filter_person);
                        editor.commit();
                    }
                }
                else{
                    Txt_Person3.setTextColor(getResources().getColor(R.color.white));
                    Txt_Person3.setBackgroundColor(getResources().getColor(R.color.point3));

                    filter_person = filter_person +"3";
                    editor = preferences.edit();
                    editor.putString("filter_theme_person", filter_person);
                    editor.commit();
                }
            }
        });
        Txt_Person4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Person4.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_person.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 인원을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Person4.setTextColor(getResources().getColor(R.color.black));
                        Txt_Person4.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_person = filter_person.replaceAll("4", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_person", filter_person);
                        editor.commit();
                    }
                }
                else{
                    Txt_Person4.setTextColor(getResources().getColor(R.color.white));
                    Txt_Person4.setBackgroundColor(getResources().getColor(R.color.point3));

                    filter_person = filter_person +"4";
                    editor = preferences.edit();
                    editor.putString("filter_theme_person", filter_person);
                    editor.commit();
                }
            }
        });
        Txt_Person5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Person5.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_person.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 인원을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Person5.setTextColor(getResources().getColor(R.color.black));
                        Txt_Person5.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_person = filter_person.replaceAll("5", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_person", filter_person);
                        editor.commit();
                    }
                }
                else{
                    Txt_Person5.setTextColor(getResources().getColor(R.color.white));
                    Txt_Person5.setBackgroundColor(getResources().getColor(R.color.point3));

                    filter_person = filter_person +"5";
                    editor = preferences.edit();
                    editor.putString("filter_theme_person", filter_person);
                    editor.commit();
                }
            }
        });
    }
    public void setLevel_Event(){
        Layout_Level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ColorDrawable)Layout_Level1.getBackground()).getColor() == getResources().getColor(R.color.point3)){
                    if(filter_level.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 난이도를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Layout_Level1.setBackgroundColor(getResources().getColor(R.color.white));
                        Img_Level1_1.setImageResource(R.drawable.theme_level_yellow);

                        filter_level = filter_level.replaceAll("1", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_level", filter_level);
                        editor.commit();
                    }
                }
                else{
                    Layout_Level1.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Level1_1.setImageResource(R.drawable.theme_level_white);

                    filter_level = filter_level +"1";
                    editor = preferences.edit();
                    editor.putString("filter_theme_level", filter_level);
                    editor.commit();
                }
            }
        });
        Layout_Level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ColorDrawable)Layout_Level2.getBackground()).getColor() == getResources().getColor(R.color.point3)){
                    if(filter_level.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 난이도를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Layout_Level2.setBackgroundColor(getResources().getColor(R.color.white));
                        Img_Level2_1.setImageResource(R.drawable.theme_level_yellow);
                        Img_Level2_2.setImageResource(R.drawable.theme_level_yellow);

                        filter_level = filter_level.replaceAll("2", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_level", filter_level);
                        editor.commit();
                    }
                }
                else{
                    Layout_Level2.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Level2_1.setImageResource(R.drawable.theme_level_white);
                    Img_Level2_2.setImageResource(R.drawable.theme_level_white);

                    filter_level = filter_level +"2";
                    editor = preferences.edit();
                    editor.putString("filter_theme_level", filter_level);
                    editor.commit();
                }
            }
        });
        Layout_Level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ColorDrawable)Layout_Level3.getBackground()).getColor() == getResources().getColor(R.color.point3)){
                    if(filter_level.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 난이도를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Layout_Level3.setBackgroundColor(getResources().getColor(R.color.white));
                        Img_Level3_1.setImageResource(R.drawable.theme_level_yellow);
                        Img_Level3_2.setImageResource(R.drawable.theme_level_yellow);
                        Img_Level3_3.setImageResource(R.drawable.theme_level_yellow);

                        filter_level = filter_level.replaceAll("3", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_level", filter_level);
                        editor.commit();
                    }
                }
                else{
                    Layout_Level3.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Level3_1.setImageResource(R.drawable.theme_level_white);
                    Img_Level3_2.setImageResource(R.drawable.theme_level_white);
                    Img_Level3_3.setImageResource(R.drawable.theme_level_white);

                    filter_level = filter_level +"3";
                    editor = preferences.edit();
                    editor.putString("filter_theme_level", filter_level);
                    editor.commit();
                }
            }
        });
        Layout_Level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ColorDrawable)Layout_Level4.getBackground()).getColor() == getResources().getColor(R.color.point3)){
                    if(filter_level.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 난이도를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Layout_Level4.setBackgroundColor(getResources().getColor(R.color.white));
                        Img_Level4_1.setImageResource(R.drawable.theme_level_yellow);
                        Img_Level4_2.setImageResource(R.drawable.theme_level_yellow);
                        Img_Level4_3.setImageResource(R.drawable.theme_level_yellow);
                        Img_Level4_4.setImageResource(R.drawable.theme_level_yellow);

                        filter_level = filter_level.replaceAll("4", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_level", filter_level);
                        editor.commit();
                    }
                }
                else{
                    Layout_Level4.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Level4_1.setImageResource(R.drawable.theme_level_white);
                    Img_Level4_2.setImageResource(R.drawable.theme_level_white);
                    Img_Level4_3.setImageResource(R.drawable.theme_level_white);
                    Img_Level4_4.setImageResource(R.drawable.theme_level_white);

                    filter_level = filter_level +"4";
                    editor = preferences.edit();
                    editor.putString("filter_theme_level", filter_level);
                    editor.commit();
                }
            }
        });
        Layout_Level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ColorDrawable)Layout_Level5.getBackground()).getColor() == getResources().getColor(R.color.point3)){
                    if(filter_level.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 난이도를 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Layout_Level5.setBackgroundColor(getResources().getColor(R.color.white));
                        Img_Level5_1.setImageResource(R.drawable.theme_level_yellow);
                        Img_Level5_2.setImageResource(R.drawable.theme_level_yellow);
                        Img_Level5_3.setImageResource(R.drawable.theme_level_yellow);
                        Img_Level5_4.setImageResource(R.drawable.theme_level_yellow);
                        Img_Level5_5.setImageResource(R.drawable.theme_level_yellow);

                        filter_level = filter_level.replaceAll("5", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_level", filter_level);
                        editor.commit();
                    }
                }
                else{
                    Layout_Level5.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Level5_1.setImageResource(R.drawable.theme_level_white);
                    Img_Level5_2.setImageResource(R.drawable.theme_level_white);
                    Img_Level5_3.setImageResource(R.drawable.theme_level_white);
                    Img_Level5_4.setImageResource(R.drawable.theme_level_white);
                    Img_Level5_5.setImageResource(R.drawable.theme_level_white);

                    filter_level = filter_level +"5";
                    editor = preferences.edit();
                    editor.putString("filter_theme_level", filter_level);
                    editor.commit();
                }
            }
        });
    }
    public void setActivity_Event(){
        Layout_Activity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ColorDrawable)Layout_Activity1.getBackground()).getColor() == getResources().getColor(R.color.point3)){
                    if(filter_activity.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 활동성을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Layout_Activity1.setBackgroundColor(getResources().getColor(R.color.white));
                        Img_Activity1.setImageResource(R.drawable.theme_filter_activity1);

                        filter_activity = filter_activity.replaceAll("1", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_activity", filter_activity);
                        editor.commit();
                    }
                }
                else{
                    Layout_Activity1.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Activity1.setImageResource(R.drawable.theme_filter_activity1_white);

                    filter_activity = filter_activity +"1";
                    editor = preferences.edit();
                    editor.putString("filter_theme_activity", filter_activity);
                    editor.commit();
                }
            }
        });
        Layout_Activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ColorDrawable)Layout_Activity2.getBackground()).getColor() == getResources().getColor(R.color.point3)){
                    if(filter_activity.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 활동성을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Layout_Activity2.setBackgroundColor(getResources().getColor(R.color.white));
                        Img_Activity2.setImageResource(R.drawable.theme_filter_activity2);

                        filter_activity = filter_activity.replaceAll("2", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_activity", filter_activity);
                        editor.commit();
                    }
                }
                else{
                    Layout_Activity2.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Activity2.setImageResource(R.drawable.theme_filter_activity2_white);

                    filter_activity = filter_activity +"2";
                    editor = preferences.edit();
                    editor.putString("filter_theme_activity", filter_activity);
                    editor.commit();
                }
            }
        });
        ;
        Layout_Activity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ColorDrawable)Layout_Activity3.getBackground()).getColor() == getResources().getColor(R.color.point3)){
                    if(filter_activity.length() == 1){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 활동성을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Layout_Activity3.setBackgroundColor(getResources().getColor(R.color.white));
                        Img_Activity3.setImageResource(R.drawable.theme_filter_activity3);

                        filter_activity = filter_activity.replaceAll("3", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_activity", filter_activity);
                        editor.commit();
                    }
                }
                else{
                    Layout_Activity3.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Activity3.setImageResource(R.drawable.theme_filter_activity3_white);

                    filter_activity = filter_activity +"3";
                    editor = preferences.edit();
                    editor.putString("filter_theme_activity", filter_activity);
                    editor.commit();
                }
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




