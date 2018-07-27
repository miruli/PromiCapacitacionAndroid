package com.prominente.android.viaticgo.clients;

import com.prominente.android.viaticgo.interfaces.IApiClient;
import com.prominente.android.viaticgo.models.LoggedUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient implements IApiClient {
    private static ApiClient instance;

    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://domain/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    private ApiClient()    {
        if (instance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }
    public static synchronized ApiClient getInstance(){
        if (instance == null)
            instance = new ApiClient();
        return instance;
    }

    @Override
    public Call<LoggedUser> login(String userName, String password) {
        //make call to api
        return null;
    }
}
