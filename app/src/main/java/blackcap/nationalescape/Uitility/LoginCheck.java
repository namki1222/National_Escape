package blackcap.nationalescape.Uitility;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by 박효근 on 2018-02-12.
 */

public class LoginCheck extends AppCompatActivity {
    SharedPreferences preferences; //캐쉬 데이터 생성
    public String LoginPk(){
        preferences = getSharedPreferences("blahblah", MODE_PRIVATE);
        String Pk = preferences.getString("Pk", ".");
        return Pk;
    }
}
