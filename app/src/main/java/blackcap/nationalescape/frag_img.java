package blackcap.nationalescape;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class frag_img extends Fragment {
    ImageView img;
    String str_Image = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.frag_img, container, false);

        Bundle extra = getArguments();
        str_Image = extra.getString("Image");
        Log.i("테스트", str_Image);
        img = (ImageView) rootView.findViewById(R.id.img);
        Glide.with(getContext()).load("https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_Image)
                .into(img);

        /*img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), product_focus_img.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
            }
        });*/
        return rootView;
    }

}
