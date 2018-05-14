package com.dongzhex.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.dongzhex.NomalService.BaseTool;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.Info;
import com.dongzhex.jsonService.JsonService;

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

public class RequestNotificationList extends AsyncTask<String,Integer,Integer> {
    List<Info> list;
    String urlString = NetUnit.URL+"/infoSystem/RequestNotificationList";
    private static final String TAG = "RequestNotificationList";
    public RequestNotificationList(List<Info> list) {
        
        this.list = list;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String class_id = params[0];
        OutputStream out;
        InputStream in;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        String line,jsonBack;
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            BaseTool.initConn(conn);
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(class_id);
            conn.connect();
            if(conn.getResponseCode()==200){
                Log.d(TAG, "连接成功");
            }
            in = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            while((line=bufferedReader.readLine())!=null){
                builder.append(line);
            }
            jsonBack = builder.toString();
            list = JsonService.jsonToList(jsonBack,Info.class);

        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }
}
