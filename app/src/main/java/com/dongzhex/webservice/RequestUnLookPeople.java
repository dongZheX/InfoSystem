package com.dongzhex.someactivities.infosystem;

import android.os.AsyncTask;
import android.util.Log;

import com.dongzhex.NomalService.NetUnit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ASUS on 2018/5/21.
 */

public class RequestUnLookPeople extends AsyncTask<String,Integer,Integer>{
    String urls = NetUnit.URL+"/InfoSystem/ReturnUnLookPeople";
    private static final String TAG = "RequestUnLookPeople";
    private String[] datas;
    private String line,data;

    public RequestUnLookPeople(String[] datas) {
        this.datas = datas;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String info_id = params[0];
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        InputStream in;
        OutputStream out;


        try {
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            NetUnit.initConn(conn);
            in = conn.getInputStream();
            out = conn.getOutputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(info_id);
            conn.connect();
            if(conn.getResponseCode()==200){
                Log.d(TAG, "连接成功");
                StringBuilder builder = new StringBuilder();
                while((line = bufferedReader.readLine())!=null){
                    builder.append(line);
                }
                data = builder.toString();
                if(!data.toString().equals("1"))
                    return 1;
                else
                  return 0;
            }
            else{
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(integer==1){
            datas = data.split("/");
        }
        super.onPostExecute(integer);
    }
}
