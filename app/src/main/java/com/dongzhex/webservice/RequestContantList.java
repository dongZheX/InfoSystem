package com.dongzhex.webservice;

import android.os.AsyncTask;

import com.dongzhex.NomalService.BaseTool;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.UserX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by ASUS on 2018/5/14.
 */

public class RequestContantList extends AsyncTask<String,Integer,Integer> {
    List<UserX> mlist;
    String urlS = NetUnit.URL+"/InfoSystem/ReturnContantList";
    public RequestContantList(List<UserX> mlist) {
        this.mlist = mlist;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String Class_id = params[0];
        String line,jsonBack;
        StringBuilder stringBuilder;
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
            bufferedWriter.write(Class_id);
            bufferedWriter.flush();
            bufferedWriter.close(); out.close();//关闭流
            conn.connect();



        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
