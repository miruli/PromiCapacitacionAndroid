package com.prominente.android.viaticgo.interfaces;

import com.prominente.android.viaticgo.models.LoggedUser;
import com.prominente.android.viaticgo.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface IApiClient {
    @HTTP(method = "GET", path = "Login")
    Call<LoginResponse> login(@Query("userName") String userName, @Query("password") String password);
}
