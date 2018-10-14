package com.prominente.android.viaticgo.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.print.PrinterId;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.adapters.ArrayIpAdapter;
import com.prominente.android.viaticgo.constants.ExtraKeys;
import com.prominente.android.viaticgo.data.LocalStorageRepository;
import com.prominente.android.viaticgo.data.SugarRepository;
import com.prominente.android.viaticgo.fragments.DatePickerFragment;
import com.prominente.android.viaticgo.fragments.ItemPickerFragment;
import com.prominente.android.viaticgo.interfaces.ICurrencyRepository;
import com.prominente.android.viaticgo.interfaces.IExpenseTypeRepository;
import com.prominente.android.viaticgo.interfaces.IExpensesRepository;
import com.prominente.android.viaticgo.interfaces.IServiceLineRepository;
import com.prominente.android.viaticgo.models.Currency;
import com.prominente.android.viaticgo.models.Expense;
import com.prominente.android.viaticgo.models.ExpenseType;
import com.prominente.android.viaticgo.models.ServiceLine;

import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private IServiceLineRepository serviceLineRepository;
    private ICurrencyRepository currencyRepository;
    private IExpenseTypeRepository expenseTypeRepository;
    private AppCompatImageView ivTicket;
    private static final int RESULT_LOAD_IMG = 1;

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
        expensesRepository = SugarRepository.getInstance();
        serviceLineRepository = SugarRepository.getInstance();
        currencyRepository = SugarRepository.getInstance();
        expenseTypeRepository = SugarRepository.getInstance();
        txtDate = findViewById(R.id.txtDate);
        dateFormat = android.text.format.DateFormat.getDateFormat(this);
        ivTicket = findViewById(R.id.ivTicket);

        final AppCompatButton btnAddExpense = findViewById(R.id.btnAddExpense);
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatEditText txtDescription = findViewById(R.id.txtDescription);
                AppCompatEditText txtAmount = findViewById(R.id.txtAmount);
                Expense expense = new Expense(txtDescription.getText().toString(), Double.valueOf(txtAmount.getText().toString()));
                expense.setType(typesAdapter.getSelectedItem());
                expense.setCurrency(currenciesAdapter.getSelectedItem());
                expense.setServiceLine(serviceLinesAdapter.getSelectedItem());
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
                ArrayList<ExpenseType> items = expenseTypeRepository.getAllExpenseTypes(ExpenseActivity.this);
                typesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, items);
                showItemPickerDialog(v, typesAdapter, (AppCompatEditText)findViewById(R.id.txtType));
            }
        });

        final AppCompatImageButton btnSelectCurrency = findViewById(R.id.btnSelectCurrency);
        btnSelectCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Currency> items = currencyRepository.getAllCurrencies(ExpenseActivity.this);
                currenciesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, items);
                showItemPickerDialog(v, currenciesAdapter, (AppCompatEditText)findViewById(R.id.txtCurrency));
            }
        });

        final AppCompatImageButton btnSelectServiceLine = findViewById(R.id.btnSelectServiceLine);
        btnSelectServiceLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ServiceLine> items = serviceLineRepository.getAllServiceLines(ExpenseActivity.this);
                serviceLinesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, items);
                showItemPickerDialog(v, serviceLinesAdapter, (AppCompatEditText)findViewById(R.id.txtServiceLine));
            }
        });

        final AppCompatImageButton btnAddImageTicket = findViewById(R.id.btnAddImageTicket);
        btnAddImageTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

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
            for (Expense expense:newExpenses) {
                expensesRepository.saveExpense(ExpenseActivity.this, expense);
            }
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

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                if(reqCode == RESULT_LOAD_IMG) {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ivTicket.setImageBitmap(selectedImage);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, R.string.image_not_selected_expense_fragment,Toast.LENGTH_LONG).show();
        }
    }


}
