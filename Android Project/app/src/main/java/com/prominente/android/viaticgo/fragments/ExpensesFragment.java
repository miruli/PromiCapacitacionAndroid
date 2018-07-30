package com.prominente.android.viaticgo.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.adapters.IExpensesRecyclerViewAdapterClickListener;
import com.prominente.android.viaticgo.adapters.ExpensesRecyclerViewAdapter;
import com.prominente.android.viaticgo.models.Expense;

import java.util.ArrayList;

public class ExpensesFragment extends Fragment {
    private ExpensesRecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    public ExpensesFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        adapter = new ExpensesRecyclerViewAdapter(getContext());


        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        adapter.setClickLister(new IExpensesRecyclerViewAdapterClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Toast.makeText(getContext(), "Position "+position, Toast.LENGTH_SHORT).show();
//                //Expense t = adapter.getItems().get(position);
//            }
//
//            @Override
//            public void onItemLongClick(View v, int position) {
//
//            }
//        });
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
