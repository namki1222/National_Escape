package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import blackcap.nationalescape.Model.Review_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.StartRange;
import me.drakeet.materialdialog.MaterialDialog;

import static blackcap.nationalescape.Activity.tab1.Home_Focus.Edit_Review_Content;
import static blackcap.nationalescape.Activity.tab1.Home_Focus.Img_ReviewWrite_Star1;
import static blackcap.nationalescape.Activity.tab1.Home_Focus.Img_ReviewWrite_Star2;
import static blackcap.nationalescape.Activity.tab1.Home_Focus.Img_ReviewWrite_Star3;
import static blackcap.nationalescape.Activity.tab1.Home_Focus.Img_ReviewWrite_Star4;
import static blackcap.nationalescape.Activity.tab1.Home_Focus.Img_ReviewWrite_Star5;
import static blackcap.nationalescape.Activity.tab1.Home_Focus.review_adapter;
import static blackcap.nationalescape.Activity.tab1.Home_Focus.review_models;

public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Review_Model> arrData;
    private boolean favorite = false;

    public Review_Adapter(Context c, ArrayList<Review_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Review_Model items = arrData.get(position);
        try {
            //닉네임 셋팅
            holder.Txt_Nickname.setText(items.getNickname());
            //날짜 셋팅
            holder.Txt_Date.setText(items.getDate());
            //컨텐츠 셋팅
            holder.Txt_Content.setText(items.getContent());

            StartRange startRange = new StartRange();
            startRange.rangestart(items.getActivity(), Double.parseDouble(items.getGrade()), holder.Img_Star1, holder.Img_Star2, holder.Img_Star3, holder.Img_Star4, holder.Img_Star5);

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
        } catch (Exception e){

        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Txt_Nickname, Txt_Date, Txt_Content;
        ImageView Img_Star1, Img_Star2, Img_Star3, Img_Star4, Img_Star5;
        ImageView Img_My, Img_Setting;
        public ViewHolder(final View itemView) {
            super(itemView);
            Txt_Nickname = (TextView)itemView.findViewById(R.id.txt_nickname);
            Txt_Date = (TextView)itemView.findViewById(R.id.txt_date);
            Txt_Content = (TextView)itemView.findViewById(R.id.txt_content);
            Img_Star1 = (ImageView) itemView.findViewById(R.id.img_star1);
            Img_Star2 = (ImageView) itemView.findViewById(R.id.img_star2);
            Img_Star3 = (ImageView) itemView.findViewById(R.id.img_star3);
            Img_Star4 = (ImageView) itemView.findViewById(R.id.img_star4);
            Img_Star5 = (ImageView) itemView.findViewById(R.id.img_star5);
            Img_My = (ImageView)itemView.findViewById(R.id.img_my);
            Img_Setting = (ImageView)itemView.findViewById(R.id.img_setting);
        }
    }
    public void setDialog_Setting(final Review_Model items){
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
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();
                String result = http.HttpClient("Web_Escape", "Home_Focus_Review_Delete.jsp",items.getCompany_Pk(), items.getUser_Pk());
                if(result.equals("succed")){
                    String result3 = http.HttpClient("Web_Escape", "Home_Focus_Review_3.jsp",items.getCompany_Pk(), items.getUser_Pk());
                    String [][] parseredData_reivew = jsonParserList.jsonParserList_Data5(result3);
                    review_models.clear();
                    for (int i = 0; i < parseredData_reivew.length; i++) {
                        String nickname = parseredData_reivew[i][0];
                        String grage = parseredData_reivew[i][1];
                        String content = parseredData_reivew[i][2];
                        String date = parseredData_reivew[i][3];
                        String review_user_pk = parseredData_reivew[i][4];
                        review_models.add(new Review_Model(items.getActivity(), items.getUser_Pk(), review_user_pk, nickname, grage, content, date, items.getCompany_Pk()));
                    }
                    review_adapter.notifyDataSetChanged();

                    StartRange startRange = new StartRange();
                    startRange.rangestart(items.getActivity(), Double.parseDouble(items.getGrade()), Img_ReviewWrite_Star1, Img_ReviewWrite_Star2, Img_ReviewWrite_Star3, Img_ReviewWrite_Star4, Img_ReviewWrite_Star5);
                    Edit_Review_Content.setText(items.getContent());
                    TeamInfo_Dialog.dismiss();
                }
                else{
                    Toast.makeText(items.getActivity()," 잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Txt_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();
                String result = http.HttpClient("Web_Escape", "Home_Focus_Review_Delete.jsp",items.getCompany_Pk(), items.getUser_Pk());
                if(result.equals("succed")){
                    String result3 = http.HttpClient("Web_Escape", "Home_Focus_Review_3.jsp",items.getCompany_Pk(), items.getUser_Pk());
                    String [][] parseredData_reivew = jsonParserList.jsonParserList_Data5(result3);
                    review_models.clear();
                    for (int i = 0; i < parseredData_reivew.length; i++) {
                        String nickname = parseredData_reivew[i][0];
                        String grage = parseredData_reivew[i][1];
                        String content = parseredData_reivew[i][2];
                        String date = parseredData_reivew[i][3];
                        String review_user_pk = parseredData_reivew[i][4];
                        review_models.add(new Review_Model(items.getActivity(), items.getUser_Pk(), review_user_pk, nickname, grage, content, date, items.getCompany_Pk()));
                    }
                    review_adapter.notifyDataSetChanged();
                    Toast.makeText(items.getActivity(),"삭제되었습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(items.getActivity(),"잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                }
                TeamInfo_Dialog.dismiss();
            }
        });
    }
}



