package com.dongzhex.webservice;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dongzhex.NomalService.BaseTool;
import com.dongzhex.NomalService.MessageBox;
import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.PackPhoto;
import com.dongzhex.jsonService.JsonService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ASUS on 2018/5/3.
 */

public class UploadBitmap extends AsyncTask<Bitmap,Integer,String> {
    private static final String TAG = "UploadBitmap";
    private  String presentUsername;

    public UploadBitmap(String presentUsername) {
        this.presentUsername = presentUsername;
    }

    @Override
    protected String doInBackground(Bitmap... params) {
        Bitmap bit = params[0];
        int resultcode = 0;
        StringBuilder stringBuilder = new StringBuilder();
        String upload = NetUnit.URL+"/InfoSystem/UploadPhoto";

        try {

            stringBuilder.append(BaseTool.Bitmap2String(bit));
            PackPhoto packPhoto = new PackPhoto(presentUsername,stringBuilder.toString());
            URL url = new URL(upload);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);

            String jsonData = JsonService.javabeanToJson(packPhoto);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            bufferedWriter.write(jsonData);
            bufferedWriter.flush();
            Log.d("TextUploadImage",jsonData);
            bufferedWriter.close();
            conn.connect();
            if(conn.getResponseCode()==200){
                Log.d(TAG, "连接成功 ");
            }
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result = bufferReader.readLine();
            bufferedWriter.close();
            bufferReader.close();
            conn.disconnect();
            return result;

        }catch (Exception e){
            e.printStackTrace();
            MessageBox.showMessageBox("警告","系统错误，请联系管理员",true).show();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(Myapplication.getRealContext(),s,Toast.LENGTH_LONG);
    }
}
