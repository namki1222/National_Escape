package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.tab1.Home_Focus;
import blackcap.nationalescape.Activity.tab3.Theme_Focus;
import blackcap.nationalescape.Model.Company_Model;
import blackcap.nationalescape.Model.Theme_Model;
import blackcap.nationalescape.R;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class Tab0_Theme_Adapter extends RecyclerView.Adapter<Tab0_Theme_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Theme_Model> arrData;
    private boolean favorite = false;

    public Tab0_Theme_Adapter(Context c, ArrayList<Theme_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_theme, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Theme_Model items = arrData.get(position);
        try {
            //업체 이미지
            Glide.with(items.getActivity()).load("http://www.yologuys.com/Escape_img/theme/"+items.getTheme_Pk()+".jpg").apply(new RequestOptions().placeholder(R.drawable.theme_bg).transforms(new CenterCrop(), new RoundedCorners(20)))
                    .into(holder.Img_Img);
            //업체 이미지
            Glide.with(items.getActivity()).load(R.drawable.img_info_bg).apply(new RequestOptions().transform(new RoundedCorners(20)))
                    .into(holder.Img_Br);
            //업체 타이틀
            holder.Txt_Title.setText(items.getTitle());
            //업체 소개
            holder.Txt_Intro.setText(items.getIntro());
            //업체 평점
            holder.Txt_GradeAvg.setText(items.getGrade());
            holder.Txt_Category.setText(items.getCategory());
            //난이도
            if(items.getLevel().equals("1")){
                holder.Img_Key1.setVisibility(View.VISIBLE);
                holder.Img_Key2.setVisibility(View.GONE);
                holder.Img_Key3.setVisibility(View.GONE);
                holder.Img_Key4.setVisibility(View.GONE);
                holder.Img_Key5.setVisibility(View.GONE);

            }
            else if(items.getLevel().equals("2")){
                holder.Img_Key1.setVisibility(View.VISIBLE);
                holder.Img_Key2.setVisibility(View.VISIBLE);
                holder.Img_Key3.setVisibility(View.GONE);
                holder.Img_Key4.setVisibility(View.GONE);
                holder.Img_Key5.setVisibility(View.GONE);
            }
            else if(items.getLevel().equals("3")){
                holder.Img_Key1.setVisibility(View.VISIBLE);
                holder.Img_Key2.setVisibility(View.VISIBLE);
                holder.Img_Key3.setVisibility(View.VISIBLE);
                holder.Img_Key4.setVisibility(View.GONE);
                holder.Img_Key5.setVisibility(View.GONE);
            }
            else if(items.getLevel().equals("4")){
                holder.Img_Key1.setVisibility(View.VISIBLE);
                holder.Img_Key2.setVisibility(View.VISIBLE);
                holder.Img_Key3.setVisibility(View.VISIBLE);
                holder.Img_Key4.setVisibility(View.VISIBLE);
                holder.Img_Key5.setVisibility(View.GONE);
            }
            else if(items.getLevel().equals("5")){
                holder.Img_Key1.setVisibility(View.VISIBLE);
                holder.Img_Key2.setVisibility(View.VISIBLE);
                holder.Img_Key3.setVisibility(View.VISIBLE);
                holder.Img_Key4.setVisibility(View.VISIBLE);
                holder.Img_Key5.setVisibility(View.VISIBLE);
            }
        } catch (Exception e){

        }
    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout Layout_Root;
        ImageView Img_Img, Img_Br;
        TextView Txt_Title, Txt_Category, Txt_Intro, Txt_GradeAvg;
        ImageView Img_Key1, Img_Key2, Img_Key3, Img_Key4, Img_Key5;
        public ViewHolder(final View itemView) {
            super(itemView);
            Layout_Root = (LinearLayout)itemView.findViewById(R.id.layout_root);
            Img_Img = (ImageView)itemView.findViewById(R.id.img_theme);
            Img_Br = (ImageView)itemView.findViewById(R.id.img_br);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Category = (TextView)itemView.findViewById(R.id.txt_category);
            Txt_Intro = (TextView)itemView.findViewById(R.id.txt_contents);
            Txt_GradeAvg = (TextView)itemView.findViewById(R.id.txt_gradeavg);
            Img_Key1 = (ImageView)itemView.findViewById(R.id.img_key1);
            Img_Key2 = (ImageView)itemView.findViewById(R.id.img_key2);
            Img_Key3 = (ImageView)itemView.findViewById(R.id.img_key3);
            Img_Key4 = (ImageView)itemView.findViewById(R.id.img_key4);
            Img_Key5 = (ImageView)itemView.findViewById(R.id.img_key5);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, Theme_Focus.class);
            intent.putExtra("Theme_Pk", arrData.get(getAdapterPosition()).getTheme_Pk());
            context.startActivity(intent);

            arrData.get(getAdapterPosition()).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        }
    }
}

