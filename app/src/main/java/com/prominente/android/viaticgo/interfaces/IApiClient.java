package com.prominente.android.viaticgo.interfaces;

import com.prominente.android.viaticgo.models.LoggedUser;

import retrofit2.Call;
import retrofit2.http.HTTP;

public interface IApiClient {
    @HTTP(method = "GET", path = "dummy/login")
    Call<LoggedUser> login(String userName, String password);

}
