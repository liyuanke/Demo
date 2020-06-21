package com.lyk.data.http;

import com.lyk.data.common.Configs;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class Http {

    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;

    //构造方法私有
    private Http() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Configs.base_url)
                .build();
    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final Http INSTANCE = new Http();
    }

    //获取单例
    public static Http getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
