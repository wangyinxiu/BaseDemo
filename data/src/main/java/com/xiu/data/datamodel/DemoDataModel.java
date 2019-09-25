package com.xiu.data.datamodel;

import com.xiu.data.bean.list.InformationList;
import com.xiu.data.bean.params.InformationParams;
import com.xiu.data.bean.response.Information;
import com.xiu.data.core.DaoManager;
import com.xiu.data.core.Network;
import com.xiu.data.greendao.InformationDao;
import com.xiu.utils.LogUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DemoDataModel extends CoreDataModel {

    private static final String TAG = "DemoDataModel";
    /**
     * 按需加载网络数据或本地数据
     *
     * @param disposableObserver
     */
    public static void requestInformation(int page,
                                          final DisposableObserver<List<Information>> disposableObserver) {

        Observable.create((ObservableOnSubscribe<List<Information>>) emitter -> {

                    InformationDao dao = DaoManager.getInstance().getDaoSession().getInformationDao();
                    List<Information> informations = dao.queryBuilder()
                            .offset(page * 20)
                            .limit(20)
                            .orderAsc(InformationDao.Properties.Id)
                            .build().list();
                    if (informations == null || informations.size() == 0) {
                        doRequest(Network.getApi().getInformationList(new InformationParams("", page)),
                                new DisposableObserver<InformationList>() {
                                    @Override
                                    public void onNext(InformationList data) {
                                        LogUtil.i(TAG,"load by network");
                                        List<Information> list = data.getInformationList();
                                        if (list != null && list.size() > 0) {
                                            dao.insertOrReplaceInTx(list);
                                        }
                                        emitter.onNext(list);
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
                    }else {
                        LogUtil.i(TAG,"load by green dao");
                        emitter.onNext(informations);
                        emitter.onComplete();
                    }
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }





}
