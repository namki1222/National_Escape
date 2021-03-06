package blackcap.nationalescape.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import blackcap.nationalescape.Activity.tab1.Home_Focus;
import blackcap.nationalescape.Adapter.Location_Adapter;
import blackcap.nationalescape.Model.Company_Model;
import blackcap.nationalescape.Model.Location_Model;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.GpsInfo;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

import static blackcap.nationalescape.Activity.MainActivity.filter_sort;

public class Fragment_main2 extends Fragment implements MapView.POIItemEventListener{
    boolean view = false;

    private MapView mapView;
    private ViewGroup mapViewContainer;
    private GpsInfo gps;
    String gps_x = "", gps_y = "";

    private List<Location_Model> location_list;
    private ArrayList<Location_Model> location_models;
    private Location_Adapter location_adapter;

    public static RecyclerView List_Searchlist;
    private EditText Edit_Search;

    private ImageView Img_Refresh;
    private LinearLayout Layout_Company;
    private TextView Txt_Title, Txt_Grageavg, Txt_Distance, Txt_Recommend, Txt_Contents;
    private ImageView Img_Company;

    ArrayList<Company_Model> company_models;
    private static View parent;
    private String[][] parseredData_company;
    private String Comapny_Pk = "", Title = "", Grade_Avg = "", Recommend_Count = "", Distance = "", Intro = "", Address = "";
    public static String frag2_address_x = "", frag2_address_y = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
        parent = rootView;
        //?????? ?????????
        init(rootView);
        //?????? ??? ????????? ??? ?????? ??? ???
        Async async = new Async();
        async.execute();
        //????????? ????????? ??? ?????????
        setSearch_Event();
        //?????? ????????? ?????????
        //setMap_Refresh();
        view = true;
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(view){

        }
        //?????? ????????? ?????? ??? ??????
        else{
            if(getActivity() != null){
                if(mapView == null){
                    mapView = new MapView(getActivity());
                    mapView.setPOIItemEventListener(this);
                    mapViewContainer = (ViewGroup)parent.findViewById(R.id.map_view);
                    mapViewContainer.addView(mapView);

                    Async_reload async_reload = new Async_reload();
                    async_reload.execute();
                }
            }
        }
        view = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewContainer.removeView(mapView);
        mapViewContainer.removeAllViews();
        mapView = null;
        view = false;
    }
    public void init(View rootView){
        gps = new GpsInfo(getActivity());
        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup)rootView.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setPOIItemEventListener(this);
        List_Searchlist = (RecyclerView)rootView.findViewById(R.id.list_searchlist);
        List_Searchlist.setVisibility(View.GONE);
        Edit_Search = (EditText)rootView.findViewById(R.id.edit_search);

        Img_Refresh = (ImageView)rootView.findViewById(R.id.img_refresh);
        Layout_Company = (LinearLayout)rootView.findViewById(R.id.layout_company);
        Layout_Company.setVisibility(View.GONE);

        Img_Company = (ImageView)rootView.findViewById(R.id.img_company);
        Txt_Title = (TextView)rootView.findViewById(R.id.txt_title);
        Txt_Grageavg = (TextView)rootView.findViewById(R.id.txt_grageavg);
        Txt_Distance = (TextView)rootView.findViewById(R.id.txt_distance);
        Txt_Recommend = (TextView)rootView.findViewById(R.id.txt_recommend);
        Txt_Contents = (TextView)rootView.findViewById(R.id.txt_contents);

        location_list = new ArrayList<Location_Model>();
    }
    public void setSearch_Event(){
        Edit_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input?????? ????????? ?????????????????? ????????????.
                // search ???????????? ????????????.
                String text = Edit_Search.getText().toString();
                search(text);
            }
        });
    }
    public void search(String charText) {

        // ?????? ??????????????? ???????????? ????????? ?????? ????????????.
        location_list.clear();

        // ?????? ????????? ???????????? ?????? ???????????? ????????????.
        if (charText.length() == 0) {
            location_list.clear();
            List_Searchlist.setVisibility(View.GONE);
        }
        // ?????? ????????? ??????..
        else {
            List_Searchlist.setVisibility(View.VISIBLE);
            // ???????????? ?????? ???????????? ????????????.
            for (int i = 0; i < location_models.size(); i++) {
                // arraylist??? ?????? ???????????? ???????????? ??????(charText)??? ???????????? ????????? true??? ????????????.
                if (location_models.get(i).getTitle().toLowerCase().contains(charText)) {
                    // ????????? ???????????? ???????????? ????????????.
                    location_list.add(location_models.get(i));
                }
            }
        }
        if(location_adapter != null){
            location_adapter.notifyDataSetChanged();
        }
        // ????????? ???????????? ????????????????????? ???????????? ???????????? ????????? ???????????? ????????? ????????????.

    }
    public void setMap(String x, String y){
        //????????? ??????
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(x), Double.parseDouble(y)), true);
        //??? ?????? ??????
        //mapView.setZoomLevel(-5, true);
        //??? ???
        mapView.zoomIn(true);
        //??? ??????
        mapView.zoomOut(true);

    }
    public void setMap_Refresh(){
        Img_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gps.isGetLocation()) {
                    gps_x = Double.toString(gps.getLatitude());
                    gps_y = Double.toString(gps.getLongitude());
                } else {
                    // GPS ??? ???????????? ????????????
                    gps_x = 37.497942+"";
                    gps_y = 127.0254323+"";
                }
                setMap(gps_x, gps_y);

            }
        });
    }
    public void setMap_This(int position, String pk, String title, String x, String y){
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("?????? ??????");
        marker.setTag(-1);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(x), Double.parseDouble(y)));
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // ??????????????? ????????? ????????? ??????.
        marker.setCustomImageResourceId(R.drawable.map_now); // ?????? ?????????.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setCustomSelectedImageResourceId(R.drawable.map_now);
        marker.setCustomImageAutoscale(false); // hdpi, xhdpi ??? ??????????????? ???????????? ???????????? ????????? ?????? ?????? ?????????????????? ????????? ????????? ??????.
        marker.setCustomImageAnchor(0.5f, 1.0f);
        mapView.addPOIItem(marker);
        mapView.selectPOIItem(marker, true);
    }

    public void setMap_Marker(int position, String pk, String title, String x, String y){
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(title);
        marker.setTag(position);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(x), Double.parseDouble(y)));
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // ??????????????? ????????? ????????? ??????.
        marker.setCustomImageResourceId(R.drawable.map_marker_gray); // ?????? ?????????.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setCustomSelectedImageResourceId(R.drawable.map_marker);
        marker.setCustomImageAutoscale(false); // hdpi, xhdpi ??? ??????????????? ???????????? ???????????? ????????? ?????? ?????? ?????????????????? ????????? ????????? ??????.
        marker.setCustomImageAnchor(0.5f, 1.0f);
        mapView.addPOIItem(marker);
        mapView.selectPOIItem(marker, true);
    }

    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData_location;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(getActivity(),"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //?????? ?????? ????????????
            try {
                if (gps.isGetLocation()) {
                    gps_x = Double.toString(gps.getLatitude());
                    gps_y = Double.toString(gps.getLongitude());
                } else {
                    // GPS ??? ???????????? ????????????
                    //gps.showSettingsAlert();
                    gps_x = 37.497942+"";
                    gps_y = 127.0254323+"";
                }


                HttpClient http = new HttpClient();
                //?????? ?????? ????????????
                String result = http.HttpClient("Web_Escape", "Tab2_Location.jsp");
                parseredData_location = jsonParserList(result);
                //?????? ????????????
                String result_company = http.HttpClient("Web_Escape", "Home_List.jsp", gps_x, gps_y, ".", "??????", filter_sort);
                parseredData_company = jsonParserList_company(result_company);

                for (int i = 0; i < parseredData_location.length; i++) {
                    String title = parseredData_location[i][0];
                    String gps_x = parseredData_location[i][1];
                    String gps_y = parseredData_location[i][2];
                    location_list.add(new Location_Model(title, gps_x, gps_y));
                }

                location_models = new ArrayList<Location_Model>();
                location_models.addAll(location_list);

                company_models = new ArrayList<Company_Model>();
                for (int i = 0; i < parseredData_company.length; i++) {
                    String company_pk = parseredData_company[i][0];
                    String owner_pk = parseredData_company[i][1];
                    String title = parseredData_company[i][2];
                    String intro = parseredData_company[i][3];
                    String grade_avg = parseredData_company[i][4];
                    String recommend_count = parseredData_company[i][5];
                    String address = parseredData_company[i][6];
                    String distance = parseredData_company[i][7];
                    String favorite = parseredData_company[i][8];
                    String address_x = parseredData_company[i][9];
                    String address_y = parseredData_company[i][10];
                    company_models.add(new Company_Model(getActivity(),company_pk, owner_pk, title, intro, grade_avg, recommend_count, address, distance, favorite, "false"));
                    setMap_Marker(i,company_pk, title, address_x, address_y);
                }

                setMap_This(0, "1", "t", gps_x, gps_y);
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //setMap(gps_x, gps_y);
            gps.stopUsingGPS();

            //????????? ??? ?????? ????????????
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);

            //?????? ????????? ??????
            location_adapter = new Location_Adapter(getActivity(), (ArrayList<Location_Model>) location_list, mapView);
            List_Searchlist.setLayoutManager(layoutManager);
            List_Searchlist.setAdapter(location_adapter);

            //?????? ????????? ?????????
            location_list.clear();
            location_adapter.notifyDataSetChanged();
            setMap(gps_x, gps_y);
            progressDialog.dismiss();
        }
    }
    public class Async_reload extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(getActivity(),"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //?????? ?????? ????????????
            try {
                if (gps.isGetLocation()) {
                    gps_x = Double.toString(gps.getLatitude());
                    gps_y = Double.toString(gps.getLongitude());
                } else {
                    // GPS ??? ???????????? ????????????
                    gps_x = 37.497942+"";
                    gps_y = 127.0254323+"";
                }

                //?????? ??? ????????? ??? ?????? ??? ???
                company_models.clear();
                for (int i = 0; i < parseredData_company.length; i++) {
                    String company_pk = parseredData_company[i][0];
                    String owner_pk = parseredData_company[i][1];
                    String title = parseredData_company[i][2];
                    String intro = parseredData_company[i][3];
                    String grade_avg = parseredData_company[i][4];
                    String recommend_count = parseredData_company[i][5];
                    String address = parseredData_company[i][6];
                    String distance = parseredData_company[i][7];
                    String favorite = parseredData_company[i][8];
                    String address_x = parseredData_company[i][9];
                    String address_y = parseredData_company[i][10];
                    company_models.add(new Company_Model(getActivity(),company_pk, owner_pk, title, intro, grade_avg, recommend_count, address, distance, favorite, "false"));
                    setMap_Marker(i,company_pk, title, address_x, address_y);
                }

                setMap_This(0, "1", "t", gps_x, gps_y);
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            setMap_Refresh();
            gps.stopUsingGPS();

            progressDialog.dismiss();
        }
    }
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("???????????? ?????? ?????? ??????", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3"};
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

    public String[][] jsonParserList_company(String pRecvServerPage) {
        Log.i("???????????? ?????? ?????? ?????? ?????????", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3","msg4", "msg5", "msg6","msg7", "msg8", "msg9","msg10", "msg11"};
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

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        if(mapPOIItem.getTag() != -1){
            Layout_Company.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams plControl = (RelativeLayout.LayoutParams) Img_Refresh.getLayoutParams();
            /*?????? margin??? ??????*/
            plControl.bottomMargin = 450;
            /*????????? ?????? ??????????????? ?????? ???????????? ???????????? ?????? ??????*/
            Img_Refresh.setLayoutParams(plControl);

            Comapny_Pk = company_models.get(mapPOIItem.getTag()+0).getCompany_Pk();
            Title = company_models.get(mapPOIItem.getTag()+0).getTitle();
            Grade_Avg = company_models.get(mapPOIItem.getTag()+0).getGrade_Avg();
            Distance = company_models.get(mapPOIItem.getTag()+0).getDistance();
            Recommend_Count = company_models.get(mapPOIItem.getTag()+0).getRecommend_Count();
            Intro = company_models.get(mapPOIItem.getTag()+0).getIntro();
            Address = company_models.get(mapPOIItem.getTag()+0).getAddress();
            frag2_address_x = mapView.getX()+"";
            frag2_address_y = mapView.getY()+"";
            Glide.with(getActivity()).load("http://www.yologuys.com/Escape_img/company/"+(Comapny_Pk)+".jpg").apply(new RequestOptions().placeholder(R.drawable.tab2_company_basic).centerCrop())
                    .into(Img_Company);

            Txt_Title.setText(Title);
            Txt_Distance.setText(Distance);
            Txt_Grageavg.setText(Grade_Avg);
            Txt_Recommend.setText(Recommend_Count);
            Txt_Contents.setText(Intro);

            Layout_Company.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Home_Focus.class);
                    intent.putExtra("Company_Pk", Comapny_Pk);
                    intent.putExtra("Title", Title);
                    intent.putExtra("Grade_Avg", Grade_Avg);
                    intent.putExtra("Recommend_Count", Recommend_Count);
                    intent.putExtra("Distance", Distance);
                    intent.putExtra("Intro", Intro);
                    intent.putExtra("Address", Address);
                    startActivity(intent);

                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            });
        }
    }
    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }
}
