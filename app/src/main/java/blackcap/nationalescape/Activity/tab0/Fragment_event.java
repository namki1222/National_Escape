package blackcap.nationalescape.Activity.tab0;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

public class Fragment_event extends Fragment {
    ImageView img;
    String str_Image = "", str_category = "", str_contents ="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        Bundle extra = getArguments();
        str_Image = extra.getString("Image");
        str_category = extra.getString("category");
        str_contents = extra.getString("contents");

        img = (ImageView) rootView.findViewById(R.id.img);
        setImage(rootView, str_Image);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Async async = new Async();
                async.execute(str_Image, "and");
            }
        });
        return rootView;
    }

    //이미지 셋팅
    public void setImage(View rootview, String imgUrl) {

        try {
            String En_img = URLEncoder.encode(imgUrl,"utf-8");
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
                    .skipMemoryCache(true);
            Glide.with(rootview.getContext()).load("http://www.yologuys.com/Escape_img/event/" + En_img + ".jpg").apply(requestOptions)
                    .into(img);

        } catch (UnsupportedEncodingException e) {

        }
    }

    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData, parseredData_company, parseredData_theme;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(getActivity(),"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {

                HttpClient http = new HttpClient();

                //이벤트 데이터 셋팅
                String result = http.HttpClient("Web_Escape", "Home_Event_Visit.jsp",params);

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(str_category.equals("url")){
                Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(str_contents));
                startActivity(newIntent);
            }else{
                Intent intent = new Intent(getActivity(), Event_Focus.class);
                intent.putExtra("contents", str_contents);
                startActivity(intent);
            }

            progressDialog.dismiss();
        }
    }
}