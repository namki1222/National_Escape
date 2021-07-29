package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import blackcap.nationalescape.Model.Book_ThemeTime_Model;
import blackcap.nationalescape.Model.Book_Theme_Model;
import blackcap.nationalescape.Model.Theme_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;

import static blackcap.nationalescape.Activity.tab1.Book_Chapter2.book_chapter2_time_adapter;
import static blackcap.nationalescape.Activity.tab1.Book_Chapter2.book_themeTime_models;
import static blackcap.nationalescape.Activity.tab1.Book_Chapter2.initFlowLayout;
import static blackcap.nationalescape.Activity.tab1.Book_Chapter2.str_book_chapter2_date;

public class Book_Theme_Adapter extends RecyclerView.Adapter<Book_Theme_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Book_Theme_Model> arrData;
    private boolean favorite = false;

    public Book_Theme_Adapter(Context c, ArrayList<Book_Theme_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book_theme, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Book_Theme_Model items = arrData.get(position);
        try {
            //업체 이미지
            Glide.with(items.getActivity()).load("http://www.yologuys.com/Escape_img/theme/"+items.getTheme_Pk()+".jpg").apply(new RequestOptions().placeholder(R.drawable.tab1_list_bg).transform(new RoundedCorners(20)))
                    .into(holder.Img_Img);
            //업체 타이틀
            holder.Txt_Title.setText(items.getTitle());
            //업체 소개
            holder.Txt_Category.setText(items.getCategory());
            if(items.getFlag_Click().equals("true")){
                holder.Layout_Root.setBackground(items.getActivity().getDrawable(R.drawable.round_book_point));
            }
            else{
                holder.Layout_Root.setBackground(items.getActivity().getDrawable(R.drawable.rount_reivew));
            }

            holder.Layout_Root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HttpClient http = new HttpClient();
                    JsonParserList jsonParserList = new JsonParserList();
                    String[][] parseredData_time;
                    for(int i = 0; i < arrData.size() ; i++){
                        //공지사항 리스트 데이터 셋팅
                        if(i == position){
                            arrData.get(i).setFlag_Click("true");

                            String result_time = http.HttpClient("Web_Escape", "Book_Timelist_v2.jsp", arrData.get(i).getTheme_Pk(), str_book_chapter2_date);
                            parseredData_time = jsonParserList.jsonParserList_Data2(result_time);

                            book_themeTime_models.clear();
                            for (int j = 0; j < parseredData_time.length; j++) {
                                String str_time = parseredData_time[j][0];
                                String str_possible = parseredData_time[j][1];
                                if(j == 0){
                                    book_themeTime_models.add(new Book_ThemeTime_Model(arrData.get(i).getActivity(),str_time,str_possible,"true"));
                                }
                                else{
                                    book_themeTime_models.add(new Book_ThemeTime_Model(arrData.get(i).getActivity(),str_time,str_possible,"false"));
                                }
                            }
                            initFlowLayout(book_themeTime_models);
                            //book_chapter2_time_adapter.notifyDataChanged();

                        }
                        else{
                            arrData.get(i).setFlag_Click("false");
                        }
                        notifyDataSetChanged();
                    }
                }
            });
        } catch (Exception e){

        }
    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout Layout_Root;
        ImageView Img_Img;
        TextView Txt_Title, Txt_Category;
        public ViewHolder(final View itemView) {
            super(itemView);
            Layout_Root = (LinearLayout)itemView.findViewById(R.id.layout_root);
            Img_Img = (ImageView)itemView.findViewById(R.id.img_theme);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Category = (TextView)itemView.findViewById(R.id.txt_category);
        }
    }
}

