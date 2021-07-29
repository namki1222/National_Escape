package blackcap.nationalescape.Activity.tab1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import blackcap.nationalescape.R;

public class Home_Focus_Fragment extends Fragment {
    ImageView img;
    String Image = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_homefocus_img, container, false);

        Bundle extra = getArguments();
        Image = extra.getString("Image");

        img = (ImageView) rootView.findViewById(R.id.img);
        setImage(rootView, Image);
        return rootView;
    }

    //이미지 셋팅
    public void setImage(View rootview, String imgUrl) {

        try {
            String En_img = URLEncoder.encode(imgUrl,"utf-8");
            Glide.with(rootview.getContext()).load("http://www.yologuys.com/Escape_img/company_focus/" + En_img + ".jpg").apply(new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.homefocus_basicimg))
                    .into(img);
        } catch (UnsupportedEncodingException e) {

        }
    }
}
