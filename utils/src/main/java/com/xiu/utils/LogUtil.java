package com.xiu.utils;

import android.util.Log;

import com.google.gson.Gson;

public class LogUtil {

    private static final String TAG = "xiu_neu";


    public static void v(String tag, String msg) {
        if (BuildConfig.LOG_ENABLE) {
            Log.v(TAG, tag + " --> " + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.LOG_ENABLE) {
            Log.d(TAG, tag + " --> " + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.LOG_ENABLE) {
            Log.i(TAG, tag + " --> " + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.LOG_ENABLE) {
            Log.w(TAG, tag + " --> " + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.LOG_ENABLE) {
            Log.e(TAG, tag + " --> " + msg);
        }
    }

    public static  void o(Object obj) {
        if (BuildConfig.LOG_ENABLE) {
            String msg = null;
            if (obj == null) {
                msg = "Http:next --> object is null";
            } else {
                msg = "Http:next --> " + obj.getClass().getSimpleName() +
                        " == " + new Gson().toJson(obj);
            }
            Log.i(TAG, msg);
        }
    }

    public static void e(Throwable e) {
        if (BuildConfig.LOG_ENABLE) {
            Log.e(TAG, "Http:error --> " + e.getMessage());
        }
    }


}
