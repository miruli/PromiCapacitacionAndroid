package com.prominente.android.viaticgo.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.prominente.android.viaticgo.adapters.ExpensesRecyclerViewAdapter;
import com.prominente.android.viaticgo.data.LocalStorageRepository;
import com.prominente.android.viaticgo.interfaces.IExpensesRepository;
import com.prominente.android.viaticgo.models.Expense;

import java.util.ArrayList;

public class ExpensesFragment extends Fragment {
    private ExpensesRecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private IExpensesRepository expensesRepository;
    private LoadExpensesTask loadExpensesTask;
    private DeleteExpenseTask deleteExpenseTask;

    public ExpensesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        expensesRepository = LocalStorageRepository.getInstance();
        runLoadTask();
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
        adapter = new ExpensesRecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (loadExpensesTask != null && (loadExpensesTask.getStatus().equals(AsyncTask.Status.RUNNING) || loadExpensesTask.getStatus().equals(AsyncTask.Status.PENDING)))
            loadExpensesTask.cancel(true);
        if (deleteExpenseTask != null && (deleteExpenseTask.getStatus().equals(AsyncTask.Status.RUNNING) || deleteExpenseTask.getStatus().equals(AsyncTask.Status.PENDING)))
            deleteExpenseTask.cancel(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        boolean itemSelected = false;
        for (Expense e:adapter.getItems()) {
            if (e.getSelected()){
                itemSelected = true;
                break;
            }
        }
        menu.setGroupVisible(R.id.menu_expense_group, itemSelected);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteExpenseTask = new DeleteExpenseTask();
                deleteExpenseTask.execute();
                getActivity().invalidateOptionsMenu();
                return true;
        }
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

    private class DeleteExpenseTask extends AsyncTask<ArrayList<Expense>, Integer, Void>{
        public DeleteExpenseTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(ArrayList<Expense>... arrayLists) {
            ArrayList<Expense> newExpensesList = new ArrayList<>();
            for (Expense expense:adapter.getItems()) {
                if (!expense.getSelected()) {
                    newExpensesList.add(expense);
                }
            }
            expensesRepository.saveExpenses(getContext(), newExpensesList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            runLoadTask();
        }
    }
}
