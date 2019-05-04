package blackcap.nationalescape.Uitility;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import blackcap.nationalescape.R;

public class levelRange {
    public void levelstart(Activity activity, int Rating, ImageView Img_level1, ImageView Img_level2, ImageView Img_level3, ImageView Img_level4, ImageView Img_level5){
        if(Rating  == 1){
            Img_level1.setVisibility(View.VISIBLE);
            Img_level2.setVisibility(View.GONE);
            Img_level3.setVisibility(View.GONE);
            Img_level4.setVisibility(View.GONE);
            Img_level5.setVisibility(View.GONE);
        }
        else if(Rating  == 2){
            Img_level1.setVisibility(View.VISIBLE);
            Img_level2.setVisibility(View.VISIBLE);
            Img_level3.setVisibility(View.GONE);
            Img_level4.setVisibility(View.GONE);
            Img_level5.setVisibility(View.GONE);
        }
        else if(Rating  == 3){
            Img_level1.setVisibility(View.VISIBLE);
            Img_level2.setVisibility(View.VISIBLE);
            Img_level3.setVisibility(View.VISIBLE);
            Img_level4.setVisibility(View.GONE);
            Img_level5.setVisibility(View.GONE);
        }
        else if(Rating  == 4){
            Img_level1.setVisibility(View.VISIBLE);
            Img_level2.setVisibility(View.VISIBLE);
            Img_level3.setVisibility(View.VISIBLE);
            Img_level4.setVisibility(View.VISIBLE);
            Img_level5.setVisibility(View.GONE);
        }
        else{
            Img_level1.setVisibility(View.VISIBLE);
            Img_level2.setVisibility(View.VISIBLE);
            Img_level3.setVisibility(View.VISIBLE);
            Img_level4.setVisibility(View.VISIBLE);
            Img_level5.setVisibility(View.VISIBLE);
        }
    }
}
