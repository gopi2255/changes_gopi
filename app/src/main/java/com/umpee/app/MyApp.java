package com.umpee.app;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;


public class MyApp extends Application {
    private static final String TAG = "MyApp";
    private static MyApp mInstance;


    public MyApp() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static Context getContext() {
        return mInstance;
    }

}
