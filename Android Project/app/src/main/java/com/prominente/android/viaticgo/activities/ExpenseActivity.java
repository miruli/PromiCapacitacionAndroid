package com.prominente.android.viaticgo.activities;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.adapters.ArrayIpAdapter;
import com.prominente.android.viaticgo.constants.ExtraKeys;
import com.prominente.android.viaticgo.constants.RequestCodes;
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
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExpenseActivity extends LightDarkAppCompatActivity {
    private IExpensesRepository expensesRepository;
    private SaveExpenseTask saveExpenseTask;
    private UpdateExpenseTask updateExpenseTask;
    private AppCompatEditText txtDate;
    private DateFormat dateFormat;
    private ArrayIpAdapter<ExpenseType> typesAdapter;
    private ArrayIpAdapter<Currency> currenciesAdapter;
    private ArrayIpAdapter<ServiceLine> serviceLinesAdapter;
    private IServiceLineRepository serviceLineRepository;
    private ICurrencyRepository currencyRepository;
    private IExpenseTypeRepository expenseTypeRepository;
    private AppCompatImageView ivTicket;
    private ArrayList<ServiceLine> itemsServiceLine;
    private ArrayList<ExpenseType> itemsExpenseType;
    private ArrayList<Currency> itemsCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_arrow_back);

        expensesRepository = SugarRepository.getInstance();
        serviceLineRepository = SugarRepository.getInstance();
        currencyRepository = SugarRepository.getInstance();
        expenseTypeRepository = SugarRepository.getInstance();
        txtDate = findViewById(R.id.txtDate);
        dateFormat = android.text.format.DateFormat.getDateFormat(this);
        ivTicket = findViewById(R.id.ivTicket);
        final AppCompatButton btnAddExpense = findViewById(R.id.btnAddExpense);

        final AppCompatEditText txtExpenseId = findViewById(R.id.txtExpenseId);
        txtExpenseId.setText(Long.toString(getNewExpenseId()));

        itemsServiceLine = serviceLineRepository.getAllServiceLines(ExpenseActivity.this);
        serviceLinesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, itemsServiceLine);
        itemsExpenseType = expenseTypeRepository.getAllExpenseTypes(ExpenseActivity.this);
        typesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, itemsExpenseType);
        itemsCurrency = currencyRepository.getAllCurrencies(ExpenseActivity.this);
        currenciesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, itemsCurrency);

        Expense expense = (Expense)getIntent().getSerializableExtra(ExtraKeys.EXPENSE);
        if(expense != null){
            toolbar.setTitle(R.string.edit_expense);
            btnAddExpense.setText(R.string.save_expense_button);
            editExpense(expense);
        }

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatEditText txtDescription = findViewById(R.id.txtDescription);
                AppCompatEditText txtAmount = findViewById(R.id.txtAmount);
                Expense expense = new Expense(txtDescription.getText().toString(), Double.valueOf(txtAmount.getText().toString()));
                expense.setDate(parseStringToDate(txtDate.getText().toString()));
                expense.setType(typesAdapter.getSelectedItem());
                expense.setCurrency(currenciesAdapter.getSelectedItem());
                expense.setServiceLine(serviceLinesAdapter.getSelectedItem());
                expense.setExpenseId(Long.valueOf(txtExpenseId.getText().toString()));
                Intent intent = new Intent();
                intent.putExtra(ExtraKeys.EXPENSE, expense);
                setResult(RESULT_OK, intent);

                Bundle extras = getIntent().getExtras();
                if(extras == null)
                {
                    saveExpenseTask = new SaveExpenseTask(findViewById(R.id.appBarMain));
                    saveExpenseTask.execute(expense);
                }
                else {
                    if(extras.getInt(ExtraKeys.MODE_EXPENSE_ACTIVITY) == RequestCodes.EDIT_EXPENSE) {
                        updateExpenseTask = new UpdateExpenseTask(findViewById(R.id.appBarMain));
                        updateExpenseTask.execute(expense);
                    }
                }
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
                showItemPickerDialog(v, typesAdapter, (AppCompatEditText)findViewById(R.id.txtType));
            }
        });

        final AppCompatImageButton btnSelectCurrency = findViewById(R.id.btnSelectCurrency);
        btnSelectCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemPickerDialog(v, currenciesAdapter, (AppCompatEditText)findViewById(R.id.txtCurrency));
            }
        });

        final AppCompatImageButton btnSelectServiceLine = findViewById(R.id.btnSelectServiceLine);
        btnSelectServiceLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemPickerDialog(v, serviceLinesAdapter, (AppCompatEditText)findViewById(R.id.txtServiceLine));
            }
        });

        final AppCompatImageButton btnAddImageTicket = findViewById(R.id.btnAddImageTicket);
        btnAddImageTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RequestCodes.RESULT_LOAD_IMG);

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

    private void editExpense(Expense expense) {
        AppCompatEditText txtDescription = findViewById(R.id.txtDescription);
        txtDescription.setText(expense.getDescription());
        AppCompatEditText txtAmount = findViewById(R.id.txtAmount);
        txtAmount.setText(Double.valueOf(expense.getAmount()).toString());
        AppCompatEditText txtExpenseId = findViewById(R.id.txtExpenseId);
        txtExpenseId.setText(Long.toString(expense.getExpenseId()));
        txtDate.setText(expense.getDate().toString());

        serviceLinesAdapter.setSelectedIndex(getIndexServiceLinesAdapter(itemsServiceLine, expense.getServiceLine().getServiceLineId()));
        setEditTextAdapter(serviceLinesAdapter, (AppCompatEditText)findViewById(R.id.txtServiceLine));
        currenciesAdapter.setSelectedIndex(getIndexCurrenciesAdapter(itemsCurrency, expense.getCurrency().getCurrencyId()));
        setEditTextAdapter(currenciesAdapter, (AppCompatEditText)findViewById(R.id.txtCurrency));
        typesAdapter.setSelectedIndex(getIndexTypesAdapter(itemsExpenseType, expense.getType().getExpenseTypeId()));
        setEditTextAdapter(typesAdapter, (AppCompatEditText)findViewById(R.id.txtType));
    }

    private Long getNewExpenseId(){
        Date date = new Date();
        return date.getTime();
    }

    private int getIndexServiceLinesAdapter(ArrayList<ServiceLine> items, int id){
        int i=0;
        for(i=0; i<=items.size(); i++) {
            if(id == items.get(i).getServiceLineId())
                break;
        }
        return i;
    }

    private int getIndexCurrenciesAdapter(ArrayList<Currency> items, int id){
        int i=0;
        for(i=0; i<=items.size(); i++) {
            if(id == items.get(i).getCurrencyId())
                break;
        }
        return i;
    }

    private int getIndexTypesAdapter(ArrayList<ExpenseType> items, int id){
        int i=0;
        for(i=0; i<=items.size(); i++) {
            if(id == items.get(i).getExpenseTypeId())
                break;
        }
        return i;
    }

    private void setEditTextAdapter(final ArrayIpAdapter adapter, final AppCompatEditText appCompatEditText){
        appCompatEditText.setText(adapter.getSelectedItem().toString());
    }

    private Date parseStringToDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(txtDate.getText().toString());
        } catch (ParseException e) {
        }
        return convertedDate;
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                if(reqCode == RequestCodes.RESULT_LOAD_IMG) {
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

    private class UpdateExpenseTask extends AsyncTask<Expense, Integer, Void> {
        private View viewForSnackbar;

        public UpdateExpenseTask(View viewForSnackbar) {
            this.viewForSnackbar = viewForSnackbar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Expense... newExpenses) {
            for (Expense expense:newExpenses) {
                expensesRepository.updateExpense(ExpenseActivity.this, expense);
            }
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
