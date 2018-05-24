package com.dongzhex.NomalService;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

/**
 * Created by ASUS on 2018/4/15.
 */

public class NetUnit {
    public static  String URL  = "http://172.26.65.219:8080";
    public static void initConn(HttpURLConnection conn) throws ProtocolException {
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(1000);
        conn.setReadTimeout(1000);

    }
}
