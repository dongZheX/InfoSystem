package com.dongzhex.NomalService;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by ASUS on 2018/4/12.
 */

public class Myapplication extends Application {
   static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        LitePalApplication.initialize(context);
        super.onCreate();
    }
    public static Context getRealContext(){
        return  context;
    }
}
