package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import blackcap.nationalescape.Activity.tab1.Home_Focus;
import blackcap.nationalescape.Model.Company_Model;
import blackcap.nationalescape.R;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static blackcap.nationalescape.Activity.MainActivity.bol_main;
import static blackcap.nationalescape.Activity.MainActivity.mInterstitialAd;


public class Tab0_Company_Adapter extends RecyclerView.Adapter<Tab0_Company_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Company_Model> arrData;
    private boolean favorite = false;

    public Tab0_Company_Adapter(Context c, ArrayList<Company_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tab0_company, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Company_Model items = arrData.get(position);
        try {
            //업체 이미지
            Glide.with(items.getActivity()).load("http://www.yologuys.com/Escape_img/company/"+items.getCompany_Pk()+".jpg").apply(new RequestOptions().placeholder(R.drawable.tab1_list_bg).transform(new RoundedCorners(20)))
                    .into(holder.Img_Img);
            Glide.with(items.getActivity()).load(R.drawable.img_info_bg).apply(new RequestOptions().transform(new RoundedCorners(20)))
                    .into(holder.Img_Gr);

            //업체 타이틀
            holder.Txt_Title.setText(items.getTitle());
            //업체 소개
            holder.Txt_Intro.setText(items.getIntro());
            //업체 평점
            holder.Txt_GradeAvg.setText(items.getGrade_Avg());
            //업체와의 거리
            holder.Txt_Distance.setText(items.getDistance());
        } catch (Exception e){

        }
    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout Layout_Root;
        ImageView Img_Img, Img_Gr;
        TextView Txt_Title, Txt_Intro, Txt_GradeAvg, Txt_Distance;
        public ViewHolder(final View itemView) {
            super(itemView);
            Layout_Root = (LinearLayout)itemView.findViewById(R.id.layout_root);
            Img_Img = (ImageView)itemView.findViewById(R.id.img_company);
            Img_Gr = (ImageView)itemView.findViewById(R.id.img_gr);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Intro = (TextView)itemView.findViewById(R.id.txt_contents);
            Txt_GradeAvg = (TextView)itemView.findViewById(R.id.txt_gradeavg);
            Txt_Distance = (TextView)itemView.findViewById(R.id.txt_distance);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Home_Focus.class);
            intent.putExtra("Company_Pk", arrData.get(getAdapterPosition()).getCompany_Pk());
            intent.putExtra("Title", arrData.get(getAdapterPosition()).getTitle());
            intent.putExtra("Grade_Avg", arrData.get(getAdapterPosition()).getGrade_Avg());
            intent.putExtra("Recommend_Count", arrData.get(getAdapterPosition()).getRecommend_Count());
            intent.putExtra("Distance", arrData.get(getAdapterPosition()).getDistance());
            intent.putExtra("Intro", arrData.get(getAdapterPosition()).getIntro());
            intent.putExtra("Address", arrData.get(getAdapterPosition()).getAddress());
            context.startActivity(intent);

            arrData.get(getAdapterPosition()).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        }
    }
}
