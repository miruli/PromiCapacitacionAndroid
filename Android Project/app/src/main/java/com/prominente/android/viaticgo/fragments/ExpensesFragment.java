package com.prominente.android.viaticgo.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.activities.ExpenseActivity;
import com.prominente.android.viaticgo.adapters.ExpensesRecyclerViewAdapter;
import com.prominente.android.viaticgo.constants.RequestCodes;
import com.prominente.android.viaticgo.data.SugarRepository;
import com.prominente.android.viaticgo.interfaces.IExpensesRepository;
import com.prominente.android.viaticgo.models.Expense;

import java.util.ArrayList;

public class ExpensesFragment extends Fragment {
    private ExpensesRecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private IExpensesRepository expensesRepository;
    private LoadExpensesTask loadExpensesTask;

    public ExpensesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        expensesRepository = SugarRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expenses, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.rv_expenses);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new ExpensesRecyclerViewAdapter((AppCompatActivity)getActivity());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExpenseActivity.class);
                startActivityForResult(intent, RequestCodes.NEW_EXPENSE);
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle_expense_fragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        runLoadTask();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (loadExpensesTask != null && (loadExpensesTask.getStatus().equals(AsyncTask.Status.RUNNING) || loadExpensesTask.getStatus().equals(AsyncTask.Status.PENDING)))
            loadExpensesTask.cancel(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.fragment_expenses_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        /*boolean itemSelected = false;
        for (Expense e:adapter.getItems()) {
            if (e.getSelected()){
                itemSelected = true;
                break;
            }
        }
        menu.setGroupVisible(R.id.menu_expense_group, itemSelected);*/
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case R.id.action_delete:
                deleteExpenseTask = new DeleteExpenseTask();
                deleteExpenseTask.execute();
                getActivity().invalidateOptionsMenu();
                return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    public void runLoadTask() {
        loadExpensesTask = new LoadExpensesTask();
        loadExpensesTask.execute();
    }

    public void addExpense(Expense expense) {
        adapter.add(expense);
    }

    private class LoadExpensesTask extends AsyncTask<Void, Integer, ArrayList<Expense>> {
        public LoadExpensesTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Expense> doInBackground(Void... voids) {
            return expensesRepository.loadExpenses(getContext());
        }

        @Override
        protected void onPostExecute(ArrayList<Expense> expenses) {
            if (expenses != null) {
                adapter.clear();
                adapter.addAll(expenses);
            }
        }
    }
}
