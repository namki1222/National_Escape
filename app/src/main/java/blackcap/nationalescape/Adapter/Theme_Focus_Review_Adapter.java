package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.tab3.Theme_Focus_Modify;
import blackcap.nationalescape.Activity.tab3.Theme_Focus_Review;
import blackcap.nationalescape.Activity.tab4.Review_Search;
import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.Model.Theme_Review_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import me.drakeet.materialdialog.MaterialDialog;

import static android.content.Context.MODE_PRIVATE;
import static blackcap.nationalescape.Activity.tab3.Theme_Focus.str_theme_focus_title;
import static blackcap.nationalescape.Activity.tab3.Theme_Focus_Review.str_theme_focus_Company_Title;
import static blackcap.nationalescape.Activity.tab3.Theme_Focus_Review.theme_review_adapter;
import static blackcap.nationalescape.Activity.tab3.Theme_Focus_Review.theme_review_models;

public class Theme_Focus_Review_Adapter extends RecyclerView.Adapter<Theme_Focus_Review_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Theme_Review_Model> arrData;
    private boolean favorite = false;
    private ImageView Txt_1, Txt_2, Txt_3, Txt_4;
    private EditText Dialog_Edit_Memo;

    private String str_filter_review = "";
    public Theme_Focus_Review_Adapter(Context c, ArrayList<Theme_Review_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == 0){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_theme_review_header, parent, false);
        }
        else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_theme_focus_review, parent, false);
        }
        return new ViewHolder(v);
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else
            return 1;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Theme_Review_Model items = arrData.get(position);
        if(position ==0){
            try {
                Txt_1 = holder.Txt_Category1;
                Txt_2 = holder.Txt_Category2;
                Txt_3 = holder.Txt_Category3;
                Txt_4 = holder.Txt_Category4;

                str_filter_review = items.getOrderBy();

                if(str_filter_review.equals("desc")){
                    Txt_1.setImageResource(R.drawable.themelist_rank);
                    Txt_2.setImageResource(R.drawable.themelist_new_check);
                    Txt_3.setImageResource(R.drawable.themelist_grade);
                    Txt_4.setImageResource(R.drawable.themelist_rec);
                }
                else if(str_filter_review.equals("review")){
                    Txt_1.setImageResource(R.drawable.themelist_rank);
                    Txt_2.setImageResource(R.drawable.themelist_new);
                    Txt_3.setImageResource(R.drawable.themelist_grade_check);
                    Txt_4.setImageResource(R.drawable.themelist_rec);
                }
                else if(str_filter_review.equals("record")){
                    Txt_1.setImageResource(R.drawable.themelist_rank_check);
                    Txt_2.setImageResource(R.drawable.themelist_new);
                    Txt_3.setImageResource(R.drawable.themelist_grade);
                    Txt_4.setImageResource(R.drawable.themelist_rec);
                }
                else {
                    Txt_1.setImageResource(R.drawable.themelist_rank);
                    Txt_2.setImageResource(R.drawable.themelist_new);
                    Txt_3.setImageResource(R.drawable.themelist_grade);
                    Txt_4.setImageResource(R.drawable.themelist_rec_click);
                }
                holder.Txt_Category1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Async async = new Async();
                        async.execute(position+"", "record");


                        /*HttpClient http = new HttpClient();
                        JsonParserList jsonParserList = new JsonParserList();
                        SharedPreferences preferences = items.getActivity().getSharedPreferences("escape", MODE_PRIVATE);
                        String User_Pk = preferences.getString("pk", ".");

                        String str_result_desc = http.HttpClient("Web_Escape", "Theme_Focus_Review_v8.jsp", items.getTheme_Pk(), User_Pk, "record");
                        String[][] parseredData_reivew = jsonParserList.jsonParserList_Data18(str_result_desc);

                        theme_review_models.clear();
                        theme_review_models.add(new Theme_Review_Model(items.getActivity(), User_Pk, "*", "*","*", "*", "*", items.getTheme_Pk(), "*", "*", "*", "*", "*", "*", items.getUser_Time(), items.getTheme_Time(), "*", "*", "*", "*", "*", "*", "*", "record"));
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
                            String line = parseredData_reivew[i][10];
                            String recommend_count = parseredData_reivew[i][12];
                            String report_count = parseredData_reivew[i][13];
                            String grade_flag = parseredData_reivew[i][14];
                            String bol_owner = parseredData_reivew[i][15];
                            String owner_date = parseredData_reivew[i][16];
                            String owner_memo = parseredData_reivew[i][17];

                            theme_review_models.add(new Theme_Review_Model(items.getActivity(), User_Pk, review_user_pk, nickname, grage, content, date, items.getTheme_Pk(), level, status, time, hint, experience, line, items.getUser_Time(), items.getTheme_Time(), recommend_count, report_count, grade_flag, bol_owner, str_theme_focus_Company_Title, owner_date, owner_memo, "record"));
                        }

                        theme_review_adapter.notifyDataSetChanged();*/

                    }
                });

                holder.Txt_Category2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Async async = new Async();
                        async.execute(position+"", "desc");
                        /*HttpClient http = new HttpClient();
                        JsonParserList jsonParserList = new JsonParserList();
                        SharedPreferences preferences = items.getActivity().getSharedPreferences("escape", MODE_PRIVATE);
                        String User_Pk = preferences.getString("pk", ".");

                        String str_result_desc = http.HttpClient("Web_Escape", "Theme_Focus_Review_v8.jsp", items.getTheme_Pk(), User_Pk, "desc");
                        String[][] parseredData_reivew = jsonParserList.jsonParserList_Data18(str_result_desc);

                        theme_review_models.clear();
                        theme_review_models.add(new Theme_Review_Model(items.getActivity(), User_Pk, "*", "*","*", "*", "*", items.getTheme_Pk(), "*", "*", "*", "*", "*", "*", items.getUser_Time(), items.getTheme_Time(), "*", "*", "*", "*", "*", "*", "*", "desc"));
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
                            String line = parseredData_reivew[i][10];
                            String recommend_count = parseredData_reivew[i][12];
                            String report_count = parseredData_reivew[i][13];
                            String grade_flag = parseredData_reivew[i][14];
                            String bol_owner = parseredData_reivew[i][15];
                            String owner_date = parseredData_reivew[i][16];
                            String owner_memo = parseredData_reivew[i][17];

                            theme_review_models.add(new Theme_Review_Model(items.getActivity(), User_Pk, review_user_pk, nickname, grage, content, date, items.getTheme_Pk(), level, status, time, hint, experience, line, items.getUser_Time(), items.getTheme_Time(), recommend_count, report_count, grade_flag, bol_owner, str_theme_focus_Company_Title, owner_date, owner_memo,"desc"));
                        }

                        theme_review_adapter.notifyDataSetChanged();*/

                    }
                });

                holder.Txt_Category3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Async async = new Async();
                        async.execute(position+"", "review");
                       /* HttpClient http = new HttpClient();
                        JsonParserList jsonParserList = new JsonParserList();
                        SharedPreferences preferences = items.getActivity().getSharedPreferences("escape", MODE_PRIVATE);
                        String User_Pk = preferences.getString("pk", ".");

                        String str_result_desc = http.HttpClient("Web_Escape", "Theme_Focus_Review_v8.jsp", items.getTheme_Pk(), User_Pk, "review");
                        String[][] parseredData_reivew = jsonParserList.jsonParserList_Data18(str_result_desc);

                        theme_review_models.clear();
                        theme_review_models.add(new Theme_Review_Model(items.getActivity(), User_Pk, "*", "*","*", "*", "*", items.getTheme_Pk(), "*", "*", "*", "*", "*", "*", items.getUser_Time(), items.getTheme_Time(), "*", "*", "*", "*", "*", "*", "*", "review"));
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
                            String line = parseredData_reivew[i][10];
                            String recommend_count = parseredData_reivew[i][12];
                            String report_count = parseredData_reivew[i][13];
                            String grade_flag = parseredData_reivew[i][14];
                            String bol_owner = parseredData_reivew[i][15];
                            String owner_date = parseredData_reivew[i][16];
                            String owner_memo = parseredData_reivew[i][17];

                            theme_review_models.add(new Theme_Review_Model(items.getActivity(), User_Pk, review_user_pk, nickname, grage, content, date, items.getTheme_Pk(), level, status, time, hint, experience, line, items.getUser_Time(), items.getTheme_Time(), recommend_count, report_count, grade_flag, bol_owner, str_theme_focus_Company_Title, owner_date, owner_memo, "review"));
                        }

                        theme_review_adapter.notifyDataSetChanged();*/

                    }
                });

                holder.Txt_Category4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* HttpClient http = new HttpClient();
                        JsonParserList jsonParserList = new JsonParserList();
                        SharedPreferences preferences = items.getActivity().getSharedPreferences("escape", MODE_PRIVATE);
                        String User_Pk = preferences.getString("pk", ".");

                        String str_result_desc = http.HttpClient("Web_Escape", "Theme_Focus_Review_v8.jsp", items.getTheme_Pk(), User_Pk, "recommend");
                        String[][] parseredData_reivew = jsonParserList.jsonParserList_Data18(str_result_desc);

                        theme_review_models.clear();
                        theme_review_models.add(new Theme_Review_Model(items.getActivity(), User_Pk, "*", "*","*", "*", "*", items.getTheme_Pk(), "*", "*", "*", "*", "*", "*", items.getUser_Time(), items.getTheme_Time(), "*", "*", "*", "*", "*", "*", "*", "recommend"));
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
                            String line = parseredData_reivew[i][10];
                            String recommend_count = parseredData_reivew[i][12];
                            String report_count = parseredData_reivew[i][13];
                            String grade_flag = parseredData_reivew[i][14];
                            String bol_owner = parseredData_reivew[i][15];
                            String owner_date = parseredData_reivew[i][16];
                            String owner_memo = parseredData_reivew[i][17];

                            theme_review_models.add(new Theme_Review_Model(items.getActivity(), User_Pk, review_user_pk, nickname, grage, content, date, items.getTheme_Pk(), level, status, time, hint, experience, line, items.getUser_Time(), items.getTheme_Time(), recommend_count, report_count, grade_flag, bol_owner, str_theme_focus_Company_Title, owner_date, owner_memo, "recommend"));
                        }

                        theme_review_adapter.notifyDataSetChanged();*/

                        Async async = new Async();
                        async.execute(position+"", "recommend");
                    }
                });


            } catch (Exception e){
                Log.i("테스트", e+ "테스트");
            }
        }
        else{
            try {
                //닉네임 셋팅
                holder.Txt_Nickname.setText(items.getNickname());
                //날짜 셋팅
                holder.Txt_Date.setText(items.getDate());
                //컨텐츠 셋팅
                holder.Txt_Content.setText(items.getContent());

                holder.Txt_Gradeavg.setText(items.getGrade()+"점");

                //난이도 셋팅
                if(items.getLevel().equals("veryEasy")){
                    holder.Img_Level.setImageResource(R.drawable.theme_level_1);
                }else if(items.getLevel().equals("easy")){
                    holder.Img_Level.setImageResource(R.drawable.theme_level_2);
                }
                else if(items.getLevel().equals("normal")){
                    holder.Img_Level.setImageResource(R.drawable.theme_level_3);
                }
                else if(items.getLevel().equals("hard")){
                    holder.Img_Level.setImageResource(R.drawable.theme_level_4);
                }
                else{
                    holder.Img_Level.setImageResource(R.drawable.theme_level_5);
                }


                //성공여부
                if(items.getStatus().equals("success")){
                    holder.Img_Escape.setImageResource(R.drawable.theme_escpae_succed);
                }
                else{
                    holder.Img_Escape.setImageResource(R.drawable.theme_escape_failed);
                }

                if(items.getUser_Pk().equals(items.getReview_User_Pk())){
                    holder.Img_My.setVisibility(View.VISIBLE);
                }
                else{
                    holder.Img_My.setVisibility(View.GONE);
                }
                //사용 힌트 수
                if(items.getHint().equals(".")){
                    holder.Txt_Hint.setVisibility(View.GONE);
                }
                else{
                    holder.Txt_Hint.setVisibility(View.VISIBLE);
                    holder.Txt_Hint.setText(" | 사용힌트수 : "+ items.getHint());
                }

                //남은 시간 수
                if(items.getTime().equals(".")){
                    holder.Txt_Time.setVisibility(View.GONE);
                }
                else{
                    holder.Txt_Time.setVisibility(View.VISIBLE);
                    if(items.getUser_Time().equals("extra")  || items.getUser_Time().equals(".")){
                        holder.Txt_Time.setText(" | 남은시간 : "+ items.getTime());
                    }
                    else{
                        holder.Txt_Time.setText(" | 걸린시간 : "+ items.getTime());
                    }
                }

                //방 탈출 횟수
                if(items.getExperience().equals("0")){
                    holder.Img_Medal.setVisibility(View.GONE);
                }
                else if(items.getExperience().equals("1")){
                    holder.Img_Medal.setVisibility(View.VISIBLE);
                    holder.Img_Medal.setImageResource(R.drawable.user_medal_1_10);
                }else if(items.getExperience().equals("2")){
                    holder.Img_Medal.setVisibility(View.VISIBLE);
                    holder.Img_Medal.setImageResource(R.drawable.user_medal_11_50);
                }else if(items.getExperience().equals("3")){
                    holder.Img_Medal.setVisibility(View.VISIBLE);
                    holder.Img_Medal.setImageResource(R.drawable.user_medal_51_100);
                }else if(items.getExperience().equals("4")){
                    holder.Img_Medal.setVisibility(View.VISIBLE);
                    holder.Img_Medal.setImageResource(R.drawable.user_medal_101_200);
                }else if(items.getExperience().equals("5")){
                    holder.Img_Medal.setVisibility(View.VISIBLE);
                    holder.Img_Medal.setImageResource(R.drawable.user_medal_201_300);
                }else if(items.getExperience().equals("6")){
                    holder.Img_Medal.setVisibility(View.VISIBLE);
                    holder.Img_Medal.setImageResource(R.drawable.user_medal_301_500);
                }else if(items.getExperience().equals("7")){
                    holder.Img_Medal.setVisibility(View.VISIBLE);
                    holder.Img_Medal.setImageResource(R.drawable.user_medal_501_1000);
                }else if(items.getExperience().equals("8")){
                    holder.Img_Medal.setVisibility(View.VISIBLE);
                    holder.Img_Medal.setImageResource(R.drawable.user_medal_1000);
                }

                if(items.getLine().equals("gold")){
                    holder.Layout_Frame.setBackgroundResource(R.drawable.round_review_gold);
                    holder.Img_LineMedal.setImageResource(R.drawable.theme_modal1);
                    holder.Img_LineMedal.setVisibility(View.GONE);
                }
                else if(items.getLine().equals("silver")){
                    holder.Layout_Frame.setBackgroundResource(R.drawable.round_review_silver);
                    holder.Img_LineMedal.setImageResource(R.drawable.theme_modal2);
                    holder.Img_LineMedal.setVisibility(View.GONE);
                }
                else if(items.getLine().equals("copper")){
                    holder.Layout_Frame.setBackgroundResource(R.drawable.round_review_copper);
                    holder.Img_LineMedal.setImageResource(R.drawable.theme_modal3);
                    holder.Img_LineMedal.setVisibility(View.GONE);
                }
                else{
                    holder.Layout_Frame.setBackgroundResource(R.drawable.rount_reivew);
                    holder.Img_LineMedal.setVisibility(View.GONE);
                }

                holder.Layout_Top.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Review_Search.class);
                        intent.putExtra("Nickname", items.getNickname());
                        intent.putExtra("timeview", items.getUser_Time());
                        context.startActivity(intent);

                        items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                });

                holder.Txt_Recommend_Count.setText(items.getRecommend_Count());

                if(items.getRecommend_Flag().equals("true")){
                    holder.Img_Recommend.setImageResource(R.drawable.theme_list_good_on);
                }
                else{
                    holder.Img_Recommend.setImageResource(R.drawable.theme_list_good_off);
                }

                //테마 평점 안 메긴 경우 표시
                if(items.getGrade_Flag().equals("true")){
                    holder.Txt_Gradeavg.setVisibility(View.VISIBLE);
                }
                else{
                    holder.Txt_Gradeavg.setVisibility(View.INVISIBLE);
                }

                //업체 답변
                if(items.getBol_Owner().equals("true")){
                    holder.Layout_Owner.setVisibility(View.VISIBLE);
                    holder.Txt_Owner_Title.setText(items.getOwner_Name());
                    holder.Txt_Owner_Date.setText(items.getOwner_Date());
                    holder.Txt_Owner_Memo.setText(items.getOwner_Memo());

                }
                else{
                    holder.Layout_Owner.setVisibility(View.GONE);
                }

                //추천하기 버튼 이벤트
                holder.Img_Recommend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(items.getUser_Pk().equals(".")){
                            Intent intent = new Intent(items.getActivity(), Login.class);
                            items.getActivity().startActivity(intent);
                            items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                        }
                        else{
                            if(items.getUser_Pk().equals(items.getReview_User_Pk())){
                                Toast.makeText(items.getActivity(), "본인 리뷰는 추천할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Drawable temp = holder.Img_Recommend.getDrawable();
                                Drawable temp1 = items.getActivity().getDrawable(R.drawable.theme_list_good_on);

                                Bitmap temBitmap = ((BitmapDrawable)temp).getBitmap();
                                Bitmap temBitmap1 = ((BitmapDrawable)temp1).getBitmap();

                                HttpClient http = new HttpClient();
                                if(temBitmap.equals(temBitmap1)){
                                    String result = http.HttpClient("Web_Escape", "theme_review_recommend.jsp",items.getTheme_Pk(), items.getUser_Pk(), items.getReview_User_Pk(), "down");
                                    result = result.trim();

                                    if(result.equals("success")){
                                        holder.Img_Recommend.setImageResource(R.drawable.theme_list_good_off);
                                        int count = Integer.parseInt(holder.Txt_Recommend_Count.getText().toString())-1;
                                        if(count < 0){
                                            count = 0;
                                        }
                                        holder.Txt_Recommend_Count.setText(count+"");
                                    }
                                    else{
                                        Toast.makeText(items.getActivity(), "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    String result = http.HttpClient("Web_Escape", "theme_review_recommend.jsp",items.getTheme_Pk(), items.getUser_Pk(), items.getReview_User_Pk(), "up");
                                    result = result.trim();

                                    if(result.equals("success")){
                                        holder.Img_Recommend.setImageResource(R.drawable.theme_list_good_on);
                                        int count = Integer.parseInt(holder.Txt_Recommend_Count.getText().toString())+1;

                                        holder.Txt_Recommend_Count.setText(count+"");
                                    }
                                    else{
                                        Toast.makeText(items.getActivity(), "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        }
                    }
                });

//신고하기 버튼 이벤트
                holder.Img_Report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(items.getUser_Pk().equals(".")){
                            Intent intent = new Intent(items.getActivity(), Login.class);
                            items.getActivity().startActivity(intent);
                            items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                        }
                        else{
                            if(items.getUser_Pk().equals(items.getReview_User_Pk())){
                                Toast.makeText(items.getActivity(), "본인 리뷰는 신고할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                HttpClient http = new HttpClient();
                                String result = http.HttpClient("Web_Escape", "theme_review_report_flag.jsp",items.getTheme_Pk(), items.getUser_Pk(), items.getReview_User_Pk());
                                result = result.trim();

                                if(result.equals("true")){
                                    Log.i("테스트1", "테스트1");
                                    setDialog_Report(items);
                                }
                                else{
                                    Toast.makeText(items.getActivity(), "이미 신고한 내용이 있습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            } catch (Exception e){
                Log.i("테스트1", e+"테스트1");
            }
        }
    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Txt_Nickname, Txt_Date, Txt_Content, Txt_Gradeavg;
        TextView Txt_Time, Txt_Hint;
        ImageView Img_My, Img_Level, Img_Escape, Img_Medal, Img_LineMedal;
        LinearLayout Layout_Frame, Layout_Top;
        ImageView Txt_Category1, Txt_Category2, Txt_Category3, Txt_Category4;
        ImageView Img_Recommend, Img_Report;TextView Txt_Recommend_Count;

        LinearLayout Layout_Owner;
        TextView Txt_Owner_Title, Txt_Owner_Date, Txt_Owner_Memo;

        public ViewHolder(final View itemView) {
            super(itemView);
            Txt_Nickname = (TextView)itemView.findViewById(R.id.txt_nickname);
            Txt_Date = (TextView)itemView.findViewById(R.id.txt_date);
            Txt_Content = (TextView)itemView.findViewById(R.id.txt_content);
            Txt_Gradeavg = (TextView)itemView.findViewById(R.id.txt_gradeavg);
            Img_My = (ImageView)itemView.findViewById(R.id.img_my);
            Img_Level = (ImageView)itemView.findViewById(R.id.img_level);
            Img_Escape = (ImageView)itemView.findViewById(R.id.img_escape);
            Txt_Time = (TextView)itemView.findViewById(R.id.txt_time);
            Txt_Hint = (TextView)itemView.findViewById(R.id.txt_hint);
            Img_Medal = (ImageView)itemView.findViewById(R.id.img_medal);
            Img_LineMedal = (ImageView)itemView.findViewById(R.id.img_linemedal);
            Layout_Frame = (LinearLayout)itemView.findViewById(R.id.layout_frame);
            Layout_Top = (LinearLayout)itemView.findViewById(R.id.layout_top);
            Txt_Category1 = (ImageView) itemView.findViewById(R.id.txt_category1);
            Txt_Category2 = (ImageView)itemView.findViewById(R.id.txt_category2);
            Txt_Category3 = (ImageView)itemView.findViewById(R.id.txt_category3);
            Txt_Category4 = (ImageView)itemView.findViewById(R.id.txt_category4);
            Img_Recommend = (ImageView)itemView.findViewById(R.id.img_recommend);
            Img_Report = (ImageView)itemView.findViewById(R.id.img_report);
            Txt_Recommend_Count = (TextView) itemView.findViewById(R.id.txt_recommend_count);

            Layout_Owner = (LinearLayout)itemView.findViewById(R.id.layout_owner);
            Txt_Owner_Title = (TextView)itemView.findViewById(R.id.txt_owner_title);
            Txt_Owner_Date = (TextView)itemView.findViewById(R.id.txt_owner_date);
            Txt_Owner_Memo = (TextView)itemView.findViewById(R.id.txt_owner_memo);
        }
    }
    //신고하기 다이얼로그 팝업
    public void setDialog_Report(final Theme_Review_Model items){
        Log.i("테스트1", items.getTheme_Pk());
        LayoutInflater inflater = (LayoutInflater)items.getActivity().getSystemService(items.getActivity().LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_review_report, (ViewGroup)items.getActivity().findViewById(R.id.root));
        Dialog_Edit_Memo  = (EditText) layout.findViewById(R.id.edit_memo);
        ImageView Img_Input  = (ImageView) layout.findViewById(R.id.img_input);
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(items.getActivity());
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();
        Img_Input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();
                String result = http.HttpClient("Web_Escape", "theme_review_report.jsp",items.getTheme_Pk(), items.getUser_Pk(), items.getReview_User_Pk(), Dialog_Edit_Memo.getText().toString());

                result = result.trim();
                Log.i("테스트1", items.getTheme_Pk());
                Log.i("테스트1", items.getUser_Pk());
                Log.i("테스트1", items.getReview_User_Pk());
                Log.i("테스트1", result);
                if(result.equals("success")){
                    Toast.makeText(items.getActivity(),"신고되었습니다.", Toast.LENGTH_SHORT).show();
                    TeamInfo_Dialog.dismiss();
                }
                else{
                    Toast.makeText(items.getActivity(),"잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class Async extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;

        Theme_Review_Model items;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(context,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                int position = Integer.parseInt(params[0]);
                String filter = params[1];

                Theme_Review_Model items = arrData.get(position);

                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();
                SharedPreferences preferences = items.getActivity().getSharedPreferences("escape", MODE_PRIVATE);
                String User_Pk = preferences.getString("pk", ".");

                String str_result_desc = http.HttpClient("Web_Escape", "Theme_Focus_Review_v8.jsp", items.getTheme_Pk(), User_Pk, filter);
                String[][] parseredData_reivew = jsonParserList.jsonParserList_Data18(str_result_desc);

                theme_review_models.clear();
                theme_review_models.add(new Theme_Review_Model(items.getActivity(), User_Pk, "*", "*","*", "*", "*", items.getTheme_Pk(), "*", "*", "*", "*", "*", "*", items.getUser_Time(), items.getTheme_Time(), "*", "*", "*", "*", "*", "*", "*", filter));
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
                    String line = parseredData_reivew[i][10];
                    String recommend_count = parseredData_reivew[i][12];
                    String report_count = parseredData_reivew[i][13];
                    String grade_flag = parseredData_reivew[i][14];
                    String bol_owner = parseredData_reivew[i][15];
                    String owner_date = parseredData_reivew[i][16];
                    String owner_memo = parseredData_reivew[i][17];

                    theme_review_models.add(new Theme_Review_Model(items.getActivity(), User_Pk, review_user_pk, nickname, grage, content, date, items.getTheme_Pk(), level, status, time, hint, experience, line, items.getUser_Time(), items.getTheme_Time(), recommend_count, report_count, grade_flag, bol_owner, str_theme_focus_Company_Title, owner_date, owner_memo, filter));
                }


                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            theme_review_adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }
    }
}



