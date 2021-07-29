package blackcap.nationalescape.Activity.user;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import blackcap.nationalescape.R;

public class Login_Join0 extends AppCompatActivity {
    ImageView Back;

    TextView Txt_Privacy1, Txt_Privacy2;
    ImageView Img_Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join0);

        init();
        //인증번호 받기
        setBtnNext();
        //뒤로가기 선언 및 이벤트 셋팅
        setBack();
    }

    public void init() {
        Txt_Privacy1 = (TextView) findViewById(R.id.txt_privacy1);
        Txt_Privacy2 = (TextView) findViewById(R.id.txt_privacy2);
        Img_Next = (ImageView)findViewById(R.id.img_next);;

        Txt_Privacy1.setText(R.string.privacy1);
        Txt_Privacy2.setText(R.string.privacy2);
    }
    public void setBtnNext() {
        Img_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Join0.this, Login_Join1.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }
    public void setBack() {
        Back = (ImageView) findViewById(R.id.img_back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

