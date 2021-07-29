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
import blackcap.nationalescape.Activity.Community.Write_Free;
import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.Model.Com_Free_Model;
import blackcap.nationalescape.Model.Img_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.Http_Get;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static blackcap.nationalescape.Activity.Community.Focus_Free.img_height;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;
import static blackcap.nationalescape.Activity.MainActivity.act_main;
import static blackcap.nationalescape.Activity.frag_com_free.com_free_adapters;
import static blackcap.nationalescape.Activity.frag_com_free.com_free_models;

public class Img_Adapter extends RecyclerView.Adapter<Img_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Img_Model> arrData;
    private boolean favorite = false;

    public Img_Adapter(Context c, ArrayList<Img_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public Img_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_img, parent, false);

        return new Img_Adapter.ViewHolder(v);
    }
    @Override
    public int getItemViewType(int position) {
      return 0;
    }

    @Override
    public void onBindViewHolder(final Img_Adapter.ViewHolder holder, final int position) {
        final Img_Model items = arrData.get(position);
        try {
            Glide.with(items.getActivity()).load("https://d35jysenqmci34.cloudfront.net/fit-in/600x600/"+items.getImg())
                    .into(holder.Img);

            Log.i("테스트", "https://d35jysenqmci34.cloudfront.net/fit-in/600x600/"+items.getImg());
        } catch (Exception e){
            Log.i("테스트", e+ "테스트");
        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Img;
        public ViewHolder(final View itemView) {
            super(itemView);
            Img = (ImageView)itemView.findViewById(R.id.img);
        }
    }
}


