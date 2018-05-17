package com.dongzhex.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.dongzhex.NomalService.MessageBox;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.User;
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

public class LoginService extends AsyncTask<String,User,User> {
    private static final String TAG = "LoginService";
    private String username;
    private String password;
    private User backUser;
    private User need;

    public LoginService(User need) {
        this.need = need;
    }

    @Override
    protected User doInBackground(String... params) {
        username = params[0];
        password = params[1];
        User user = new User(username,password);
        //服务器地址
        String connecturl = NetUnit.URL+"/InfoSystem/LoginService";
        try {
            //可以代码服用，暂不复用
            URL url = new URL(connecturl);
            String jsonUser,jsonBack;
            HttpURLConnection conn = (HttpURLConnection) url.getContent();
            OutputStream out = conn.getOutputStream();
            BufferedWriter bufferWriter =  new BufferedWriter(new OutputStreamWriter(out));
            jsonUser = JsonService.javabeanToJson(user);
            bufferWriter.write(jsonUser);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(1000);
            conn.setConnectTimeout(1000);
            conn.setDoInput(true);
            conn.setDoInput(true);
            conn.connect();
            if(conn.getResponseCode()==200) {
                Log.d(TAG, "连接成功");
                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                String line,jsonBackData;
                while((line=bufferReader.readLine())!=null){
                    builder.append(line);
                }
                jsonBackData = builder.toString();
                backUser = JsonService.jsonToJavaBean(jsonBackData,User.class);


            }
        } catch (Exception e) {
            MessageBox.showMessageBox("警告","系统错误，请联系管理员",true).show();
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(User s) {
        need = s;
        super.onPostExecute(s);

    }
}
