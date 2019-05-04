package blackcap.nationalescape.Activity.tab1;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.StartRange;

public class Home_Focus_Fragment_First extends Fragment {
    private ImageView Img;
    private ImageView Img_star1, Img_star2, Img_star3, Img_star4, Img_star5;
    private TextView Txt_Title, Txt_Distance, Txt_Intro;

    private String Image = "", title = "", grage_avg = "", intro = "", distance = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_homefocus_img_first, container, false);

        Bundle extra = getArguments();
        Image = extra.getString("Image");
        title = extra.getString("title");
        grage_avg = extra.getString("grage_avg");
        distance = extra.getString("distance");
        intro = extra.getString("intro");

        init(rootView);
        setData();
        setImage(rootView, Image);
        return rootView;
    }
    private void init(View rootView){
        Img = (ImageView) rootView.findViewById(R.id.img);
        Txt_Title = (TextView) rootView.findViewById(R.id.txt_title);
        Txt_Distance = (TextView) rootView.findViewById(R.id.txt_distance);
        Txt_Intro = (TextView) rootView.findViewById(R.id.txt_intro);
        Img_star1 = (ImageView) rootView.findViewById(R.id.img_star1);
        Img_star2 = (ImageView) rootView.findViewById(R.id.img_star2);
        Img_star3 = (ImageView) rootView.findViewById(R.id.img_star3);
        Img_star4 = (ImageView) rootView.findViewById(R.id.img_star4);
        Img_star5 = (ImageView) rootView.findViewById(R.id.img_star5);
    }
    //데이터 셋팅
    private void setData(){
        Txt_Title.setText(title);
        Txt_Distance.setText(distance);
        Txt_Intro.setText(intro);
        Rating_Range(Double.parseDouble(grage_avg));
    }
    //이미지 셋팅
    private void setImage(View rootview, String imgUrl) {
        try {
            String En_img = URLEncoder.encode(imgUrl,"utf-8");
            Glide.with(rootview.getContext()).load("http://www.yologuys.com/Escape_img/company_focus/" + En_img + ".jpg")
                    .into(Img);
        } catch (UnsupportedEncodingException e) {

        }
    }
    public void Rating_Range(double Rating){
//        if(Rating >= 0 && Rating < 0.5){
//            Img_star1.setBackground(getResources().getDrawable(R.drawable.star_gray));
//            Img_star2.setBackground(getResources().getDrawable(R.drawable.star_gray));
//            Img_star3.setBackground(getResources().getDrawable(R.drawable.star_gray));
//            Img_star4.setBackground(getResources().getDrawable(R.drawable.star_gray));
//            Img_star5.setBackground(getResources().getDrawable(R.drawable.star_gray));
//        }
//        else if(Rating >= 0.5 && Rating < 1){
//            Img_star1.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
//            Img_star2.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//        }
//        else if(Rating >= 1 && Rating < 1.5){
//            Img_star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star2.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//        }
//        else if(Rating >= 1.5 && Rating < 2){
//            Img_star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star2.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
//            Img_star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//        }
//        else if(Rating >= 2 && Rating < 2.5){
//            Img_star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star3.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//        }
//        else if(Rating >= 2.5 && Rating < 3){
//            Img_star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star3.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
//            Img_star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//        }
//        else if(Rating >= 3 && Rating < 3.5){
//            Img_star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star4.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//            Img_star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//        }
//        else if(Rating >= 3.5 && Rating < 4){
//            Img_star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star4.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
//            Img_star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//        }
//        else if(Rating >= 4 && Rating < 4.5){
//            Img_star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star5.setImageDrawable(getResources().getDrawable(R.drawable.star_gray));
//        }
//        else if(Rating >= 4.5 && Rating < 5){
//            Img_star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star5.setImageDrawable(getResources().getDrawable(R.drawable.star_half));
//        }
//        else if(Rating == 5){
//            Img_star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
//            Img_star5.setImageDrawable(getResources().getDrawable(R.drawable.star));
//        }
        StartRange startRange = new StartRange();
        startRange.rangestart(getActivity(), Rating, Img_star1, Img_star2, Img_star3, Img_star4, Img_star5);
    }
}

