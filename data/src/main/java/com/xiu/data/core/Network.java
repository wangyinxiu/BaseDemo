package com.xiu.data.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xiu.data.api.Api;
import com.xiu.data.BuildConfig;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    public static final String DILIMIT = "&#-#&";

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String VALUE_CONTENT_TYPE = "application/json";

    private static final String HEADER_CHARSET = "charset";
    private static final String VALUE_CHARSET = "UTF-8";

    private static final String HEADER_ACCEPT = "Accept";
    private static final String VALUE_ACCEPT = "application/json";

    private static final String HEADER_TOKEN = "token";
    private static final String HEADER_TIME = "time";
    private static final String HEADER_REQUEST_ID = "requestId";

    private Api api;
    private volatile static Network singleton;
    private static final int TIME_OUT = 30;
    private Cache cache;

    private Network() {
        createRetrofit();
    }

    public static Network getInstance() {
        if (singleton == null) {
            synchronized (Network.class) {
                if (singleton == null) {
                    singleton = new Network();
                }
            }
        }
        return singleton;
    }

    public static Api getApi() {
        return getInstance().api;
    }

    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.registerTypeAdapterFactory(new ResponseTypeAdapterFactory());
        return builder.create();

    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(addQueryParameterInterceptor);
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        builder.retryOnConnectionFailure(true);
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        return builder.build();
    }

    private void createRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.addConverterFactory(GsonConverterFactory.create(createGson()));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.client(createOkHttpClient());
        builder.baseUrl(BuildConfig.BASE_URL);
        api = builder.build().create(Api.class);
    }

    private final Interceptor addQueryParameterInterceptor = chain -> {
        Request originalRequest = chain.request();
        Headers.Builder headers = originalRequest.headers().newBuilder();
        headers.add(HEADER_CONTENT_TYPE, VALUE_CONTENT_TYPE);
        headers.add(HEADER_CHARSET, VALUE_CHARSET);
        headers.add(HEADER_ACCEPT, VALUE_ACCEPT);
        headers.add(HEADER_REQUEST_ID, UUID.randomUUID().toString());
        headers.add(HEADER_TOKEN, cache.get(Cache.Key.KEY_TOKEN, ""));
        headers.add(HEADER_TIME, String.valueOf(System.currentTimeMillis()));
        String modifiedUrl = originalRequest.url().toString();
        Request request = originalRequest.newBuilder().url(modifiedUrl).headers(headers.build()).build();
        return chain.proceed(request);
    };


    public  void setCache(Cache cache) {
        this.cache = cache;
    }
}
