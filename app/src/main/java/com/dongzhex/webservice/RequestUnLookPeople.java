package com.dongzhex.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.slStringInterface;

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
 * Created by ASUS on 2018/5/21.
 */

public class RequestUnLookPeople extends AsyncTask<String,Integer,Integer>{
    private
    String urls = NetUnit.URL+"/InfoSystem/ReturnUnLookPeople";
    private static final String TAG = "RequestUnLookPeople";
    private List<String> datas;
    private String line,data;
    slStringInterface sl;
    public RequestUnLookPeople(slStringInterface sk) {
        sl = sk;
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
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            in = conn.getInputStream();
            out = conn.getOutputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(info_id);
            bufferedWriter.flush();
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
        if(data!=null)

        sl.success(data);
        super.onPostExecute(integer);
    }
}
