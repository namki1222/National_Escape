package blackcap.nationalescape.Activity.tab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import blackcap.nationalescape.Calendar.view.CalendarView;
import blackcap.nationalescape.R;

public class Book_Chapter1 extends AppCompatActivity {
    private CalendarView calendarView;
    private ImageView Img_Next, Img_Back;
    private TextView Txt_Company;
    // private EditText editText;
    private String str_company_pk = "", str_company_title = "", str_theme_pk = "", str_theme_title = "";
    private String str_date = "";

    public static Activity activity_book_chapter1;

    Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_chapter1);


        intent1 = getIntent();
        str_company_pk = intent1.getStringExtra("Company_Pk");
        str_company_title = intent1.getStringExtra("Company_Title");
        Log.i("테스3", str_company_title);
        if(intent1.hasExtra("Theme_Pk") == true){
            str_theme_pk = intent1.getStringExtra("Theme_Pk");
            str_theme_title = intent1.getStringExtra("Theme_Title");
        }
        initialize();
        setImgBack();
    }

    private void initialize(){
        activity_book_chapter1 = Book_Chapter1.this;
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        Img_Next = (ImageView)findViewById(R.id.img_next);
        Txt_Company = (TextView)findViewById(R.id.txt_company);
        Txt_Company.setText(str_company_title);

        str_date = setNowTime();

        calendarView.setOnDayClickListener(new CalendarView.OnDayClickListener() {
            @Override
            public void onClick(int day, int month, int year, boolean hasEvent) {
                String str_month = "";
                String str_day = "";
                if(month <= 9){
                    str_month = "0"+month;
                }else{
                    str_month = month+"";
                }

                if(day <= 9){
                    str_day = "0"+day;
                }else{
                    str_day = day+"";
                }

                str_date = Integer.toString(year)+str_month+str_day+"";
                calendarView.setToday(day, month, year);
                Log.i("테스트", str_date);
            }
        });
        Img_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book_Chapter1.this, Book_Chapter2.class);
                intent.putExtra("Company_Pk", str_company_pk);
                intent.putExtra("Company_Title", str_company_title);
                if(intent1.hasExtra("Theme_Pk") == true){
                    intent.putExtra("Theme_Pk", str_theme_pk);
                    intent.putExtra("Theme_Title", str_theme_title);
                }
                intent.putExtra("Theme_Pk", str_theme_pk);
                intent.putExtra("Theme_Title", str_company_title);
                intent.putExtra("Date", str_date);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Img_Back = (ImageView)findViewById(R.id.img_back);
    }
    private String setNowTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        return sdf.format(date)+"";
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
}