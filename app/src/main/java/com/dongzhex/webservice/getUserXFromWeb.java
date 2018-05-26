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

/**
 * Created by ASUS on 2018/5/17.
 */

public class getUserXFromWeb extends AsyncTask<String,String,String>{
    UserX userX1;
    UserX user;
    String urlS = NetUnit.URL+"/InfoSystem/ReturnUserX";
    private static final String TAG = "getUserXFromWeb";
    private successListener cs;
    public getUserXFromWeb(successListener cc) {
        cs = cc;
    }

    @Override
    protected String doInBackground(String... params) {
        String username = params[0];
        String line,jsonBack;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader;
            BufferedWriter bufferedWriter;
            InputStream in;
            OutputStream out;
            URL url = new URL(urlS);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            NetUnit.initConn(conn);
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out,"GBK"));
            bufferedWriter.write(username);
            Log.d(TAG, username);
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

                in.close();
                out.close();
                bufferedReader.close();
                bufferedWriter.close();
                return jsonBack;
            }
            conn.disconnect();
           return null;

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "出错");
            return null;
        }


    }

    @Override
    protected void onPostExecute(String s) {
       /* if(integer == 1){
            SharedPreferences share = Myapplication.getRealContext().getSharedPreferences("tempData",MODE_PRIVATE);
            final SharedPreferences.Editor  editors = share.edit();
            Log.d(TAG, JsonService.javabeanToJson(userX1));
            editors.putString("data2",JsonService.javabeanToJson(userX1));
            editors.apply();
        }*/
        super.onPostExecute(s);
        if(s!=null&&!s.equals("")){
            cs.success(s);
        }
    }
}
