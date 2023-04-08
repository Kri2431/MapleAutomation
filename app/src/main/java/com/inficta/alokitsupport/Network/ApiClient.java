package com.inficta.alokitsupport.Network;


import com.inficta.alokitsupport.Network.CustomHttp;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String BASE_URL = "https://alokind.co.in/";

    public static ApiService getAPIService() {
        Retrofit.Builder retrofit = new Retrofit.Builder();
        retrofit
                .baseUrl(BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .client(customLogInterceptor())
                .build();
        return retrofit.build().create(ApiService.class);
    }

    private static OkHttpClient customLogInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new CustomHttp());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        Interceptor interceptor = chain -> {
            Request request;
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("version", "v5");
            request = requestBuilder.build();
            return chain.proceed(request);
        };
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient = okHttpClient.newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .connectTimeout(200, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }
}