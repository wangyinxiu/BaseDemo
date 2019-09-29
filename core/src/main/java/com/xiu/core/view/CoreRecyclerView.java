package com.xiu.core.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xiu.core.R;
import com.xiu.core.app.adapter.CoreAdapter;
import com.xiu.core.app.core.CoreActivity;
import com.xiu.core.app.core.recycler.MvpBaseRecyclerView;

import java.util.List;

public class CoreRecyclerView extends FrameLayout {

    private static final int DEFAULT_TIME_OUT = 30;
    private CoreRecyclerConfig coreRecyclerConfig;
    private int currentPage = 1;
    private CoreAdapter adapter;
    private SmartRefreshLayout refreshLayout;
    private View empty;

    public CoreRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public CoreRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoreRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.activity_list, this);
    }


    public <T> void setConfig(CoreRecyclerConfig<T> config) {
        this.coreRecyclerConfig = config;
        init();
    }

    private void init() {
        refreshLayout = findViewById(R.id.refresh_container);
        refreshLayout.setVisibility(View.VISIBLE);
        empty = findViewById(R.id.empty_container);
        empty.setVisibility(View.GONE);
        refreshLayout.setEnableRefresh(coreRecyclerConfig.isRefresh());
        refreshLayout.setEnableLoadMore(coreRecyclerConfig.isLoadMore());
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
        coreRecyclerConfig.addCustomHeader(findViewById(R.id.header_container));
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(coreRecyclerConfig.setRecyclerLayoutManager());
        adapter = coreRecyclerConfig.createRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        refreshOrLoadMore();

    }


    public void onRefreshData(int page, List data) {
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
        coreRecyclerConfig.requestPageData(currentPage);
        if (currentPage == 1) {
            refreshLayout.finishRefresh(setTimeOut());
        } else {
            refreshLayout.finishLoadMore(setTimeOut());
        }
        currentPage++;
    }

    public int setTimeOut() {
        return DEFAULT_TIME_OUT;
    }


    private void showRefreshLayout() {
        empty.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
    }


    public abstract static class CoreRecyclerConfig<T> implements MvpBaseRecyclerView<T> {

        private CoreRecyclerView coreRecyclerView;

        public CoreRecyclerConfig(CoreRecyclerView coreRecyclerView) {
            this.coreRecyclerView = coreRecyclerView;
        }


        protected abstract CoreAdapter<T> createRecyclerAdapter();


        public abstract void requestPageData(int page);


        public RecyclerView.LayoutManager setRecyclerLayoutManager() {
            return new LinearLayoutManager(coreRecyclerView.getContext());
        }



        public void addCustomHeader(FrameLayout headerContainer) {

        }

        @Override
        public void onRefreshData(int page, List<T> data) {
            coreRecyclerView.onRefreshData(page, data);
        }

        @Override
        public CoreActivity getCoreActivity() {
            return (CoreActivity) coreRecyclerView.getContext();
        }


        protected boolean isRefresh() {
            return true;
        }

        protected boolean isLoadMore() {
            return true;
        }

    }


}
