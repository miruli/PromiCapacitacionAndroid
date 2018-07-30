package com.prominente.android.viaticgo.clients;

import com.prominente.android.viaticgo.interfaces.IApiClient;
import com.prominente.android.viaticgo.models.LoggedUser;
import com.prominente.android.viaticgo.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {
    private static ApiClient instance;

    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.0.53/api/")
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

    public LoginResponse login(String userName, String password) {
        try {
            Retrofit retrofit = buildRetrofit();
            IApiClient client = retrofit.create(IApiClient.class);
            Call<LoginResponse> loginCall = client.login(userName, password);
            LoginResponse loginResponse = loginCall.execute().body();
            return loginResponse;
        }
        catch (Exception e) {
            e.toString();
        }
        return null;
    }
}
