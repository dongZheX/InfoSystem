package com.dongzhex.webservice;

import android.os.AsyncTask;

import com.dongzhex.NomalService.BaseTool;
import com.dongzhex.NomalService.MessageBox;
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
 * Created by ASUS on 2018/5/17.
 */

public class ResetPassWebService extends AsyncTask<String,Integer,Integer> {
    private String urls = NetUnit.URL+"InfoSystem/ResetPassword";
    String result;
    @Override
    protected Integer doInBackground(String... params) {
        String data = params[0]+"/"+params[1]+"/"+params[2];
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        OutputStream out;
        InputStream in;

        try {
            URL url = new URL(urls);
            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            BaseTool.initConn(conn);
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(data);
            bufferedWriter.flush();
            conn.connect();
            //接受服务器数据
            in = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            if(conn.getResponseCode()==200) {

                result = bufferedReader.readLine();

            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showMessageBox("警告","系统错误，请联系管理员",true).show();
        }
        if(result.equals("1")){
            return 1;
        }
        else{
            return 0;
        }

    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(integer==1){
            MessageBox.showMessageBox("提示","修改成功",true).show();
        }
        else{
            MessageBox.showMessageBox("错误","修改失败，请确认原密码是否正确",true).show();
        }
        super.onPostExecute(integer);
    }
}