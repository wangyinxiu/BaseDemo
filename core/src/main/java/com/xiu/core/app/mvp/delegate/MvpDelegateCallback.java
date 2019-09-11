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
 * The MvpDelegate callback that will be called from {@link ActivityMvpDelegate} or {@link
 * FragmentMvpDelegate} or {@link ViewGroupMvpDelegate}. This interface must be implemented by all
 * Activity,
 * Fragment or android.view.View that you want to support mosbys mvp.y
 *
 * @param <V> The type of {@link MvpView}
 * @param <P> The type of {@link MvpPresenter}
 * @author Hannes Dorfmann
 * @since 1.1.0
 */
public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {

  /**
   * Creates the presenter getInstance
   *
   * @return the created presenter getInstance
   */
  P createPresenter();

  /**
   * Get the presenter. If null is returned, then a internally a new presenter getInstance gets
   * created
   * by calling {@link #createPresenter()}
   *
   * @return the presenter getInstance. can be null.
   */
  P getPresenter();

  /**
   * Sets the presenter getInstance
   *
   * @param presenter The presenter getInstance
   */
  void setPresenter(P presenter);

  /**
   * Get the MvpView for the presenter
   *
   * @return The view associated with the presenter
   */
  V getMvpView();

  /**
   * Is the view retaining? This boolean flag is used for {@link MvpPresenter#detachView(boolean)}
   * as parameter.
   *
   * @return true if the view is retaining, hence the presenter should be retaining as well.
   */
  boolean isRetainingInstance();
}
