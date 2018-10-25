package com.prominente.android.viaticgo.models;

import java.util.ArrayList;

public class Surrender extends ViaticGoModel {
    private ArrayList<Expense> expensesList;
    private int tripId;
    private Trip trip;

    public Surrender(){
    }

    public ArrayList<Expense> getExpensesList() {
        return expensesList;
    }

    public void setExpensesList(ArrayList<Expense> expensesList) {
        this.expensesList = expensesList;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
        this.tripId = trip.getTripId();
    }
}
