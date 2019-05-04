package blackcap.nationalescape.Uitility;

import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 박효근 on 2018-08-30.
 */

public class HttpClient_auth {
    public String HttpClient_auth(String Web, String Jsp, String... params){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";
        try {
            org.apache.http.client.HttpClient client = new DefaultHttpClient();
            String postURL = "https://testapi.open-platform.or.kr/oauth/2.0/token";
            HttpPost post = new HttpPost(postURL);
            post.setHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();

            params1.add(new BasicNameValuePair("client_id", "abcdtyy456@naver.com"));
            params1.add(new BasicNameValuePair("client_secret", "0f5b3d2c20a240a6bdb30fc83e43845b"));
            params1.add(new BasicNameValuePair("scope", "oob"));
            params1.add(new BasicNameValuePair("grant_type", "client_credentials"));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params1, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String line = null;
            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }
}
