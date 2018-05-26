package com.dongzhex.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.Info;
import com.dongzhex.entity.successListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by ASUS on 2018/5/7.
 */

public class RequestNotificationList extends AsyncTask<String,Integer,String> {
    List<Info> list;
    String urlString = NetUnit.URL+"/InfoSystem/RequestNotificationList";
    private static final String TAG = "RequestNotificationList";
    successListener sl;
    private String line,jsonBack = "";
    StringBuilder builder = new StringBuilder();
    public RequestNotificationList(successListener e) {
        
        sl = e;
    }

    @Override
    protected String doInBackground(String... params) {
        String class_id = params[0];
        OutputStream out;
        InputStream in;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;


        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out,"GBK"));
            bufferedWriter.write(class_id);
            bufferedWriter.flush();
            conn.connect();
            if(conn.getResponseCode()==200){
                Log.d(TAG, "连接成功");
                in = conn.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(in,"GBK"));
                while((line=bufferedReader.readLine())!=null){
                    builder.append(line);
                }
                jsonBack = builder.toString();
                System.out.println(jsonBack);

                return jsonBack;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "出错");
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null) {
            Log.d(TAG, s);
            sl.success(s);
        }
    }
}
