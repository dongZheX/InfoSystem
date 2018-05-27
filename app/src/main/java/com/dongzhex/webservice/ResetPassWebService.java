package com.dongzhex.webservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

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
    private String urls = NetUnit.URL+"/InfoSystem/ResetPassword";
    String result;
    private static final String TAG = "ResetPassWebService";
    private Activity act;
    public ResetPassWebService(Activity act) {
        this.act = act;
    }

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
            NetUnit.initConn(conn);
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out,"GBK"));
            bufferedWriter.write(data);
            bufferedWriter.flush();
            conn.connect();
            //接受服务器数据
            in = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in,"GBK"));
            if(conn.getResponseCode()==200) {

                result = bufferedReader.readLine();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "ERROR ");
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setTitle("提示");
            builder.setMessage("修改成功");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setTitle("提示");
            builder.setMessage("修改失败");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        super.onPostExecute(integer);
    }
}
