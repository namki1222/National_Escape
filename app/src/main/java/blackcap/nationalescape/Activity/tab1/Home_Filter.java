package blackcap.nationalescape.Activity.tab1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import blackcap.nationalescape.R;

import static blackcap.nationalescape.Activity.Fragment_main1.tab1_refresh;

public class Home_Filter extends AppCompatActivity {
    private SharedPreferences preferences; //캐쉬 데이터 생성
    private SharedPreferences.Editor editor;

    private ImageView Img_Back;
    private TextView Txt_All, Txt_1, Txt_2, Txt_3, Txt_4, Txt_5;
    private TextView Txt_6, Txt_7, Txt_8, Txt_9, Txt_10, Txt_11;
    private TextView Txt_12, Txt_13, Txt_14, Txt_15, Txt_16, Txt_17;
    private TextView Txt_18, Txt_19, Txt_20, Txt_21, Txt_22, Txt_23;
    private TextView Txt_24, Txt_25, Txt_26;

    private TextView Txt_Distance, Txt_Grade;

    private LinearLayout Layout_Foreign, Layout_Alone, Layout_Escape;
    private ImageView Img_Foreign, Img_Alone, Img_Escape;

    private String filter_area = "", filter_sort = "";
    private int count = 0;
    private String check_foreign = "", check_alone = "", check_escape = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        init();
        setFilter_init();
        setArea_Event();
        //체크박스 이벤트
        setCheck_Event();
        setImgBack();

    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Txt_All = (TextView)findViewById(R.id.txt_all);
        Txt_All.setTag("전국");
        Txt_1 = (TextView)findViewById(R.id.txt_1);
        Txt_1.setTag("홍대_");
        Txt_2 = (TextView)findViewById(R.id.txt_2);
        Txt_2.setTag("강남_");
        Txt_3 = (TextView)findViewById(R.id.txt_3);
        Txt_3.setTag("건대_");
        Txt_4 = (TextView)findViewById(R.id.txt_4);
        Txt_4.setTag("신촌_");
        Txt_5 = (TextView)findViewById(R.id.txt_5);
        Txt_5.setTag("대학로_");
        Txt_6 = (TextView)findViewById(R.id.txt_6);
        Txt_6.setTag("강북_");
        Txt_7 = (TextView)findViewById(R.id.txt_7);
        Txt_7.setTag("신림_");
        Txt_8 = (TextView)findViewById(R.id.txt_8);
        Txt_8.setTag("서울(기타)_");
        Txt_9 = (TextView)findViewById(R.id.txt_9);
        Txt_9.setTag("부천_");
        Txt_10 = (TextView)findViewById(R.id.txt_10);
        Txt_10.setTag("일산_");
        Txt_11 = (TextView)findViewById(R.id.txt_11);
        Txt_11.setTag("수원_");
        Txt_12 = (TextView)findViewById(R.id.txt_12);
        Txt_12.setTag("안양_");
        Txt_13 = (TextView)findViewById(R.id.txt_13);
        Txt_13.setTag("경기(기타)_");
        Txt_14 = (TextView)findViewById(R.id.txt_14);
        Txt_14.setTag("인천_");
        Txt_15 = (TextView)findViewById(R.id.txt_15);
        Txt_15.setTag("대전_");
        Txt_16 = (TextView)findViewById(R.id.txt_16);
        Txt_16.setTag("천안_");
        Txt_17 = (TextView)findViewById(R.id.txt_17);
        Txt_17.setTag("청주_");
        Txt_18 = (TextView)findViewById(R.id.txt_18);
        Txt_18.setTag("충청(기타)_");
        Txt_19 = (TextView)findViewById(R.id.txt_19);
        Txt_19.setTag("대구_");
        Txt_20 = (TextView)findViewById(R.id.txt_20);
        Txt_20.setTag("부산_");
        Txt_21 = (TextView)findViewById(R.id.txt_21);
        Txt_21.setTag("경상(기타)_");
        Txt_22 = (TextView)findViewById(R.id.txt_22);
        Txt_22.setTag("전주_");
        Txt_23 = (TextView)findViewById(R.id.txt_23);
        Txt_23.setTag("광주_");
        Txt_24 = (TextView)findViewById(R.id.txt_24);
        Txt_24.setTag("전라(기타)_");
        Txt_25 = (TextView)findViewById(R.id.txt_25);
        Txt_25.setTag("강원_");
        Txt_26 = (TextView)findViewById(R.id.txt_26);
        Txt_26.setTag("제주_");

        Txt_Distance = (TextView)findViewById(R.id.txt_distance);
        Txt_Distance.setTag("distance");
        Txt_Grade = (TextView)findViewById(R.id.txt_grade);
        Txt_Grade.setTag("grade");

        Layout_Foreign = (LinearLayout)findViewById(R.id.layout_foreign);
        Layout_Alone = (LinearLayout)findViewById(R.id.layout_alone);
        Layout_Escape = (LinearLayout)findViewById(R.id.layout_escape);
        Img_Foreign = (ImageView)findViewById(R.id.img_foreign);
        Img_Alone = (ImageView)findViewById(R.id.img_alone);
        Img_Escape = (ImageView)findViewById(R.id.img_escape);

        tab1_refresh = true;
    }
    public void setFilter_init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        filter_area = preferences.getString("filter_area", "전국");
        filter_sort = preferences.getString("filter_sort", "distance");
        check_foreign = preferences.getString("check_foreign", "");
        check_alone = preferences.getString("check_alone", "");
        check_escape = preferences.getString("check_escape", "");
        Log.i("테스1", filter_area);
        //전체
        if(filter_area.contains(Txt_All.getTag().toString())){
            Txt_All.setTextColor(getResources().getColor(R.color.white));
            Txt_All.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        else{
            //서울
            if(filter_area.contains(Txt_1.getTag().toString())){
                Txt_1.setTextColor(getResources().getColor(R.color.white));
                Txt_1.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //홍대
            if(filter_area.contains(Txt_2.getTag().toString())){
                Txt_2.setTextColor(getResources().getColor(R.color.white));
                Txt_2.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //강남
            if(filter_area.contains(Txt_3.getTag().toString())){
                Txt_3.setTextColor(getResources().getColor(R.color.white));
                Txt_3.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //건대
            if(filter_area.contains(Txt_4.getTag().toString())){
                Txt_4.setTextColor(getResources().getColor(R.color.white));
                Txt_4.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //경기
            if(filter_area.contains(Txt_5.getTag().toString())){
                Txt_5.setTextColor(getResources().getColor(R.color.white));
                Txt_5.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //부평
            if(filter_area.contains(Txt_6.getTag().toString())){
                Txt_6.setTextColor(getResources().getColor(R.color.white));
                Txt_6.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //인천
            if(filter_area.contains(Txt_7.getTag().toString())){
                Txt_7.setTextColor(getResources().getColor(R.color.white));
                Txt_7.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //수원
            if(filter_area.contains(Txt_8.getTag().toString())){
                Txt_8.setTextColor(getResources().getColor(R.color.white));
                Txt_8.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //안산
            if(filter_area.contains(Txt_9.getTag().toString())){
                Txt_9.setTextColor(getResources().getColor(R.color.white));
                Txt_9.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //부산
            if(filter_area.contains(Txt_10.getTag().toString())){
                Txt_10.setTextColor(getResources().getColor(R.color.white));
                Txt_10.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //대구
            if(filter_area.contains(Txt_11.getTag().toString())){
                Txt_11.setTextColor(getResources().getColor(R.color.white));
                Txt_11.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //울산
            if(filter_area.contains(Txt_12.getTag().toString())){
                Txt_12.setTextColor(getResources().getColor(R.color.white));
                Txt_12.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //대전
            if(filter_area.contains(Txt_13.getTag().toString())){
                Txt_13.setTextColor(getResources().getColor(R.color.white));
                Txt_13.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //광주
            if(filter_area.contains(Txt_14.getTag().toString())){
                Txt_14.setTextColor(getResources().getColor(R.color.white));
                Txt_14.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //경상 전체
            if(filter_area.contains(Txt_15.getTag().toString())){
                Txt_15.setTextColor(getResources().getColor(R.color.white));
                Txt_15.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //전라 전체
            if(filter_area.contains(Txt_16.getTag().toString())){
                Txt_16.setTextColor(getResources().getColor(R.color.white));
                Txt_16.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //충청 전체
            if(filter_area.contains(Txt_17.getTag().toString())){
                Txt_17.setTextColor(getResources().getColor(R.color.white));
                Txt_17.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //강원 전체
            if(filter_area.contains(Txt_18.getTag().toString())){
                Txt_18.setTextColor(getResources().getColor(R.color.white));
                Txt_18.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_19.getTag().toString())){
                Txt_19.setTextColor(getResources().getColor(R.color.white));
                Txt_19.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_20.getTag().toString())){
                Txt_20.setTextColor(getResources().getColor(R.color.white));
                Txt_20.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_21.getTag().toString())){
                Txt_21.setTextColor(getResources().getColor(R.color.white));
                Txt_21.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_22.getTag().toString())){
                Txt_22.setTextColor(getResources().getColor(R.color.white));
                Txt_22.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_23.getTag().toString())){
                Txt_23.setTextColor(getResources().getColor(R.color.white));
                Txt_23.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_24.getTag().toString())){
                Txt_24.setTextColor(getResources().getColor(R.color.white));
                Txt_24.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_25.getTag().toString())){
                Txt_25.setTextColor(getResources().getColor(R.color.white));
                Txt_25.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
            //제주 전체
            if(filter_area.contains(Txt_26.getTag().toString())){
                Txt_26.setTextColor(getResources().getColor(R.color.white));
                Txt_26.setBackgroundColor(getResources().getColor(R.color.point3));
                count++;
            }
        }


        //정렬 기준
        if(filter_sort.contains(Txt_Distance.getTag().toString())){
            Txt_Distance.setTextColor(getResources().getColor(R.color.white));
            Txt_Distance.setBackgroundColor(getResources().getColor(R.color.point3));
        }
        else{
            Txt_Grade.setTextColor(getResources().getColor(R.color.white));
            Txt_Grade.setBackgroundColor(getResources().getColor(R.color.point3));
        }

        if(check_foreign.equals("foreign")){
            Img_Foreign.setImageResource(R.drawable.filter_check);
        }
        else{
            Img_Foreign.setImageResource(R.drawable.filter_uncheck);
        }

        if(check_alone.equals("alone")){
            Img_Alone.setImageResource(R.drawable.filter_check);
        }
        else{
            Img_Alone.setImageResource(R.drawable.filter_uncheck);
        }

        if(check_escape.equals("escape")){
            Img_Escape.setImageResource(R.drawable.filter_check);
        }
        else{
            Img_Escape.setImageResource(R.drawable.filter_uncheck);
        }

    }
    public void setArea_Event(){
        Txt_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_All.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_All.setTextColor(getResources().getColor(R.color.black));
                        Txt_All.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_All.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    Txt_All.setTextColor(getResources().getColor(R.color.white));
                    Txt_All.setBackgroundColor(getResources().getColor(R.color.point3));

                    filter_area = Txt_All.getTag().toString();
                    editor = preferences.edit();
                    editor.putString("filter_area", filter_area);
                    editor.commit();

                    count = 1;

                    setArea_All_Event();
                }
            }
        });
        Txt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_1.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_1.setTextColor(getResources().getColor(R.color.black));
                        Txt_1.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_1.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_1.setTextColor(getResources().getColor(R.color.white));
                        Txt_1.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_1.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_2.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_2.setTextColor(getResources().getColor(R.color.black));
                        Txt_2.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_2.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_2.setTextColor(getResources().getColor(R.color.white));
                        Txt_2.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_2.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_3.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_3.setTextColor(getResources().getColor(R.color.black));
                        Txt_3.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_3.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_3.setTextColor(getResources().getColor(R.color.white));
                        Txt_3.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_3.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_4.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_4.setTextColor(getResources().getColor(R.color.black));
                        Txt_4.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_4.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_4.setTextColor(getResources().getColor(R.color.white));
                        Txt_4.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_4.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_5.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_5.setTextColor(getResources().getColor(R.color.black));
                        Txt_5.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_5.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_5.setTextColor(getResources().getColor(R.color.white));
                        Txt_5.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_5.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_6.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_6.setTextColor(getResources().getColor(R.color.black));
                        Txt_6.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_6.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }

                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_6.setTextColor(getResources().getColor(R.color.white));
                        Txt_6.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_6.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_7.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_7.setTextColor(getResources().getColor(R.color.black));
                        Txt_7.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_7.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_7.setTextColor(getResources().getColor(R.color.white));
                        Txt_7.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_7.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_8.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_8.setTextColor(getResources().getColor(R.color.black));
                        Txt_8.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_8.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_8.setTextColor(getResources().getColor(R.color.white));
                        Txt_8.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_8.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_9.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_9.setTextColor(getResources().getColor(R.color.black));
                        Txt_9.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_9.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_9.setTextColor(getResources().getColor(R.color.white));
                        Txt_9.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_9.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_10.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_10.setTextColor(getResources().getColor(R.color.black));
                        Txt_10.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_10.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_10.setTextColor(getResources().getColor(R.color.white));
                        Txt_10.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_10.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_11.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_11.setTextColor(getResources().getColor(R.color.black));
                        Txt_11.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_11.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_11.setTextColor(getResources().getColor(R.color.white));
                        Txt_11.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_11.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_12.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_12.setTextColor(getResources().getColor(R.color.black));
                        Txt_12.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_12.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_12.setTextColor(getResources().getColor(R.color.white));
                        Txt_12.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_12.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_13.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_13.setTextColor(getResources().getColor(R.color.black));
                        Txt_13.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_13.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_13.setTextColor(getResources().getColor(R.color.white));
                        Txt_13.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_13.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_14.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_14.setTextColor(getResources().getColor(R.color.black));
                        Txt_14.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_14.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_14.setTextColor(getResources().getColor(R.color.white));
                        Txt_14.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_14.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_15.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_15.setTextColor(getResources().getColor(R.color.black));
                        Txt_15.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_15.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_15.setTextColor(getResources().getColor(R.color.white));
                        Txt_15.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_15.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_16.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_16.setTextColor(getResources().getColor(R.color.black));
                        Txt_16.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_16.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_16.setTextColor(getResources().getColor(R.color.white));
                        Txt_16.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_16.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_17.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_17.setTextColor(getResources().getColor(R.color.black));
                        Txt_17.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_17.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_17.setTextColor(getResources().getColor(R.color.white));
                        Txt_17.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_17.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_18.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_18.setTextColor(getResources().getColor(R.color.black));
                        Txt_18.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_18.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_18.setTextColor(getResources().getColor(R.color.white));
                        Txt_18.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_18.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_19.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_19.setTextColor(getResources().getColor(R.color.black));
                        Txt_19.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_19.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_19.setTextColor(getResources().getColor(R.color.white));
                        Txt_19.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_19.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_20.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_20.setTextColor(getResources().getColor(R.color.black));
                        Txt_20.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_20.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_20.setTextColor(getResources().getColor(R.color.white));
                        Txt_20.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_20.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_21.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_21.setTextColor(getResources().getColor(R.color.black));
                        Txt_21.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_21.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_21.setTextColor(getResources().getColor(R.color.white));
                        Txt_21.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_21.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_22.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_22.setTextColor(getResources().getColor(R.color.black));
                        Txt_22.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_22.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_22.setTextColor(getResources().getColor(R.color.white));
                        Txt_22.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_22.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_23.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_23.setTextColor(getResources().getColor(R.color.black));
                        Txt_23.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_23.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_23.setTextColor(getResources().getColor(R.color.white));
                        Txt_23.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_23.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_24.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_24.setTextColor(getResources().getColor(R.color.black));
                        Txt_24.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_24.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_24.setTextColor(getResources().getColor(R.color.white));
                        Txt_24.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_24.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_25.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_25.setTextColor(getResources().getColor(R.color.black));
                        Txt_25.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_25.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_25.setTextColor(getResources().getColor(R.color.white));
                        Txt_25.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_25.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_26.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_26.setTextColor(getResources().getColor(R.color.black));
                        Txt_26.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replace(Txt_26.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 5){
                        Toast.makeText(Home_Filter.this, "최대 5개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_26.setTextColor(getResources().getColor(R.color.white));
                        Txt_26.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_26.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Txt_Distance.setTextColor(getResources().getColor(R.color.white));
                Txt_Distance.setBackgroundColor(getResources().getColor(R.color.point3));
                Txt_Grade.setTextColor(getResources().getColor(R.color.black));
                Txt_Grade.setBackgroundColor(getResources().getColor(R.color.white));

                editor = preferences.edit();
                editor.putString("filter_sort", "distance");
                editor.commit();
            }
        });
        Txt_Grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Txt_Grade.setTextColor(getResources().getColor(R.color.white));
                Txt_Grade.setBackgroundColor(getResources().getColor(R.color.point3));
                Txt_Distance.setTextColor(getResources().getColor(R.color.black));
                Txt_Distance.setBackgroundColor(getResources().getColor(R.color.white));

                editor = preferences.edit();
                editor.putString("filter_sort", "grade");
                editor.commit();
            }
        });
    }
    public void setArea_AllDelete_Event(){
        if(Txt_All.getCurrentTextColor() == getResources().getColor(R.color.white)){
            Txt_All.setTextColor(getResources().getColor(R.color.black));
            Txt_All.setBackgroundColor(getResources().getColor(R.color.white));

            filter_area = filter_area.replace(Txt_All.getTag().toString(), "");
            editor = preferences.edit();
            editor.putString("filter_area", filter_area);
            editor.commit();

            count--;
        }
    }
    public void setArea_All_Event(){
        Txt_1.setTextColor(getResources().getColor(R.color.black));
        Txt_1.setBackgroundColor(getResources().getColor(R.color.white));
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
        Txt_20.setTextColor(getResources().getColor(R.color.black));
        Txt_20.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_21.setTextColor(getResources().getColor(R.color.black));
        Txt_21.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_22.setTextColor(getResources().getColor(R.color.black));
        Txt_22.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_23.setTextColor(getResources().getColor(R.color.black));
        Txt_23.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_24.setTextColor(getResources().getColor(R.color.black));
        Txt_24.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_25.setTextColor(getResources().getColor(R.color.black));
        Txt_25.setBackgroundColor(getResources().getColor(R.color.white));
        Txt_26.setTextColor(getResources().getColor(R.color.black));
        Txt_26.setBackgroundColor(getResources().getColor(R.color.white));
    }
    public void setCheck_Event(){
        Layout_Foreign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_foreign.equals("foreign")){
                    Img_Foreign.setImageResource(R.drawable.filter_uncheck);
                    check_foreign = "";

                    editor = preferences.edit();
                    editor.putString("check_foreign", check_foreign);
                    editor.commit();
                }
                else{
                    Img_Foreign.setImageResource(R.drawable.filter_check);
                    check_foreign = "foreign";

                    editor = preferences.edit();
                    editor.putString("check_foreign", check_foreign);
                    editor.commit();
                }
            }
        });

        Layout_Alone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_alone.equals("alone")){
                    Img_Alone.setImageResource(R.drawable.filter_uncheck);
                    check_alone = "";

                    editor = preferences.edit();
                    editor.putString("check_alone", check_alone);
                    editor.commit();
                }
                else{
                    Img_Alone.setImageResource(R.drawable.filter_check);
                    check_alone = "alone";

                    editor = preferences.edit();
                    editor.putString("check_alone", check_alone);
                    editor.commit();
                }
            }
        });

        Layout_Escape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_escape.equals("escape")){
                    Img_Escape.setImageResource(R.drawable.filter_uncheck);
                    check_escape = "";

                    editor = preferences.edit();
                    editor.putString("check_escape", check_escape);
                    editor.commit();
                }
                else{
                    Img_Escape.setImageResource(R.drawable.filter_check);
                    check_escape = "escape";

                    editor = preferences.edit();
                    editor.putString("check_escape", check_escape);
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



