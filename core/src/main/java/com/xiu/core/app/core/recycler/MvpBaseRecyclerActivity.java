package com.xiu.core.app.core.recycler;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xiu.core.R;
import com.xiu.core.app.adapter.CoreAdapter;
import com.xiu.core.app.mvp.MvpActivity;

import java.util.List;

public abstract class MvpBaseRecyclerActivity<T, A extends CoreAdapter<T>, V extends MvpBaseRecyclerView<T>, P extends MvpBaseRecyclerPresenter<V>> extends MvpActivity<V, P> implements MvpBaseRecyclerView<T> {

    private static final int DEFAULT_TIME_OUT = 30;

    private A adapter;
    private int currentPage = 1;
    private SmartRefreshLayout refreshLayout;
    private View empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }


    @Override
    public void onUIContentChanged() {
        super.onUIContentChanged();

        refreshLayout = getViewCache().getView(R.id.refresh_container);
        refreshLayout.setVisibility(View.VISIBLE);
        empty = getViewCache().getView(R.id.empty_container);
        empty.setVisibility(View.GONE);
        refreshLayout.setEnableRefresh(isRefresh());
        refreshLayout.setEnableLoadMore(isLoadMore());
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshOrLoadMore();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                refreshOrLoadMore();
            }
        });
        addCustomHeader(getViewCache().getView(R.id.header_container));
        RecyclerView recyclerView = getViewCache().getView(R.id.recycler);
        recyclerView.setLayoutManager(setRecyclerLayoutManager());
        adapter = createAdapter();
        recyclerView.setAdapter(adapter);
        refreshOrLoadMore();
    }

    protected abstract boolean isRefresh();

    protected abstract boolean isLoadMore();

    public abstract A createAdapter();

    public RecyclerView.LayoutManager setRecyclerLayoutManager() {
        return new LinearLayoutManager(getCoreActivity());
    }

    @Override
    public void onRefreshData(int page, List<T> data) {
        if (page == 1) {
            if (data.size() == 0 && adapter.getItemCount() == 0) {
                empty.setVisibility(View.VISIBLE);
                refreshLayout.setVisibility(View.GONE);
            }
            if (data.size() > 0 && adapter.getItemCount() > 0) {
                adapter.clear();
            }
            showRefreshLayout();
            adapter.addData(data);
        } else {
            adapter.addData(data);
        }
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    private void refreshOrLoadMore() {
        getPresenter().requestPageData(currentPage);
        if (currentPage == 1) {
            refreshLayout.finishRefresh(setTimeOut());
        } else {
            refreshLayout.finishLoadMore(setTimeOut());
        }
        currentPage++;
    }

    private void showRefreshLayout() {
        empty.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
    }

    public int setTimeOut() {
        return DEFAULT_TIME_OUT;
    }


    public void addCustomHeader(FrameLayout headerContainer) {

    }

}
