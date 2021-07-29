package blackcap.nationalescape.Activity.tab3;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import blackcap.nationalescape.R;

import static blackcap.nationalescape.Activity.Fragment_main3.tab3_refresh;

public class Theme_Filter extends AppCompatActivity {
    private SharedPreferences preferences; //캐쉬 데이터 생성
    private SharedPreferences.Editor editor;

    private ImageView Img_Back;
    private TextView Txt_All_loc, Txt_1_loc, Txt_2_loc, Txt_3_loc, Txt_4_loc, Txt_5_loc;
    private TextView Txt_6_loc, Txt_7_loc, Txt_8_loc, Txt_9_loc, Txt_10_loc, Txt_11_loc;
    private TextView Txt_12_loc, Txt_13_loc, Txt_14_loc, Txt_15_loc, Txt_16_loc, Txt_17_loc;
    private TextView Txt_18_loc, Txt_19_loc, Txt_20_loc, Txt_21_loc, Txt_22_loc, Txt_23_loc;
    private TextView Txt_24_loc, Txt_25_loc, Txt_26_loc;

    private TextView Txt_1, Txt_2, Txt_3, Txt_4, Txt_5;
    private TextView Txt_6, Txt_7, Txt_8, Txt_9, Txt_10, Txt_11;
    private TextView Txt_12, Txt_13, Txt_14, Txt_15, Txt_16, Txt_17;
    private TextView Txt_18, Txt_19, Txt_20, Txt_21;

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

    private Switch Switch_Myreview;

    private String filter_category = "", filter_tool = "", filter_person = "", filter_level = "", filter_activity = "", filter_area = "", filter_myreview = "";
    private int count = 0;
    public boolean Theme_refresh = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3_filter);

        init();
        setFilter_init();
        //장르 선택
        setTheme_Event();
        //지역 선택
        setArea_Event();
        //장치/자물쇠 선택
        setTool_Event();
        //추천인원 선택
        setPerson_Event();
        //난이도 선택
        setLevel_Event();
        //활동성 선택
        setActivity_Event();
        //내 리뷰 선택
        setMyReview_Event();
        setImgBack();
        tab3_refresh = true;
    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Txt_All_loc = (TextView)findViewById(R.id.txt_all_loc);
        Txt_All_loc.setTag("전국");
        Txt_1_loc = (TextView)findViewById(R.id.txt_1_loc);
        Txt_1_loc.setTag("홍대_");
        Txt_2_loc = (TextView)findViewById(R.id.txt_2_loc);
        Txt_2_loc.setTag("강남_");
        Txt_3_loc = (TextView)findViewById(R.id.txt_3_loc);
        Txt_3_loc.setTag("건대_");
        Txt_4_loc = (TextView)findViewById(R.id.txt_4_loc);
        Txt_4_loc.setTag("신촌_");
        Txt_5_loc = (TextView)findViewById(R.id.txt_5_loc);
        Txt_5_loc.setTag("대학로_");
        Txt_6_loc = (TextView)findViewById(R.id.txt_6_loc);
        Txt_6_loc.setTag("강북_");
        Txt_7_loc = (TextView)findViewById(R.id.txt_7_loc);
        Txt_7_loc.setTag("신림_");
        Txt_8_loc = (TextView)findViewById(R.id.txt_8_loc);
        Txt_8_loc.setTag("서울(기타)_");
        Txt_9_loc = (TextView)findViewById(R.id.txt_9_loc);
        Txt_9_loc.setTag("부천_");
        Txt_10_loc = (TextView)findViewById(R.id.txt_10_loc);
        Txt_10_loc.setTag("일산_");
        Txt_11_loc = (TextView)findViewById(R.id.txt_11_loc);
        Txt_11_loc.setTag("수원_");
        Txt_12_loc = (TextView)findViewById(R.id.txt_12_loc);
        Txt_12_loc.setTag("안양_");
        Txt_13_loc = (TextView)findViewById(R.id.txt_13_loc);
        Txt_13_loc.setTag("경기(기타)_");
        Txt_14_loc = (TextView)findViewById(R.id.txt_14_loc);
        Txt_14_loc.setTag("인천_");
        Txt_15_loc = (TextView)findViewById(R.id.txt_15_loc);
        Txt_15_loc.setTag("대전_");
        Txt_16_loc = (TextView)findViewById(R.id.txt_16_loc);
        Txt_16_loc.setTag("천안_");
        Txt_17_loc = (TextView)findViewById(R.id.txt_17_loc);
        Txt_17_loc.setTag("청주_");
        Txt_18_loc = (TextView)findViewById(R.id.txt_18_loc);
        Txt_18_loc.setTag("충청(기타)_");
        Txt_19_loc = (TextView)findViewById(R.id.txt_19_loc);
        Txt_19_loc.setTag("대구_");
        Txt_20_loc = (TextView)findViewById(R.id.txt_20_loc);
        Txt_20_loc.setTag("부산_");
        Txt_21_loc = (TextView)findViewById(R.id.txt_21_loc);
        Txt_21_loc.setTag("경상(기타)_");
        Txt_22_loc = (TextView)findViewById(R.id.txt_22_loc);
        Txt_22_loc.setTag("전주_");
        Txt_23_loc = (TextView)findViewById(R.id.txt_23_loc);
        Txt_23_loc.setTag("광주_");
        Txt_24_loc = (TextView)findViewById(R.id.txt_24_loc);
        Txt_24_loc.setTag("전라(기타)_");
        Txt_25_loc = (TextView)findViewById(R.id.txt_25_loc);
        Txt_25_loc.setTag("강원_");
        Txt_26_loc = (TextView)findViewById(R.id.txt_26_loc);
        Txt_26_loc.setTag("제주_");

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
        Txt_15 = (TextView)findViewById(R.id.txt_15);
        Txt_15.setTag("공포");
        Txt_16 = (TextView)findViewById(R.id.txt_16);
        Txt_16.setTag("드라마");
        Txt_17 = (TextView)findViewById(R.id.txt_17);
        Txt_17.setTag("미스터리");
        Txt_18 = (TextView)findViewById(R.id.txt_18);
        Txt_18.setTag("아케이드");
        Txt_19 = (TextView)findViewById(R.id.txt_19);
        Txt_19.setTag("액션");
        Txt_20 = (TextView)findViewById(R.id.txt_20);
        Txt_20.setTag("야외");
        Txt_21 = (TextView)findViewById(R.id.txt_21);
        Txt_21.setTag("\\?");
        Txt_21.setText("?");
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

        Switch_Myreview = (Switch)findViewById(R.id.switch_myreview);

        tab3_refresh = true;
    }
    public void setFilter_init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        filter_category = preferences.getString("filter_theme_category", "전체");
        filter_tool = preferences.getString("filter_theme_tools", "rockdeviceboth");
        filter_person = preferences.getString("filter_theme_person", "2345");
        filter_level = preferences.getString("filter_theme_level", "12345");
        filter_activity = preferences.getString("filter_theme_activity", "123");
        filter_area = preferences.getString("filter_theme_area", "전국");
        filter_myreview = preferences.getString("filter_theme_myreview", "off");

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
        if(filter_category.contains(Txt_15.getTag().toString())){
            Txt_15.setTextColor(getResources().getColor(R.color.white));
            Txt_15.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_category.contains(Txt_16.getTag().toString())){
            Txt_16.setTextColor(getResources().getColor(R.color.white));
            Txt_16.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_category.contains(Txt_17.getTag().toString())){
            Txt_17.setTextColor(getResources().getColor(R.color.white));
            Txt_17.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_category.contains(Txt_18.getTag().toString())){
            Txt_18.setTextColor(getResources().getColor(R.color.white));
            Txt_18.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_category.contains(Txt_19.getTag().toString())){
            Txt_19.setTextColor(getResources().getColor(R.color.white));
            Txt_19.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_category.contains(Txt_20.getTag().toString())){
            Txt_20.setTextColor(getResources().getColor(R.color.white));
            Txt_20.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_category.contains(Txt_21.getTag().toString())){
            Txt_21.setTextColor(getResources().getColor(R.color.white));
            Txt_21.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        if(filter_tool.contains("rock")){
            Layout_Tool_Rock.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Tool_Rock.setImageResource(R.drawable.theme_filter_rock_white);
            Txt_Tool_Rock.setTextColor(getResources().getColor(R.color.white));
        }
        if(filter_tool.contains("device")){
            Layout_Tool_Device.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Tool_Device.setImageResource(R.drawable.theme_filter_device_white);
            Txt_Tool_Device.setTextColor(getResources().getColor(R.color.white));
        }
        if(filter_tool.contains("both")){
            Layout_Tool_Both.setBackgroundColor(getResources().getColor(R.color.point3));
            Img_Tool_Both.setImageResource(R.drawable.theme_filter_both_white);
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

        //전체
        if(filter_area.contains(Txt_All_loc.getTag().toString())){
            Txt_All_loc.setTextColor(getResources().getColor(R.color.white));
            Txt_All_loc.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        else{
            //서울
            if(filter_area.contains(Txt_1_loc.getTag().toString())){
                Txt_1_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_1_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //홍대
            if(filter_area.contains(Txt_2_loc.getTag().toString())){
                Txt_2_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_2_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //강남
            if(filter_area.contains(Txt_3_loc.getTag().toString())){
                Txt_3_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_3_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //건대
            if(filter_area.contains(Txt_4_loc.getTag().toString())){
                Txt_4_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_4_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //경기
            if(filter_area.contains(Txt_5_loc.getTag().toString())){
                Txt_5_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_5_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //부평
            if(filter_area.contains(Txt_6_loc.getTag().toString())){
                Txt_6_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_6_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //인천
            if(filter_area.contains(Txt_7_loc.getTag().toString())){
                Txt_7_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_7_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //수원
            if(filter_area.contains(Txt_8_loc.getTag().toString())){
                Txt_8_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_8_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //안산
            if(filter_area.contains(Txt_9_loc.getTag().toString())){
                Txt_9_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_9_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //부산
            if(filter_area.contains(Txt_10_loc.getTag().toString())){
                Txt_10_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_10_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //대구
            if(filter_area.contains(Txt_11_loc.getTag().toString())){
                Txt_11_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_11_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //울산
            if(filter_area.contains(Txt_12_loc.getTag().toString())){
                Txt_12_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_12_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //대전
            if(filter_area.contains(Txt_13_loc.getTag().toString())){
                Txt_13_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_13_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //광주
            if(filter_area.contains(Txt_14_loc.getTag().toString())){
                Txt_14_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_14_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //경상 전체
            if(filter_area.contains(Txt_15_loc.getTag().toString())){
                Txt_15_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_15_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //전라 전체
            if(filter_area.contains(Txt_16_loc.getTag().toString())){
                Txt_16_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_16_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //충청 전체
            if(filter_area.contains(Txt_17_loc.getTag().toString())){
                Txt_17_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_17_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //강원 전체
            if(filter_area.contains(Txt_18_loc.getTag().toString())){
                Txt_18_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_18_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_19_loc.getTag().toString())){
                Txt_19_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_19_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_20_loc.getTag().toString())){
                Txt_20_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_20_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_21_loc.getTag().toString())){
                Txt_21_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_21_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_22_loc.getTag().toString())){
                Txt_22_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_22_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_23_loc.getTag().toString())){
                Txt_23_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_23_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_24_loc.getTag().toString())){
                Txt_24_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_24_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_25_loc.getTag().toString())){
                Txt_25_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_25_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_26_loc.getTag().toString())){
                Txt_26_loc.setTextColor(getResources().getColor(R.color.white));
                Txt_26_loc.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
        }

        if(filter_myreview.equals("on")){
            Switch_Myreview.setChecked(true);
        }
        else{
            Switch_Myreview.setChecked(false);
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
        Txt_15.setTextColor(getResources().getColor(R.color.black));
        Txt_15.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_16.setTextColor(getResources().getColor(R.color.black));
        Txt_16.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_17.setTextColor(getResources().getColor(R.color.black));
        Txt_17.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_18.setTextColor(getResources().getColor(R.color.black));
        Txt_18.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_19.setTextColor(getResources().getColor(R.color.black));
        Txt_19.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_20_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_20.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_21.setTextColor(getResources().getColor(R.color.black));
        Txt_21.setBackgroundColor(getResources().getColor(R.color.white));
    }
    public void setOtherArea_Event(){
        Txt_1.setTextColor(getResources().getColor(R.color.black));
        Txt_1.setBackgroundColor(getResources().getColor(R.color.white));

        filter_category = filter_category.replace(Txt_1.getTag().toString(), "");
        editor = preferences.edit();
        editor.putString("filter_theme_category", filter_category);
        editor.commit();
    }
    public void setTheme_Event(){
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

                        filter_category = filter_category.replace(Txt_1.getTag().toString(), "");
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

                        filter_category = filter_category.replace(Txt_2.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_2.setTextColor(getResources().getColor(R.color.white));
                    Txt_2.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_2.getTag().toString())){
                        filter_category = filter_category+Txt_2.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_3.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_3.setTextColor(getResources().getColor(R.color.white));
                    Txt_3.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_3.getTag().toString())){
                        filter_category = filter_category+Txt_3.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_4.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_4.setTextColor(getResources().getColor(R.color.white));
                    Txt_4.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_4.getTag().toString())){
                        filter_category = filter_category+Txt_4.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_5.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_5.setTextColor(getResources().getColor(R.color.white));
                    Txt_5.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_5.getTag().toString())){
                        filter_category = filter_category+Txt_5.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_6.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_6.setTextColor(getResources().getColor(R.color.white));
                    Txt_6.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_6.getTag().toString())){
                        filter_category = filter_category+Txt_6.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_7.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_7.setTextColor(getResources().getColor(R.color.white));
                    Txt_7.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_7.getTag().toString())){
                        filter_category = filter_category+Txt_7.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_8.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_8.setTextColor(getResources().getColor(R.color.white));
                    Txt_8.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_8.getTag().toString())){
                        filter_category = filter_category+Txt_8.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_9.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_9.setTextColor(getResources().getColor(R.color.white));
                    Txt_9.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_9.getTag().toString())){
                        filter_category = filter_category+Txt_9.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_10.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_10.setTextColor(getResources().getColor(R.color.white));
                    Txt_10.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_10.getTag().toString())){
                        filter_category = filter_category+Txt_10.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_11.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_11.setTextColor(getResources().getColor(R.color.white));
                    Txt_11.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_11.getTag().toString())){
                        filter_category = filter_category+Txt_11.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_12.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_12.setTextColor(getResources().getColor(R.color.white));
                    Txt_12.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_12.getTag().toString())){
                        filter_category = filter_category+Txt_12.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_13.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_13.setTextColor(getResources().getColor(R.color.white));
                    Txt_13.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_13.getTag().toString())){
                        filter_category = filter_category+Txt_13.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_category = filter_category.replace(Txt_14.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_14.setTextColor(getResources().getColor(R.color.white));
                    Txt_14.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_14.getTag().toString())){
                        filter_category = filter_category+Txt_14.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
            }
        });
        Txt_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_15.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_15.setTextColor(getResources().getColor(R.color.black));
                        Txt_15.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replace(Txt_15.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_15.setTextColor(getResources().getColor(R.color.white));
                    Txt_15.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_15.getTag().toString())){
                        filter_category = filter_category+Txt_15.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
            }
        });

        //드라마 이벤트
        Txt_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_16.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_16.setTextColor(getResources().getColor(R.color.black));
                        Txt_16.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replace(Txt_16.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_16.setTextColor(getResources().getColor(R.color.white));
                    Txt_16.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_16.getTag().toString())){
                        filter_category = filter_category+Txt_16.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
            }
        });

        //미스테리
        Txt_17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_17.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_17.setTextColor(getResources().getColor(R.color.black));
                        Txt_17.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replace(Txt_17.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_17.setTextColor(getResources().getColor(R.color.white));
                    Txt_17.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_17.getTag().toString())){
                        filter_category = filter_category+Txt_17.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
            }
        });

        //아케이드
        Txt_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_18.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_18.setTextColor(getResources().getColor(R.color.black));
                        Txt_18.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replace(Txt_18.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_18.setTextColor(getResources().getColor(R.color.white));
                    Txt_18.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_18.getTag().toString())){
                        filter_category = filter_category+Txt_18.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
            }
        });

        //액션
        Txt_19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_19.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_19.setTextColor(getResources().getColor(R.color.black));
                        Txt_19.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replace(Txt_19.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_19.setTextColor(getResources().getColor(R.color.white));
                    Txt_19.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_19.getTag().toString())){
                        filter_category = filter_category+Txt_19.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
            }
        });

        //야외
        Txt_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_20.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_20.setTextColor(getResources().getColor(R.color.black));
                        Txt_20.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replace(Txt_20.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_20.setTextColor(getResources().getColor(R.color.white));
                    Txt_20.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_20.getTag().toString())){
                        filter_category = filter_category+Txt_20.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
            }
        });

        //?
        Txt_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_21.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(filter_category.length() <= 3){
                        Toast.makeText(Theme_Filter.this, "한개 이상의 장르를 선택해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_21.setTextColor(getResources().getColor(R.color.black));
                        Txt_21.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_category = filter_category.replace(Txt_21.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
                }
                else{
                    Txt_21.setTextColor(getResources().getColor(R.color.white));
                    Txt_21.setBackgroundColor(getResources().getColor(R.color.point3));
                    setOtherArea_Event();
                    if(!filter_category.contains(Txt_21.getTag().toString())){
                        filter_category = filter_category+Txt_21.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_category", filter_category);
                        editor.commit();
                    }
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

                        filter_tool = filter_tool.replace("rock", "");
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

                        filter_tool = filter_tool.replace("device", "");
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

                        filter_tool = filter_tool.replace("both", "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_tools", filter_tool);
                        editor.commit();
                    }
                }
                else{
                    Layout_Tool_Both.setBackgroundColor(getResources().getColor(R.color.point3));
                    Img_Tool_Both.setImageResource(R.drawable.theme_filter_both_white);
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

                        filter_person = filter_person.replace("2", "");
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

                        filter_person = filter_person.replace("3", "");
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

                        filter_person = filter_person.replace("4", "");
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

                        filter_person = filter_person.replace("5", "");
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

                        filter_level = filter_level.replace("1", "");
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

                        filter_level = filter_level.replace("2", "");
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

                        filter_level = filter_level.replace("3", "");
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

                        filter_level = filter_level.replace("4", "");
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

                        filter_level = filter_level.replace("5", "");
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

                        filter_activity = filter_activity.replace("1", "");
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

                        filter_activity = filter_activity.replace("2", "");
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

                        filter_activity = filter_activity.replace("3", "");
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
    public void setArea_Event(){
        Txt_All_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_All_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_All_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_All_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_All_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    Txt_All_loc.setTextColor(getResources().getColor(R.color.white));
                    Txt_All_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                    filter_area = Txt_All_loc.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_theme_area", filter_area);
                    editor.commit();

                    count=1;

                    setArea_All_Event();
                }
            }
        });
        Txt_1_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_1_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_1_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_1_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_1_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_1_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_1_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_1_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_2_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_2_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_2_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_2_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_2_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_2_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_2_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_2_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_3_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_3_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_3_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_3_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_3_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_3_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_3_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_3_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_4_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_4_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_4_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_4_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_4_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_4_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_4_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area + Txt_4_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_5_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_5_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_5_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_5_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_5_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_5_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_5_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_5_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_6_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_6_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_6_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_6_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_6_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }

                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_6_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_6_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_6_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_7_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_7_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_7_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_7_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_7_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_7_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_7_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_7_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_8_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_8_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_8_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_8_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_8_loc.getTag().toString(), "");
                        Log.i("테스트1", filter_area);
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_8_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_8_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_8_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_9_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_9_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_9_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_9_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_9_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_9_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_9_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_9_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_10_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_10_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_10_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_10_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_10_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_10_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_10_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_10_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_11_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_11_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_11_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_11_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_11_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_11_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_11_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_11_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_12_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_12_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_12_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_12_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_12_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_12_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_12_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_12_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_13_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_13_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_13_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_13_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_13_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_13_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_13_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_13_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_14_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_14_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_14_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_14_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_14_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_14_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_14_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_14_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_15_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_15_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_15_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_15_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_15_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_15_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_15_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_15_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_16_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_16_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_16_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_16_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_16_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_16_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_16_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_16_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_17_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_17_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_17_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_17_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_17_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_17_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_17_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_17_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_18_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_18_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_18_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_18_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_18_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_18_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_18_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_18_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_19_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_19_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_19_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_19_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_19_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_19_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_19_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_19_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_20_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_20_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_20_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_20_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_20_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_20_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_20_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_20_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_21_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_21_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_21_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_21_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_21_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_21_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_21_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_21_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_22_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_22_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_22_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_22_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_22_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_22_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_22_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_22_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_23_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_23_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_23_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_23_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_23_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_23_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_23_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_23_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_24_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_24_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_24_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_24_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_24_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_24_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_24_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_24_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_25_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_25_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_25_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_25_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_25_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_25_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_25_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_25_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_26_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_26_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Theme_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_26_loc.setTextColor(getResources().getColor(R.color.black));
                        Txt_26_loc.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_26_loc.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Theme_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_26_loc.setTextColor(getResources().getColor(R.color.white));
                        Txt_26_loc.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_26_loc.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_theme_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
    }
    public void setArea_All_Event(){
        Txt_1_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_1_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_2_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_2_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_3_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_3_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_4_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_4_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_5_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_5_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_6_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_6_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_7_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_7_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_8_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_8_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_9_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_9_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_10_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_10_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_11_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_11_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_12_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_12_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_13_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_13_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_14_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_14_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_15_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_15_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_16_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_16_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_17_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_17_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_18_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_18_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_19_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_19_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_20_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_20_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_21_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_21_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_22_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_22_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_23_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_23_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_24_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_24_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_25_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_25_loc.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_26_loc.setTextColor(getResources().getColor(R.color.black));
        Txt_26_loc.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void setArea_AllDelete_Event(){
        if(Txt_All_loc.getCurrentTextColor() == getResources().getColor(R.color.white)){
            Txt_All_loc.setTextColor(getResources().getColor(R.color.black));
            Txt_All_loc.setBackgroundColor(getResources().getColor(R.color.white));

            filter_area = filter_area.replace(Txt_All_loc.getTag().toString(), "");
            editor = preferences.edit();
            editor.putString("filter_theme_area", filter_area);
            editor.commit();

            count--;
        }
    }
    public void setMyReview_Event(){
        Switch_Myreview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    Switch_Myreview.setChecked(true);

                    editor = preferences.edit();
                    editor.putString("filter_theme_myreview", "on");
                    editor.commit();
                }
                else{
                    Switch_Myreview.setChecked(false);

                    editor = preferences.edit();
                    editor.putString("filter_theme_myreview", "off");
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




