package com.zws.ble.contacthuawei;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by zws on 2017/8/3.
 */

public class MyApplication extends Application {
    public static int userID ;
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
