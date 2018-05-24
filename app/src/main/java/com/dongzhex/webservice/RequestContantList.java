package com.dongzhex.webservice;

import android.os.AsyncTask;

import com.dongzhex.NomalService.MessageBox;
import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.UserX;
import com.dongzhex.entity.successListener;
import com.dongzhex.jsonService.JsonService;

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

public class RequestContantList extends AsyncTask<String,Integer,Integer> {
    successListener st;
    List<UserX> list;
    String urlS = NetUnit.URL+"/InfoSystem/ReturnContantList";
    public RequestContantList(successListener sl) {
        st = sl;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String Class_id = params[0];
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
            bufferedWriter.write(Class_id);
            bufferedWriter.flush();
            bufferedWriter.close(); out.close();//关闭流
            conn.connect();
            in = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            while((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            jsonBack = stringBuilder.toString();
            list = JsonService.jsonToList(jsonBack,UserX.class);
            conn.disconnect();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showMessageBox(Myapplication.getRealContext(),"警告","系统错误，请联系管理员",true).show();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer == 1){
            st.successUserX(list);
        }

    }
}
