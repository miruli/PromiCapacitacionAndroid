package com.prominente.android.viaticgo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.adapters.IExpensesRecyclerViewAdapterClickListener;
import com.prominente.android.viaticgo.adapters.ExpensesRecyclerViewAdapter;
import com.prominente.android.viaticgo.models.Expense;

import java.util.ArrayList;

public class OldMainActivity extends AppCompatActivity {
    private ExpensesRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldmain);

        RecyclerView rvExpenses = findViewById(R.id.rv_expenses);
        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpensesRecyclerViewAdapter(new IExpensesRecyclerViewAdapterClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(OldMainActivity.this, "Position "+position, Toast.LENGTH_SHORT).show();
                Expense t = adapter.getItems().get(position);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
        rvExpenses.setAdapter(adapter);
        adapter.addAll(getItems());
    }

    private ArrayList<Expense> getItems(){
        ArrayList<Expense> expensesArrayList = new ArrayList<>();
        expensesArrayList.add(new Expense("description 1", 1));
        expensesArrayList.add(new Expense("description 2", 2));
        expensesArrayList.add(new Expense("description 3", 3));
        expensesArrayList.add(new Expense("description 4", 4));
        expensesArrayList.add(new Expense("description 5", 5));
        expensesArrayList.add(new Expense("description 6", 6));
        expensesArrayList.add(new Expense("description 7", 7));
        expensesArrayList.add(new Expense("description 8", 8));
        expensesArrayList.add(new Expense("description 9", 9));
        return expensesArrayList;
    }
}
