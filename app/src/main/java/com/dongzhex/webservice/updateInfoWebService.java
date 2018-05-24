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

/**
 * Created by ASUS on 2018/5/21.
 */

public class updateInfoWebService extends AsyncTask<String,Integer,Integer> {
    String urls = NetUnit.URL+"/InfoSystem/updateInfo";

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
            NetUnit.initConn(conn);
            in = conn.getInputStream();
            out = conn.getOutputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(data);
            conn.connect();

            if(conn.getResponseCode()== 200){
                String result = bufferedReader.readLine();
                bufferedReader.close();
                bufferedWriter.close();
                in.close();
                out.close();
                return Integer.parseInt(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(integer == 1){
            MessageBox.showMessageBox(Myapplication.getRealContext(),"提示","修改成功",true).show();
        }
        else{
            MessageBox.showMessageBox(Myapplication.getRealContext(),"提示","修改失败",true).show();
        }
        super.onPostExecute(integer);
    }
}
