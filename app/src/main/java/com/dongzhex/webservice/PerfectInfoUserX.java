package com.dongzhex.webservice;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dongzhex.NomalService.BaseTool;
import com.dongzhex.NomalService.MessageBox;
import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.UserX;
import com.dongzhex.jsonService.JsonService;
import com.dongzhex.someactivities.infosystem.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by ASUS on 2018/5/3.
 * Description:完善信息
 */

public class PerfectInfoUserX extends AsyncTask<UserX,Integer,Integer> {
    private String url = NetUnit.URL+"/InfoSystem/PerfectUserXservice";
    public static final int SUCCESS = 1;
    public static final int FAULT = 1;
    private UserX userX;
    @Override
    protected Integer doInBackground(UserX... params) {
        userX = params[0];
        String line;
        String jsonData;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;
        InputStream in;
        OutputStream out;
        try {
            URL urlT = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)urlT.openConnection();
            BaseTool.initConn(conn);
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            jsonData = JsonService.javabeanToJson(userX);
            bufferedWriter.write(jsonData);
            bufferedWriter.flush();
            conn.connect();
            if(conn.getResponseCode()==200){
                Log.d(TAG, "d连接成功");
            }
            in = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String result = bufferedReader.readLine();
            if(result.equals("success")){
                Log.d(TAG, "success");
                return SUCCESS;
            }
            else{
                Log.d(TAG, "fault");
                return FAULT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showMessageBox("警告","系统错误，请联系管理员",true).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {

        super.onPostExecute(integer);
        if(integer==1){
            Toast.makeText(Myapplication.getRealContext(), "完善成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Myapplication.getRealContext(), MainActivity.class);
            intent.putExtra("username",userX.getUsername());
            Myapplication.getRealContext().startActivity(intent);
        }
        else{
            Toast.makeText(Myapplication.getRealContext(), "失败,请联系管理员：15650111502", Toast.LENGTH_SHORT).show();
        }
    }
}
