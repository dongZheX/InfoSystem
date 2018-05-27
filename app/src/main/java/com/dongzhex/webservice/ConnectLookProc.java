package com.dongzhex.webservice;

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
 * Created by ASUS on 2018/5/20.
 */

public class ConnectLookProc extends AsyncTask<String,Integer,Integer> {
    String urls = NetUnit.URL + "/InfoSystem/LookRecordService";
    private static final String TAG = "ConnectLookProc";
    @Override
    protected Integer doInBackground(String... params) {
        String username, infoId;
        String result;//使用类结果
        username = params[0];
        infoId = params[1];
        String data = username + "/" + infoId;
        try {
            OutputStream out;
            InputStream in;
            BufferedReader bufferedReader;
            BufferedWriter bufferedWriter;
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(data);
            bufferedWriter.flush();
            in = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            conn.connect();
            if (conn.getResponseCode() == 200) {

                result = bufferedReader.readLine();
                if (result.equals("1")) {
                    Log.d(TAG, "成功");
                } else {
                    Log.d(TAG, "出错");
                }

            }
            conn.disconnect();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (Exception e) {
            Log.d(TAG, "出错");

        }
        return 0;
    }

}


