package com.prominente.android.viaticgo.interfaces;

import android.content.Context;

import com.prominente.android.viaticgo.models.Expense;

import java.util.ArrayList;

public interface IExpensesRepository {
    void saveExpense(Context context, Expense expense);

    void deleteExpenses(Context context, ArrayList<Expense> expenses);

    ArrayList<Expense> loadExpenses(Context context);
}
