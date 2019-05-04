package blackcap.nationalescape.Uitility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 박효근 on 2018-03-09.
 */

public class VideoDownloader {
    /** Called when the activity is first created. */
    String File_Name = "";
    String File_extend = "mp4";

    String fileURL = ""; // URL
    String Save_Path;
    String Save_folder = "/Movies";

    ProgressBar loadingBar;
    DownloadThread dThread;
    ProgressDialog asyncDialog;

    Activity Activity;
    public VideoDownloader(Activity activity, String Category, String filename){
        Activity = activity;
        asyncDialog = new ProgressDialog(Activity);
        fileURL = "http://13.124.32.32/Blah_video/"+Category+"/";
        File_Name = filename + ".mp4";
    }
    public void start(){
        // 다운로드 경로를 외장메모리 사용자 지정 폴더로 함.
        String ext = Environment.getExternalStorageState();
        if (ext.equals(Environment.MEDIA_MOUNTED)) {
            Save_Path = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + Save_folder;
        }

        File dir = new File(Save_Path);
        // 폴더가 존재하지 않을 경우 폴더를 만듦
        if (!dir.exists()) {
            dir.mkdir();
        }

        // 다운로드 폴더에 동일한 파일명이 존재하는지 확인해서
        // 없으면 다운받고 있으면 해당 파일 실행시킴.
        if (new File(Save_Path + "/" + File_Name).exists() == false) {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩 중입니다.");
            // show dialog
            asyncDialog.show();
            dThread = new DownloadThread(fileURL + "/" + File_Name,
                    Save_Path + "/" + File_Name);
            dThread.start();
        } else {
            //showDownloadFile();
        }
    }
    // 다운로드 쓰레드로 돌림..
    class DownloadThread extends Thread {
        String ServerUrl;
        String LocalPath;

        DownloadThread(String serverPath, String localPath) {
            ServerUrl = serverPath;
            LocalPath = localPath;
        }

        @Override
        public void run() {
            Log.i("완료1","완료1");
            URL imgurl;
            int Read;
            try {
                imgurl = new URL(ServerUrl);
                HttpURLConnection conn = (HttpURLConnection) imgurl
                        .openConnection();
                int len = conn.getContentLength();
                byte[] tmpByte = new byte[4096];
                InputStream is = conn.getInputStream();
                File file = new File(LocalPath);
                FileOutputStream fos = new FileOutputStream(file);
                for (;;) {
                    Read = is.read(tmpByte);
                    if (Read <= 0) {
                        break;
                    }
                    fos.write(tmpByte, 0, Read);
                }
                is.close();
                fos.close();
                conn.disconnect();
                //미디어 스캐닝
                Activity.sendBroadcast(new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)) );
            } catch (MalformedURLException e) {
                Log.e("ERROR1", e.getMessage());
            } catch (IOException e) {
                Log.e("ERROR2", e.getMessage());
                e.printStackTrace();
            }
            mAfterDown.sendEmptyMessage(0);
        }
    }

    Handler mAfterDown = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 파일 다운로드 종료 후 다운받은 파일을 실행시킨다.
            //showDownloadFile();
            Log.i("완료","완료");
            asyncDialog.dismiss();
            Toast.makeText(Activity, "다운로드 완료", Toast.LENGTH_SHORT).show();
        }

    };
}
