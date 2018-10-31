package com.prominente.android.viaticgo.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import android.net.Uri;
import com.bumptech.glide.Glide;
import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.adapters.ArrayIpAdapter;
import com.prominente.android.viaticgo.constants.ExtraKeys;
import com.prominente.android.viaticgo.constants.RequestCodes;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpenseActivity extends LightDarkAppCompatActivity {
    private IExpensesRepository expensesRepository;
    private AppCompatEditText txtDescription;
    private AppCompatEditText txtAmount;
    private AppCompatEditText txtExpenseId;
    private AppCompatEditText txtId;
    private AppCompatEditText txtDate;
    private AppCompatImageView ivTicket;
    private DateFormat dateFormat;
    private ArrayIpAdapter<ExpenseType> typesAdapter;
    private ArrayIpAdapter<Currency> currenciesAdapter;
    private ArrayIpAdapter<ServiceLine> serviceLinesAdapter;
    private IServiceLineRepository serviceLineRepository;
    private ICurrencyRepository currencyRepository;
    private IExpenseTypeRepository expenseTypeRepository;
    private ArrayList<ServiceLine> itemsServiceLine;
    private ArrayList<ExpenseType> itemsExpenseType;
    private ArrayList<Currency> itemsCurrency;
    private Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        txtExpenseId = findViewById(R.id.txtExpenseId);
        txtDescription = findViewById(R.id.txtDescription);
        txtAmount = findViewById(R.id.txtAmount);
        txtExpenseId = findViewById(R.id.txtExpenseId);
        txtId = findViewById(R.id.txtId);
        txtDate = findViewById(R.id.txtDate);
        ivTicket = findViewById(R.id.ivTicket);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_arrow_back);

        expensesRepository = SugarRepository.getInstance();
        serviceLineRepository = SugarRepository.getInstance();
        currencyRepository = SugarRepository.getInstance();
        expenseTypeRepository = SugarRepository.getInstance();
        dateFormat = android.text.format.DateFormat.getDateFormat(this);
        itemsServiceLine = serviceLineRepository.getAllServiceLines(ExpenseActivity.this);
        serviceLinesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, itemsServiceLine);
        itemsExpenseType = expenseTypeRepository.getAllExpenseTypes(ExpenseActivity.this);
        typesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, itemsExpenseType);
        itemsCurrency = currencyRepository.getAllCurrencies(ExpenseActivity.this);
        currenciesAdapter = new ArrayIpAdapter<>(ExpenseActivity.this, android.R.layout.select_dialog_item, itemsCurrency);

        expense = (Expense)getIntent().getSerializableExtra(ExtraKeys.EXPENSE);
        if(expense != null){
            actionbar.setTitle(R.string.edit_expense);
            edit(expense);
        }
        else
        {
            expense = new Expense();
        }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_expense_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                return true;

            case R.id.action_delete:
                delete();
                return true;

            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void edit(Expense expense) {
        txtDescription.setText(expense.getDescription());
        txtAmount.setText(Double.valueOf(expense.getAmount()).toString());
        if (expense.getExpenseId() != null)
            txtExpenseId.setText(Long.toString(expense.getExpenseId()));
        txtId.setText(Long.toString(expense.getId()));
        txtDate.setText(parseDateToString(expense.getDate()));
        serviceLinesAdapter.setSelectedIndex(getIndexServiceLinesAdapter(itemsServiceLine, expense.getServiceLine().getServiceLineId()));
        setEditTextAdapter(serviceLinesAdapter, (AppCompatEditText)findViewById(R.id.txtServiceLine));
        currenciesAdapter.setSelectedIndex(getIndexCurrenciesAdapter(itemsCurrency, expense.getCurrency().getCurrencyId()));
        setEditTextAdapter(currenciesAdapter, (AppCompatEditText)findViewById(R.id.txtCurrency));
        typesAdapter.setSelectedIndex(getIndexTypesAdapter(itemsExpenseType, expense.getType().getExpenseTypeId()));
        setEditTextAdapter(typesAdapter, (AppCompatEditText)findViewById(R.id.txtType));
        if (expense.getImageUri() != null)
            loadImageFromUri(this,ivTicket, Uri.parse(expense.getImageUri()));
    }

    private void save(){
        expense.setDescription(txtDescription.getText().toString());
        expense.setAmount(Double.valueOf(txtAmount.getText().toString()));
        expense.setDate(parseStringToDate(txtDate.getText().toString()));
        expense.setType(typesAdapter.getSelectedItem());
        expense.setCurrency(currenciesAdapter.getSelectedItem());
        expense.setServiceLine(serviceLinesAdapter.getSelectedItem());
        if (txtExpenseId.length() > 0)
            expense.setExpenseId(Long.valueOf(txtExpenseId.getText().toString()));
        if (txtId.length() > 0)
            expense.setId(Long.valueOf(txtId.getText().toString()));

        Intent intent = new Intent();
        intent.putExtra(ExtraKeys.EXPENSE, expense);
        setResult(RESULT_OK, intent);

        Bundle extras = getIntent().getExtras();
        if(extras == null)
        {
            SaveExpenseTask saveExpenseTask = new SaveExpenseTask(findViewById(R.id.appBarMain));
            saveExpenseTask.execute(expense);
        }
        else {
            if(extras.getInt(ExtraKeys.MODE_EXPENSE_ACTIVITY) == RequestCodes.EDIT_EXPENSE) {
                UpdateExpenseTask updateExpenseTask = new UpdateExpenseTask(findViewById(R.id.appBarMain));
                updateExpenseTask.execute(expense);
            }
        }
        finish();
    }

    private void delete(){
        Intent intent = new Intent();
        intent.putExtra(ExtraKeys.EXPENSE, expense);
        setResult(RESULT_OK, intent);

        DeleteExpenseTask deleteExpenseTask = new DeleteExpenseTask(findViewById(R.id.appBarMain));
        deleteExpenseTask.execute(expense);
        finish();
    }

    private void showDatePickerDialog(View v) {
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

    private void showItemPickerDialog(final View v, final ArrayIpAdapter adapter, final AppCompatEditText appCompatEditText) {
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

    private int getIndexServiceLinesAdapter(ArrayList<ServiceLine> items, int id){
        int i;
        for(i=0; i<=items.size(); i++) {
            if(id == items.get(i).getServiceLineId())
                break;
        }
        return i;
    }

    private int getIndexCurrenciesAdapter(ArrayList<Currency> items, int id){
        int i;
        for(i=0; i<=items.size(); i++) {
            if(id == items.get(i).getCurrencyId())
                break;
        }
        return i;
    }

    private int getIndexTypesAdapter(ArrayList<ExpenseType> items, int id){
        int i;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(date);
        } catch (ParseException e) {
        }
        return convertedDate;
    }

    private String parseDateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String convertedDate = dateFormat.format(date);
        return convertedDate;
    }

    private void loadImageFromUri(Context context, AppCompatImageView imageView, Uri uri) {
        Glide.with(context).load(uri).into(imageView);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(reqCode == RequestCodes.RESULT_LOAD_IMG) {
                final Uri imageUri = data.getData();
                expense.setImageUri(imageUri.toString());
                loadImageFromUri(this, ivTicket, imageUri);

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

    private class DeleteExpenseTask extends AsyncTask<Expense, Integer, Void> {
        private View viewForSnackbar;

        public DeleteExpenseTask(View viewForSnackbar) {
            this.viewForSnackbar = viewForSnackbar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            for (Expense expense:expenses) {
                expensesRepository.deleteExpense(ExpenseActivity.this, expense);
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
