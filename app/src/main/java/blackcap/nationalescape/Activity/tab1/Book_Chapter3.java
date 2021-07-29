package blackcap.nationalescape.Activity.tab1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import blackcap.nationalescape.R;
import blackcap.nationalescape.Uitility.HttpClient;
import blackcap.nationalescape.Uitility.Progressbar_wheel;

import static blackcap.nationalescape.Activity.tab1.Book_Chapter1.activity_book_chapter1;
import static blackcap.nationalescape.Activity.tab1.Book_Chapter2.Activity_Book_Chapter2;
import static blackcap.nationalescape.Activity.tab1.Book_Chapter2.str_book_chapter2_date;

public class Book_Chapter3 extends AppCompatActivity {
    private ImageView Img_Next, Img_Back;
    private TextView Txt_Company, Txt_Theme, Txt_Time;
    private EditText Edit_Name, Edit_Phone1, Edit_Phone2, Edit_Phone3, Edit_Memo;
    // private EditText editText;
    private String str_company_pk = "", str_company_title = "", str_theme_pk = "", str_theme_title = "", str_Time = "";
    private String str_date = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_chapter3);


        Intent intent1 = getIntent();
        str_company_pk = intent1.getStringExtra("Company_Pk");
        str_company_title = intent1.getStringExtra("Company_Title");
        str_theme_pk = intent1.getStringExtra("Theme_Pk");
        str_theme_title = intent1.getStringExtra("Theme_Title");
        str_Time = intent1.getStringExtra("Time");

        init();
        setImgBack();
    }
    public void init(){
        Img_Next = (ImageView)findViewById(R.id.img_next);
        Img_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edit_Name.getText().toString().equals("")){
                    Toast.makeText(Book_Chapter3.this, "예약자를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Edit_Phone1.getText().toString().equals("") || Edit_Phone2.getText().toString().equals("") || Edit_Phone3.getText().toString().equals("")){
                        Toast.makeText(Book_Chapter3.this, "연락처를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String str_phone = Edit_Phone1.getText().toString() + Edit_Phone2.getText().toString() + Edit_Phone3.getText().toString();
                        Async async = new Async();
                        async.execute(str_company_pk, str_theme_pk, Edit_Name.getText().toString(), str_phone, str_book_chapter2_date, str_Time, Edit_Memo.getText().toString());
                    }
                }
            }
        });
        Txt_Company = (TextView) findViewById(R.id.txt_company);
        Txt_Theme = (TextView) findViewById(R.id.txt_theme);
        Txt_Time = (TextView) findViewById(R.id.txt_time);
        Edit_Name = (EditText)findViewById(R.id.edit_name);
        Edit_Phone1 = (EditText)findViewById(R.id.edit_phone1);
        Edit_Phone2 = (EditText)findViewById(R.id.edit_phone2);
        Edit_Phone3 = (EditText)findViewById(R.id.edit_phone3);
        Edit_Memo = (EditText)findViewById(R.id.edit_memo);

        Txt_Company.setText(str_company_title);
        Txt_Theme.setText(str_theme_title);
        Txt_Time.setText(str_Time);

    }

    public class Async extends AsyncTask<String, Void, String> {
        public Progressbar_wheel progressDialog;

        private String str_result = "";
        @Override
        protected void onPreExecute() {
            progressDialog= Progressbar_wheel.show(Book_Chapter3.this,"","",true,true,null);
            progressDialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //현재 좌표 받아오기
            try {
                //유저 정보 response
                HttpClient http = new HttpClient();
                str_result = http.HttpClient("Web_Escape", "Book_Input.jsp",params);

                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(str_result.equals("succed")){
                activity_book_chapter1.finish();
                Activity_Book_Chapter2.finish();
                Intent intent = new Intent(Book_Chapter3.this, Book_Chapter4.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
            else if(str_result.equals("exist")){
                Toast.makeText(Book_Chapter3.this, "이미 예약되었습니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.i("테스트", str_result);
                Toast.makeText(Book_Chapter3.this, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        }
    }
    private void setImgBack() {
        Img_Back = (ImageView)findViewById(R.id.img_back);
        Img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }
}
