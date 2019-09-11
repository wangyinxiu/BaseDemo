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

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xiu.core.app.mvp.MvpPresenter;
import com.xiu.core.app.mvp.MvpView;


/**
 * * The default implementation of {@link FragmentMvpDelegate}
 *
 * @param <V> The type of {@link MvpView}
 * @param <P> The type of {@link MvpPresenter}
 * @author Hannes Dorfmann
 * @see FragmentMvpDelegate
 * @since 1.1.0
 */
public class FragmentMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
    implements FragmentMvpDelegate<V, P> {

  protected MvpDelegateCallback<V, P> delegateCallback;
  protected MvpInternalDelegate<V, P> internalDelegate;

  public FragmentMvpDelegateImpl(MvpDelegateCallback<V, P> delegateCallback) {
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
  public void onCreate(Bundle saved) {

  }

  @Override
  public void onDestroy() {

  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    getInternalDelegate().createPresenter();
    getInternalDelegate().attachView();
  }

  @Override
  public void onDestroyView() {
    getInternalDelegate().detachView();
  }

  @Override
  public void onPause() {

  }

  @Override
  public void onResume() {

  }

  @Override
  public void onStart() {

  }

  @Override
  public void onStop() {

  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {

  }

  @Override
  public void onAttach(Activity activity) {

  }

  @Override
  public void onDetach() {

  }

  @Override
  public void onSaveInstanceState(Bundle outState) {

  }
}
