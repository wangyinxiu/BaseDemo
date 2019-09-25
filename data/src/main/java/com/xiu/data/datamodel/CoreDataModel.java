package com.xiu.data.datamodel;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CoreDataModel {

    public  String TAG = getClass().getSimpleName();

    protected static <T> void doRequest(Observable<T> observable, ObservableEmitter<T> emitter) {
        observable.subscribeOn(Schedulers.io()).subscribe(new DisposableObserver<T>() {
            @Override
            public void onNext(T t) {
                emitter.onNext(t);
            }

            @Override
            public void onError(Throwable e) {
                emitter.tryOnError(e);
            }

            @Override
            public void onComplete() {
                emitter.onComplete();
            }
        });
    }

    protected static <T> void doRequest(Observable<T> observable,
                                        DisposableObserver<T> disposableObserver) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
    }


}
