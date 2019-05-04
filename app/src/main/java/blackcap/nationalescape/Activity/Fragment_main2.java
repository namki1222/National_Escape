package blackcap.nationalescape.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class Fragment_main2 extends android.support.v4.app.Fragment implements MapView.POIItemEventListener{
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
        parent = rootView;
        //변수 초기화
        init(rootView);
        //서버 값 리스트 뷰 전달 및 뷰
        Async async = new Async();
        async.execute();
        //검색시 리스트 뷰 이벤트
        setSearch_Event();
        //지도 원위치 이벤트
        setMap_Refresh();
        view = true;
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(view){

        }
        //뷰가 포커스 갔다 온 경우
        else{
            mapView = new MapView(getActivity());
            mapView.setPOIItemEventListener(this);
            mapViewContainer = (ViewGroup)parent.findViewById(R.id.map_view);
            mapViewContainer.addView(mapView);

            Async_reload async_reload = new Async_reload();
            async_reload.execute();
        }
        view = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewContainer.removeView(mapView);
        mapViewContainer.removeAllViews();
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
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = Edit_Search.getText().toString();
                search(text);
            }
        });
    }
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        location_list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            location_list.clear();
            List_Searchlist.setVisibility(View.GONE);
        }
        // 문자 입력을 할때..
        else {
            List_Searchlist.setVisibility(View.VISIBLE);
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < location_models.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (location_models.get(i).getTitle().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    location_list.add(location_models.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        location_adapter.notifyDataSetChanged();
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
    }
    public void setMap_Refresh(){
        Img_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gps.isGetLocation()) {
                    gps_x = Double.toString(gps.getLatitude());
                    gps_y = Double.toString(gps.getLongitude());
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
                setMap(gps_x, gps_y);
            }
        });
    }
    public void setMap_Marker(int position, String pk, String title, String x, String y){
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(title);
        marker.setTag(position);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(x), Double.parseDouble(y)));
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
        marker.setCustomImageResourceId(R.drawable.map_marker_gray); // 마커 이미지.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setCustomSelectedImageResourceId(R.drawable.map_marker);
        marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
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
            //현재 좌표 받아오기
            try {
                if (gps.isGetLocation()) {
                    gps_x = Double.toString(gps.getLatitude());
                    gps_y = Double.toString(gps.getLongitude());
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }

                HttpClient http = new HttpClient();
                //장소 좌표 받아오기
                String result = http.HttpClient("Web_Escape", "Tab2_Location.jsp");
                parseredData_location = jsonParserList(result);
                //업체 불러오기
                String result_company = http.HttpClient("Web_Escape", "Home_List.jsp", gps_x, gps_y, ".", "전국", filter_sort);
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
                    company_models.add(new Company_Model(getActivity(),company_pk, owner_pk, title, intro, grade_avg, recommend_count, address, distance, favorite));
                    setMap_Marker(i,company_pk, title, address_x, address_y);
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
            //setMap(gps_x, gps_y);
            gps.stopUsingGPS();

            //리스트 뷰 세로 방향으로
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);

            //검색 리스트 구현
            location_adapter = new Location_Adapter(getActivity(), (ArrayList<Location_Model>) location_list, mapView);
            List_Searchlist.setLayoutManager(layoutManager);
            List_Searchlist.setAdapter(location_adapter);

            //검색 리스트 초기화
            location_list.clear();
            location_adapter.notifyDataSetChanged();

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
            //현재 좌표 받아오기
            try {
                if (gps.isGetLocation()) {
                    gps_x = Double.toString(gps.getLatitude());
                    gps_y = Double.toString(gps.getLongitude());
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }

                //서버 값 리스트 뷰 전달 및 뷰
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
                    company_models.add(new Company_Model(getActivity(),company_pk, owner_pk, title, intro, grade_avg, recommend_count, address, distance, favorite));
                    setMap_Marker(i,company_pk, title, address_x, address_y);
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
            setMap(gps_x, gps_y);
            gps.stopUsingGPS();

            progressDialog.dismiss();
        }
    }
    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
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
        Log.i("서버에서 받은 전체 내용 테스트", pRecvServerPage);
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
        Layout_Company.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams plControl = (RelativeLayout.LayoutParams) Img_Refresh.getLayoutParams();
        /*해당 margin값 변경*/
        plControl.bottomMargin = 450;
        /*변경된 값의 파라미터를 해당 레이아웃 파라미터 값에 셋팅*/
        Img_Refresh.setLayoutParams(plControl);

        Comapny_Pk = company_models.get(mapPOIItem.getTag()+0).getCompany_Pk();
        Title = company_models.get(mapPOIItem.getTag()+0).getTitle();
        Grade_Avg = company_models.get(mapPOIItem.getTag()+0).getGrade_Avg();
        Distance = company_models.get(mapPOIItem.getTag()+0).getDistance();
        Recommend_Count = company_models.get(mapPOIItem.getTag()+0).getRecommend_Count();
        Intro = company_models.get(mapPOIItem.getTag()+0).getIntro();
        Address = company_models.get(mapPOIItem.getTag()+0).getAddress();

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
