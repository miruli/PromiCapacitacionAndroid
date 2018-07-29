package com.grupoprominente.payme.Model;

import java.io.Serializable;

public class User implements Serializable {



    private String sName;
    private String sPass;
    private String sToken;


    public String getName() {
        return sName;
    }

    public void setName(String sName) {
        this.sName = sName;
    }

    public String getPass() {
        return sPass;
    }

    public void setPass(String sPass) {
        this.sPass = sPass;
    }

    public String getToken() {
        return sToken;
    }

    public void setToken(String sToken) {
        this.sToken = sToken;
    }

}
