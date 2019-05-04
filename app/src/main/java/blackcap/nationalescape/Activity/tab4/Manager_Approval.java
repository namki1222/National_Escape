package blackcap.nationalescape.Activity.tab4;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import me.drakeet.materialdialog.MaterialDialog;

import static blackcap.nationalescape.Activity.tab4.Manager.act_manager;

public class Manager_Approval extends AppCompatActivity implements BillingProcessor.IBillingHandler {
    SharedPreferences preferences; //캐쉬 데이터 생성

    private BillingProcessor bp;
    public static ArrayList<SkuDetails> products;
    private MaterialDialog purchaseDialog;

    private ImageView Img_Back;
    private RadioButton Radio_Choice1, Radio_Choice2;
    private Button Btn_Purchase;

    String User_Pk = "";
    private static String purchase_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArcaAXW5HJHYVl1RJVcChlccoFfWl71W67Np48bkxzPud2Yt73sdKHo11r2g7bP+eTGoYSjEHf2kwaggaAAQrL9RGmpxDDR5Eu0Bnnz1Sb2/cg+GicMXP4cGKtGh7CQ88LuSO/4UPXBywvYxEt+BW1ihvDIpRsPRbGhSp9BDU+kogIKQMI18S2jt6UlS5zrLyE+9sBMf8q94x1ZfevNy8DEqaFseXvFMmidg+7wQDbNPurrFrw1ZI/tTtUj/38k2jjv0/fh74jHlEVIhbbBudHjlYj6cEZR5gy12DleJbZtZtzw0SFnZeOPFyVZiL0ib2T2GT6/5vz824ueKB0isj6QIDAQAB";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_manager_approval);

        preferences = getSharedPreferences("escape", MODE_PRIVATE);
        User_Pk = preferences.getString("pk", ".");

        init();
        setBack();
        setRadio_Event();
        setBtn_Purchase();
}
    public void init(){

        bp = new BillingProcessor(this, purchase_key, this);

        Radio_Choice1 = (RadioButton)findViewById(R.id.radio_choice1);
        Radio_Choice1.setChecked(true);
        Radio_Choice2 = (RadioButton)findViewById(R.id.radio_choice2);
        Btn_Purchase = (Button)findViewById(R.id.btn_purchase);
    }
    public void setBtn_Purchase(){
        Btn_Purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bp.purchase(Manager_Approval.this, "nationalescape");
            }
        });
    }

    @Override
    public void onBillingInitialized() {
        bp.getPurchaseListingDetails("nationalescape");
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        // 구매한 아이템 정보
        SkuDetails sku = bp.getPurchaseListingDetails(productId);
        // 메세지 띄우기
        String purchaseMessage = sku.title + "구매 완료";
        Log.i("구매완료", purchaseMessage);

        HttpClient http = new HttpClient();
        String result = http.HttpClient("Web_Escape", "Manager_approval.jsp", User_Pk, setNowTime());
        if(result.equals("succed")){
            Intent intent = new Intent(Manager_Approval.this, Manager_Succed.class);
            startActivity(intent);
            act_manager.finish();
            finish();
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        }else{
            Toast.makeText(Manager_Approval.this, "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Log.i("에러 테스트", error+"");

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    public void setRadio_Event(){
        Radio_Choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Radio_Choice1.setChecked(true);
                Radio_Choice2.setChecked(false);
            }
        });
        Radio_Choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Radio_Choice1.setChecked(false);
                Radio_Choice2.setChecked(true);
            }
        });
    }
    public void setBack(){
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public String setNowTime(){
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddhhmm", Locale.KOREA);

        return dataFormat.format(date);
    }
}

