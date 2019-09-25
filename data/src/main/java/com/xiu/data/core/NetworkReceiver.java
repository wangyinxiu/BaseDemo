package com.xiu.data.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkReceiver extends BroadcastReceiver {

    private OnNetStateChangedListener listener;

    public NetworkReceiver(OnNetStateChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        listener.onNetConnected(NetworkUtil.isAvailable(context));
    }


}
