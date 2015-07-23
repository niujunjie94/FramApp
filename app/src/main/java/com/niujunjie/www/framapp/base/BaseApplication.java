package com.niujunjie.www.framapp.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;



/**
 * Created by niujunjie on 2015/7/23.
 */
public class BaseApplication extends Application{
    private static Context appContext;
    //主线程id
    private static  int mainThreadId;
    //主线程
    private static Thread mainThread;

    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        //Context

        appContext = getApplicationContext();
        mainThreadId = android.os.Process.myTid();
        mainThread = Thread.currentThread();
        handler = new Handler();
    }

    public static Context getAppContext() {
        return appContext;
    }
    public static int getMainThreadId() {
        return mainThreadId;
    }
    public static Thread getMainThread() {
        return mainThread;
    }
    public static Handler getHandler() {
        return handler;
    }
}
