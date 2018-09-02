package com.prominente.android.viaticgo.interfaces;

import android.content.Context;
import com.prominente.android.viaticgo.models.ExpenseType;
import java.util.ArrayList;

public interface IExpenseTypeRepository {
    void syncExpenseTypes(Context context, ArrayList<ExpenseType> expenseTypes);

    ArrayList<ExpenseType> getAllExpenseTypes(Context context);
}
