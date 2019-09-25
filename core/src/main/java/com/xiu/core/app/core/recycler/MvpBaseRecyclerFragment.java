package com.xiu.core.app.core.recycler;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xiu.core.R;
import com.xiu.core.app.adapter.CoreAdapter;
import com.xiu.core.app.mvp.MvpFragment;

import java.util.List;

public abstract class MvpBaseRecyclerFragment<T, A extends CoreAdapter<T>, V extends MvpBaseRecyclerView<T>, P extends MvpBaseRecyclerPresenter<V>> extends MvpFragment<V, P> implements MvpBaseRecyclerView<T> {

    private static final int DEFAULT_TIME_OUT = 30;

    private A adapter;
    private int page = 1;
    private SmartRefreshLayout refreshLayout;
    private View empty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                page = 1;
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
        getPresenter().requestPageData(page);
        if (page == 1) {
            refreshLayout.finishRefresh(setTimeOut());
        } else {
            refreshLayout.finishLoadMore(setTimeOut());
        }
        page++;
    }

    private void showRefreshLayout() {
        empty.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
    }

    public int setTimeOut() {
        return DEFAULT_TIME_OUT;
    }


    /**
     * you must resize header container
     *
     * @param headerContainer
     */
    public void addCustomHeader(FrameLayout headerContainer) {
    }


}
