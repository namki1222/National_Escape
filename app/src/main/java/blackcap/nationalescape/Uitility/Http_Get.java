package blackcap.nationalescape.Uitility;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Http_Get {
    public String Http_Get(String token, String Web) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";
        try {
            //DefaultHttpClient client = new DefaultHttpClient();
            //((AbstractHttpClient) client).setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
            HttpParams params2 = new BasicHttpParams();

            params2.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            DefaultHttpClient client = new DefaultHttpClient(params2);

            String postURL = "http://52.78.226.88:33667/" + Web;

            //postURL.replaceAll(" ", "%20");
            HttpGet post = new HttpGet(postURL);
            if(token.equals(".")){

            }
            else{
                post.addHeader("authorization", "" + token);
            }

            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String line = null;
            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
            client.getCredentialsProvider().clear();
            client.getCookieStore().clear();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("테스트", e+"");
        }
        return result;
    }
}
