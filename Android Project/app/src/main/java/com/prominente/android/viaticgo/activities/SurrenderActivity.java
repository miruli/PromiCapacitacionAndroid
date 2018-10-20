package com.prominente.android.viaticgo.activities;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.adapters.ArrayIpAdapter;
import com.prominente.android.viaticgo.constants.ExtraKeys;
import com.prominente.android.viaticgo.fragments.ItemPickerFragment;
import com.prominente.android.viaticgo.interfaces.ITripRepository;
import com.prominente.android.viaticgo.models.Expense;
import com.prominente.android.viaticgo.models.Trip;
import com.prominente.android.viaticgo.data.SugarRepository;

import java.util.ArrayList;

public class SurrenderActivity extends LightDarkAppCompatActivity {
    private ITripRepository tripRepository;
    private ArrayList<Trip> itemsTrip;
    private ArrayIpAdapter<Trip> tripAdapter;
    private AppCompatEditText txtCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surrender);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tripRepository = SugarRepository.getInstance();

        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_arrow_back);
        actionbar.setTitle(R.string.surrender_tickets);


        ArrayList<Expense> expenseList = (ArrayList<Expense>)getIntent().getSerializableExtra(ExtraKeys.SURRENDER_EXPENSE);
        if(expenseList != null){
            txtCount = findViewById(R.id.txtCount);
            txtCount.setText(Integer.toString(expenseList.size()));
        }

        final AppCompatImageButton btnSelectNumberTrip = findViewById(R.id.btnSelectNumberTrip);
        btnSelectNumberTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsTrip = tripRepository.getAllTrips(SurrenderActivity.this);
                tripAdapter = new ArrayIpAdapter<>(SurrenderActivity.this, android.R.layout.select_dialog_item, itemsTrip);
                showItemPickerDialog(v, tripAdapter, (AppCompatEditText)findViewById(R.id.txtNumberTrip));
            }
        });
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
}
