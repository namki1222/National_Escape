package blackcap.nationalescape.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import blackcap.nationalescape.Model.Location_Model;
import blackcap.nationalescape.R;

import static blackcap.nationalescape.Activity.Fragment_main2.List_Searchlist;
import static blackcap.nationalescape.Activity.Fragment_main2.frag2_address_y;
import static blackcap.nationalescape.Activity.Fragment_main2.frag2_address_x;

public class Location_Adapter extends RecyclerView.Adapter<Location_Adapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Location_Model> arrData;

    private MapView mapView;
    public Location_Adapter(Context c, ArrayList<Location_Model> arr, MapView mapView) {
        this.context = c;
        this.arrData = arr;
        this.mapView = mapView;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_searchlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Location_Model items = arrData.get(position);
        try {
            //공지 타이틀
            holder.Txt_Title.setText(items.getTitle());
            //공지 날짜
        } catch (Exception e){

        }

    }
    @Override

    public int getItemCount() {
        return this.arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Txt_Title;
        public ViewHolder(final View itemView) {
            super(itemView);
            Txt_Title = (TextView)itemView.findViewById(R.id.txt_title);
            Txt_Title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frag2_address_x = arrData.get(getAdapterPosition()).getGps_x();
                    frag2_address_y = arrData.get(getAdapterPosition()).getGps_y();
                    setMap(arrData.get(getAdapterPosition()).getGps_x(), arrData.get(getAdapterPosition()).getGps_y());
                    List_Searchlist.setVisibility(View.GONE);
                }
            });
        }

    }
    public void setMap(String x, String y){
        //중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(x), Double.parseDouble(y)), true);
        //줌 레벨 변경
        //mapView.setZoomLevel(-5, true);
        //줌 인
        mapView.zoomIn(true);
        //줌 아웃
        mapView.zoomOut(true);

        mapView.setClickable(false);
    }
}

