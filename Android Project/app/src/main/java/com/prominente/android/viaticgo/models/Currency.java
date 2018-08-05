package com.prominente.android.viaticgo.models;

import java.io.Serializable;

public class Currency implements Serializable{
    private int currencyId;
    private char symbol;
    private String description;

    public Currency(int currencyId, char symbol, String description) {
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

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
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
