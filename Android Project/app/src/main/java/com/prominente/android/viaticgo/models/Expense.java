package com.prominente.android.viaticgo.models;

import com.orm.dsl.Table;
import java.io.Serializable;
import java.util.Date;

@Table
public class Expense implements Serializable {

    private String description;
    private double amount;
    private Date date;
    private ExpenseType type;
    private int expenseTypeId;
    private Currency currency;
    private int currencyId;
    private ServiceLine serviceLine;
    private int serviceLineId;
    private boolean selected;//TODO: revisar esto

    public Expense(){

    }

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
        this.expenseTypeId = type.getExpenseTypeId();
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        this.currencyId = currency.getCurrencyId();
    }

    public ServiceLine getServiceLine() {
        return serviceLine;
    }

    public void setServiceLine(ServiceLine serviceLine) {
        this.serviceLine = serviceLine;
        this.serviceLineId = serviceLine.getServiceLineId();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return  selected;
    }
}
