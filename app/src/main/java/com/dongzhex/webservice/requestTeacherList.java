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

/**
 * Created by ASUS on 2018/5/26.
 */

public class requestTeacherList extends AsyncTask<String,Integer,String> {
    private slStringInterface sl;
    private String data,line;
    private StringBuilder stringBuilder;
    private String urls = NetUnit.URL+"/InfoSystem/returnTeacherList";
    private static final String TAG = "requestTeacherList";
    public requestTeacherList(slStringInterface st) {
        sl = st;
    }

    @Override
    protected String doInBackground(String... params) {
        String Class_id = params[0];
        URL url = null;
        try {
            url = new URL(urls);
            OutputStream out;
            InputStream in;
            BufferedReader bufferedReader;
            BufferedWriter bufferedWriter;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out,"GBK"));
            bufferedWriter.write(Class_id);
            bufferedWriter.flush();
            conn.connect();
            if(conn.getResponseCode()==200) {
                in = conn.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(in, "GBK"));

                stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                data = stringBuilder.toString();
                return data;
            }
           return null;
        } catch (Exception e) {
            Log.d(TAG, "出错");
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null)
        sl.success(s);
    }
}
