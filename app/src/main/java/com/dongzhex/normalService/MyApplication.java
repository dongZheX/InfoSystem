package com.dongzhex.normalService;

import android.app.Application;
import android.content.Context;

/**
 * Created by ASUS on 2018/3/22.
 */

public class MyApplication extends Application {
    private static Context myContext;
    @Override
    public void onCreate() {
        super.onCreate();
        myContext = getApplicationContext();

    }

    public static Context getContext(){
        return myContext;
    }
}
