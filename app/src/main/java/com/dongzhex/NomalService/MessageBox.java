package com.dongzhex.NomalService;

import android.app.AlertDialog;

/**
 * Created by ASUS on 2018/5/3.
 */

public class MessageBox {
    public static AlertDialog.Builder showMessageBox(String title, String content,boolean cancel){
       AlertDialog.Builder builder  = new AlertDialog.Builder(Myapplication.getRealContext());
        builder.setCancelable(cancel);
        builder.setTitle(title);
        builder.setMessage(content);
        return builder;
    }
}
