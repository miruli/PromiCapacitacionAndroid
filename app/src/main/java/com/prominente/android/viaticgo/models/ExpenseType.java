package com.prominente.android.viaticgo.models;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.io.Serializable;

@Table
public class ExpenseType implements Serializable {

    @SerializedName("TipoDeTicketId")
    @Unique
    private int expenseTypeId;
    @SerializedName("Descripcion")
    private String description;

    public ExpenseType(){

    }

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
