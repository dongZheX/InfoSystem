package com.dongzhex.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.UserX;
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
 * Created by ASUS on 2018/5/14.
 */

public class RequestContantList extends AsyncTask<String,Integer,String> {
    successListener st;
    List<UserX> list;
    String line,jsonBack;
    String urlS = NetUnit.URL+"/InfoSystem/ReturnContantList";
    private static final String TAG = "RequestContantList";
    public RequestContantList(successListener sl) {
        st = sl;
    }

    @Override
    protected String doInBackground(String... params) {
        String Class_id = params[0];

        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader;
            BufferedWriter bufferedWriter;
            InputStream in;
            OutputStream out;
            URL url = new URL(urlS);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out,"GBK"));
            bufferedWriter.write(Class_id);
            bufferedWriter.flush();
            bufferedWriter.close(); out.close();//关闭流
            conn.connect();
            if(conn.getResponseCode()==200) {
                in = conn.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(in,"GBK"));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                jsonBack = stringBuilder.toString();
                Log.d(TAG, jsonBack);
                conn.disconnect();
                return jsonBack;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "出错");
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null) {
            Log.d(TAG, s);
            st.success(s);
        }


    }
}
