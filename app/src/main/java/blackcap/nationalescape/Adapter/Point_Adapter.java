package blackcap.nationalescape.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import blackcap.nationalescape.Model.Point_Model;
import blackcap.nationalescape.R;

public class Point_Adapter extends RecyclerView.Adapter<Point_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Point_Model> arrData;
    private boolean favorite = false;

    public Point_Adapter(Context c, ArrayList<Point_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_point, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Point_Model items = arrData.get(position);
        try {
            //업체 타이틀
            holder.Txt_Title.setText(items.getTitle());
            //업체 소개
            //업체 평점
            if(items.getCategory().equals("적립")){
                holder.Txt_Point.setText("+"+items.getPoint()+"P");
                holder.Txt_Cateogory.setTextColor(items.getActivity().getResources().getColor(R.color.point_add));
                holder.Txt_Cateogory.setText("적립");
            }
            else{
                holder.Txt_Point.setText("-"+items.getPoint()+"P");
                holder.Txt_Cateogory.setTextColor(items.getActivity().getResources().getColor(R.color.point_use));
                holder.Txt_Cateogory.setText("사용");
            }
            holder.Txt_Date.setText(items.getDate().substring(4,6)+"."+items.getDate().substring(6,8));

        } catch (Exception e){

        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Txt_Title, Txt_Cateogory, Txt_Point, Txt_Date;
        public ViewHolder(final View itemView) {
            super(itemView);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Cateogory = (TextView)itemView.findViewById(R.id.txt_category);
            Txt_Point = (TextView)itemView.findViewById(R.id.txt_point);
            Txt_Date = (TextView)itemView.findViewById(R.id.txt_date);

        }
    }
}

