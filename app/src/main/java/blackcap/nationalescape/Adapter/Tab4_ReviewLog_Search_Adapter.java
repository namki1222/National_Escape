package blackcap.nationalescape.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blackcap.nationalescape.Activity.tab3.Theme_Focus;
import blackcap.nationalescape.Model.Review_Log_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;
import blackcap.nationalescape.Uitility.StartRange;
import me.drakeet.materialdialog.MaterialDialog;

import static android.content.Context.MODE_PRIVATE;
import static blackcap.nationalescape.Activity.tab4.Review_Search.Review_Search_Edit_Search;
import static blackcap.nationalescape.Activity.tab4.Review_Search.str_allplay;
import static blackcap.nationalescape.Activity.tab4.Review_Search.str_nohintplay;
import static blackcap.nationalescape.Activity.tab4.Review_Search.str_successplay;
import static blackcap.nationalescape.Activity.tab4.Review_Search.reviewSearch_adapter;
import static blackcap.nationalescape.Activity.tab4.Review_Search.review_models;

public class Tab4_ReviewLog_Search_Adapter extends RecyclerView.Adapter<Tab4_ReviewLog_Search_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Review_Log_Model> arrData;
    private boolean favorite = false;

    private TextView Txt_1, Txt_2, Txt_3, Txt_4;

    private Activity act;
    Review_Log_Model items;
    public Tab4_ReviewLog_Search_Adapter(Context c, ArrayList<Review_Log_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == 0){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reviewlog_header, parent, false);
        }
        else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reviewlog, parent, false);
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
    public void onBindViewHolder(ViewHolder holder,  int position) {
        items = arrData.get(position);
        act = arrData.get(position).getActivity();
        if(position ==0){
            try {
                //업체 타이틀
                holder.Txt_Total.setText(str_allplay);
                //업체 소개
                holder.Txt_Success.setText(str_successplay);
                //업체 평점
                holder.Txt_Nohint.setText(str_nohintplay);
                Txt_1 = holder.Txt_Category1;
                Txt_2 = holder.Txt_Category2;
                Txt_3 = holder.Txt_Category3;
                Txt_4 = holder.Txt_Category4;

                holder.Txt_Category1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Async async = new Async();
                        async.execute("category1");
                    }
                });

                holder.Txt_Category2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Async async = new Async();
                        async.execute("category2");
                    }
                });

                holder.Txt_Category3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Async async = new Async();
                        async.execute("category3");
                    }
                });

                holder.Txt_Category4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Async async = new Async();
                        async.execute("category4");
                    }
                });

            } catch (Exception e){
                Log.i("테스트", e+ "테스트");
            }
        }else{
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
                    holder.Txt_Time.setVisibility(View.VISIBLE);
                    if(items.getTimeview().equals("extra")){
                        holder.Txt_Time.setText("남은시간 : "+items.getTime());
                    }
                    else{
                        holder.Txt_Time.setText("걸린시간 : "+items.getTime());
                    }
                }

                if(items.getHint().equals(".")){
                    holder.Txt_Hint.setVisibility(View.GONE);
                }
                else{
                    holder.Txt_Hint.setVisibility(View.VISIBLE);
                    holder.Txt_Hint.setText("사용힌트수 : "+items.getHint());
                }


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

                //테마 평점 안 매긴경우 처리
                if(items.getGradeFlag().equals("true")){
                    holder.Layout_Star.setVisibility(View.VISIBLE);
                }
                else{
                    holder.Layout_Star.setVisibility(View.INVISIBLE);
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
        private TextView Txt_Total, Txt_Success, Txt_Nohint;
        private TextView Txt_Category1, Txt_Category2, Txt_Category3, Txt_Category4;
        private LinearLayout Layout_Star;

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

            Txt_Total = (TextView)itemView.findViewById(R.id.txt_total);
            Txt_Success = (TextView)itemView.findViewById(R.id.txt_success);
            Txt_Nohint = (TextView)itemView.findViewById(R.id.txt_nohint);

            Txt_Category1 = (TextView)itemView.findViewById(R.id.txt_category1);
            Txt_Category2 = (TextView)itemView.findViewById(R.id.txt_category2);
            Txt_Category3 = (TextView)itemView.findViewById(R.id.txt_category3);
            Txt_Category4 = (TextView)itemView.findViewById(R.id.txt_category4);

            Layout_Star = (LinearLayout)itemView.findViewById(R.id.layout_star);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() != 0){
                setDialog_Focus(arrData.get(getAdapterPosition()));
            }
        }
    }
    public void setDialog_Focus(final Review_Log_Model items){
        LayoutInflater inflater = (LayoutInflater)items.getActivity().getSystemService(items.getActivity().LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_reviewsearch_log, (ViewGroup)items.getActivity().findViewById(R.id.root));
        TextView Txt_Company, Txt_Theme, Txt_Escape, Txt_Time, Txt_Hint, Txt_Memo, Txt_ThemeGo;
        ImageView Img_Escape, Img_Level;
        ImageView Img_Star1, Img_Star2, Img_Star3, Img_Star4, Img_Star5;
        LinearLayout Layout_Star;

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
        Txt_ThemeGo = (TextView)layout.findViewById(R.id.txt_themego);
        Layout_Star = (LinearLayout) layout.findViewById(R.id.layout_star);

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
            if(items.getTimeview().equals("extra")){
                Txt_Time.setText("남은시간 : "+items.getTime());
            }
            else{
                Txt_Time.setText("걸린시간 : "+items.getTime());
            }
        }

        if(items.getHint().equals(".")){
            Txt_Hint.setVisibility(View.GONE);
        }
        else{
            Txt_Hint.setText("사용힌트수 : "+items.getHint());
        }

        //테마 평점 안 매긴경우 처리
        if(items.getGradeFlag().equals("true")){
            Layout_Star.setVisibility(View.VISIBLE);
        }
        else{
            Layout_Star.setVisibility(View.INVISIBLE);
        }

        Txt_Memo.setText(items.getContent());


        //난이도 셋팅
        if(items.getLevel().equals("veryEasy")){
            Img_Level.setImageResource(R.drawable.theme_level_1);
        }else if(items.getLevel().equals("easy")){
            Img_Level.setImageResource(R.drawable.theme_level_2);
        }
        else if(items.getLevel().equals("normal")){
            Img_Level.setImageResource(R.drawable.theme_level_3);
        }
        else if(items.getLevel().equals("hard")){
            Img_Level.setImageResource(R.drawable.theme_level_4);
        }
        else{
            Img_Level.setImageResource(R.drawable.theme_level_5);
        }

        Txt_ThemeGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(items.getActivity(), Theme_Focus.class);
                intent.putExtra("Theme_Pk", items.getTheme_Pk());

                items.getActivity().startActivity(intent);
                items.getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        final MaterialDialog TeamInfo_Dialog = new MaterialDialog(items.getActivity());
        TeamInfo_Dialog
                .setContentView(layout)
                .setCanceledOnTouchOutside(true);
        TeamInfo_Dialog.show();
    }
    public String[][] jsonParserList_Data11(String result){
        Log.i("서버에서 받은 전체 내용", result);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7", "msg8", "msg9", "msg10", "msg11"};
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
    public class Async extends AsyncTask<String, Void, String> {
        private Progressbar_wheel progressDialog;
        private String str_category = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(act,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                str_category = params[0];
                if(params[0].equals("category1")){
                    HttpClient http = new HttpClient();
                    SharedPreferences preferences = items.getActivity().getSharedPreferences("escape", MODE_PRIVATE);
                    String User_Pk = preferences.getString("pk", ".");
                    JsonParserList jsonParserList = new JsonParserList();

                    String str_result_desc = "";
                    if(Txt_1.getText().toString().equals("최신 순 ▲")){
                        str_result_desc = http.HttpClient("Web_Escape", "Review_Search_v3.jsp", Review_Search_Edit_Search.getText().toString(), "datedown", User_Pk);
                    }
                    else{
                        str_result_desc = http.HttpClient("Web_Escape", "Review_Search_v3.jsp", Review_Search_Edit_Search.getText().toString(), "dateup", User_Pk);
                    }

                    String[][] parseredData_desc = jsonParserList.jsonParserList_Data15(str_result_desc);

                    review_models.clear();
                    review_models.add(new Review_Log_Model(items.getActivity(), "*", "*", "*","*", "*", "*", "*", "*", items.getTimeview(), "*", "*", "*", "*", "*"));
                    for (int i = 0; i < parseredData_desc.length; i++) {
                        String grade = parseredData_desc[i][0];
                        String content = parseredData_desc[i][1];
                        String date = parseredData_desc[i][2];
                        String user_pk = parseredData_desc[i][3];
                        String level = parseredData_desc[i][4];
                        String status = parseredData_desc[i][5];
                        String time = parseredData_desc[i][6];
                        String hint = parseredData_desc[i][7];
                        String experience = parseredData_desc[i][8];
                        String company_name = parseredData_desc[i][9];
                        String theme_name = parseredData_desc[i][10];
                        String deadtime = parseredData_desc[i][11];
                        String theme_pk = parseredData_desc[i][12];
                        String timeview = parseredData_desc[i][13];
                        String gradeflag = parseredData_desc[i][14];
                        review_models.add(new Review_Log_Model(items.getActivity(), company_name, theme_name, grade, content, level, status, time, hint, items.getTimeview(), theme_pk, timeview, deadtime, date, gradeflag));
                    }
                }
                else if(params[0].equals("category2")){
                    HttpClient http = new HttpClient();
                    JsonParserList jsonParserList = new JsonParserList();
                    SharedPreferences preferences = items.getActivity().getSharedPreferences("escape", MODE_PRIVATE);
                    String User_Pk = preferences.getString("pk", ".");


                    String str_result_review = "";
                    if(Txt_2.getText().toString().equals("등록 순 ▲")){
                        str_result_review = http.HttpClient("Web_Escape", "Review_Search_v3.jsp", Review_Search_Edit_Search.getText().toString(), "ascdown", User_Pk);
                    }
                    else{
                        str_result_review = http.HttpClient("Web_Escape", "Review_Search_v3.jsp", Review_Search_Edit_Search.getText().toString(), "ascup", User_Pk);
                    }

                    String[][] parseredData_review = jsonParserList.jsonParserList_Data15(str_result_review);

                    review_models.clear();
                    review_models.add(new Review_Log_Model(items.getActivity(), "*", "*", "*","*", "*", "*", "*", "*", items.getTimeview(), "*", "*", "*", "*", "*"));
                    for (int i = 0; i < parseredData_review.length; i++) {
                        String grade = parseredData_review[i][0];
                        String content = parseredData_review[i][1];
                        String date = parseredData_review[i][2];
                        String user_pk = parseredData_review[i][3];
                        String level = parseredData_review[i][4];
                        String status = parseredData_review[i][5];
                        String time = parseredData_review[i][6];
                        String hint = parseredData_review[i][7];
                        String experience = parseredData_review[i][8];
                        String company_name = parseredData_review[i][9];
                        String theme_name = parseredData_review[i][10];
                        String deadtime = parseredData_review[i][11];
                        String theme_pk = parseredData_review[i][12];
                        String timeview = parseredData_review[i][13];
                        String gradeflag = parseredData_review[i][14];
                        review_models.add(new Review_Log_Model(items.getActivity(), company_name, theme_name, grade, content, level, status, time, hint, items.getTimeview(), theme_pk, timeview, deadtime, date, gradeflag));
                    }
                }
                else if(params[0].equals("category3")){
                    HttpClient http = new HttpClient();
                    JsonParserList jsonParserList = new JsonParserList();
                    SharedPreferences preferences = items.getActivity().getSharedPreferences("escape", MODE_PRIVATE);
                    String User_Pk = preferences.getString("pk", ".");
                    Log.i("테스트xx",User_Pk);
                    String str_result_review = "";
                    if(Txt_3.getText().toString().equals("평점 순 ▲")){
                        Log.i("테스트","xx1");
                        str_result_review = http.HttpClient("Web_Escape", "Review_Search_v3.jsp", Review_Search_Edit_Search.getText().toString(), "gradedown", User_Pk);
                    }
                    else{
                        Log.i("테스트","xx2");
                        str_result_review = http.HttpClient("Web_Escape", "Review_Search_v3.jsp", Review_Search_Edit_Search.getText().toString(), "gradeup", User_Pk);
                    }
                    String[][] parseredData_review = jsonParserList.jsonParserList_Data15(str_result_review);

                    review_models.clear();
                    review_models.add(new Review_Log_Model(items.getActivity(), "*", "*", "*","*", "*", "*", "*", "*", items.getTimeview(), "*", "*", "*", "*", "*"));
                    for (int i = 0; i < parseredData_review.length; i++) {
                        String grade = parseredData_review[i][0];
                        String content = parseredData_review[i][1];
                        String date = parseredData_review[i][2];
                        String user_pk = parseredData_review[i][3];
                        String level = parseredData_review[i][4];
                        String status = parseredData_review[i][5];
                        String time = parseredData_review[i][6];
                        String hint = parseredData_review[i][7];
                        String experience = parseredData_review[i][8];
                        String company_name = parseredData_review[i][9];
                        String theme_name = parseredData_review[i][10];
                        String deadtime = parseredData_review[i][11];
                        String theme_pk = parseredData_review[i][12];
                        String timeview = parseredData_review[i][13];
                        String gradeflag = parseredData_review[i][14];
                        review_models.add(new Review_Log_Model(items.getActivity(), company_name, theme_name, grade, content, level, status, time, hint, items.getTimeview(), theme_pk, timeview, deadtime, date, gradeflag));
                    }
                }
                else{
                    HttpClient http = new HttpClient();
                    JsonParserList jsonParserList = new JsonParserList();
                    SharedPreferences preferences = items.getActivity().getSharedPreferences("escape", MODE_PRIVATE);
                    String User_Pk = preferences.getString("pk", ".");

                    String str_result_review = "";
                    if(Txt_4.getText().toString().equals("난이도 순 ▲")){
                        str_result_review = http.HttpClient("Web_Escape", "Review_Search_v3.jsp", Review_Search_Edit_Search.getText().toString(), "leveldown", User_Pk);
                    }
                    else{
                        str_result_review = http.HttpClient("Web_Escape", "Review_Search_v3.jsp", Review_Search_Edit_Search.getText().toString(), "levelup", User_Pk);
                    }

                    String[][] parseredData_review = jsonParserList.jsonParserList_Data15(str_result_review);

                    review_models.clear();
                    review_models.add(new Review_Log_Model(items.getActivity(), "*", "*", "*","*", "*", "*", "*", "*", items.getTimeview(), "*", "*", "*", "*", "*"));
                    for (int i = 0; i < parseredData_review.length; i++) {
                        String grade = parseredData_review[i][0];
                        String content = parseredData_review[i][1];
                        String date = parseredData_review[i][2];
                        String user_pk = parseredData_review[i][3];
                        String level = parseredData_review[i][4];
                        String status = parseredData_review[i][5];
                        String time = parseredData_review[i][6];
                        String hint = parseredData_review[i][7];
                        String experience = parseredData_review[i][8];
                        String company_name = parseredData_review[i][9];
                        String theme_name = parseredData_review[i][10];
                        String deadtime = parseredData_review[i][11];
                        String theme_pk = parseredData_review[i][12];
                        String timeview = parseredData_review[i][13];
                        String gradeflag = parseredData_review[i][14];
                        review_models.add(new Review_Log_Model(items.getActivity(), company_name, theme_name, grade, content, level, status, time, hint, items.getTimeview(), theme_pk, timeview, deadtime, date, gradeflag));
                    }
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
            reviewSearch_adapter.notifyDataSetChanged();

            if(str_category.equals("category1")){
                if(Txt_1.getText().toString().equals("최신 순 ▲")){
                    Txt_1.setText("최신 순 ▼");
                }
                else{
                    Txt_1.setText("최신 순 ▲");
                }

                Txt_1.setTextColor(items.getActivity().getResources().getColor(R.color.black));
                Txt_2.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
                Txt_3.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
                Txt_4.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
            }
            else if(str_category.equals("category2")){
                if(Txt_2.getText().toString().equals("등록 순 ▲")){
                    Txt_2.setText("등록 순 ▼");
                }
                else{
                    Txt_2.setText("등록 순 ▲");
                }
                Txt_1.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
                Txt_2.setTextColor(items.getActivity().getResources().getColor(R.color.black));
                Txt_3.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
                Txt_4.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
            }
            else if(str_category.equals("category3")){
                if(Txt_3.getText().toString().equals("평점 순 ▲")){
                    Txt_3.setText("평점 순 ▼");
                }
                else{
                    Txt_3.setText("평점 순 ▲");
                }
                Txt_1.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
                Txt_2.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
                Txt_3.setTextColor(items.getActivity().getResources().getColor(R.color.black));
                Txt_4.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
            }
            else{
                if(Txt_4.getText().toString().equals("난이도 순 ▲")){
                    Txt_4.setText("난이도 순 ▼");
                }
                else{
                    Txt_4.setText("난이도 순 ▲");
                }
                Txt_1.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
                Txt_2.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
                Txt_3.setTextColor(items.getActivity().getResources().getColor(R.color.date_gray));
                Txt_4.setTextColor(items.getActivity().getResources().getColor(R.color.black));
            }

            progressDialog.dismiss();
        }
    }
}




