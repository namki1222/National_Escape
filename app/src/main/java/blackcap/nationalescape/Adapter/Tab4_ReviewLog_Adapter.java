package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import blackcap.nationalescape.Model.Review_Log_Model;
import blackcap.nationalescape.Model.Theme_Review_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.StartRange;
import me.drakeet.materialdialog.MaterialDialog;

public class Tab4_ReviewLog_Adapter extends RecyclerView.Adapter<Tab4_ReviewLog_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Review_Log_Model> arrData;
    private boolean favorite = false;

    public Tab4_ReviewLog_Adapter(Context c, ArrayList<Review_Log_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reviewlog, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,  int position) {
        final Review_Log_Model items = arrData.get(position);
        try {
            holder.Txt_Company.setText(items.getCompany_Name());
            holder.Txt_Theme.setText(items.getTheme_Name());

            StartRange startRange = new StartRange();
            startRange.rangestart(items.getActivity(), Double.parseDouble(items.getGrade()), holder.Img_Star1, holder.Img_Star2, holder.Img_Star3, holder.Img_Star4, holder.Img_Star5);

            if(items.getStatus().equals("success")){
                holder.Img_Escape.setImageResource(R.drawable.theme_escpae_succed);
                holder.Txt_Escape.setText("성공");
            }
            else{
                holder.Img_Escape.setImageResource(R.drawable.theme_escape_failed);
                holder.Txt_Escape.setText("실패");
            }

            if(items.getTime().equals(".")){
                holder.Txt_Time.setVisibility(View.GONE);
            }
            else{
                holder.Txt_Time.setText("남은시간 : "+items.getTime());
            }

            if(items.getHint().equals(".")){
                holder.Txt_Hint.setVisibility(View.GONE);
            }
            else{
                holder.Txt_Hint.setText("사용힌트수 : "+items.getHint());
            }


            //난이도 셋팅
            if(items.getLevel().equals("easy")){
                holder.Img_Level.setImageResource(R.drawable.theme_level_easy);
            }else if(items.getLevel().equals("normal")){
                holder.Img_Level.setImageResource(R.drawable.theme_level_normal);
            }
            else{
                holder.Img_Level.setImageResource(R.drawable.theme_level_hard);
            }

//            holder.Root.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    setDialog_Focus(items);
//                }
//            });
        } catch (Exception e){

        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout Root;
        private TextView Txt_Company, Txt_Theme, Txt_Escape, Txt_Time, Txt_Hint;
        private ImageView Img_Escape, Img_Level;
        private ImageView Img_Star1, Img_Star2, Img_Star3, Img_Star4, Img_Star5;

        public ViewHolder(final View itemView) {
            super(itemView);
            Root = (LinearLayout)itemView.findViewById(R.id.layout_log);
            Txt_Company = (TextView)itemView.findViewById(R.id.txt_company);
            Txt_Theme = (TextView)itemView.findViewById(R.id.txt_theme);
            Txt_Time = (TextView)itemView.findViewById(R.id.txt_time);
            Txt_Hint = (TextView)itemView.findViewById(R.id.txt_hint);
            Img_Escape = (ImageView) itemView.findViewById(R.id.img_escape);
            Txt_Escape = (TextView) itemView.findViewById(R.id.txt_escape);
            Img_Level = (ImageView) itemView.findViewById(R.id.img_level);
            Img_Star1 = (ImageView) itemView.findViewById(R.id.img_star1);
            Img_Star2 = (ImageView) itemView.findViewById(R.id.img_star2);
            Img_Star3 = (ImageView) itemView.findViewById(R.id.img_star3);
            Img_Star4 = (ImageView) itemView.findViewById(R.id.img_star4);
            Img_Star5 = (ImageView) itemView.findViewById(R.id.img_star5);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            setDialog_Focus(arrData.get(getAdapterPosition()));
        }
    }
    public void setDialog_Focus(final Review_Log_Model items){
        LayoutInflater inflater = (LayoutInflater)items.getActivity().getSystemService(items.getActivity().LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_reviewlog, (ViewGroup)items.getActivity().findViewById(R.id.root));
        TextView Txt_Company, Txt_Theme, Txt_Escape, Txt_Time, Txt_Hint, Txt_Memo;
        ImageView Img_Escape, Img_Level;
        ImageView Img_Star1, Img_Star2, Img_Star3, Img_Star4, Img_Star5;
        Txt_Company = (TextView)layout.findViewById(R.id.txt_company);
        Txt_Theme = (TextView)layout.findViewById(R.id.txt_theme);
        Txt_Time = (TextView)layout.findViewById(R.id.txt_time);
        Txt_Hint = (TextView)layout.findViewById(R.id.txt_hint);
        Img_Escape = (ImageView) layout.findViewById(R.id.img_escape);
        Txt_Escape = (TextView) layout.findViewById(R.id.txt_escape);
        Img_Level = (ImageView) layout.findViewById(R.id.img_level);
        Img_Star1 = (ImageView) layout.findViewById(R.id.img_star1);
        Img_Star2 = (ImageView) layout.findViewById(R.id.img_star2);
        Img_Star3 = (ImageView) layout.findViewById(R.id.img_star3);
        Img_Star4 = (ImageView) layout.findViewById(R.id.img_star4);
        Img_Star5 = (ImageView) layout.findViewById(R.id.img_star5);
        Txt_Memo = (TextView)layout.findViewById(R.id.txt_memo);

        Txt_Company.setText(items.getCompany_Name());
        Txt_Theme.setText(items.getTheme_Name());

        StartRange startRange = new StartRange();
        startRange.rangestart(items.getActivity(), Double.parseDouble(items.getGrade()), Img_Star1, Img_Star2, Img_Star3, Img_Star4, Img_Star5);

        if(items.getStatus().equals("success")){
            Img_Escape.setImageResource(R.drawable.theme_escpae_succed);
            Txt_Escape.setText("성공");
        }
        else{
            Img_Escape.setImageResource(R.drawable.theme_escape_failed);
            Txt_Escape.setText("실패");
        }

        if(items.getTime().equals(".")){
            Txt_Time.setVisibility(View.GONE);
        }
        else{
            Txt_Time.setText("남은시간 : "+items.getTime());
        }

        if(items.getHint().equals(".")){
            Txt_Hint.setVisibility(View.GONE);
        }
        else{
            Txt_Hint.setText("사용힌트수 : "+items.getHint());
        }

        Txt_Memo.setText(items.getContent());

        //난이도 셋팅
        if(items.getLevel().equals("easy")){
            Img_Level.setImageResource(R.drawable.theme_level_easy);
        }else if(items.getLevel().equals("normal")){
            Img_Level.setImageResource(R.drawable.theme_level_normal);
        }
        else{
            Img_Level.setImageResource(R.drawable.theme_level_hard);
        }

        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(items.getActivity());
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();
    }
}



