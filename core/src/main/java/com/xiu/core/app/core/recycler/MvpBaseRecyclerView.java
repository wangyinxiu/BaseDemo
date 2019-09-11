package com.xiu.core.app.core.recycler;

import com.xiu.core.app.mvp.MvpView;

import java.util.List;

public interface MvpBaseRecyclerView<T> extends MvpView {

    void onRefreshData(int page, List<T> data);

}
