package com.prominente.android.viaticgo.models;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;
import java.io.Serializable;

@Table
public class Currency implements Serializable{

    @SerializedName("MonedaId")
    @Unique
    private int currencyId;
    private String symbol;
    @SerializedName("Descripcion")
    private String description;

    public Currency() {

    }

    public Currency(int currencyId, String symbol, String description) {
        this.currencyId = currencyId;
        this.symbol = symbol;
        this.description = description;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
