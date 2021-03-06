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
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.Community.Focus_Free;
import blackcap.nationalescape.Activity.Community.Write_Free;
import blackcap.nationalescape.Activity.tab1.Book_Chapter1;
import blackcap.nationalescape.Activity.tab1.Book_Chapter2;
import blackcap.nationalescape.Activity.tab1.Home_Focus;
import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.Model.Com_Free_Model;
import blackcap.nationalescape.Model.Company_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Http_Get;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;
import static blackcap.nationalescape.Activity.MainActivity.act_main;
import static blackcap.nationalescape.Activity.frag_com_free.com_free_models;
import static blackcap.nationalescape.Activity.frag_com_free.com_free_adapters;

public class Com_Free_Adapter extends RecyclerView.Adapter<Com_Free_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Com_Free_Model> arrData;
    private boolean favorite = false;

    public Com_Free_Adapter(Context c, ArrayList<Com_Free_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public Com_Free_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == 0){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_com_search, parent, false);
        }
        else if(viewType == 1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_com_free_write, parent, false);
        }
        else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_com_free, parent, false);
        }
        return new Com_Free_Adapter.ViewHolder(v);
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
    public void onBindViewHolder(final Com_Free_Adapter.ViewHolder holder, final int position) {
        final Com_Free_Model items = arrData.get(position);
        try {
            if(position == 0){
                holder.Img_Search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.Edit_Search.getText().toString().equals("")){
                            Toast.makeText(items.getActivity(), "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Http_Get http = new Http_Get();
                            String result = http.Http_Get(User_Pk, "v1/board/list?diff=???????????????&sort=?????????&search="+holder.Edit_Search.getText().toString().replace(" ", "%20"));
                            String[][] parseredData = jsonParserList(result);

                            com_free_models.clear();
                            com_free_models.add(new Com_Free_Model(act_main,"", "", "", "", "", "", "", "", "", ""));
                            com_free_models.add(new Com_Free_Model(act_main,"", "", "", "", "", "", "", "", "", ""));
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
                                com_free_models.add(new Com_Free_Model(act_main,id, user_pk, nickname, grade, title, category, commentCount, isLike, diff, createdAt));
                            }

                            com_free_adapters.notifyDataSetChanged();

                            InputMethodManager imm = (InputMethodManager) act_main.getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(holder.Edit_Search.getWindowToken(), 0);
                            holder.Img_Refresh.setVisibility(View.VISIBLE);
                        }
                    }
                });

                holder.Img_Refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Http_Get http = new Http_Get();
                        String result = http.Http_Get(User_Pk, "v1/board/list?diff=???????????????&sort=?????????");
                        String[][] parseredData = jsonParserList(result);

                        com_free_models.clear();
                        com_free_models.add(new Com_Free_Model(act_main,"", "", "", "", "", "", "", "", "", ""));
                        com_free_models.add(new Com_Free_Model(act_main,"", "", "", "", "", "", "", "", "", ""));
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
                            com_free_models.add(new Com_Free_Model(act_main,id, user_pk, nickname, grade, title, category, commentCount, isLike, diff, createdAt));
                        }

                        com_free_adapters.notifyDataSetChanged();

                        InputMethodManager imm = (InputMethodManager) act_main.getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(holder.Edit_Search.getWindowToken(), 0);
                        holder.Edit_Search.setText("");
                        holder.Img_Refresh.setVisibility(View.GONE);
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
                            Intent intent = new Intent(items.getActivity(), Write_Free.class);
                            intent.putExtra("category", "??????");
                            items.getActivity().startActivity(intent);
                            items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                        }
                    }
                });
            }
            else{
                //?????? ?????????
                holder.Txt_Title.setText(items.getTitle());
                //?????? ??????
                holder.Txt_Nick.setText(items.getNickname());
                //?????? ??????
                holder.Txt_Date.setText(items.getCreatedAt());
                //???????????? ??????
                holder.Txt_Comment_Count.setText(items.getCommentCount());

                if(items.getCategory().equals("??????")){
                    holder.Img_Grade.setVisibility(View.VISIBLE);
                    holder.Img_Grade.setImageResource(R.drawable.comu_master);
                    holder.Txt_Nick.setTypeface(null, Typeface.BOLD);
                    holder.Img_Caregory.setVisibility(View.VISIBLE);
                    //holder.Txt_Nickname.setTypeface("monospace");
                }
                else{
                    holder.Img_Caregory.setVisibility(View.GONE);
                    holder.Txt_Nick.setTypeface(null, Typeface.NORMAL);
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
            Log.i("?????????", e+ "?????????");
        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        EditText Edit_Search;
        TextView Txt_Title, Txt_Nick, Txt_Date, Txt_Comment_Count;
        ImageView Img_Write, Img_Search, Img_Refresh, Img_Grade, Img_Caregory;
        LinearLayout Layout;
        public ViewHolder(final View itemView) {
            super(itemView);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Nick = (TextView)itemView.findViewById(R.id.txt_nick);
            Txt_Date = (TextView)itemView.findViewById(R.id.txt_date);
            Txt_Comment_Count = (TextView)itemView.findViewById(R.id.txt_comment_count);
            Img_Search = (ImageView)itemView.findViewById(R.id.img_search);
            Img_Refresh = (ImageView)itemView.findViewById(R.id.img_refresh);
            Edit_Search = (EditText)itemView.findViewById(R.id.edit_search);
            Img_Write = (ImageView)itemView.findViewById(R.id.img_write);
            Img_Grade = (ImageView)itemView.findViewById(R.id.img_grade);
            Img_Caregory = (ImageView)itemView.findViewById(R.id.img_category);
            Layout = (LinearLayout)itemView.findViewById(R.id.layout);
        }
    }
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("???????????? ?????? ?????? ??????", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("list");
            String[] jsonName = {"id", "user_pk", "nickname","grade", "title", "category","commentCount", "isLike", "diff", "createdAt"};
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

