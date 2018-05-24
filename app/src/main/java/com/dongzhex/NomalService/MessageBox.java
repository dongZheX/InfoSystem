package com.dongzhex.NomalService;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ASUS on 2018/5/3.
 */

public class MessageBox {
    public static AlertDialog.Builder showMessageBox(Context context,String title, String content, boolean cancel){
       AlertDialog.Builder builder  = new AlertDialog.Builder(context);
        builder.setCancelable(cancel);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder;
    }
}
