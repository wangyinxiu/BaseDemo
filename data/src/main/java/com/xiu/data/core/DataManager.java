package com.xiu.data.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.xiu.utils.LogUtil;


public class DataManager implements ServiceConnection {

    private static final String TAG = "DataManager";

    private static DataManager ourInstance;
    private DataBinder service;

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    private DataManager() {

    }


    public void startDataService(Context context) {
        Intent intent = new Intent(context, DataService.class);
        context.bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    public void stopDataService(Context context) {
        context.unbindService(this);
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        LogUtil.i(TAG, "onServiceConnected == " + name.getClassName());
        this.service = (DataBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        LogUtil.i(TAG, "onServiceDisconnected == " + name.getClassName());
        service = null;
    }


    public static DataBinder getDataService() {
        return getInstance().service;
    }
}
