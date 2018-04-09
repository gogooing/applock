package com.sanqiu.loro.applocktest.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by loro on 2018/4/9.
 */

public class AppLockAPP extends Application {
    private static AppLockAPP mInstance = null;

    public static AppLockAPP getApplication() {
        return mInstance;
    }

    public static Context getContext() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Application", "onCreate: ");
        mInstance = this;
    }

}
