package com.xiu.core.app.mvp;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * A base implementation of a {@link MvpPresenter} that uses a <b>WeakReference</b> for referring
 * to the attached view.
 * <p>
 * You should always com.gapday.check {@link #isViewAttached()} to com.gapday.check if the view is attached to this
 * presenter before calling {@link #getView()} to access the view.
 * </p>
 *
 * @param <V> type of the {@link MvpView}
 * @author Hannes Dorfmann
 * @see MvpNullObjectBasePresenter
 * @since 1.0.0
 */
public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    protected Activity context;
    private WeakReference<V> viewRef;

    public MvpBasePresenter() {

    }


    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    /**
     * Get the attached view. You should always call {@link #isViewAttached()} to com.gapday.check if the view
     * is
     * attached to avoid NullPointerExceptions.
     *
     * @return <code>null</code>, if view is not attached, otherwise the concrete view getInstance
     */
    @Nullable
    protected V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    /**
     * Checks if a view is attached to this presenter. You should always call this method before
     * calling {@link #getView()} to get the view getInstance.
     */
    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
        context = null;
    }

    //    protected Api getApi(){
    //        return Network.getService();
    //    }


    public Activity getContext() {
        return context;
    }



}
