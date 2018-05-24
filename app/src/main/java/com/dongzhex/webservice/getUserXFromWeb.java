package com.dongzhex.webservice;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.UserX;
import com.dongzhex.jsonService.JsonService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ASUS on 2018/5/17.
 */

public class getUserXFromWeb extends AsyncTask<String,Integer,Integer>{
    UserX userX1;
    UserX user;
    String urlS = NetUnit.URL+"/InfoSystem/ReturnContantList";
    public getUserXFromWeb() {

    }

    @Override
    protected Integer doInBackground(String... params) {
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
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(username);
            bufferedWriter.flush();
            bufferedWriter.close(); out.close();//关闭流
            conn.connect();
            if(conn.getResponseCode()==200) {
                in = conn.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(in));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                jsonBack = stringBuilder.toString();
                userX1 = JsonService.jsonToJavaBean(jsonBack,UserX.class);
                in.close();
                out.close();
                bufferedReader.close();
                bufferedWriter.close();
                return 1;
            }
            conn.disconnect();
           return 0;

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Myapplication.getRealContext(), "失败,请联系管理员：15650111502", Toast.LENGTH_SHORT).show();

        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(integer == 1){
            SharedPreferences share = Myapplication.getRealContext().getSharedPreferences("tempData",MODE_PRIVATE);
            final SharedPreferences.Editor  editors = share.edit();
            Log.d(TAG, JsonService.javabeanToJson(userX1));
            editors.putString("data2",JsonService.javabeanToJson(userX1));
            editors.apply();
        }
        super.onPostExecute(integer);
    }
}
