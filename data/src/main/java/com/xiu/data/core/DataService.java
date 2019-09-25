package com.xiu.data.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DataService extends Service {

    private DataBinder binder;

    public DataService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new DataBinder(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binder != null) {
            binder.onDestroy();
        }
    }
}
