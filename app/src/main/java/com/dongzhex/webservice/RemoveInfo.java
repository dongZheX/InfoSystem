package com.dongzhex.webservice;

import android.os.AsyncTask;

import com.dongzhex.NomalService.MessageBox;
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

import static android.R.attr.data;

/**
 * Created by ASUS on 2018/5/21.
 */

public class RemoveInfo extends AsyncTask<String,Integer,Integer> {
    String urls = NetUnit.URL+"/InfoSystem/removeInfo";
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
             NetUnit.initConn(conn);
            in = conn.getInputStream();
            out = conn.getOutputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(data);
            bufferedWriter.flush();
              conn.connect();
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
         }
        return 0;

    }
    @Override
    protected void onPostExecute(Integer integer) {
        if(integer == 1){
            MessageBox.showMessageBox(Myapplication.getRealContext(),"提示","删除成功",true).show();
        }
        super.onPostExecute(integer);
    }
}
