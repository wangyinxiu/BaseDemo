package com.xiu.network.datamodel;

import com.xiu.core.utils.MemoryCache;
import com.xiu.greendao.InformationDao;
import com.xiu.network.bean.params.InformationParams;
import com.xiu.network.bean.response.Information;
import com.xiu.network.datamodel.database.DaoManager;
import com.xiu.network.net.Network;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DemoDataModel extends CoreDataModel {
    /**
     * 按需加载网络数据或本地数据
     *
     * @param params
     * @param disposableObserver
     */
    public static void requestInformation(int page,
                                          final DisposableObserver<List<Information>> disposableObserver) {

        Observable.create((ObservableOnSubscribe<List<Information>>) emitter -> {
            InformationDao dao = DaoManager.getInstance().getDaoSession().getInformationDao();
            List<Information> informationList =
                    dao.queryBuilder()
                            .offset(page * 20)
                            .limit(20)
                            .orderAsc(InformationDao.Properties.Id)
                            .build().list();
            if (informationList != null && informationList.size() > 0) {
                emitter.onNext(informationList);
                emitter.onComplete();
            } else {

                doRequest(Network.getApi().getInformationList(new InformationParams(page)),
                        new DisposableObserver<List<Information>>() {
                            @Override
                            public void onNext(List<Information> list) {
                                if (list != null && list.size() > 0) {
                                    dao.insertOrReplaceInTx(list);
                                }
                                disposableObserver.onNext(list);
                            }

                            @Override
                            public void onError(Throwable e) {
                                disposableObserver.onError(e);
                            }

                            @Override
                            public void onComplete() {
                                disposableObserver.onComplete();
                            }
                        });
//                doRequest(Network.getApi().getInformationList(new InformationParams()), emitter);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }


    public static void requestInformation1(int page,
                                           DisposableObserver<List<Information>> disposableObserver) {
        Observable.create((ObservableOnSubscribe<List<Information>>) emitter -> {
            List<Information> informationList = MemoryCache.get(0, null);
            if (informationList != null && informationList.size() > 0) {
                emitter.onNext(informationList);
                emitter.onComplete();
            } else {
                doRequest(Network.getApi().getInformationList(new InformationParams(page)),
                        emitter);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    public static void requestInformation2(int page,
                                           DisposableObserver<List<Information>> disposableObserver) {
        doRequest(Network.getApi().getInformationList(new InformationParams(page)), disposableObserver);
    }

    public static void requetInformation3(int page,
                                          DisposableObserver<List<Information>> disposableObserver) {
        List<Information> informationList = MemoryCache.get(0, null);
        disposableObserver.onNext(informationList);
        disposableObserver.onComplete();
    }


}
