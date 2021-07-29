package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.Community.Focus_Free;
import blackcap.nationalescape.Activity.Community.Write_Board;
import blackcap.nationalescape.Activity.Community.Write_Info;
import blackcap.nationalescape.Activity.frag_com_join;
import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.Model.Com_Board_Model;
import blackcap.nationalescape.Model.Com_Free_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.Http_Get;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;
import static blackcap.nationalescape.Activity.MainActivity.act_main;
import static blackcap.nationalescape.Activity.frag_com_board.com_free_models;
import static blackcap.nationalescape.Activity.frag_com_board.com_free_adapters;
import static blackcap.nationalescape.Activity.frag_com_board.str_search;

public class Com_Board_Adapter extends RecyclerView.Adapter<Com_Board_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Com_Board_Model> arrData;
    private boolean favorite = false;

    public Com_Board_Adapter(Context c, ArrayList<Com_Board_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public Com_Board_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == 0){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_com_search, parent, false);
        }
        else if(viewType == 1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comu_board_write2, parent, false);
        }
        /*else if(viewType == 1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comu_board_write2, parent, false);
        }
        else if(viewType == 2){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comu_board_write2, parent, false);
        }*/
        else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comu_board, parent, false);
        }
        return new Com_Board_Adapter.ViewHolder(v);
    }
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }
        else if(position == 1){
            return 1;
        }
        else {
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(final Com_Board_Adapter.ViewHolder holder, final int position) {
        final Com_Board_Model items = arrData.get(position);
        try {
            if(position == 0){
                holder.Img_Search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.Edit_Search.getText().toString().equals("")){
                            Toast.makeText(items.getActivity(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Http_Get http = new Http_Get();
                            String result = http.Http_Get(User_Pk, "v1/board/list?diff=보드판%20갤러리&sort=최신순&search="+holder.Edit_Search.getText().toString().replace(" ", "%20"));
                            String[][] parseredData = jsonParserList(result);

                            com_free_models.clear();
                            com_free_models.add(new Com_Board_Model(items.getActivity(),"", "", "", "", "", "", "", "", "", "","", "","", ""));
                            com_free_models.add(new Com_Board_Model(items.getActivity(),"", "", "", "", "", "", "", "", "", "","", "","", ""));
                            for (int i = 0; i < parseredData.length; i++) {
                                String id = parseredData[i][0];
                                String user_pk = parseredData[i][1];
                                String nickname = parseredData[i][2];
                                String grade = parseredData[i][3];
                                String title = parseredData[i][4];
                                String category = parseredData[i][5];
                                String commentCount = parseredData[i][6];
                                String isLike = parseredData[i][7];
                                String diff = parseredData[i][8];
                                String createdAt = parseredData[i][9];
                                String thumbnail = parseredData[i][10];
                                String company_name = parseredData[i][11];
                                String theme_name = parseredData[i][12];
                                String like_count = parseredData[i][13];

                                com_free_models.add(new Com_Board_Model(items.getActivity(),id, user_pk, nickname, grade, title, category, commentCount, isLike, diff, createdAt, thumbnail, company_name, theme_name, like_count));
                            }

                            com_free_adapters.notifyDataSetChanged();

                            InputMethodManager imm = (InputMethodManager) act_main.getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(holder.Edit_Search.getWindowToken(), 0);
                            holder.Img_Refresh.setVisibility(View.VISIBLE);

                            str_search = holder.Edit_Search.getText().toString();
                        }
                    }
                });

                holder.Img_Refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Http_Get http = new Http_Get();
                        String result = http.Http_Get(User_Pk, "v1/board/list?diff=보드판%20갤러리&sort=최신순");
                        String[][] parseredData = jsonParserList(result);

                        com_free_models.clear();
                        com_free_models.add(new Com_Board_Model(items.getActivity(),"", "", "", "", "", "", "", "", "", "","", "","", ""));
                        com_free_models.add(new Com_Board_Model(items.getActivity(),"", "", "", "", "", "", "", "", "", "","", "","", ""));
                        for (int i = 0; i < parseredData.length; i++) {
                            String id = parseredData[i][0];
                            String user_pk = parseredData[i][1];
                            String nickname = parseredData[i][2];
                            String grade = parseredData[i][3];
                            String title = parseredData[i][4];
                            String category = parseredData[i][5];
                            String commentCount = parseredData[i][6];
                            String isLike = parseredData[i][7];
                            String diff = parseredData[i][8];
                            String createdAt = parseredData[i][9];
                            String thumbnail = parseredData[i][10];
                            String company_name = parseredData[i][11];
                            String theme_name = parseredData[i][12];
                            String like_count = parseredData[i][13];

                            com_free_models.add(new Com_Board_Model(items.getActivity(),id, user_pk, nickname, grade, title, category, commentCount, isLike, diff, createdAt, thumbnail, company_name, theme_name, like_count));
                        }

                        com_free_adapters.notifyDataSetChanged();

                        InputMethodManager imm = (InputMethodManager) act_main.getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(holder.Edit_Search.getWindowToken(), 0);
                        holder.Edit_Search.setText("");
                        holder.Img_Refresh.setVisibility(View.GONE);

                        str_search = "";
                    }
                });
            }
            else if(position == 1){
                holder.Img_Write.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(User_Pk.equals(".")){
                            Intent intent = new Intent(items.getActivity(), Login.class);
                            items.getActivity().startActivity(intent);
                            items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                        }
                        else{
                            Intent intent = new Intent(items.getActivity(), Write_Board.class);
                            intent.putExtra("category", "등록");
                            items.getActivity().startActivity(intent);
                            items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                        }
                    }
                });

                holder.Txt_New.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Http_Get http = new Http_Get();
                        String result = http.Http_Get(User_Pk, "v1/board/list?diff=보드판%20갤러리&sort=최신순");
                        String[][] parseredData = jsonParserList(result);

                        com_free_models.clear();
                        com_free_models.add(new Com_Board_Model(items.getActivity(),"", "", "", "", "", "", "", "", "", "","", "","", ""));
                        com_free_models.add(new Com_Board_Model(items.getActivity(),"", "", "", "", "", "", "", "", "", "","", "","", ""));
                        for (int i = 0; i < parseredData.length; i++) {
                            String id = parseredData[i][0];
                            String user_pk = parseredData[i][1];
                            String nickname = parseredData[i][2];
                            String grade = parseredData[i][3];
                            String title = parseredData[i][4];
                            String category = parseredData[i][5];
                            String commentCount = parseredData[i][6];
                            String isLike = parseredData[i][7];
                            String diff = parseredData[i][8];
                            String createdAt = parseredData[i][9];
                            String thumbnail = parseredData[i][10];
                            String company_name = parseredData[i][11];
                            String theme_name = parseredData[i][12];
                            String like_count = parseredData[i][13];

                            com_free_models.add(new Com_Board_Model(items.getActivity(),id, user_pk, nickname, grade, title, category, commentCount, isLike, diff, createdAt, thumbnail, company_name, theme_name, like_count));
                        }
                        com_free_adapters.notifyDataSetChanged();
                        holder.Txt_New.setTextColor(items.getActivity().getResources().getColor(R.color.black));
                        holder.Txt_Rec.setTextColor(items.getActivity().getResources().getColor(R.color.gray));
                    }
                });

                holder.Txt_Rec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Http_Get http = new Http_Get();
                        String result = http.Http_Get(User_Pk, "v1/board/list?diff=보드판%20갤러리&sort=추천순");
                        String[][] parseredData = jsonParserList(result);

                        com_free_models.clear();
                        com_free_models.add(new Com_Board_Model(items.getActivity(),"", "", "", "", "", "", "", "", "", "","", "","",""));
                        com_free_models.add(new Com_Board_Model(items.getActivity(),"", "", "", "", "", "", "", "", "", "","", "","",""));
                        for (int i = 0; i < parseredData.length; i++) {
                            String id = parseredData[i][0];
                            String user_pk = parseredData[i][1];
                            String nickname = parseredData[i][2];
                            String grade = parseredData[i][3];
                            String title = parseredData[i][4];
                            String category = parseredData[i][5];
                            String commentCount = parseredData[i][6];
                            String isLike = parseredData[i][7];
                            String diff = parseredData[i][8];
                            String createdAt = parseredData[i][9];
                            String thumbnail = parseredData[i][10];
                            String company_name = parseredData[i][11];
                            String theme_name = parseredData[i][12];
                            String like_count = parseredData[i][13];

                            com_free_models.add(new Com_Board_Model(items.getActivity(),id, user_pk, nickname, grade, title, category, commentCount, isLike, diff, createdAt, thumbnail, company_name, theme_name, like_count));
                        }
                        com_free_adapters.notifyDataSetChanged();
                        holder.Txt_New.setTextColor(items.getActivity().getResources().getColor(R.color.gray));
                        holder.Txt_Rec.setTextColor(items.getActivity().getResources().getColor(R.color.black));
                    }
                });
            }
            else{
                Glide.with(items.getActivity()).load("https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+items.getThumbnail())
                        .into(holder.Img_Thumb);

                //업체 타이틀
                holder.Txt_Company.setText(items.getCompany_name());
                //업체 소개
                holder.Txt_Theme.setText(items.getTheme_name());
                //업체 평점
                holder.Txt_Date.setText(items.getCreatedAt());
                //업체와의 거리
                holder.Txt_Nickname.setText(items.getNickname());
                holder.Txt_Date.setText(items.getCreatedAt());
                holder.Txt_Recommend.setText(items.getLikeCount());

                if(items.getCategory().equals("공지")){
                    holder.Img_Grade.setVisibility(View.VISIBLE);
                    holder.Img_Grade.setImageResource(R.drawable.comu_master);
                    holder.Txt_Nickname.setTypeface(null, Typeface.BOLD);
                    //holder.Txt_Nickname.setTypeface("monospace");
                }
                else{
                    holder.Txt_Nickname.setTypeface(null, Typeface.NORMAL);
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
            }
        } catch (Exception e){
            Log.i("테스트", e+ "테스트");
        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Txt_Company, Txt_Theme, Txt_Nickname, Txt_Date, Txt_Recommend;
        TextView Txt_New, Txt_Rec;
        ImageView Img_Write, Img_Thumb, Img_Grade, Img_Refresh, Img_Search;
        LinearLayout Layout;
        EditText Edit_Search;
        public ViewHolder(final View itemView) {
            super(itemView);
            Txt_Company = (TextView)itemView.findViewById(R.id.txt_company);
            Txt_Theme = (TextView)itemView.findViewById(R.id.txt_theme);
            Txt_Nickname = (TextView)itemView.findViewById(R.id.txt_nickname);
            Txt_Date = (TextView)itemView.findViewById(R.id.txt_date);
            Txt_Recommend = (TextView)itemView.findViewById(R.id.txt_recommend);
            Img_Write = (ImageView) itemView.findViewById(R.id.img_write);
            Img_Thumb = (ImageView)itemView.findViewById(R.id.img_thumb);
            Img_Grade = (ImageView)itemView.findViewById(R.id.img_grade);

            Txt_New = (TextView)itemView.findViewById(R.id.txt_new);
            Txt_Rec = (TextView)itemView.findViewById(R.id.txt_rec);

            Layout = (LinearLayout)itemView.findViewById(R.id.layout);
            Img_Search = (ImageView)itemView.findViewById(R.id.img_search);
            Img_Refresh = (ImageView)itemView.findViewById(R.id.img_refresh);
            Edit_Search = (EditText)itemView.findViewById(R.id.edit_search);
        }
    }

    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("list");
            String[] jsonName = {"id", "user_pk", "nickname","grade", "title", "category","commentCount", "isLike", "diff", "createdAt", "thumbnail", "company_name", "theme_name", "likeCount"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
