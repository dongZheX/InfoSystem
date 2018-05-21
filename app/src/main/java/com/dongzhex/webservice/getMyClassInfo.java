package com.dongzhex.someactivities.infosystem;

import android.os.AsyncTask;

import com.dongzhex.NomalService.MessageBox;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.MyClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ASUS on 2018/5/20.
 */

public class getMyClassInfo extends AsyncTask<String,Integer,Integer> {
    String urls = NetUnit.URL+"/InfoSystem/getMyClassInfo";
    private MyClass myclass;
    String class_name;
    String class_count;
    String class_id;

    public getMyClassInfo(MyClass myclass) {
        this.myclass = myclass;
    }

    @Override
    protected Integer doInBackground(String... params) {
       class_id = params[0];
        try {
            //网络初次配置
            InputStream in;
            OutputStream out;
            BufferedReader bufferedReader;
            BufferedWriter bufferedWriter;
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            NetUnit.initConn(conn);
            //载入数据
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new BufferedWriter(out));
            bufferedWriter.write(class_id);
            conn.connect();
            in = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String data = bufferedReader.readLine();
            String results[] = data.split("/");
            class_name = results[0];
            class_count = results[1];
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showMessageBox("警告","错误提示BUG来自getMyClassInfo",true).show();
            return 0
        }

    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(integer == 1){
            myclass = new Class(class_name,class_id,class_count);
        }
        super.onPostExecute(integer);
    }
}
