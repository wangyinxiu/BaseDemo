package com.xiu.data.core;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Binder;


import com.xiu.data.bean.response.Information;
import com.xiu.data.datamodel.DemoDataModel;
import com.xiu.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class DataBinder extends Binder implements OnNetStateChangedListener {

    private static final String TAG = "DataBinder";

    private NetworkReceiver receiver;
    private Context context;
    private boolean isNetConnected;
    private Cache cache;

    private List<OnNetStateChangedListener> onNetStateChangedListeners;

    public DataBinder(Context context) {
        this.context = context;
        DaoManager.getInstance().init(context);
        cache = new Cache(context);
        Network.getInstance().setCache(cache);
        onNetStateChangedListeners = new ArrayList<>();
        isNetConnected = NetworkUtil.isAvailable(context);
        receiver = new NetworkReceiver(this::onNetConnected);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, filter);

    }


    public void onDestroy() {
        context.unregisterReceiver(receiver);
    }

    @Override
    public void onNetConnected(boolean isConnected) {
        LogUtil.i(TAG, "onNetConnected == " + isConnected);
        if (isNetConnected != isConnected) {
            isNetConnected = isConnected;
            for (OnNetStateChangedListener listener : onNetStateChangedListeners) {
                listener.onNetConnected(isConnected);
            }
        }
    }

    public void requestInformation(int page, DisposableObserver<List<Information>> disposableObserver) {
        if (isNetConnected) {
            DemoDataModel.requestInformation(page, disposableObserver);
        }
    }

    public boolean isNetConnected() {
        return isNetConnected;
    }

    public boolean registerOnNetStateChangedListener(OnNetStateChangedListener listener) {
        LogUtil.i(TAG, "registerOnNetStateChangedListener");
        return onNetStateChangedListeners.add(listener);
    }

    public boolean unregisterOnNetstateChangedListener(OnNetStateChangedListener listener) {
        LogUtil.i(TAG, "unregisterOnNetstateChangedListener");
        return onNetStateChangedListeners.remove(listener);
    }


}
