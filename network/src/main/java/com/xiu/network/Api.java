package com.xiu.network;


import com.xiu.network.bean.params.InformationParams;
import com.xiu.network.bean.params.LoginParams;
import com.xiu.network.bean.response.Information;
import com.xiu.network.bean.response.Token;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {


    @POST(BuildConfig.DOMAIN1 + "/sk/account/login")
    Observable<Token> login(@Body LoginParams body);

    @POST(BuildConfig.DOMAIN1 + "/sk/account/login")
    Observable<List<Information>> getInformationList(@Body InformationParams body);
}
