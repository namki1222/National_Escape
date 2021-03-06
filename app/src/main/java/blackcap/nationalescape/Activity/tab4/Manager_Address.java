package blackcap.nationalescape.Activity.tab4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import blackcap.nationalescape.R;

public class Manager_Address extends AppCompatActivity {
    private ImageView Img_Back;
    private WebView web_address;
    private Handler handler;

    String User_Pk = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4_manager_address);

        setBack();
        setWebView();

        handler = new Handler();
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
    public void setWebView(){
        // WebView 설정
        web_address = (WebView) findViewById(R.id.web_address);
        // JavaScript 허용
        web_address.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        web_address.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        // 두 번째 파라미터는 사용될 php에도 동일하게 사용해야함
        web_address.addJavascriptInterface(new AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
        web_address.setWebChromeClient(new WebChromeClient());
        web_address.loadUrl("http://www.yologuys.com/address/search_and.html");
    }
    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.putExtra("Address_Num", arg1);
                    intent.putExtra("Address_Txt", arg2 + " "+ arg3);
                    setResult(1, intent);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    //setWebView();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}

