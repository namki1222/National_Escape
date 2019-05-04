package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import blackcap.nationalescape.Model.Price_Model;
import blackcap.nationalescape.R;

public class Price_Write_Adapter extends RecyclerView.Adapter<Price_Write_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Price_Model> arrData;
    private boolean favorite = false;

    public Price_Write_Adapter(Context c, ArrayList<Price_Model> arr) {
        this.context = c;
        this.arrData = arr;

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Price_Model items = arrData.get(position);
        holder.Edit_Price.setText(items.getPrice());
        holder.Edit_Title.setText(items.getTitle());
        holder.Img_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrData.remove(position);
                notifyItemRemoved(position);
            }
        });
        holder.Edit_Price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                items.setPrice(holder.Edit_Price.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.Edit_Title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                items.setTitle(holder.Edit_Title.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText Edit_Title, Edit_Price;
        ImageView Img_Delete;
        public ViewHolder(final View itemView) {
            super(itemView);
            Edit_Title = (EditText)itemView.findViewById(R.id.edit_menu);
            Edit_Price = (EditText)itemView.findViewById(R.id.edit_price);
            Img_Delete = (ImageView)itemView.findViewById(R.id.img_delete);
        }
    }
}
