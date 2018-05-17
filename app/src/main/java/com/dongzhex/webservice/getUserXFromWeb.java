package com.dongzhex.webservice;

import android.os.AsyncTask;

import com.dongzhex.NomalService.BaseTool;
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

/**
 * Created by ASUS on 2018/5/17.
 */

public class getUserXFromWeb extends AsyncTask<String,Integer,Integer>{
    UserX userX1;
    UserX user;
    String urlS = NetUnit.URL+"/InfoSystem/ReturnContantList";
    public getUserXFromWeb(UserX userx) {
        this.user = userx;
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
            BaseTool.initConn(conn);
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

        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(integer == 1){
            user = userX1;
        }
        super.onPostExecute(integer);
    }
}
