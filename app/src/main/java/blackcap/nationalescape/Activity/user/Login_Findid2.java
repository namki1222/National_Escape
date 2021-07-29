package blackcap.nationalescape.Activity.user;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;


public class Login_Findid2 extends AppCompatActivity {
    private TextView Txt_Finish;
    private ImageView Img_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findid2);

        init();
        setImgBack();
    }
    public void init(){
        Txt_Finish = (TextView)findViewById(R.id.txt_finish);
        Txt_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
    }
    private void setImgBack() {
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }
}

