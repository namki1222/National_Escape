package blackcap.nationalescape.Uitility;

import android.app.Activity;
import android.widget.ImageView;

import blackcap.nationalescape.R;

public class StartRange {
    public void rangestart(Activity activity, double Rating, ImageView Img_star1, ImageView Img_star2, ImageView Img_star3, ImageView Img_star4, ImageView Img_star5){
        if(Rating >= 0.0 && Rating < 0.5){
            Img_star1.setBackground(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star2.setBackground(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star3.setBackground(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star4.setBackground(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star5.setBackground(activity.getResources().getDrawable(R.drawable.star_gray));
        }
        else if(Rating >= 0.5 && Rating < 1){
            Img_star1.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_half));
            Img_star2.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star3.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star4.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star5.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
        }
        else if(Rating >= 1 && Rating < 1.5){
            Img_star1.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star2.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star3.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star4.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star5.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
        }
        else if(Rating >= 1.5 && Rating < 2){
            Img_star1.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star2.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_half));
            Img_star3.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star4.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star5.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
        }
        else if(Rating >= 2 && Rating < 2.5){
            Img_star1.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star2.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star3.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star4.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star5.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
        }
        else if(Rating >= 2.5 && Rating < 3){
            Img_star1.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star2.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star3.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_half));
            Img_star4.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star5.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
        }
        else if(Rating >= 3 && Rating < 3.5){
            Img_star1.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star2.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star3.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star4.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star5.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
        }
        else if(Rating >= 3.5 && Rating < 4){
            Img_star1.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star2.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star3.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star4.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_half));
            Img_star5.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
        }
        else if(Rating >= 4 && Rating < 4.5){
            Img_star1.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star2.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star3.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star4.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star5.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_gray));
        }
        else if(Rating >= 4.5 && Rating < 5){
            Img_star1.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star2.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star3.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star4.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star5.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_half));
        }
        else if(Rating == 5){
            Img_star1.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star2.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star3.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star4.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
            Img_star5.setImageDrawable(activity.getResources().getDrawable(R.drawable.star));
        }
        else{
            Img_star1.setBackground(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star2.setBackground(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star3.setBackground(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star4.setBackground(activity.getResources().getDrawable(R.drawable.star_gray));
            Img_star5.setBackground(activity.getResources().getDrawable(R.drawable.star_gray));
        }
    }
}
