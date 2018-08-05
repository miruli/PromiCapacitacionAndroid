package com.prominente.android.viaticgo.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.adapters.ArrayIpAdapter;
import com.prominente.android.viaticgo.constants.ExtraKeys;
import com.prominente.android.viaticgo.data.LocalStorageRepository;
import com.prominente.android.viaticgo.fragments.DatePickerFragment;
import com.prominente.android.viaticgo.fragments.ItemPickerFragment;
import com.prominente.android.viaticgo.interfaces.IExpensesRepository;
import com.prominente.android.viaticgo.models.Currency;
import com.prominente.android.viaticgo.models.Expense;
import com.prominente.android.viaticgo.models.ExpenseType;
import com.prominente.android.viaticgo.models.ServiceLine;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpenseActivity extends LightDarkAppCompatActivity {
    private IExpensesRepository expensesRepository;
    private SaveExpenseTask saveExpenseTask;
    private AppCompatEditText txtDate;
    private DateFormat dateFormat;
    private ArrayIpAdapter<ExpenseType> typesAdapter;
    private ArrayIpAdapter<Currency> currenciesAdapter;
    private ArrayIpAdapter<ServiceLine> serviceLinesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_arrow_back);

        saveExpenseTask = new SaveExpenseTask(findViewById(R.id.appBarMain));
        expensesRepository = LocalStorageRepository.getInstance();
        txtDate = findViewById(R.id.txtDate);
        dateFormat = android.text.format.DateFormat.getDateFormat(this);

        final AppCompatButton btnAddExpense = findViewById(R.id.btnAddExpense);
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatEditText txtDescription = findViewById(R.id.txtDescription);
                AppCompatEditText txtAmount = findViewById(R.id.txtAmount);
                Expense expense = new Expense(txtDescription.getText().toString(), Double.valueOf(txtAmount.getText().toString()));
                Intent intent = new Intent();
                intent.putExtra(ExtraKeys.EXPENSE, expense);
                setResult(RESULT_OK, intent);
                saveExpenseTask.execute(expense);
                finish();
            }
        });

        final AppCompatImageButton btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        final AppCompatImageButton btnSelectType = findViewById(R.id.btnSelectType);
        btnSelectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ExpenseType> items = new ArrayList<>();
                items.add(new ExpenseType(0, "tipo uno"));
                items.add(new ExpenseType(1, "tipo dos"));
                items.add(new ExpenseType(2, "tipo tres"));
                items.add(new ExpenseType(3, "tipo cuatro"));
                typesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, items);
                showItemPickerDialog(v, typesAdapter, (AppCompatEditText)findViewById(R.id.txtType));
            }
        });

        final AppCompatImageButton btnSelectCurrency = findViewById(R.id.btnSelectCurrency);
        btnSelectCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Currency> items = new ArrayList<>();
                items.add(new Currency(0, '$', "AR$"));
                items.add(new Currency(1, '$', "US$"));
                currenciesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, items);
                showItemPickerDialog(v, currenciesAdapter, (AppCompatEditText)findViewById(R.id.txtCurrency));
            }
        });

        final AppCompatEditText txtServiceLine = findViewById(R.id.txtServiceLine);
        final AppCompatImageButton btnSelectServiceLine = findViewById(R.id.btnSelectServiceLine);
        btnSelectServiceLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ServiceLine> items = new ArrayList<>();
                items.add(new ServiceLine(0, "linea 1"));
                items.add(new ServiceLine(1, "linea 2"));
                items.add(new ServiceLine(2, "linea 3"));
                serviceLinesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, items);
                showItemPickerDialog(v, serviceLinesAdapter, txtServiceLine);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Date date = new Date(year - 1900, month, dayOfMonth);
                txtDate.setText(dateFormat.format(date));
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showItemPickerDialog(final View v, final ArrayIpAdapter adapter, final AppCompatEditText appCompatEditText) {
        ItemPickerFragment newFragment = new ItemPickerFragment();
        newFragment.setAdapter(adapter);
        newFragment.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.setSelectedIndex(which);
                appCompatEditText.setText(adapter.getSelectedItem().toString());
            }
        });
        newFragment.show(getSupportFragmentManager(), "itemPicker");
    }

    private class SaveExpenseTask extends AsyncTask<Expense, Integer, Void> {
        private View viewForSnackbar;

        public SaveExpenseTask(View viewForSnackbar) {
            this.viewForSnackbar = viewForSnackbar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Expense... newExpenses) {
            ArrayList<Expense> expenses = expensesRepository.loadExpenses(ExpenseActivity.this);
            for (Expense newExpense : newExpenses) {
                expenses.add(newExpense);
            }
            expensesRepository.saveExpenses(ExpenseActivity.this, expenses);
            //TODO: preguntarle a Alfredo si esta bien devolver null en este caso
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            if (viewForSnackbar != null) {
                Snackbar.make(viewForSnackbar, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }
}
