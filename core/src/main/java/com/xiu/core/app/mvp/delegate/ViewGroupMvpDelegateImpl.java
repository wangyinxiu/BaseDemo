/*
 * Copyright 2015 Hannes Dorfmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiu.core.app.mvp.delegate;


import com.xiu.core.app.mvp.MvpPresenter;
import com.xiu.core.app.mvp.MvpView;

/**
 * The default implementation of {@link ViewGroupMvpDelegate}
 *
 * @author Hannes Dorfmann
 * @see ViewGroupMvpDelegate
 * @since 1.1.0
 */
public class ViewGroupMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
    implements ViewGroupMvpDelegate<V, P> {

  protected MvpDelegateCallback<V, P> delegateCallback;
  protected MvpInternalDelegate<V, P> internalDelegate;

  public ViewGroupMvpDelegateImpl(MvpDelegateCallback<V, P> delegateCallback) {
    if (delegateCallback == null) {
      throw new NullPointerException("MvpDelegateCallback is null!");
    }
    this.delegateCallback = delegateCallback;
  }

  protected MvpInternalDelegate<V, P> getInternalDelegate() {
    if (internalDelegate == null) {
      internalDelegate = new MvpInternalDelegate<>(delegateCallback);
    }

    return internalDelegate;
  }

  @Override
  public void onAttachedToWindow() {
    getInternalDelegate().createPresenter();
    getInternalDelegate().attachView();
  }

  @Override
  public void onDetachedFromWindow() {
    getInternalDelegate().detachView();
  }
}
