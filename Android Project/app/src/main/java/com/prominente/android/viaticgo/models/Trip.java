package com.prominente.android.viaticgo.models;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.io.Serializable;

@Table
public class Trip implements Serializable {

    @SerializedName("TripId")
    @Unique
    private int tripId;
    @SerializedName("Descripcion")
    private String description;

    public Trip() {
    }

    public Trip(int tripId, String description) {
        this.tripId = tripId;
        this.description = description;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
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
