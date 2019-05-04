package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import blackcap.nationalescape.Model.Price_Model;
import blackcap.nationalescape.R;

public class Price_Adapter extends RecyclerView.Adapter<Price_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Price_Model> arrData;
    private boolean favorite = false;

    public Price_Adapter(Context c, ArrayList<Price_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_price, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Price_Model items = arrData.get(position);
        try {
            //공지 타이틀
            holder.Txt_Title.setText(items.getTitle());
            //공지 날짜
            holder.Txt_Price.setText(setPoint_rest(items.getPrice())+"원");
        } catch (Exception e){

        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Txt_Title, Txt_Price;
        public ViewHolder(final View itemView) {
            super(itemView);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Price = (TextView)itemView.findViewById(R.id.txt_price);
        }
    }
    //금액 콤마 표현
    public String setPoint_rest(String point){
        DecimalFormat df = new DecimalFormat("#,##0");

        return df.format(Integer.parseInt(point))+"";
    }
}


