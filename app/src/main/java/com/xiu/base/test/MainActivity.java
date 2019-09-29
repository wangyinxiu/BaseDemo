package com.xiu.base.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiu.base.R;
import com.xiu.core.app.adapter.CoreAdapter;
import com.xiu.core.app.core.CoreActivity;
import com.xiu.core.app.dialog.AppAlertDialog;
import com.xiu.core.app.dialog.AppToastDialog;
import com.xiu.core.view.CoreRecyclerView;
import com.xiu.data.bean.response.Information;
import com.xiu.data.core.DataManager;
import com.xiu.utils.FileUtil;
import com.xiu.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class MainActivity extends CoreActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBackgroundRes(R.color.white);
        DataManager.getInstance().startDataService(this);


        CoreRecyclerView coreRecyclerView = getViewCache().getView(R.id.core_recycler);
        coreRecyclerView.setConfig(new CoreRecyclerView.CoreRecyclerConfig<TestEntity>(coreRecyclerView) {
            @Override
            protected CoreAdapter<TestEntity> createRecyclerAdapter() {
                return new TestCoreAdapter(getCoreActivity());
            }

            @Override
            public void requestPageData(int page) {
                List<TestEntity> data = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    data.add(new TestEntity("aaa" + i));
                }
                onRefreshData(page, data);
            }
        });
    }

    @Override
    public void onUIContentChanged() {
        super.onUIContentChanged();

        getViewCache().getView(R.id.test_net_request).setOnClickListener(v -> {
            DataManager.getDataService().requestInformation(0, new DisposableObserver<List<Information>>() {
                @Override
                public void onNext(List<Information> information) {

                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.o(e);
                }

                @Override
                public void onComplete() {
                    LogUtil.i(TAG, "onComplete");
                }
            });

        });

        getViewCache().getView(R.id.clear_image_cache).setOnClickListener(v -> {
            new Thread(() -> FileUtil.clearImageCache()).start();
        });

        getViewCache().getView(R.id.clear_string_to_sp_cache).setOnClickListener(v -> {


        });

        getViewCache().getView(R.id.get_string_to_sp_cache).setOnClickListener(v -> {

        });

        getViewCache().getView(R.id.put_string_to_sp_cache).setOnClickListener(v -> {
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
