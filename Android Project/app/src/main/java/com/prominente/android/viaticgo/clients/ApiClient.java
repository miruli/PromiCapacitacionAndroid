package com.prominente.android.viaticgo.clients;

import android.content.Context;
import com.prominente.android.viaticgo.data.LocalStorageRepository;
import com.prominente.android.viaticgo.interfaces.IApiClient;
import com.prominente.android.viaticgo.interfaces.ILoggedUserRepository;
import com.prominente.android.viaticgo.models.Currency;
import com.prominente.android.viaticgo.models.ExpenseType;
import com.prominente.android.viaticgo.models.LoggedUser;
import com.prominente.android.viaticgo.models.LoginResponse;
import com.prominente.android.viaticgo.models.ServiceLine;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {
    private static ApiClient instance;
    private ILoggedUserRepository loggedUserRepository;
    private Context context;

    private ApiClient() {
        if (instance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public void setLoggedUserRepository(ILoggedUserRepository loggedUserRepository) {
        this.loggedUserRepository = loggedUserRepository;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static synchronized ApiClient getInstance(Context context) {
        if (instance == null)
            instance = new ApiClient();
        instance.setContext(context);
        instance.setLoggedUserRepository(LocalStorageRepository.getInstance());
        return instance;
    }

    private Retrofit buildRetrofit() {
        final LoggedUser loggedUser = loggedUserRepository.loadLoggedUser(context);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + loggedUser.getToken())
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl("http://demo.pectra.com/rendiciones/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private Retrofit buildRetrofitNoToken() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl("http://demo.pectra.com/rendiciones/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public LoginResponse login(String userName, String password) {
        LoginResponse loginResponse;
        try {
            Retrofit retrofit = buildRetrofitNoToken();
            IApiClient client = retrofit.create(IApiClient.class);
            Call<LoginResponse> loginCall = client.login(userName, password, "password");
            retrofit2.Response<LoginResponse> response = loginCall.execute();

            if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                Converter<ResponseBody, LoginResponse> errorConverter = retrofit.responseBodyConverter(LoginResponse.class, new Annotation[0]);
                loginResponse = errorConverter.convert(response.errorBody());
            } else
                loginResponse = response.body();
        } catch (Exception e) {
            loginResponse = new LoginResponse();
            loginResponse.setError(e.getClass().getName());
            loginResponse.setErrorDescription(e.getMessage());
        }
        loginResponse.setUserName(userName);
        return loginResponse;
    }

    public ArrayList<ServiceLine> fetchAllServiceLines() {
        try {
            Retrofit retrofit = buildRetrofit();
            IApiClient client = retrofit.create(IApiClient.class);
            Call<ArrayList<ServiceLine>> getAllServiceLinesCall = client.getAllServiceLines();
            return getAllServiceLinesCall.execute().body();
        } catch (Exception e) {
/*            loginResponse = new LoginResponse();
            loginResponse.setError(e.getClass().getName());
            loginResponse.setErrorDescription(e.getMessage());*/
        }
        return null;
    }

    public ArrayList<Currency> fetchAllCurrencies() {
        try {
            Retrofit retrofit = buildRetrofit();
            IApiClient client = retrofit.create(IApiClient.class);
            Call<ArrayList<Currency>> getAllCurrenciesCall = client.getAllCurrencies();
            return getAllCurrenciesCall.execute().body();
        } catch (Exception e) {
/*            loginResponse = new LoginResponse();
            loginResponse.setError(e.getClass().getName());
            loginResponse.setErrorDescription(e.getMessage());*/
        }
        return null;
    }

    public ArrayList<ExpenseType> fetchAllExpenseTypes() {
        try {
            Retrofit retrofit = buildRetrofit();
            IApiClient client = retrofit.create(IApiClient.class);
            Call<ArrayList<ExpenseType>> getAllCurrenciesCall = client.getAllExpenseTypes();
            return getAllCurrenciesCall.execute().body();
        } catch (Exception e) {
/*            loginResponse = new LoginResponse();
            loginResponse.setError(e.getClass().getName());
            loginResponse.setErrorDescription(e.getMessage());*/
        }
        return null;
    }
}
