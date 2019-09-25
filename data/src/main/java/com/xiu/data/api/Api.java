package com.xiu.data.api;


import com.xiu.data.BuildConfig;
import com.xiu.data.bean.list.InformationList;
import com.xiu.data.bean.params.InformationParams;
import com.xiu.data.bean.params.LoginParams;
import com.xiu.data.bean.response.Token;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {


    @POST(BuildConfig.DOMAIN1 + "/sk/account/login")
    Observable<Token> login(@Body LoginParams body);

    @POST(BuildConfig.DOMAIN2 + "/discover/information/officialArticleList")
    Observable<InformationList> getInformationList(@Body InformationParams body);
}
