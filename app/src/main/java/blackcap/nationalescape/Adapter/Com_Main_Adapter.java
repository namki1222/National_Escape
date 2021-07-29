package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.Community.Focus_Free;
import blackcap.nationalescape.Activity.Community.Write_Free;
import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.Model.Com_Free_Model;
import blackcap.nationalescape.R;

import static blackcap.nationalescape.Activity.MainActivity.User_Pk;

public class Com_Main_Adapter extends RecyclerView.Adapter<Com_Main_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Com_Free_Model> arrData;
    private boolean favorite = false;

    public Com_Main_Adapter(Context c, ArrayList<Com_Free_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public Com_Main_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_com_free, parent, false);

        return new Com_Main_Adapter.ViewHolder(v);
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(final Com_Main_Adapter.ViewHolder holder, final int position) {
        final Com_Free_Model items = arrData.get(position);
        try {
            //업체 타이틀
            holder.Txt_Title.setText("["+items.getDiff()+"] "+items.getTitle());
            //업체 소개
            holder.Txt_Nick.setText(items.getNickname());
            //업체 평점
            holder.Txt_Date.setText(items.getCreatedAt());
            //업체와의 거리
            holder.Txt_Comment_Count.setText(items.getCommentCount());

            if(items.getGrade().equals("0")){
                holder.Img_Grade.setVisibility(View.INVISIBLE);
            }
            else if(items.getGrade().equals("1")){
                holder.Img_Grade.setVisibility(View.VISIBLE);
                holder.Img_Grade.setImageResource(R.drawable.user_medal_1_10);
            }
            else if(items.getGrade().equals("2")){
                holder.Img_Grade.setVisibility(View.VISIBLE);
                holder.Img_Grade.setImageResource(R.drawable.user_medal_11_50);
            }
            else if(items.getGrade().equals("3")){
                holder.Img_Grade.setVisibility(View.VISIBLE);
                holder.Img_Grade.setImageResource(R.drawable.user_medal_51_100);
            }
            else if(items.getGrade().equals("4")){
                holder.Img_Grade.setVisibility(View.VISIBLE);
                holder.Img_Grade.setImageResource(R.drawable.user_medal_101_200);
            }
            else if(items.getGrade().equals("5")){
                holder.Img_Grade.setVisibility(View.VISIBLE);
                holder.Img_Grade.setImageResource(R.drawable.user_medal_201_300);
            }
            else if(items.getGrade().equals("6")){
                holder.Img_Grade.setVisibility(View.VISIBLE);
                holder.Img_Grade.setImageResource(R.drawable.user_medal_301_500);
            }
            else if(items.getGrade().equals("7")){
                holder.Img_Grade.setVisibility(View.VISIBLE);
                holder.Img_Grade.setImageResource(R.drawable.user_medal_501_1000);
            }
            else{
                holder.Img_Grade.setVisibility(View.VISIBLE);
                holder.Img_Grade.setImageResource(R.drawable.user_medal_1000);
            }

            holder.Layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Focus_Free.class);
                    intent.putExtra("id", items.getId());
                    items.getActivity().startActivity(intent);
                    items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);

                }
            });
        } catch (Exception e){
            Log.i("테스트", e+ "테스트");
        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Txt_Title, Txt_Nick, Txt_Date, Txt_Comment_Count;
        ImageView Img_Write, Img_Grade;
        LinearLayout Layout;
        public ViewHolder(final View itemView) {
            super(itemView);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Nick = (TextView)itemView.findViewById(R.id.txt_nick);
            Txt_Date = (TextView)itemView.findViewById(R.id.txt_date);
            Txt_Comment_Count = (TextView)itemView.findViewById(R.id.txt_comment_count);
            Img_Grade = (ImageView)itemView.findViewById(R.id.img_grade);
            Img_Write = (ImageView)itemView.findViewById(R.id.img_write);
            Layout = (LinearLayout)itemView.findViewById(R.id.layout);
        }
    }
}

