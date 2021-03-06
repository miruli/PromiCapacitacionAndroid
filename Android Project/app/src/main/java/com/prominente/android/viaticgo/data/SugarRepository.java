package com.prominente.android.viaticgo.data;

import android.content.Context;

import com.orm.SugarRecord;
import com.prominente.android.viaticgo.interfaces.ICurrencyRepository;
import com.prominente.android.viaticgo.interfaces.IExpenseTypeRepository;
import com.prominente.android.viaticgo.interfaces.IExpensesRepository;
import com.prominente.android.viaticgo.interfaces.IServiceLineRepository;
import com.prominente.android.viaticgo.interfaces.ITripRepository;
import com.prominente.android.viaticgo.interfaces.ISurrenderRepository;
import com.prominente.android.viaticgo.models.Currency;
import com.prominente.android.viaticgo.models.Expense;
import com.prominente.android.viaticgo.models.ExpenseType;
import com.prominente.android.viaticgo.models.ServiceLine;
import com.prominente.android.viaticgo.models.Surrender;
import com.prominente.android.viaticgo.models.Trip;

import java.util.ArrayList;
import java.util.List;

public class SugarRepository implements IServiceLineRepository, ICurrencyRepository, IExpenseTypeRepository,
                                        IExpensesRepository, ITripRepository, ISurrenderRepository {
    private static SugarRepository instance;

    private SugarRepository() {
        if (instance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static SugarRepository getInstance() {
        if (instance == null)
            instance = new SugarRepository();
        return instance;
    }

    @Override
    public void syncServiceLines(Context context, ArrayList<ServiceLine> serviceLines) {
        for (ServiceLine serverServiceLine:serviceLines) {
            List<ServiceLine> serviceLinesWithId = SugarRecord.find(ServiceLine.class, "SERVICE_LINE_ID = ?", Integer.toString(serverServiceLine.getServiceLineId()));
            if (serviceLinesWithId != null && serviceLinesWithId.size() > 0) {
                if (serviceLinesWithId.size() == 1) {
                    ServiceLine serviceLine = serviceLinesWithId.get(0);
                    serviceLine.setDescription(serverServiceLine.getDescription());
                    SugarRecord.save(serviceLine);
                }
                else {
                    //TODO: preguntar si cual de las dos
                    //throw new RuntimeException("duplicated ids");
                    SugarRecord.deleteAll(ServiceLine.class, "SERVICE_LINE_ID = ?", Integer.toString(serverServiceLine.getServiceLineId()));
                    SugarRecord.save(serverServiceLine);
                }
            }
            else {
                SugarRecord.save(serverServiceLine);
            }
        }
    }

    @Override
    public ArrayList<ServiceLine> getAllServiceLines(Context context) {
        ArrayList<ServiceLine> toReturn = new ArrayList<>();
        toReturn.addAll(SugarRecord.listAll(ServiceLine.class));
        return toReturn;
    }

    @Override
    public void syncCurrencies(Context context, ArrayList<Currency> currencies) {
        for (Currency serverCurrency:currencies) {
            List<Currency> currenciesWithId = SugarRecord.find(Currency.class, "CURRENCY_ID = ?", Integer.toString(serverCurrency.getCurrencyId()));
            if (currenciesWithId != null && currenciesWithId.size() > 0) {
                if (currenciesWithId.size() == 1) {
                    Currency currency = currenciesWithId.get(0);
                    currency.setDescription(serverCurrency.getDescription());
                    String symbol = serverCurrency.getSymbol();
                    if (symbol == null)
                        symbol = "$";
                    currency.setSymbol(symbol);
                    SugarRecord.save(currency);
                }
                else {
                    SugarRecord.deleteAll(Currency.class, "CURRENCY_ID = ?", Integer.toString(serverCurrency.getCurrencyId()));
                    SugarRecord.save(serverCurrency);
                }
            }
            else {
                SugarRecord.save(serverCurrency);
            }
        }
    }

    @Override
    public ArrayList<Currency> getAllCurrencies(Context context) {
        ArrayList<Currency> toReturn = new ArrayList<>();
        toReturn.addAll(SugarRecord.listAll(Currency.class));
        return toReturn;
    }

    @Override
    public void syncExpenseTypes(Context context, ArrayList<ExpenseType> expenseTypes) {
        for (ExpenseType serverExpenseType:expenseTypes) {
            List<ExpenseType> expenseTypesWithId = SugarRecord.find(ExpenseType.class, "EXPENSE_TYPE_ID = ?", Integer.toString(serverExpenseType.getExpenseTypeId()));
            if (expenseTypesWithId != null && expenseTypesWithId.size() > 0) {
                if (expenseTypesWithId.size() == 1) {
                    ExpenseType expenseType = expenseTypesWithId.get(0);
                    expenseType.setDescription(serverExpenseType.getDescription());
                    SugarRecord.save(expenseType);
                }
                else {
                    SugarRecord.deleteAll(Currency.class, "EXPENSE_TYPE_ID = ?", Integer.toString(serverExpenseType.getExpenseTypeId()));
                    SugarRecord.save(serverExpenseType);
                }
            }
            else {
                SugarRecord.save(serverExpenseType);
            }
        }
    }

    @Override
    public ArrayList<ExpenseType> getAllExpenseTypes(Context context) {
        ArrayList<ExpenseType> toReturn = new ArrayList<>();
        toReturn.addAll(SugarRecord.listAll(ExpenseType.class));
        return toReturn;
    }

    @Override
    public void syncTrips(Context context, ArrayList<Trip> trips) {
        for (Trip serverTrip:trips) {
            List<Trip> serviceTripsWithId = SugarRecord.find(Trip.class, "TRIP_ID = ?", Integer.toString(serverTrip.getTripId()));
            if (serviceTripsWithId != null && serviceTripsWithId.size() > 0) {
                if (serviceTripsWithId.size() == 1) {
                    Trip trip = serviceTripsWithId.get(0);
                    trip.setDescription(serverTrip.getDescription());
                    SugarRecord.save(trip);
                }
                else {
                    //TODO: preguntar si cual de las dos
                    //throw new RuntimeException("duplicated ids");
                    SugarRecord.deleteAll(Trip.class, "TRIP_ID = ?", Integer.toString(serverTrip.getTripId()));
                    SugarRecord.save(serverTrip);
                }
            }
            else {
                SugarRecord.save(serverTrip);
            }
        }
    }

    @Override
    public ArrayList<Trip> getAllTrips(Context context) {
        ArrayList<Trip> toReturn = new ArrayList<>();
        toReturn.addAll(SugarRecord.listAll(Trip.class));
        return toReturn;
    }

    @Override
    public void saveExpense(Context context, Expense expense) {
        SugarRecord.save(expense);
    }

    @Override
    public void updateExpense(Context context, Expense expense) {
        Expense expenseToUpdate = SugarRecord.findById(Expense.class, expense.getId());
        expenseToUpdate.setDescription(expense.getDescription());
        expenseToUpdate.setAmount(expense.getAmount());
        expenseToUpdate.setCurrency(expense.getCurrency());
        expenseToUpdate.setDate(expense.getDate());
        expenseToUpdate.setSelected(expense.getSelected());
        expenseToUpdate.setServiceLine(expense.getServiceLine());
        expenseToUpdate.setType(expense.getType());
        expenseToUpdate.setImageUri(expense.getImageUri());
        expenseToUpdate.setSurrenderId(expense.getSurrenderId());
        SugarRecord.save(expenseToUpdate);
    }

    @Override
    public void deleteExpense(Context context, Expense expense) {
        SugarRecord.delete(expense);
    }

    @Override
    public ArrayList<Expense> loadExpenses(Context context) {
        ArrayList<Expense> toReturn = new ArrayList<>();
        List<Expense> list = SugarRecord.find(Expense.class, "SURRENDER_ID is null");
        toReturn.addAll(list);
        return toReturn;
    }

    @Override
    public Boolean saveSurrender(Context context, Surrender surrender) {
        SugarRecord.save(surrender);
        for(Expense expense : surrender.getExpensesList())
        {
            expense.setSurrenderId(surrender.getId());
            SugarRecord.save(expense);
        }
        return true;
    }

    @Override
    public void deleteSurrender(Context context, Surrender surrender) {
        SugarRecord.delete(surrender);
        SugarRecord.deleteAll(Expense.class, "SURRENDER_ID = ?", Long.toString(surrender.getId()));
    }

    @Override
    public ArrayList<Surrender> getAllSurrenders(Context context) {
        ArrayList<Surrender> toReturn = new ArrayList<>();
        toReturn.addAll(SugarRecord.listAll(Surrender.class));
        return toReturn;
    }
}
