package com.prominente.android.viaticgo.interfaces;

import com.prominente.android.viaticgo.models.Currency;
import com.prominente.android.viaticgo.models.ExpenseType;
import com.prominente.android.viaticgo.models.LoginResponse;
import com.prominente.android.viaticgo.models.ServiceLine;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IApiClient {
    @POST("login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("username") String userName, @Field("password") String password, @Field("grant_type") String grantType);

    @GET("LineaDeServicio/GetAll")
    Call<ArrayList<ServiceLine>> getAllServiceLines();

    @GET("Moneda/GetAll")
    Call<ArrayList<Currency>> getAllCurrencies();

    @GET("TipoDeTicket/GetAll")
    Call<ArrayList<ExpenseType>> getAllExpenseTypes();
}
