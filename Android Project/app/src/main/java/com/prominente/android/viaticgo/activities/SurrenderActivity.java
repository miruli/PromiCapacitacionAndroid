package com.prominente.android.viaticgo.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.adapters.ArrayIpAdapter;
import com.prominente.android.viaticgo.constants.ExtraKeys;
import com.prominente.android.viaticgo.fragments.ItemPickerFragment;
import com.prominente.android.viaticgo.interfaces.ITripRepository;
import com.prominente.android.viaticgo.interfaces.ISurrenderRepository;
import com.prominente.android.viaticgo.models.Expense;
import com.prominente.android.viaticgo.models.Surrender;
import com.prominente.android.viaticgo.models.Trip;
import com.prominente.android.viaticgo.data.SugarRepository;

import java.util.ArrayList;

public class SurrenderActivity extends LightDarkAppCompatActivity {
    private SendSurrenderTask sendSurrenderTask;
    private Surrender surrender;
    private ITripRepository tripRepository;
    private ISurrenderRepository surrenderRepository;
    private ArrayList<Trip> itemsTrip;
    private ArrayIpAdapter<Trip> tripAdapter;
    private AppCompatEditText txtCount;
    private AppCompatEditText txtNumberTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surrender);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tripRepository = SugarRepository.getInstance();
        surrenderRepository = SugarRepository.getInstance();

        final ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_arrow_back);
        actionbar.setTitle(R.string.surrender_tickets);

        txtNumberTrip = findViewById(R.id.txtNumberTrip);
        txtCount = findViewById(R.id.txtCount);

        ArrayList<Expense> list = (ArrayList<Expense>)getIntent().getSerializableExtra(ExtraKeys.SURRENDER_EXPENSE);
        txtCount.setText(Integer.toString(list.size()));

        surrender = new Surrender();
        surrender.setExpensesList(list);

        final AppCompatImageButton btnSelectNumberTrip = findViewById(R.id.btnSelectNumberTrip);
        btnSelectNumberTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsTrip = tripRepository.getAllTrips(SurrenderActivity.this);
                tripAdapter = new ArrayIpAdapter<>(SurrenderActivity.this, android.R.layout.select_dialog_item, itemsTrip);
                showItemPickerDialog(v, tripAdapter, txtNumberTrip);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                save();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_surrender_menu, menu);
        return true;
    }

    private void save(){
        sendSurrenderTask = new SendSurrenderTask();
        surrender.setTrip(tripAdapter.getSelectedItem());
        sendSurrenderTask.execute(surrender);
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

    private class SendSurrenderTask extends AsyncTask<Surrender, Integer, Boolean> {
        public SendSurrenderTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Surrender... surrenders) {
            return surrenderRepository.saveSurrender(SurrenderActivity.this, surrenders[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
          if (result) {
                Toast.makeText(SurrenderActivity.this, R.string.surrender_save_ok, Toast.LENGTH_LONG).show();
                finish();
            }
            else {
                Toast.makeText(SurrenderActivity.this, R.string.generic_error, Toast.LENGTH_LONG).show();
            }
        }
    }
}
