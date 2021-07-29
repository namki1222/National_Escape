package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.tab1.Home_Focus;
import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.Model.Favorite_Model;
import blackcap.nationalescape.Model.Company_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;

import static blackcap.nationalescape.Activity.Fragment_main_favorite.favorite_models;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;

public class Home_Adapter extends RecyclerView.Adapter<Home_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Company_Model> arrData;
    private boolean favorite = false;

    public Home_Adapter(Context c, ArrayList<Company_Model> arr) {
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
        final Company_Model items = arrData.get(position);
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
            if(items.getFavorite().equals("true")){
                Glide.with(items.getActivity()).load(R.drawable.tab1_favorite_check)
                        .into(holder.Img_Favorite);
                favorite = true;
            }
            else{
                Glide.with(items.getActivity()).load(R.drawable.tab1_favorite)
                        .into(holder.Img_Favorite);
                favorite = false;
            }
            if(items.getFlag_Premium().equals("true")){
                holder.Img_Premium.setVisibility(View.VISIBLE);
                holder.Img_Premium_Bg.setVisibility(View.VISIBLE);
            }
            else{
                holder.Img_Premium.setVisibility(View.INVISIBLE);
                holder.Img_Premium_Bg.setVisibility(View.INVISIBLE);
            }

        } catch (Exception e){
            Log.i("테스트", e+ "테스트");
        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView Img_Img;
        TextView Txt_Title, Txt_Intro, Txt_GradeAvg, Txt_Distance;
        ImageView Img_Favorite, Img_Premium, Img_Premium_Bg;
        public ViewHolder(final View itemView) {
            super(itemView);
            Img_Img = (ImageView)itemView.findViewById(R.id.img_img);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Intro = (TextView)itemView.findViewById(R.id.txt_intro);
            Txt_GradeAvg = (TextView)itemView.findViewById(R.id.txt_grageavg);
            Txt_Distance = (TextView)itemView.findViewById(R.id.txt_distance);
            Img_Favorite = (ImageView)itemView.findViewById(R.id.img_favorite);
            Img_Premium = (ImageView)itemView.findViewById(R.id.img_premium);
            Img_Premium_Bg = (ImageView)itemView.findViewById(R.id.img_premium_bg);
            itemView.setOnClickListener(this);
            Img_Favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(User_Pk.equals(".")){
                        Intent intent = new Intent(arrData.get(getAdapterPosition()).getActivity(), Login.class);
                        arrData.get(getAdapterPosition()).getActivity().startActivity(intent);
                        arrData.get(getAdapterPosition()).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                    else{
                        //찜으로 등록하는 경우
                        if(favorite == false){
                            HttpClient http = new HttpClient();
                            String result = http.HttpClient("Web_Escape", "Favority_Add.jsp", User_Pk, arrData.get(getAdapterPosition()).getCompany_Pk());
                            if(result.equals("succed")){
                                Glide.with(arrData.get(getAdapterPosition()).getActivity()).load(R.drawable.tab1_favorite_check)
                                        .into(Img_Favorite);
                                favorite = true;
//                                favorite_models.add(new Favorite_Model(arrData.get(getAdapterPosition()).getActivity(),arrData.get(getAdapterPosition()).getCompany_Pk(), arrData.get(getAdapterPosition()).getOwner_Pk(), arrData.get(getAdapterPosition()).getTitle(), arrData.get(getAdapterPosition()).getIntro(), arrData.get(getAdapterPosition()).getGrade_Avg(), arrData.get(getAdapterPosition()).getRecommend_Count(), arrData.get(getAdapterPosition()).getAddress(), arrData.get(getAdapterPosition()).getDistance()));
//                                favorite_adpater.notifyDataSetChanged();
                            }
                            else{
                                Toast.makeText(arrData.get(getAdapterPosition()).getActivity(),R.string.http_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                        //찜을 해제하는 경우
                        else{
                            HttpClient http = new HttpClient();
                            String result = http.HttpClient("Web_Escape", "Favority_Delete.jsp", User_Pk, arrData.get(getAdapterPosition()).getCompany_Pk());
                            if(result.equals("succed")){
                                Glide.with(arrData.get(getAdapterPosition()).getActivity()).load(R.drawable.tab1_favorite)
                                        .into(Img_Favorite);
                                favorite = false;
                                //찜 탭 내 해당 index 제거
//                                int index = 0;
//                                while (index < favorite_models.size()){
//                                    if(arrData.get(getAdapterPosition()).getCompany_Pk().equals(favorite_models.get(index).getCompany_Pk())){
//                                        favorite_models.remove(index);
//                                        favorite_adpater.notifyDataSetChanged();
//                                        break;
//                                    }
//                                    else{
//
//                                    }
//                                    index++;
//                                }
                            }
                            else{
                                Toast.makeText(arrData.get(getAdapterPosition()).getActivity(),R.string.http_error, Toast.LENGTH_SHORT).show();
                            }
                        }
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
