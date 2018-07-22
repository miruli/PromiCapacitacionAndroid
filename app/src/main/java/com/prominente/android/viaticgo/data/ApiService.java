package com.prominente.android.viaticgo.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiService implements IApiService {
    private static ApiService instance;

    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://domain/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private ApiService()
    {
        if (instance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized ApiService getInstance(){
        if (instance == null)
            instance = new ApiService();
        return instance;
    }


    public void login(){

    }
}
