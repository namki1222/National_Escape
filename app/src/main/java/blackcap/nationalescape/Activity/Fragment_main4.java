package blackcap.nationalescape.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import blackcap.nationalescape.Activity.tab4.Company_Favorite;
import blackcap.nationalescape.Activity.tab4.Manager;
import blackcap.nationalescape.Activity.tab4.Manager_Modify;
import blackcap.nationalescape.Activity.tab4.Notice;
import blackcap.nationalescape.Activity.tab4.Recommend;
import blackcap.nationalescape.Activity.tab4.Review_MyReview;
import blackcap.nationalescape.Activity.tab4.Review_Search;
import blackcap.nationalescape.Activity.tab4.Setting;
import blackcap.nationalescape.Activity.tab4.Theme_Favority;
import blackcap.nationalescape.Activity.user.Login;
import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

import static blackcap.nationalescape.Activity.MainActivity.User_Pk;

public class Fragment_main4 extends android.support.v4.app.Fragment {
    private TextView Txt_Nickname;
    LinearLayout Layout_Setting, Layout_Notice, Layout_Suggest, Layout_Company_Favorite , Layout_Theme_Favorite, Layout_MyReview, Layout_SearchReview;
    public static LinearLayout ta4_Layout_company_register, ta4_Layout_company_manage;
    public static String ta4_company_status = "";
    public String str_user_status = "";
    private String Str_Nickname = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main4, container, false);

        //변수 초기화
        init(rootView);
        Async async = new Async();
        async.execute(User_Pk);
        return rootView;
    }
    public void init(View rootView){
        Txt_Nickname = (TextView)rootView.findViewById(R.id.txt_nickname);
        Layout_Setting = (LinearLayout)rootView.findViewById(R.id.layout_setting);
        Layout_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_Pk.equals(".")){
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    Intent intent = new Intent(getActivity(), Setting.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
        Layout_MyReview = (LinearLayout)rootView.findViewById(R.id.layout_myreview);
        Layout_MyReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_Pk.equals(".")){
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    Intent intent = new Intent(getActivity(), Review_MyReview.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
        Layout_SearchReview = (LinearLayout)rootView.findViewById(R.id.layout_searchReview);
        Layout_SearchReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Review_Search.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Layout_Company_Favorite = (LinearLayout)rootView.findViewById(R.id.layout_company_favorite);
        Layout_Company_Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_Pk.equals(".")){
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    Intent intent = new Intent(getActivity(), Company_Favorite.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
        Layout_Theme_Favorite = (LinearLayout)rootView.findViewById(R.id.layout_theme_favorite);
        Layout_Theme_Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_Pk.equals(".")){
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    Intent intent = new Intent(getActivity(), Theme_Favority.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
        Layout_Suggest = (LinearLayout)rootView.findViewById(R.id.layout_suggest);
        Layout_Suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Recommend.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        Layout_Notice = (LinearLayout)rootView.findViewById(R.id.layout_notice);
        Layout_Notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notice.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        ta4_Layout_company_register = (LinearLayout)rootView.findViewById(R.id.layout_company_register);
        ta4_Layout_company_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_Pk.equals(".")){
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    Intent intent = new Intent(getActivity(), Manager.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
        ta4_Layout_company_manage = (LinearLayout)rootView.findViewById(R.id.layout_company_manage);
        ta4_Layout_company_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_Pk.equals(".")){
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else{
                    if(ta4_company_status.equals("check")){
                        Toast.makeText(getActivity(), "관리자 승인 중입니다", Toast.LENGTH_SHORT).show();
                    }
                    else if(ta4_company_status.equals("approval")){
                        Intent intent = new Intent(getActivity(), Manager_Modify.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                }
            }
        });
        Txt_Nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(User_Pk.equals(".")){
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;

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
                //찜한 업체 리스트 데이터 셋팅
                HttpClient http = new HttpClient();
                JsonParserList jsonParserList = new JsonParserList();
                ta4_company_status = http.HttpClient("Web_Escape", "User_Company_Exist.jsp", User_Pk);

                if(User_Pk.equals(".")){
                    Str_Nickname = "로그인이 필요합니다.";
                }
                else{
                    String result = http.HttpClient("Web_Escape", "User.jsp",User_Pk);
                    parseredData = jsonParserList.jsonParserList_Data6(result);

                    Str_Nickname = parseredData[0][2];
                }
                str_user_status = http.HttpClient("Web_Escape", "User_Ceo.jsp", User_Pk);

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //닉네임 셋팅
            Txt_Nickname.setText(Str_Nickname);

            if(str_user_status.equals("user") || User_Pk.equals(".")){
                ta4_Layout_company_register.setVisibility(View.GONE);
                ta4_Layout_company_manage.setVisibility(View.GONE);
            }
            else{
                //매장관리 셋팅
                if(ta4_company_status.equals("not exist")){
                    ta4_Layout_company_register.setVisibility(View.VISIBLE);
                    ta4_Layout_company_manage.setVisibility(View.GONE);
                }
                else if(ta4_company_status.equals("wait")){
                    ta4_Layout_company_register.setVisibility(View.VISIBLE);
                    ta4_Layout_company_manage.setVisibility(View.GONE);
                }
                else if(ta4_company_status.equals("check")){
                    ta4_Layout_company_register.setVisibility(View.GONE);
                    ta4_Layout_company_manage.setVisibility(View.VISIBLE);
                }
                else if(ta4_company_status.equals("approval")){
                    ta4_Layout_company_register.setVisibility(View.GONE);
                    ta4_Layout_company_manage.setVisibility(View.VISIBLE);
                }
                else{
                    ta4_Layout_company_register.setVisibility(View.VISIBLE);
                    ta4_Layout_company_manage.setVisibility(View.GONE);
                }
            }
            progressDialog.dismiss();
        }
    }
}



