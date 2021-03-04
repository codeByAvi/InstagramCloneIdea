package com.skill.aboutmy;

import android.app.Application;

import com.skill.aboutmy.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static MyApplication myApplication;
    private static int REQUEST_TIMEOUT = 10;

    private Retrofit retrofitApiInstance, retrofitGraphInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication =this;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        retrofitApiInstance = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                        .build())
                .build();

        retrofitGraphInstance = new Retrofit.Builder()
                .baseUrl(Constants.BASE_GRAPH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                        .build())
                .build();
    }

    public static synchronized MyApplication getContext() {
        return myApplication;
    }

    public Retrofit getRetrofitApiInstance() {
        return retrofitApiInstance;
    }

    public Retrofit getRetrofitGraphInstance() {
        return retrofitGraphInstance;
    }

}
