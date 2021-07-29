package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.Community.Write_Free;
import blackcap.nationalescape.Activity.tab1.Home_Focus;
import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.Model.Com_Comment_Model;
import blackcap.nationalescape.Model.Com_Free_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.Http_Del;
import blackcap.nationalescape.Uitility.Http_Post;
import me.drakeet.materialdialog.MaterialDialog;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static blackcap.nationalescape.Activity.Community.Focus_Free.Layout_Comment_Nick;
import static blackcap.nationalescape.Activity.Community.Focus_Free.Txt_Comment_Nick;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;
import static blackcap.nationalescape.Activity.Community.Focus_Free.com_comment_adapters;
import static blackcap.nationalescape.Activity.Community.Focus_Free.replay_nick;
import static blackcap.nationalescape.Activity.Community.Focus_Free.replay_id;

public class Com_Comment_Adapter extends RecyclerView.Adapter<Com_Comment_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Com_Comment_Model> arrData;
    private boolean favorite = false;
    private String[] parseredData;
    public Com_Comment_Adapter(Context c, ArrayList<Com_Comment_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public Com_Comment_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_com_comment, parent, false);

        return new Com_Comment_Adapter.ViewHolder(v);
    }
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(final Com_Comment_Adapter.ViewHolder holder, final int position) {
        final Com_Comment_Model items = arrData.get(position);
        try {
            //업체 타이틀
            holder.Txt_Title.setText(items.getContent());
            //업체 소개
            holder.Txt_Nick.setText(items.getNickname());
            //업체 평점
            holder.Txt_Date.setText(items.getCreatedAt());

            if(items.getUser_pk().equals(User_Pk)){
                holder.Txt_Del.setVisibility(View.VISIBLE);
            }
            else{
                holder.Txt_Del.setVisibility(View.GONE);
            }

            if(items.getDepth().equals("0")){
                holder.Img_Depth.setVisibility(View.GONE);
                holder.Txt_Re.setVisibility(View.VISIBLE);
            }
            else{
                holder.Img_Depth.setVisibility(View.VISIBLE);
                holder.Txt_Re.setVisibility(View.GONE);
            }

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

            holder.Txt_Re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replay_nick = items.getNickname();
                    replay_id = items.getId();
                    Txt_Comment_Nick.setText(items.getNickname()+"");
                    Layout_Comment_Nick.setVisibility(View.VISIBLE);

                    InputMethodManager imm = (InputMethodManager) items.getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.showSoftInput(items.getActivity().getCurrentFocus(), 0);
                }
            });

            holder.Txt_Del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater)items.getActivity().getSystemService(Home_Focus.LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.dialog_comu_del, (ViewGroup)items.getActivity().findViewById(R.id.root));
                    final TextView Txt_Ok = (TextView) layout.findViewById(R.id.btn_ok);
                    final TextView Txt_Cancel = (TextView) layout.findViewById(R.id.btn_cancel);
                    final MaterialDialog TeamInfo_Dialog = new MaterialDialog(items.getActivity());
                    TeamInfo_Dialog
                            .setContentView(layout)
                            .setCanceledOnTouchOutside(true);
                    TeamInfo_Dialog.show();

                    Txt_Cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TeamInfo_Dialog.dismiss();
                        }
                    });
                    Txt_Ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Http_Del http = new Http_Del();
                            String result = http.Http_Del(User_Pk, "v1/boardComment/remove?id="+items.getId());
                            parseredData = jsonParserList_access(result);

                            if(parseredData[0].equals("success")){
                                arrData.remove(position);
                                com_comment_adapters.notifyDataSetChanged();
                            }
                            else{
                                Toast.makeText(items.getActivity(), parseredData[0], Toast.LENGTH_SHORT).show();
                            }
                            TeamInfo_Dialog.dismiss();
                        }
                    });

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
        TextView Txt_Title, Txt_Nick, Txt_Date;
        private TextView Txt_Re, Txt_Del;
        ImageView Img_Depth, Img_Grade;
        public ViewHolder(final View itemView) {
            super(itemView);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Nick = (TextView)itemView.findViewById(R.id.txt_nick);
            Txt_Date = (TextView)itemView.findViewById(R.id.txt_date);
            Txt_Re = (TextView)itemView.findViewById(R.id.txt_re);
            Txt_Del = (TextView)itemView.findViewById(R.id.txt_del);
            Img_Depth = (ImageView)itemView.findViewById(R.id.img_depth);
            Img_Grade = (ImageView)itemView.findViewById(R.id.img_grade);
        }

    }
    public String[] jsonParserList_access(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);

            String[] jsonName = {"message"};
            String[] parseredData = new String[jsonName.length];
            for (int j = 0; j < jsonName.length; j++) {
                parseredData[j] = json.getString(jsonName[j]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

