package com.prominente.android.viaticgo.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoggedUser implements Serializable {
    @SerializedName("UserName")
    private String userName;
    @SerializedName("Password")
    private String password;
    @SerializedName("Token")
    private String token;

    public LoggedUser(String userName, String password, String token) {
        this.userName = userName;
        this.password = password;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
