package com.prominente.android.viaticgo.data;

import android.content.Context;

import com.prominente.android.viaticgo.constants.SerializerKeys;
import com.prominente.android.viaticgo.interfaces.IExpensesRepository;
import com.prominente.android.viaticgo.interfaces.ILoggedUserRepository;
import com.prominente.android.viaticgo.models.Expense;
import com.prominente.android.viaticgo.models.LoggedUser;
import com.prominente.android.viaticgo.serializers.ObjectSerializer;

import java.util.ArrayList;

public final class LocalStorageRepository implements ILoggedUserRepository, IExpensesRepository {
    private static LocalStorageRepository instance;

    private LocalStorageRepository() {
        if (instance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static LocalStorageRepository getInstance() {
        if (instance == null)
            instance = new LocalStorageRepository();
        return instance;
    }

    public void saveLoggedUser(Context context, LoggedUser loggedUser) {
        ObjectSerializer.save(context, SerializerKeys.USER, loggedUser);
    }

    public LoggedUser loadLoggedUser(Context context) {
        return (LoggedUser) ObjectSerializer.load(context, SerializerKeys.USER);
    }

    public void saveExpenses(Context context, ArrayList<Expense> expenses) {
        ObjectSerializer.save(context, SerializerKeys.EXPENSES, expenses);
    }

    public ArrayList<Expense> loadExpenses(Context context) {
        ArrayList<Expense> toReturn = (ArrayList<Expense>) ObjectSerializer.load(context, SerializerKeys.EXPENSES);
        if (toReturn == null)
            toReturn = new ArrayList<>();
        return toReturn;
    }
}
