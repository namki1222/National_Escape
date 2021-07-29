package blackcap.nationalescape.Activity.tab4;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import blackcap.nationalescape.R;

public class Notice_Focus extends AppCompatActivity {
    private ImageView Img_Back;
    private TextView Txt_Title, Txt_Date, Txt_Contents;
    private String str_title, str_date, str_contents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_notice_focus);

        init();
        setImgBack();

    }
    public void init(){
        Intent intent1 = getIntent();
        str_title = intent1.getStringExtra("Title");
        str_date = intent1.getStringExtra("Date");
        str_contents = intent1.getStringExtra("Contents");

        Img_Back = (ImageView)findViewById(R.id.img_back);
        Txt_Title = (TextView) findViewById(R.id.txt_title);
        Txt_Title.setText(str_title);
        Txt_Date = (TextView) findViewById(R.id.txt_date);
        Txt_Date.setText(str_date);
        Txt_Contents = (TextView) findViewById(R.id.txt_contents);
        Txt_Contents.setText(str_contents);
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



