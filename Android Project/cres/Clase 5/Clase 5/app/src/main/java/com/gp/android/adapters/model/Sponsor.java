package com.gp.android.adapters.model;

import com.google.gson.annotations.SerializedName;

public class Sponsor
{
    @SerializedName("Nombre")
    private String name;
    @SerializedName("UrlLogo")
    private String urlLogo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }
}
