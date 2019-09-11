package com.xiu.core.app.core.recycler;

import com.xiu.core.app.mvp.MvpBasePresenter;

public abstract class MvpBaseRecyclerPresenter<V extends MvpBaseRecyclerView> extends MvpBasePresenter<V> {

    public abstract void requestPageData(final int page);

}
