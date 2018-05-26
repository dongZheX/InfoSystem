package com.dongzhex.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.User;
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

/**
 * Created by ASUS on 2018/4/26.
 */

public class LoginService extends AsyncTask<String,Integer,String> {
    private static final String TAG = "LoginService";
    private String username;
    private String password;
    private User backUser;
    successListener cc;
    public LoginService(successListener c) {
        cc = c;
    }

    @Override
    protected String doInBackground(String... params) {
        username = params[0];
        password = params[1];
        User user = new User(username,password);
        Log.d(TAG, "doInBackground: ");
        //服务器地址
        String connecturl = NetUnit.URL+"/InfoSystem/LoginServlet";
        try {
            //可以代码服用，暂不复用
            URL url = new URL(connecturl);
            String jsonUser,jsonBack;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            jsonUser = JsonService.javabeanToJson(user);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(1000);
            conn.setConnectTimeout(1000);
            conn.setDoInput(true);
            conn.setDoInput(true);
            OutputStream out = conn.getOutputStream();
            BufferedWriter bufferWriter =  new BufferedWriter(new OutputStreamWriter(out));
            bufferWriter.write(jsonUser);
            bufferWriter.flush();
            conn.connect();
            if(conn.getResponseCode()==conn.getResponseCode()) {
                Log.d(TAG, "连接成功");
                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                String line,jsonBackData;
                while((line=bufferReader.readLine())!=null){
                    builder.append(line);
                }
                jsonBackData = builder.toString();
                Log.d(TAG, jsonBackData);
                backUser = JsonService.jsonToJavaBean(jsonBackData,User.class);

                return jsonBackData;
            }
        } catch (Exception e) {
           // MessageBox.showMessageBox("警告","系统错误，请联系管理员",true).show();
            Log.d(TAG, "系统错误，请联系管理员");
            e.printStackTrace();
        }


        return "";
    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null&&!s.equals("")) {
            /*SharedPreferences share = Myapplication.getRealContext().getSharedPreferences("tempData",MODE_PRIVATE);
            final SharedPreferences.Editor  editors = share.edit();
            editors.clear();
            Log.d(TAG, JsonService.javabeanToJson(backUser));
            editors.putString("data1",JsonService.javabeanToJson(backUser));
            editors.apply();*/
            cc.success(s);

        }
    }
}
