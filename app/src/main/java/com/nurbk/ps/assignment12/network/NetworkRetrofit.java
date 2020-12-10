package com.nurbk.ps.assignment12.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRetrofit {
    private static final String BASE_URL = "https://www.flickr.com/services/";


    private final PhotosApiInterface photosApiInterface;

    private NetworkRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().
                addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        photosApiInterface = retrofit.create(PhotosApiInterface.class);
    }

    private static NetworkRetrofit instance = null;

    public static NetworkRetrofit getInstance() {
        if (instance == null)
            instance = new NetworkRetrofit();
        return instance;
    }

    public PhotosApiInterface getPhotosApiInterface() {
        return photosApiInterface;
    }

}
