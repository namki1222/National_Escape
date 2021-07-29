package blackcap.nationalescape.Activity.tab0;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Event_Focus extends AppCompatActivity {
    private ImageView Img_Back;
    private WebView Img_Event;
    private String str_contents= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        init();
        setImgBack();

    }
    public void init(){
        Intent intent1 = getIntent();
        str_contents = intent1.getStringExtra("contents");
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Event = (WebView) findViewById(R.id.img_event);
        Img_Event.getSettings().setLoadWithOverviewMode(true);
        Img_Event.getSettings().setUseWideViewPort(true);
        //setWeb();
        Img_Event.setFocusable(false);
        if (Img_Event != null) Img_Event.loadUrl( "http://www.yologuys.com/Escape_img/event_focus/" + str_contents + ".jpg" );

    }
    public void setWeb() {
        Img_Event.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        Img_Event.getSettings().setSupportMultipleWindows(false);
        Img_Event.getSettings().setJavaScriptEnabled(true);

        Img_Event.setWebViewClient(new WebViewClient() {
            @Override

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url != null && url.startsWith("intent://")) {

                    try {

                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);

                        startActivity(intent);

                        return true;

                    } catch (URISyntaxException e) {

                        e.printStackTrace();
                    }
                }
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
        Img_Event.loadUrl("http://www.yologuys.com/Escape_img/event_focus/" + str_contents + ".jpg");
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Event_Focus.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // 백그라운드 스레드 생성
//            Glide.with(Event_Focus.this).load("http://www.yologuys.com/Escape_img/event_focus/" + str_contents + ".jpg")
//                    .apply(new RequestOptions()
//                            .diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.tab1_list_bg).override(5000))
//                    .into(Img_Event);

            progressDialog.dismiss();
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


