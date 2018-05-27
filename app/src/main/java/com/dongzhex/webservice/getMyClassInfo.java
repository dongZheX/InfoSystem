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
 * Created by ASUS on 2018/5/20.
 */

public class getMyClassInfo extends AsyncTask<String,Integer,String> {
    String urls = NetUnit.URL+"/InfoSystem/getMyClassInfo";
    String class_id;
    String data;
    private slStringInterface st;
    private static final String TAG = "getMyClassInfo";
    public getMyClassInfo(slStringInterface sl) {
        st = sl;
    }

    @Override
    protected String doInBackground(String... params) {
       class_id = params[0];
        try {
            //网络初次配置
            InputStream in;
            OutputStream out;
            BufferedReader bufferedReader;
            BufferedWriter bufferedWriter;
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            //载入数据
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(class_id);
            bufferedWriter.flush();
            conn.connect();
            in = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            data = bufferedReader.readLine();

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "出错");
            return null;
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null&&!s.equals(""))
        st.success(s);

    }
}
