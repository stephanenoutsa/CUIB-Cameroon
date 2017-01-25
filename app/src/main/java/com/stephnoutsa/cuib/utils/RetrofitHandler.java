package com.stephnoutsa.cuib.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by stephnoutsa on 10/18/16.
 */

public class RetrofitHandler {

    // Trailing slash is needed
    private String BASE_URL = "http://192.168.43.170:8080/cuib/webapi/"; // Localhost value is 10.0.2.2
    //private String BASE_URL = "http://10.0.2.2:8080/cuib/webapi/"; // Localhost value is 10.0.2.2

    private Retrofit retrofit ;
    final private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

    public RetrofitHandler() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public RetrofitHandler(String BASE_URL) {
        this.BASE_URL = BASE_URL;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public CuibService create() {
        return retrofit.create(CuibService.class);
    }

}
