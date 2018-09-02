package com.prominente.android.viaticgo.interfaces;

import android.content.Context;

import com.prominente.android.viaticgo.models.Currency;

import java.util.ArrayList;

public interface ICurrencyRepository {
    void syncCurrencies(Context context, ArrayList<Currency> currencies);

    ArrayList<Currency> getAllCurrencies(Context context);
}
