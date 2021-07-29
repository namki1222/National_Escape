package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.tab4.Notice_Focus;
import blackcap.nationalescape.Model.Notice_Model;
import blackcap.nationalescape.R;

public class Notice_Adapter extends RecyclerView.Adapter<Notice_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Notice_Model> arrData;
    private boolean favorite = false;

    public Notice_Adapter(Context c, ArrayList<Notice_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notice, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Notice_Model items = arrData.get(position);
        try {
            //공지 타이틀
            holder.Txt_Title.setText(items.getTitle());
            //공지 날짜
            holder.Txt_Date.setText(items.getDate().substring(0, 4)+"."+items.getDate().substring(4, 6)+"."+items.getDate().substring(6, 8));

            holder.Layout_Notice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Notice_Focus.class);
                    intent.putExtra("Notice_Pk", items.getNotice_Pk());
                    intent.putExtra("Title", items.getTitle());
                    intent.putExtra("Date", items.getDate());
                    intent.putExtra("Contents", items.getContents());
                    context.startActivity(intent);
                    items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            });
        } catch (Exception e){

        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LinearLayout Layout_Notice;
        TextView Txt_Title, Txt_Date;
        public ViewHolder(final View itemView) {
            super(itemView);
            Layout_Notice = (LinearLayout)itemView.findViewById(R.id.layout_notice);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Date = (TextView)itemView.findViewById(R.id.txt_date);
        }

        @Override
        public void onClick(View v) {
            Log.i("te","te");

        }
    }
}

