package com.xiu.base.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiu.base.R;
import com.xiu.core.app.core.CoreActivity;
import com.xiu.core.app.dialog.AppAlertDialog;
import com.xiu.core.app.dialog.AppToastDialog;
import com.xiu.core.utils.FileUtil;
import com.xiu.core.utils.LogUtil;
import com.xiu.core.utils.SPCache;
import com.xiu.network.bean.response.Information;
import com.xiu.network.datamodel.DemoDataModel;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class MainActivity extends CoreActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBackgroundRes(R.color.white);
    }

    @Override
    public void onUIContentChanged() {
        super.onUIContentChanged();

        getViewCache().getView(R.id.test_net_request).setOnClickListener(v -> {
            DemoDataModel.requestInformation(0, new DisposableObserver<List<Information>>() {
                @Override
                public void onNext(List<Information> information) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        });

        getViewCache().getView(R.id.clear_image_cache).setOnClickListener(v -> {
            new Thread(() -> FileUtil.clearImageCache()).start();
        });

        getViewCache().getView(R.id.clear_string_to_sp_cache).setOnClickListener(v -> {
            SPCache.clearUserCache();
            String result = SPCache.get("ssss", "default");
            LogUtil.i(TAG, "result == " + result);
        });

        getViewCache().getView(R.id.get_string_to_sp_cache).setOnClickListener(v -> {
            int result = SPCache.get(SPCache.Key.KEY_TOKEN, 888);
            LogUtil.i(TAG, "result == " + result);
        });

        getViewCache().getView(R.id.put_string_to_sp_cache).setOnClickListener(v -> {
            boolean result = SPCache.put(SPCache.Key.KEY_TOKEN, 124);
            LogUtil.i(TAG, "result == " + result);
        });

        getViewCache().getView(R.id.btn_test_recycler_activity).setOnClickListener(v -> {
            start(TestRecyclerActivity.class);
        });


        getViewCache().getView(R.id.btn_test_pager_activity).setOnClickListener(v -> {
            start(TestPagerActivity.class);
        });

        getViewCache().getView(R.id.btn_test_tip_dialog).setOnClickListener(v -> {
            new AppToastDialog(getCoreActivity()) {
                @Override
                public String setTitle() {
                    return getResources().getString(R.string.test_app_toast);
                }
            }.show();
        });

        getViewCache().getView(R.id.btn_test_alert_dialog).setOnClickListener(v -> {
            new AppAlertDialog(getCoreActivity()).show();
        });
    }


    private void start(Class cls) {
        getCoreActivity().startActivity(new Intent(getCoreActivity(), cls));
    }
}
