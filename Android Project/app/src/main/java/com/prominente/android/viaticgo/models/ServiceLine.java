package com.prominente.android.viaticgo.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceLine implements Serializable {
    @SerializedName("LineaDeServicioId")
    private int serviceLineId;
    @SerializedName("Descripcion")
    private String description;

    public ServiceLine(int serviceLineId, String description) {
        this.serviceLineId = serviceLineId;
        this.description = description;
    }

    public int getServiceLineId() {
        return serviceLineId;
    }

    public void setServiceLineId(int serviceLineId) {
        this.serviceLineId = serviceLineId;
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
