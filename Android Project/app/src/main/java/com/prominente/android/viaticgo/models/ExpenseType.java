package com.prominente.android.viaticgo.models;

import java.io.Serializable;

public class ExpenseType implements Serializable {
    private int expenseTypeId;
    private String description;

    public ExpenseType(int expenseTypeId, String description) {
        this.expenseTypeId = expenseTypeId;
        this.description = description;
    }

    public int getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
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
