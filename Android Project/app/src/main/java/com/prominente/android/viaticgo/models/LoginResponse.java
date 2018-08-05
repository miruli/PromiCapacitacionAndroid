package com.prominente.android.viaticgo.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("Code")
    private int code;
    @SerializedName("Message")
    private String message;
    @SerializedName("Data")
    private LoggedUser data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoggedUser getData() {
        return data;
    }

    public void setData(LoggedUser data) {
        this.data = data;
    }
}
