package com.gp.android.fragments.model;

import java.io.Serializable;

public class Sponsor implements Serializable
{
    private String name;
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
