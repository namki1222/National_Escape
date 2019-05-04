package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.tab3.Theme_Focus_Modify;
import blackcap.nationalescape.Model.Theme_Review_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import me.drakeet.materialdialog.MaterialDialog;

import static blackcap.nationalescape.Activity.tab3.Theme_Focus.theme_review_adapter;
import static blackcap.nationalescape.Activity.tab3.Theme_Focus.theme_review_models;

public class Theme_Review_Adapter extends RecyclerView.Adapter<Theme_Review_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Theme_Review_Model> arrData;
    private boolean favorite = false;

    public Theme_Review_Adapter(Context c, ArrayList<Theme_Review_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_theme_review, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Theme_Review_Model items = arrData.get(position);
        try {
            //닉네임 셋팅
            holder.Txt_Nickname.setText(items.getNickname());
            //날짜 셋팅
            holder.Txt_Date.setText(items.getDate());
            //컨텐츠 셋팅
            holder.Txt_Content.setText(items.getContent());

            holder.Txt_Gradeavg.setText(items.getGrade()+"점");

            //난이도 셋팅
            if(items.getLevel().equals("easy")){
                holder.Img_Level.setImageResource(R.drawable.theme_level_easy);
            }else if(items.getLevel().equals("normal")){
                holder.Img_Level.setImageResource(R.drawable.theme_level_normal);
            }
            else{
                holder.Img_Level.setImageResource(R.drawable.theme_level_hard);
            }

            //성공여부
            if(items.getStatus().equals("success")){
                holder.Img_Escape.setImageResource(R.drawable.theme_escpae_succed);
            }
            else{
                holder.Img_Escape.setImageResource(R.drawable.theme_escape_failed);
            }

            //사용 힌트 수
            if(items.getHint().equals(".")){
                holder.Txt_Hint.setVisibility(View.GONE);
            }
            else{
                holder.Txt_Hint.setText(" | 사용힌트수 : "+ items.getHint());
            }

            //남은 시간 수
            if(items.getTime().equals(".")){
                holder.Txt_Time.setVisibility(View.GONE);
            }
            else{
                holder.Txt_Time.setText(" | 남은시간 : "+ items.getTime());
            }

            //방 탈출 횟수
            if(items.getExperience().equals("1")){
                holder.Img_Medal.setImageResource(R.drawable.user_medal_1_10);
            }else if(items.getExperience().equals("2")){
                holder.Img_Medal.setImageResource(R.drawable.user_medal_11_50);
            }else if(items.getExperience().equals("3")){
                holder.Img_Medal.setImageResource(R.drawable.user_medal_51_100);
            }else if(items.getExperience().equals("4")){
                holder.Img_Medal.setImageResource(R.drawable.user_medal_101_200);
            }else if(items.getExperience().equals("5")){
                holder.Img_Medal.setImageResource(R.drawable.user_medal_201_300);
            }else{
                holder.Img_Medal.setImageResource(R.drawable.user_medal_301);
            }

            if(items.getUser_Pk().equals(items.getReview_User_Pk())){
                holder.Img_My.setVisibility(View.VISIBLE);
                holder.Img_Setting.setVisibility(View.VISIBLE);
            }
            else{
                holder.Img_My.setVisibility(View.GONE);
                holder.Img_Setting.setVisibility(View.GONE);
            }
            holder.Img_Setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDialog_Setting(items);
                }
            });

            if(items.getLine().equals("gold")){
                holder.Layout_Frame.setBackgroundResource(R.drawable.round_review_gold);
                holder.Img_LineMedal.setImageResource(R.drawable.theme_modal1);
            }
            else if(items.getLine().equals("silver")){
                holder.Layout_Frame.setBackgroundResource(R.drawable.round_review_silver);
                holder.Img_LineMedal.setImageResource(R.drawable.theme_modal2);
            }
            else if(items.getLine().equals("copper")){
                holder.Layout_Frame.setBackgroundResource(R.drawable.round_review_copper);
                holder.Img_LineMedal.setImageResource(R.drawable.theme_modal3);
            }
            else{
                holder.Layout_Frame.setBackgroundResource(R.drawable.rount_reivew);
                holder.Img_LineMedal.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e){

        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Txt_Nickname, Txt_Date, Txt_Content, Txt_Gradeavg;
        TextView Txt_Time, Txt_Hint;
        ImageView Img_My, Img_Setting, Img_Level, Img_Escape, Img_Medal, Img_LineMedal;
        LinearLayout Layout_Frame;
        public ViewHolder(final View itemView) {
            super(itemView);
            Txt_Nickname = (TextView)itemView.findViewById(R.id.txt_nickname);
            Txt_Date = (TextView)itemView.findViewById(R.id.txt_date);
            Txt_Content = (TextView)itemView.findViewById(R.id.txt_content);
            Txt_Gradeavg = (TextView)itemView.findViewById(R.id.txt_gradeavg);
            Img_My = (ImageView)itemView.findViewById(R.id.img_my);
            Img_Setting = (ImageView)itemView.findViewById(R.id.img_setting);
            Img_Level = (ImageView)itemView.findViewById(R.id.img_level);
            Img_Escape = (ImageView)itemView.findViewById(R.id.img_escape);
            Txt_Time = (TextView)itemView.findViewById(R.id.txt_time);
            Txt_Hint = (TextView)itemView.findViewById(R.id.txt_hint);
            Img_Medal = (ImageView)itemView.findViewById(R.id.img_medal);
            Img_LineMedal = (ImageView)itemView.findViewById(R.id.img_linemedal);
            Layout_Frame = (LinearLayout)itemView.findViewById(R.id.layout_frame);
        }
    }
    public void setDialog_Setting(final Theme_Review_Model items){
        LayoutInflater inflater = (LayoutInflater)items.getActivity().getSystemService(items.getActivity().LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_review_setting, (ViewGroup)items.getActivity().findViewById(R.id.Customdialog_Strring_Root));
        TextView Txt_Modify  = (TextView) layout.findViewById(R.id.txt_modify);
        TextView Txt_Delete  = (TextView) layout.findViewById(R.id.txt_delete);
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(items.getActivity());
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();
        Txt_Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(items.getActivity(), Theme_Focus_Modify.class);
                intent.putExtra("Theme_Pk", items.getTheme_Pk());
                intent.putExtra("Grade", items.getGrade());
                intent.putExtra("Level", items.getLevel());
                intent.putExtra("Escape", items.getStatus());
                intent.putExtra("Time", items.getTime());
                intent.putExtra("Hint", items.getHint());
                intent.putExtra("Memo", items.getContent());
                items.getActivity().startActivity(intent);
                items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                TeamInfo_Dialog.dismiss();
            }
        });
        Txt_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();
                String result = http.HttpClient("Web_Escape", "Theme_Focus_Review_Delete.jsp",items.getTheme_Pk(), items.getUser_Pk());
                if(result.equals("succed")){
                    String result3 = http.HttpClient("Web_Escape", "Theme_Focus_Review_3.jsp",items.getTheme_Pk(), items.getUser_Pk());
                    String [][] parseredData_reivew = jsonParserList.jsonParserList_Data10(result3);
                    theme_review_models.clear();
                    for (int i = 0; i < parseredData_reivew.length; i++) {
                        String nickname = parseredData_reivew[i][0];
                        String grage = parseredData_reivew[i][1];
                        String content = parseredData_reivew[i][2];
                        String date = parseredData_reivew[i][3];
                        String review_user_pk = parseredData_reivew[i][4];
                        String level = parseredData_reivew[i][5];
                        String status = parseredData_reivew[i][6];
                        String time = parseredData_reivew[i][7];
                        String hint = parseredData_reivew[i][8];
                        String experience = parseredData_reivew[i][9];
                        theme_review_models.add(new Theme_Review_Model(items.getActivity(), items.getUser_Pk(), review_user_pk, nickname, grage, content, date, items.getTheme_Pk(), level, status, time, hint, experience, "default"));
                    }
                    theme_review_adapter.notifyDataSetChanged();
                    Toast.makeText(items.getActivity(),"삭제되었습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.i("테스트", result);
                    Toast.makeText(items.getActivity(),"잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                }
                TeamInfo_Dialog.dismiss();
            }
        });
    }
}



