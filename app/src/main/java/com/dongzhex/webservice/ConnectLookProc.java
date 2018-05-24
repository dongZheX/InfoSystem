package com.dongzhex.webservice;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

import static android.content.ContentValues.TAG;

/**
 * Created by ASUS on 2018/5/20.
 */

public class ConnectLookProc extends AsyncTask<String,Integer,Integer> {
    String urls = NetUnit.URL + "/InfoSystem/LookRecordService";

    @Override
    protected Integer doInBackground(String... params) {
        String username, infoId;
        String result;//使用类结果
        username = params[0];
        infoId = params[1];
        String data = username + "/" + infoId;
        try {
            OutputStream out;
            InputStream in;
            BufferedReader bufferedReader;
            BufferedWriter bufferedWriter;
            URL url = new URL(urls);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            NetUnit.initConn(conn);
            out = conn.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(data);
            bufferedWriter.flush();
            in = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            conn.connect();
            if (conn.getResponseCode() == 200) {

                result = bufferedReader.readLine();
                if (result.equals("1")) {
                    Log.d(TAG, "成功");
                } else {
                    Toast.makeText(Myapplication.getRealContext(), "出现错误", Toast.LENGTH_SHORT).show();
                }

            }
            conn.disconnect();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (Exception e) {
            Toast.makeText(Myapplication.getRealContext(), "失败,请联系管理员：15650111502", Toast.LENGTH_SHORT).show();

        }
        return 0;
    }

}


