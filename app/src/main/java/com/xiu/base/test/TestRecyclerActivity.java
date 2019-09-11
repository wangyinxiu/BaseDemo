package com.xiu.base.test;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.xiu.base.R;
import com.xiu.core.app.core.recycler.MvpBaseRecyclerActivity;
import com.xiu.core.app.core.recycler.MvpBaseRecyclerPresenter;
import com.xiu.core.app.core.recycler.MvpBaseRecyclerView;

import java.util.ArrayList;
import java.util.List;



public class TestRecyclerActivity extends MvpBaseRecyclerActivity<TestEntity, TestCoreAdapter,
        MvpBaseRecyclerView<TestEntity>, MvpBaseRecyclerPresenter<MvpBaseRecyclerView<TestEntity>>> {

    @Override
    protected boolean isRefresh() {
        return true;
    }

    @Override
    protected boolean isLoadMore() {
        return true;
    }

    @Override
    public TestCoreAdapter createAdapter() {
        return new TestCoreAdapter(getCoreActivity());
    }

    @NonNull
    @Override
    public MvpBaseRecyclerPresenter<MvpBaseRecyclerView<TestEntity>> createPresenter() {
        return new MvpBaseRecyclerPresenter<MvpBaseRecyclerView<TestEntity>>() {
            @Override
            public void requestPageData(int page) {
                List<TestEntity> data = new ArrayList<>();
                String[] images = getCoreActivity().getResources().getStringArray(R.array.images);
                for (int i = 0; i < images.length; i++) {
                    data.add(new TestEntity(images[i]));
                }
                getView().onRefreshData(page, data);
            }
        };
    }

    @Override
    public void addCustomHeader(FrameLayout headerContainer) {
        super.addCustomHeader(headerContainer);
        View view = new View(getCoreActivity());
        view.setBackgroundResource(R.color.black);
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                dp2px(100)));
        headerContainer.addView(view);
    }
}
