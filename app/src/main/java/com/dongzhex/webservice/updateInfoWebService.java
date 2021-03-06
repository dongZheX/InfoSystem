package com.dongzhex.webservice;

import android.os.AsyncTask;

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
 * Created by ASUS on 2018/5/21.
 */

public class updateInfoWebService extends AsyncTask<String,Integer,Integer> {
    String urls = NetUnit.URL+"/InfoSystem/updateInfo";
    slStringInterface s;

    public updateInfoWebService(slStringInterface s) {
        this.s = s;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String Class_id = params[0];
        String info_id = params[1];
        String content = params[3];
        String title = params[2];
        String data = Class_id+"/"+info_id+"/"+title+"/"+content;
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

            out = conn.getOutputStream();

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out,"GBK"));
            bufferedWriter.write(data);
            bufferedWriter.flush();
            conn.connect();
            in = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in,"GBK"));
            if(conn.getResponseCode()== 200){
                String result = bufferedReader.readLine();
                bufferedReader.close();
                bufferedWriter.close();
                in.close();
                out.close();
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(integer == 1){
            s.success("success");
        }
        else{
            s.success("fault");
        }
        super.onPostExecute(integer);
    }
}
