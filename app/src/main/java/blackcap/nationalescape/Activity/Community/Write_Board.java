package blackcap.nationalescape.Activity.Community;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Http_Del;
import blackcap.nationalescape.Uitility.Http_Post;
import blackcap.nationalescape.Uitility.Http_Put;
import blackcap.nationalescape.Uitility.JsonParserList;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

import static blackcap.nationalescape.Activity.Fragmetn_community.str_status;
import static blackcap.nationalescape.Activity.MainActivity.User_Pk;

public class Write_Board extends AppCompatActivity {
    private ImageView Img_Next;
    private TextView Txt_Save;
    private EditText Edit_Title, Edit_Contents, Edit_Company, Edit_Theme;
    private ImageView Img_1, Img_2, Img_3, Img_4, Img_5, Img_6;
    private ImageView Img_1_delete, Img_2_delete, Img_3_delete, Img_4_delete, Img_5_delete, Img_6_delete;
    private String str_nickname = "";
    private String str_grade = "";

    private boolean bol_img1 = false;
    private boolean bol_img2 = false;
    private boolean bol_img3 = false;
    private boolean bol_img4 = false;
    private boolean bol_img5 = false;
    private boolean bol_img6 = false;

    public List<String> photos_delete;

    private String str_img_choice = "";
    private String str_img1 = "";
    private String str_img2 = "";
    private String str_img3 = "";
    private String str_img4 = "";
    private String str_img5 = "";
    private String str_img6 = "";

    private String str_img1_id = "";
    private String str_img2_id = "";
    private String str_img3_id = "";
    private String str_img4_id = "";
    private String str_img5_id = "";
    private String str_img6_id = "";

    private String str_board_id = "";
    private String str_category = "";
    private String str_title = "";
    private String str_contents = "";
    private String str_company = "";
    private String str_theme = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comu_write_board);

        setInit();

    }

    public void setInit() {
        Intent intent1 = getIntent();
        str_category = intent1.getStringExtra("category");
        str_board_id = intent1.getStringExtra("id");
        str_title = intent1.getStringExtra("title");
        str_contents = intent1.getStringExtra("contents");
        str_company = intent1.getStringExtra("company");
        str_theme = intent1.getStringExtra("theme");

        str_img1 = intent1.getStringExtra("img1");
        str_img1_id = intent1.getStringExtra("img1_id");
        str_img2 = intent1.getStringExtra("img2");
        str_img2_id = intent1.getStringExtra("img2_id");
        str_img3 = intent1.getStringExtra("img3");
        str_img3_id = intent1.getStringExtra("img3_id");
        str_img4 = intent1.getStringExtra("img4");
        str_img4_id = intent1.getStringExtra("img4_id");
        str_img5 = intent1.getStringExtra("img5");
        str_img5_id = intent1.getStringExtra("img5_id");
        str_img6 = intent1.getStringExtra("img6");
        str_img6_id = intent1.getStringExtra("img6_id");

        photos_delete = new ArrayList<>();

        Txt_Save = (TextView)findViewById(R.id.txt_save);
        Edit_Title = (EditText)findViewById(R.id.edit_title);
        Edit_Contents = (EditText)findViewById(R.id.edit_contents);
        Edit_Company = (EditText)findViewById(R.id.edit_company);
        Edit_Theme = (EditText)findViewById(R.id.edit_theme);

        setImg();
        if(str_category.equals("??????")){
            Edit_Title.setText(str_title);
            Edit_Contents.setText(str_contents);
            Edit_Company.setText(str_company);
            Edit_Theme.setText(str_theme);

            if(str_img1 != null){
                Glide.with(Write_Board.this).load("https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img1)
                        .into(Img_1);
                Img_1_delete.setVisibility(View.VISIBLE);

                str_img1 = "https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img1;
            }
            if(str_img2 != null){
                Glide.with(Write_Board.this).load("https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img2)
                        .into(Img_2);
                Img_2_delete.setVisibility(View.VISIBLE);

                str_img2 = "https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img2;
            }
            if(str_img3 != null){
                Glide.with(Write_Board.this).load("https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img3)
                        .into(Img_3);
                Img_3_delete.setVisibility(View.VISIBLE);

                str_img3 = "https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img3;
            }
            if(str_img4 != null){
                Glide.with(Write_Board.this).load("https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img4)
                        .into(Img_4);
                Img_4_delete.setVisibility(View.VISIBLE);

                str_img4 = "https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img4;
            }
            if(str_img5 != null){
                Glide.with(Write_Board.this).load("https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img5)
                        .into(Img_5);
                Img_5_delete.setVisibility(View.VISIBLE);

                str_img5 = "https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img5;
            }
            if(str_img6 != null){
                Glide.with(Write_Board.this).load("https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img6)
                        .into(Img_6);
                Img_6_delete.setVisibility(View.VISIBLE);

                str_img6 = "https://d35jysenqmci34.cloudfront.net/fit-in/300x300/"+str_img6;
            }
        }
        else{

        }

        Txt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edit_Company.getText().toString().equals("")){
                    Toast.makeText(Write_Board.this, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Edit_Theme.getText().toString().equals("")){
                        Toast.makeText(Write_Board.this, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(Edit_Contents.getText().toString().equals("")){
                            Toast.makeText(Write_Board.this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(str_category.equals("??????")){
                                Write_Board.Async_modi async_modi = new Write_Board.Async_modi();
                                async_modi.execute(Edit_Company.getText().toString(), Edit_Theme.getText().toString(), Edit_Contents.getText().toString());
                            }else{
                                Write_Board.Async async = new Write_Board.Async();
                                async.execute(str_nickname, str_grade, Edit_Company.getText().toString(), Edit_Theme.getText().toString(), Edit_Contents.getText().toString());
                            }
                        }
                    }
                }
            }
        });

        Img_Next = (ImageView)findViewById(R.id.img_back);
        Img_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });

        Write_Board.Async_User async_user = new Write_Board.Async_User();
        async_user.execute();
    }
    public void setImg(){
        Img_1 = (ImageView)findViewById(R.id.img_1);
        Img_2 = (ImageView)findViewById(R.id.img_2);
        Img_3 = (ImageView)findViewById(R.id.img_3);
        Img_4 = (ImageView)findViewById(R.id.img_4);
        Img_5 = (ImageView)findViewById(R.id.img_5);
        Img_6 = (ImageView)findViewById(R.id.img_6);

        Img_1_delete = (ImageView)findViewById(R.id.img_1_delete);
        Img_2_delete = (ImageView)findViewById(R.id.img_2_delete);
        Img_3_delete = (ImageView)findViewById(R.id.img_3_delete);
        Img_4_delete = (ImageView)findViewById(R.id.img_4_delete);
        Img_5_delete = (ImageView)findViewById(R.id.img_5_delete);
        Img_6_delete = (ImageView)findViewById(R.id.img_6_delete);

        Img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//?????? ?????????????????? uri ????????????.
                Uri uri = Uri.parse("content://media/external/images/media");
                //????????? ??????????????? ????????? ????????? ?????? ????????????.
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //???????????? ????????? ????????????.
                intent.setAction(Intent.ACTION_PICK);
                //?????? ?????????
                intent.setType("image/*");
                //???????????? ???????????? ??????????????? ????????????.
                startActivityForResult(intent, 0);
                bol_img1 = true;
                str_img_choice = "img1";
            }
        });

        Img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//?????? ?????????????????? uri ????????????.
                Uri uri = Uri.parse("content://media/external/images/media");
                //????????? ??????????????? ????????? ????????? ?????? ????????????.
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //???????????? ????????? ????????????.
                intent.setAction(Intent.ACTION_PICK);
                //?????? ?????????
                intent.setType("image/*");
                //???????????? ???????????? ??????????????? ????????????.
                startActivityForResult(intent, 0);
                bol_img2 = true;
                str_img_choice = "img2";
            }
        });

        Img_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//?????? ?????????????????? uri ????????????.
                Uri uri = Uri.parse("content://media/external/images/media");
                //????????? ??????????????? ????????? ????????? ?????? ????????????.
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //???????????? ????????? ????????????.
                intent.setAction(Intent.ACTION_PICK);
                //?????? ?????????
                intent.setType("image/*");
                //???????????? ???????????? ??????????????? ????????????.
                startActivityForResult(intent, 0);
                bol_img3 = true;
                str_img_choice = "img3";
            }
        });

        Img_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//?????? ?????????????????? uri ????????????.
                Uri uri = Uri.parse("content://media/external/images/media");
                //????????? ??????????????? ????????? ????????? ?????? ????????????.
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //???????????? ????????? ????????????.
                intent.setAction(Intent.ACTION_PICK);
                //?????? ?????????
                intent.setType("image/*");
                //???????????? ???????????? ??????????????? ????????????.
                startActivityForResult(intent, 0);
                bol_img4 = true;
                str_img_choice = "img4";
            }
        });

        Img_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//?????? ?????????????????? uri ????????????.
                Uri uri = Uri.parse("content://media/external/images/media");
                //????????? ??????????????? ????????? ????????? ?????? ????????????.
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //???????????? ????????? ????????????.
                intent.setAction(Intent.ACTION_PICK);
                //?????? ?????????
                intent.setType("image/*");
                //???????????? ???????????? ??????????????? ????????????.
                startActivityForResult(intent, 0);
                bol_img5 = true;
                str_img_choice = "img5";
            }
        });

        Img_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//?????? ?????????????????? uri ????????????.
                Uri uri = Uri.parse("content://media/external/images/media");
                //????????? ??????????????? ????????? ????????? ?????? ????????????.
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //???????????? ????????? ????????????.
                intent.setAction(Intent.ACTION_PICK);
                //?????? ?????????
                intent.setType("image/*");
                //???????????? ???????????? ??????????????? ????????????.
                startActivityForResult(intent, 0);
                bol_img6 = true;
                str_img_choice = "img6";
            }
        });

        Img_1_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_img1.contains("http")){
                    photos_delete.add(str_img1_id);
                }

                str_img1 = "";
                Img_1.setImageResource(R.drawable.comu_img_basic);
                Img_1_delete.setVisibility(View.GONE);
            }
        });

        Img_2_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_img2.contains("http")){
                    photos_delete.add(str_img2_id);
                }

                str_img2 = "";
                Img_2.setImageResource(R.drawable.comu_img_basic);
                Img_2_delete.setVisibility(View.GONE);
            }
        });

        Img_3_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_img3.contains("http")){
                    photos_delete.add(str_img3_id);
                }

                str_img3 = "";
                Img_3.setImageResource(R.drawable.comu_img_basic);
                Img_3_delete.setVisibility(View.GONE);
            }
        });

        Img_4_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_img4.contains("http")){
                    photos_delete.add(str_img4_id);
                }

                str_img4 = "";
                Img_4.setImageResource(R.drawable.comu_img_basic);
                Img_4_delete.setVisibility(View.GONE);
            }
        });

        Img_5_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_img5.contains("http")){
                    photos_delete.add(str_img5_id);
                }

                str_img5 = "";
                Img_5.setImageResource(R.drawable.comu_img_basic);
                Img_5_delete.setVisibility(View.GONE);
            }
        });

        Img_6_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_img6.contains("http")){
                    photos_delete.add(str_img6_id);
                }

                str_img6 = "";
                Img_6.setImageResource(R.drawable.comu_img_basic);
                Img_6_delete.setVisibility(View.GONE);
            }
        });
    }
    public class Async_User extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[][] parseredData;
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Write_Board.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //?????? ?????? ????????????
            try {
                //?????? ?????? response
                HttpClient http = new HttpClient();
                String result = http.HttpClient("Web_Escape", "User_v4.jsp",User_Pk, "yologuys12");

                JsonParserList jsonParserList = new JsonParserList();
                parseredData = jsonParserList.jsonParserList_Data14(result);

                str_nickname = parseredData[0][2];
                str_grade = parseredData[0][7];

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }
    }
    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[] parseredData;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Write_Board.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //?????? ?????? ????????????
            try {
                String result = "";
                //????????? ??????
                Http_Post http = new Http_Post();
                result = http.Http_Post(User_Pk, "v1/board/register", "diff","????????? ?????????", "user_pk", User_Pk, "nickname", params[0], "grade", params[1], "company_name", params[2],  "theme_name", params[3], "title", " ", "content", params[4]);

                parseredData = jsonParserList_access(result);

                if(parseredData[0].equals("success")){
                    String[] parsed = jsonParserList_access1(result);
                    String[] parsed_info = jsonParserList_info(parsed[1]);
                    String boardId = parsed_info[0];
                    Log.i("?????????1", boardId);
                    if(!str_img1.equals("")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + boardId, str_img1);
                    }
                    if(!str_img2.equals("")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + boardId, str_img2);
                    }
                    if(!str_img3.equals("")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + boardId, str_img3);
                    }
                    if(!str_img4.equals("")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + boardId, str_img4);
                    }
                    if(!str_img5.equals("")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + boardId, str_img5);
                    }
                    if(!str_img6.equals("")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + boardId, str_img6);
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

            if(parseredData[0].equals("success")){
                str_status = "board_re";
                Toast.makeText(Write_Board.this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
            else{
                Toast.makeText(Write_Board.this, parseredData[0], Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        }
    }

    public class Async_modi extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        String[] parseredData;

        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Write_Board.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //?????? ?????? ????????????
            try {
                String result = "";
                //????????? ??????
                Http_Put http = new Http_Put();
                result = http.Http_Put(User_Pk, "v1/board/update?id="+str_board_id, "company_name", params[0], "theme_name", params[1], "content", params[2]);

                parseredData = jsonParserList_access(result);

                if(parseredData[0].equals("success")){
                    for(int i = 0; i<photos_delete.size(); i++){
                        Http_Del http_del = new Http_Del();
                        String result1 = http_del.Http_Del(User_Pk, "v1/board/remove?id="+photos_delete.get(i));
                    }

                    if(!str_img1.equals("") && !str_img1.contains("http")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + str_board_id, str_img1);
                    }
                    if(!str_img2.equals("") && !str_img2.contains("http")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + str_board_id, str_img2);
                    }
                    if(!str_img3.equals("") && !str_img3.contains("http")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + str_board_id, str_img3);
                    }
                    if(!str_img4.equals("") && !str_img4.contains("http")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + str_board_id, str_img4);
                    }
                    if(!str_img5.equals("") && !str_img5.contains("http")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + str_board_id, str_img5);
                    }
                    if(!str_img6.equals("") && !str_img6.contains("http")){
                        HttpFileUpload("http://52.78.226.88:33667/v1/boardImage/register?boardId=" + str_board_id, str_img6);
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

            if(parseredData[0].equals("success")){
                str_status = "board_re";
                Toast.makeText(Write_Board.this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
            else{
                Toast.makeText(Write_Board.this, parseredData[0], Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        }
    }
    public String[] jsonParserList_access(String pRecvServerPage) {
        Log.i("???????????? ?????? ?????? ??????", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);

            String[] jsonName = {"message"};
            String[] parseredData = new String[jsonName.length];
            for (int j = 0; j < jsonName.length; j++) {
                parseredData[j] = json.getString(jsonName[j]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] jsonParserList_access1(String pRecvServerPage) {
        Log.i("???????????? ?????? ?????? ??????", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);

            String[] jsonName = {"message", "data"};
            String[] parseredData = new String[jsonName.length];
            for (int j = 0; j < jsonName.length; j++) {
                parseredData[j] = json.getString(jsonName[j]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] jsonParserList_info(String pRecvServerPage) {
        Log.i("???????????? ?????? ?????? ??????", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);

            String[] jsonName = {"id"};
            String[] parseredData = new String[jsonName.length];
            for (int j = 0; j < jsonName.length; j++) {
                parseredData[j] = json.getString(jsonName[j]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            //???????????? ???????????? ?????? ?????????
            if (!intent.getData().equals(null)) {
                //??????????????? ???????????? intent??? ?????? ????????? uri??? ???????????? Bitmap????????? ????????????.
                Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());

                //???????????? ?????? ????????????.
                selPhoto = Bitmap.createScaledBitmap(selPhoto, 100, 100, true);
                //image_bt.setImageBitmap(selPhoto);//?????????
                //????????? ???????????????.
                if(str_img_choice.equals("img1")){
                    Img_1.setImageBitmap(selPhoto);
                    Img_1_delete.setVisibility(View.VISIBLE);
                }
                else if(str_img_choice.equals("img2")){
                    Img_2.setImageBitmap(selPhoto);
                    Img_2_delete.setVisibility(View.VISIBLE);
                }
                else if(str_img_choice.equals("img3")){
                    Img_3.setImageBitmap(selPhoto);
                    Img_3_delete.setVisibility(View.VISIBLE);
                }
                else if(str_img_choice.equals("img4")){
                    Img_4.setImageBitmap(selPhoto);
                    Img_4_delete.setVisibility(View.VISIBLE);
                }
                else if(str_img_choice.equals("img5")){
                    Img_5.setImageBitmap(selPhoto);
                    Img_5_delete.setVisibility(View.VISIBLE);
                }
                else if(str_img_choice.equals("img6")){
                    Img_6.setImageBitmap(selPhoto);
                    Img_6_delete.setVisibility(View.VISIBLE);
                }

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //????????? ???????????? uri??? ????????????.
                Uri selPhotoUri = intent.getData();
                Log.e("??????", "???~~??? ~~~~~!");
                //???????????? ????????? url ??????
                //??????????????? ????????????!!! ??????~
                Cursor c = getContentResolver().query(Uri.parse(selPhotoUri.toString()), null, null, null, null);
                c.moveToNext();
                //???????????? ????????? ???????????? ????????????("_data") ??? ?????? ??????.
                String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                Log.e("###????????? ?????? ??????###", absolutePath);
                if(str_img_choice.equals("img1")){
                    str_img1 = absolutePath;
                }
                else if(str_img_choice.equals("img2")){
                    str_img2 = absolutePath;
                }
                else if(str_img_choice.equals("img3")){
                    str_img3 = absolutePath;
                }
                else if(str_img_choice.equals("img4")){
                    str_img4 = absolutePath;
                }
                else if(str_img_choice.equals("img5")){
                    str_img5 = absolutePath;
                }
                else if(str_img_choice.equals("img6")){
                    str_img6 = absolutePath;
                }
                //setImage_Location(absolutePath, selPhoto);*/
            }
        } catch (FileNotFoundException e) {
            Log.e("tt",e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("tt",e.toString());
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.e("tt",e.toString());
        }
    }

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    public void HttpFileUpload(String urlString, String fileName) {
        try {
            //????????? ????????? ?????? ????????? ???????????? ?????? ?????? ????????? ????????? ????????????.
            FileInputStream mFileInputStream = new FileInputStream(fileName);
            //????????? ???????????? ????????? url ????????????????????? URL ?????? ????????????.
            URL connectUrl = new URL(urlString);
            //Connection ?????? ????????????.
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);//???????????? ?????????
            conn.setDoOutput(true); //???????????? ?????????
            conn.setUseCaches(false);  //?????? ???????????? ??????

           /* Map<String,Object> params = new LinkedHashMap<>(); // ???????????? ??????
            params.put("expertId", str_expert_id);*/

            /*StringBuilder postData = new StringBuilder();
            for(Map.Entry<String,Object> param : params.entrySet()) {
                if(postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            */

            //post ??????
            conn.setRequestMethod("POST");
            //?????? ????????? ?????? ????????? ????????????.
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("authorization", "" + User_Pk);

            //conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            // conn.setDoOutput(true);
            //conn.getOutputStream().write(postDataBytes);

            //DataOutputStream ?????? ????????????.
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            //????????? ???????????? ???????????? ?????????.
            //String En_TeamName = URLEncoder.encode(TeamName, "utf-8");

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + "test1"+".jpg" + "\"" + lineEnd);


            dos.writeBytes(lineEnd);
            //????????? ????????????????????? ???????????? ????????? ????????????.
            int bytesAvailable = mFileInputStream.available();
            //byte????????? ???????????? ????????? byte ?????? ????????? ????????????.
            byte[] buffer = new byte[bytesAvailable];
            int bytesRead = 0;
            // read image
            while (bytesRead != -1) {
                //???????????? ?????????????????? ????????????.
                bytesRead = mFileInputStream.read(buffer);
                if (bytesRead == -1) break; //????????? ?????? ???????????? ????????? ???????????????.
                Log.d("Test", "image byte is " + bytesRead);
                //???????????? ????????????.
                dos.write(buffer, 0, bytesRead);
                //????????? ????????? ????????????
                dos.flush();
            }
            //????????? ???????????? ????????? ?????????.
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            //flush() ???????????
            //dos.flush();
            dos.close();//????????? ????????????
            mFileInputStream.close();//????????? ????????????.
            // get response
            int ch;
            //?????? ????????? ????????? ????????????.
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            Log.e("Test", "result = " + s);

        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
            Log.d("Test", "exception " + e);
        }
    }
}