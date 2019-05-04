package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.MainActivity;
import blackcap.nationalescape.Activity.tab1.Home_Focus;
import blackcap.nationalescape.Model.Favorite_Model;
import blackcap.nationalescape.Model.Company_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;

import static blackcap.nationalescape.Activity.Fragment_main1.home_adpater;
import static blackcap.nationalescape.Activity.Fragment_main1.company_models;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;
import static blackcap.nationalescape.Activity.MainActivity.act_main;
import static blackcap.nationalescape.Activity.tab4.Company_Favorite.favorite_adpater;

public class Favorite_Adapter extends RecyclerView.Adapter<Favorite_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Favorite_Model> arrData;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public Favorite_Adapter(Context c, ArrayList<Favorite_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Favorite_Model items = arrData.get(position);
        try {
            //업체 이미지
            Glide.with(items.getActivity()).load("http://www.yologuys.com/Escape_img/company/"+items.getCompany_Pk()+".jpg").apply(new RequestOptions().placeholder(R.drawable.tab1_list_bg).centerCrop())
                    .into(holder.Img_Img);
            //업체 타이틀
            holder.Txt_Title.setText(items.getTitle());
            //업체 소개
            holder.Txt_Intro.setText(items.getIntro());
            //업체 평점
            holder.Txt_GradeAvg.setText(items.getGrade_Avg());
            //업체와의 거리
            holder.Txt_Distance.setText(items.getDistance());

            Glide.with(items.getActivity()).load(R.drawable.tab1_favorite_check)
                    .into(holder.Img_Favorite);
        }catch (Exception e){

        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView Img_Img;
        TextView Txt_Title, Txt_Intro, Txt_GradeAvg, Txt_Distance;
        ImageView Img_Favorite;
        public ViewHolder(View itemView) {
            super(itemView);
            Img_Img = (ImageView)itemView.findViewById(R.id.img_img);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Intro = (TextView)itemView.findViewById(R.id.txt_intro);
            Txt_GradeAvg = (TextView)itemView.findViewById(R.id.txt_grageavg);
            Txt_Distance = (TextView)itemView.findViewById(R.id.txt_distance);
            Img_Favorite = (ImageView)itemView.findViewById(R.id.img_favorite);
            itemView.setOnClickListener(this);
            Img_Favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpClient http = new HttpClient();
                    String result = http.HttpClient("Web_Escape", "Favority_Delete.jsp", User_Pk, arrData.get(getAdapterPosition()).getCompany_Pk());
                    if(result.equals("succed")){
                        //찜 탭 내 해당 index 제거
                        int index = 0;
                        while (index < company_models.size()){
                            if(arrData.get(getAdapterPosition()).getCompany_Pk().equals(company_models.get(index).getCompany_Pk())){
                                Log.i("테스트123", arrData.get(getAdapterPosition()).getCompany_Pk());
                                company_models.set(index, new Company_Model(act_main,arrData.get(getAdapterPosition()).getCompany_Pk(), arrData.get(getAdapterPosition()).getOwner_Pk(), arrData.get(getAdapterPosition()).getTitle(), arrData.get(getAdapterPosition()).getIntro(), arrData.get(getAdapterPosition()).getGrade_Avg(), arrData.get(getAdapterPosition()).getRecommend_Count(), arrData.get(getAdapterPosition()).getAddress(), arrData.get(getAdapterPosition()).getDistance(),"false"));
                                home_adpater.notifyDataSetChanged();
                                break;
                            }
                            else{

                            }
                            index++;
                        }
                        arrData.remove(getAdapterPosition());
                        favorite_adpater.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(arrData.get(getAdapterPosition()).getActivity(),R.string.http_error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
