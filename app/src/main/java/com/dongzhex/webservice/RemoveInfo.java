package com.dongzhex.webservice;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dongzhex.NomalService.Myapplication;
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

public class RemoveInfo extends AsyncTask<String,Integer,Integer> {
    String urls = NetUnit.URL+"/InfoSystem/removeInfo";
    private static final String TAG = "RemoveInfo";
    @Override
    protected Integer doInBackground(String... params) {
        String info_id = params[0];
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        InputStream in;
        OutputStream out;

        try{
            URL url = new URL(urls);
             HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);

            out = conn.getOutputStream();

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(info_id);
            bufferedWriter.flush();
              conn.connect();
            in = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            if(conn.getResponseCode()== 200){
                String result = bufferedReader.readLine();
                return Integer.parseInt(result);
             }
             bufferedReader.close();
            bufferedWriter.close();
            in.close();
            out.close();
         } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "出错，联系管理员吧");
         }
        return 0;

    }
    @Override
    protected void onPostExecute(Integer integer) {
        if(integer == 1){
            Toast.makeText(Myapplication.getRealContext(), "删除成功", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(integer);
    }
}
