package blackcap.nationalescape.Activity.tab4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import blackcap.nationalescape.R;

import static blackcap.nationalescape.Activity.Fragment_main4.ta4_Layout_company_manage;
import static blackcap.nationalescape.Activity.Fragment_main4.ta4_Layout_company_register;
import static blackcap.nationalescape.Activity.Fragment_main4.ta4_company_status;

public class Manager_Modify_Succed extends AppCompatActivity {
    private Button Btn_Ok;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_manager_modify_succed);

        setOk();

    }
    public void setOk(){
        Btn_Ok = (Button) findViewById(R.id.btn_ok);
        Btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ta4_company_status = "check";
                ta4_Layout_company_register.setVisibility(View.GONE);
                ta4_Layout_company_manage.setVisibility(View.VISIBLE);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}

