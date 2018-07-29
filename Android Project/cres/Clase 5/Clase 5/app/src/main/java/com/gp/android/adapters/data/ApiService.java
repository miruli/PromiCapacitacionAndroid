package com.gp.android.adapters.data;

import com.gp.android.adapters.model.Sponsor;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.HTTP;

public interface ApiService
{
    @HTTP(method = "GET", path = "EventoBasico/GetEmpresasEventoBasico")
    Call<ArrayList<Sponsor>> getSponsors();
}
