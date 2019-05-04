package blackcap.nationalescape.Activity.tab1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import blackcap.nationalescape.R;

import static blackcap.nationalescape.Activity.Fragment_main1.tab1_refresh;

public class Home_Filter extends AppCompatActivity {
    private SharedPreferences preferences; //캐쉬 데이터 생성
    private SharedPreferences.Editor editor;

    private ImageView Img_Back;
    private TextView Txt_All, Txt_Seoul, Txt_Hongdae, Txt_Gangnam, Txt_Gundae, Txt_Geonggi;
    private TextView Txt_Bupung, Txt_Incheon, Txt_Suwon, Txt_Ansan, Txt_Busan, Txt_Daegu;
    private TextView Txt_Ulsan, Txt_Dageon, Txt_Gungju, Txt_Gyeongsang, Txt_Jenla, Txt_Chungcheong;
    private TextView Txt_Gangwon, Txt_Jeju;

    private TextView Txt_Distance, Txt_Grade;

    private String filter_area = "", filter_sort = "";
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        init();
        setFilter_init();
        setArea_Event();
        setImgBack();

    }
    public void init(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Txt_All = (TextView)findViewById(R.id.txt_all);
        Txt_All.setTag("전국");
        Txt_Seoul = (TextView)findViewById(R.id.txt_seoulall);
        Txt_Seoul.setTag("서울");
        Txt_Hongdae = (TextView)findViewById(R.id.txt_hongdae);
        Txt_Hongdae.setTag("홍대");
        Txt_Gangnam = (TextView)findViewById(R.id.txt_gangnam);
        Txt_Gangnam.setTag("강남");
        Txt_Gundae = (TextView)findViewById(R.id.txt_geondae);
        Txt_Gundae.setTag("건대");
        Txt_Geonggi = (TextView)findViewById(R.id.txt_gyeonggi);
        Txt_Geonggi.setTag("경기");
        Txt_Bupung = (TextView)findViewById(R.id.txt_bupyeong);
        Txt_Bupung.setTag("부평");
        Txt_Incheon = (TextView)findViewById(R.id.txt_incheon);
        Txt_Incheon.setTag("인천");
        Txt_Suwon = (TextView)findViewById(R.id.txt_suwon);
        Txt_Suwon.setTag("수원");
        Txt_Ansan = (TextView)findViewById(R.id.txt_ansan);
        Txt_Ansan.setTag("안산");
        Txt_Busan = (TextView)findViewById(R.id.txt_busan);
        Txt_Busan.setTag("부산");
        Txt_Daegu = (TextView)findViewById(R.id.txt_daegu);
        Txt_Daegu.setTag("대구");
        Txt_Ulsan = (TextView)findViewById(R.id.txt_ulsan);
        Txt_Ulsan.setTag("울산");
        Txt_Dageon = (TextView)findViewById(R.id.txt_daejeon);
        Txt_Dageon.setTag("대전");
        Txt_Gungju = (TextView)findViewById(R.id.txt_gwangju);
        Txt_Gungju.setTag("광주");
        Txt_Gyeongsang = (TextView)findViewById(R.id.txt_gyeongsang);
        Txt_Gyeongsang.setTag("경상");
        Txt_Jenla = (TextView)findViewById(R.id.txt_jeonla);
        Txt_Jenla.setTag("전라");
        Txt_Chungcheong = (TextView)findViewById(R.id.txt_chungcheong);
        Txt_Chungcheong.setTag("충청");
        Txt_Gangwon = (TextView)findViewById(R.id.txt_gangwon);
        Txt_Gangwon.setTag("강원");
        Txt_Jeju = (TextView)findViewById(R.id.txt_jeju);
        Txt_Jeju.setTag("제주");

        Txt_Distance = (TextView)findViewById(R.id.txt_distance);
        Txt_Distance.setTag("distance");
        Txt_Grade = (TextView)findViewById(R.id.txt_grade);
        Txt_Grade.setTag("grade");

        tab1_refresh = true;
    }
    public void setFilter_init(){
        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        filter_area = preferences.getString("filter_area", "전국");
        filter_sort = preferences.getString("filter_sort", "distance");

        //전체
        if(filter_area.contains(Txt_All.getTag().toString())){
            Txt_All.setTextColor(getResources().getColor(R.color.white));
            Txt_All.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //서울
        if(filter_area.contains(Txt_Seoul.getTag().toString())){
            Txt_Seoul.setTextColor(getResources().getColor(R.color.white));
            Txt_Seoul.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //홍대
        if(filter_area.contains(Txt_Hongdae.getTag().toString())){
            Txt_Hongdae.setTextColor(getResources().getColor(R.color.white));
            Txt_Hongdae.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //강남
        if(filter_area.contains(Txt_Gangnam.getTag().toString())){
            Txt_Gangnam.setTextColor(getResources().getColor(R.color.white));
            Txt_Gangnam.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //건대
        if(filter_area.contains(Txt_Gundae.getTag().toString())){
            Txt_Gundae.setTextColor(getResources().getColor(R.color.white));
            Txt_Gundae.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //경기
        if(filter_area.contains(Txt_Geonggi.getTag().toString())){
            Txt_Geonggi.setTextColor(getResources().getColor(R.color.white));
            Txt_Geonggi.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //부평
        if(filter_area.contains(Txt_Bupung.getTag().toString())){
            Txt_Bupung.setTextColor(getResources().getColor(R.color.white));
            Txt_Bupung.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //인천
        if(filter_area.contains(Txt_Incheon.getTag().toString())){
            Txt_Incheon.setTextColor(getResources().getColor(R.color.white));
            Txt_Incheon.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //수원
        if(filter_area.contains(Txt_Suwon.getTag().toString())){
            Txt_Suwon.setTextColor(getResources().getColor(R.color.white));
            Txt_Suwon.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //안산
        if(filter_area.contains(Txt_Ansan.getTag().toString())){
            Txt_Ansan.setTextColor(getResources().getColor(R.color.white));
            Txt_Ansan.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //부산
        if(filter_area.contains(Txt_Busan.getTag().toString())){
            Txt_Busan.setTextColor(getResources().getColor(R.color.white));
            Txt_Busan.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //대구
        if(filter_area.contains(Txt_Daegu.getTag().toString())){
            Txt_Daegu.setTextColor(getResources().getColor(R.color.white));
            Txt_Daegu.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //울산
        if(filter_area.contains(Txt_Ulsan.getTag().toString())){
            Txt_Ulsan.setTextColor(getResources().getColor(R.color.white));
            Txt_Ulsan.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //대전
        if(filter_area.contains(Txt_Dageon.getTag().toString())){
            Txt_Dageon.setTextColor(getResources().getColor(R.color.white));
            Txt_Dageon.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //광주
        if(filter_area.contains(Txt_Gungju.getTag().toString())){
            Txt_Gungju.setTextColor(getResources().getColor(R.color.white));
            Txt_Gungju.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //경상 전체
        if(filter_area.contains(Txt_Gyeongsang.getTag().toString())){
            Txt_Gyeongsang.setTextColor(getResources().getColor(R.color.white));
            Txt_Gyeongsang.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //전라 전체
        if(filter_area.contains(Txt_Jenla.getTag().toString())){
            Txt_Jenla.setTextColor(getResources().getColor(R.color.white));
            Txt_Jenla.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //충청 전체
        if(filter_area.contains(Txt_Chungcheong.getTag().toString())){
            Txt_Chungcheong.setTextColor(getResources().getColor(R.color.white));
            Txt_Chungcheong.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //강원 전체
        if(filter_area.contains(Txt_Gangwon.getTag().toString())){
            Txt_Gangwon.setTextColor(getResources().getColor(R.color.white));
            Txt_Gangwon.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
        }
        //제주 전체
        if(filter_area.contains(Txt_Jeju.getTag().toString())){
            Txt_Jeju.setTextColor(getResources().getColor(R.color.white));
            Txt_Jeju.setBackgroundColor(getResources().getColor(R.color.point3));
            count++;
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
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_All.setTextColor(getResources().getColor(R.color.white));
                        Txt_All.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_All.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;

                    }
                }
            }
        });
        Txt_Seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Seoul.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Seoul.setTextColor(getResources().getColor(R.color.black));
                        Txt_Seoul.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Seoul.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Seoul.setTextColor(getResources().getColor(R.color.white));
                        Txt_Seoul.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Seoul.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Hongdae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Hongdae.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Hongdae.setTextColor(getResources().getColor(R.color.black));
                        Txt_Hongdae.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Hongdae.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Hongdae.setTextColor(getResources().getColor(R.color.white));
                        Txt_Hongdae.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Hongdae.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Gangnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Gangnam.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Gangnam.setTextColor(getResources().getColor(R.color.black));
                        Txt_Gangnam.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Gangnam.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Gangnam.setTextColor(getResources().getColor(R.color.white));
                        Txt_Gangnam.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Gangnam.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Gundae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Gundae.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Gundae.setTextColor(getResources().getColor(R.color.black));
                        Txt_Gundae.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Gundae.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Gundae.setTextColor(getResources().getColor(R.color.white));
                        Txt_Gundae.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Gundae.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Geonggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Geonggi.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Geonggi.setTextColor(getResources().getColor(R.color.black));
                        Txt_Geonggi.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Geonggi.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Geonggi.setTextColor(getResources().getColor(R.color.white));
                        Txt_Geonggi.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Geonggi.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Bupung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Bupung.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Bupung.setTextColor(getResources().getColor(R.color.black));
                        Txt_Bupung.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Bupung.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }

                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Bupung.setTextColor(getResources().getColor(R.color.white));
                        Txt_Bupung.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Bupung.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Incheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Incheon.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Incheon.setTextColor(getResources().getColor(R.color.black));
                        Txt_Incheon.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Incheon.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Incheon.setTextColor(getResources().getColor(R.color.white));
                        Txt_Incheon.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Incheon.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Suwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Suwon.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Suwon.setTextColor(getResources().getColor(R.color.black));
                        Txt_Suwon.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Suwon.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Suwon.setTextColor(getResources().getColor(R.color.white));
                        Txt_Suwon.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Suwon.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Ansan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Ansan.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Ansan.setTextColor(getResources().getColor(R.color.black));
                        Txt_Ansan.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Ansan.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Ansan.setTextColor(getResources().getColor(R.color.white));
                        Txt_Ansan.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Ansan.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Busan.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Busan.setTextColor(getResources().getColor(R.color.black));
                        Txt_Busan.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Busan.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Busan.setTextColor(getResources().getColor(R.color.white));
                        Txt_Busan.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Busan.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Daegu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Daegu.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Daegu.setTextColor(getResources().getColor(R.color.black));
                        Txt_Daegu.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Daegu.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Daegu.setTextColor(getResources().getColor(R.color.white));
                        Txt_Daegu.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Daegu.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Ulsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Ulsan.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Ulsan.setTextColor(getResources().getColor(R.color.black));
                        Txt_Ulsan.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Ulsan.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Ulsan.setTextColor(getResources().getColor(R.color.white));
                        Txt_Ulsan.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Ulsan.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Dageon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Dageon.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Dageon.setTextColor(getResources().getColor(R.color.black));
                        Txt_Dageon.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Dageon.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Dageon.setTextColor(getResources().getColor(R.color.white));
                        Txt_Dageon.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Dageon.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Gungju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Gungju.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Gungju.setTextColor(getResources().getColor(R.color.black));
                        Txt_Gungju.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Gungju.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Gungju.setTextColor(getResources().getColor(R.color.white));
                        Txt_Gungju.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Gungju.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Gyeongsang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Gyeongsang.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Gyeongsang.setTextColor(getResources().getColor(R.color.black));
                        Txt_Gyeongsang.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Gyeongsang.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Gyeongsang.setTextColor(getResources().getColor(R.color.white));
                        Txt_Gyeongsang.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Gyeongsang.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Jenla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Jenla.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Jenla.setTextColor(getResources().getColor(R.color.black));
                        Txt_Jenla.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Jenla.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Jenla.setTextColor(getResources().getColor(R.color.white));
                        Txt_Jenla.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Jenla.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Chungcheong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Chungcheong.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Chungcheong.setTextColor(getResources().getColor(R.color.black));
                        Txt_Chungcheong.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Chungcheong.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Chungcheong.setTextColor(getResources().getColor(R.color.white));
                        Txt_Chungcheong.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Chungcheong.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Gangwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Gangwon.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Gangwon.setTextColor(getResources().getColor(R.color.black));
                        Txt_Gangwon.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Gangwon.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Gangwon.setTextColor(getResources().getColor(R.color.white));
                        Txt_Gangwon.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Gangwon.getTag().toString();
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count++;
                        setArea_AllDelete_Event();
                    }
                }
            }
        });
        Txt_Jeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Txt_Jeju.getCurrentTextColor() == getResources().getColor(R.color.white)){
                    if(count == 1){
                        Toast.makeText(Home_Filter.this, "최소 1개이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Jeju.setTextColor(getResources().getColor(R.color.black));
                        Txt_Jeju.setBackgroundColor(getResources().getColor(R.color.white));

                        filter_area = filter_area.replaceAll(Txt_Jeju.getTag().toString(), "");
                        editor = preferences.edit();
                        editor.putString("filter_area", filter_area);
                        editor.commit();

                        count--;
                    }
                }
                else{
                    if(count >= 3){
                        Toast.makeText(Home_Filter.this, "최대 3개 선택 가능합니다", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Txt_Jeju.setTextColor(getResources().getColor(R.color.white));
                        Txt_Jeju.setBackgroundColor(getResources().getColor(R.color.point3));

                        filter_area = filter_area+Txt_Jeju.getTag().toString();
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

                filter_area = filter_area+Txt_Jeju.getTag().toString();
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

                filter_area = filter_area+Txt_Jeju.getTag().toString();
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

            filter_area = filter_area.replaceAll(Txt_All.getTag().toString(), "");
            editor = preferences.edit();
            editor.putString("filter_area", filter_area);
            editor.commit();

            count--;
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



