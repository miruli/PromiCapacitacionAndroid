package com.prominente.android.viaticgo.interfaces;

import android.content.Context;

import com.prominente.android.viaticgo.models.Expense;

import java.util.ArrayList;

public interface IExpensesRepository {
    public void saveExpenses(Context context, ArrayList<Expense> expenses);

    public ArrayList<Expense> loadExpenses(Context context);
}
