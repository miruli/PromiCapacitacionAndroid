package com.prominente.android.viaticgo.adapters;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.TypedValue;
import android.content.res.Resources;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.activities.ExpenseActivity;
import com.prominente.android.viaticgo.activities.SurrenderActivity;
import com.prominente.android.viaticgo.constants.RequestCodes;
import com.prominente.android.viaticgo.data.SugarRepository;
import com.prominente.android.viaticgo.interfaces.IExpensesRepository;
import com.prominente.android.viaticgo.models.Expense;
import com.prominente.android.viaticgo.constants.ExtraKeys;

import java.util.ArrayList;

public class ExpensesRecyclerViewAdapter extends ArrayRvAdapter<Expense, ExpensesRecyclerViewAdapter.ExpenseViewHolder> {
    private AppCompatActivity activity;
    private ActionMode actionMode;
    private IExpensesRepository expensesRepository;

    public ExpensesRecyclerViewAdapter(AppCompatActivity context)
    {
        this.activity = context;
        this.expensesRepository = SugarRepository.getInstance();
    }

    @NonNull
    @Override
    public ExpensesRecyclerViewAdapter.ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expenses_rv_item, parent, false);

        ExpenseViewHolder viewHolder = new ExpenseViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExpenseViewHolder holder, final int position) {
        final Expense expense = getItems().get(position);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
        @ColorInt final int color = typedValue.data;
        @ColorInt final int noColor = 0x00000000;

        holder.tvTitle.setText(expense.getDescription());
        holder.tvSubtitle.setText(String.valueOf(expense.getAmount()));
        holder.itemView.setBackgroundColor(expense.getSelected() ? color : noColor);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSelectedItemsCount() == 0) {
                    Intent intent = new Intent(activity, ExpenseActivity.class);
                    intent.putExtra(ExtraKeys.EXPENSE, expense);
                    intent.putExtra(ExtraKeys.MODE_EXPENSE_ACTIVITY,RequestCodes.EDIT_EXPENSE);
                    activity.startActivity(intent);
                }
                else {
                    refreshModeSelection(holder, expense, color, noColor);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (getSelectedItemsCount() > 0)
                    return true;

                return refreshModeSelection(holder, expense, color, noColor);
            }
        });

    }


    @Override
    public int getItemCount() {
        return getItems().size();
    }

    private int getSelectedItemsCount() {
        int count = 0;
        for (Expense expense:getItems()) {
            if (expense.getSelected()) {
                count++;
            }
        }
        return count;
    }

    private boolean refreshModeSelection(@NonNull final ExpenseViewHolder holder, Expense expense,
                                        @ColorInt final int color, @ColorInt final int noColor){

        expense.setSelected(!expense.getSelected());
        holder.itemView.setBackgroundColor(expense.getSelected() ? color : noColor);

        if (getSelectedItemsCount() == 0) {
            actionMode.finish();
            return true;
        }

        if (actionMode == null) {
            actionMode = activity.startSupportActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.fragment_expenses_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    for (Expense e:getItems()) {
                        if (e.getSelected()){
                            return true;
                        }
                    }
                    mode.finish();
                    return true;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_delete:
                            ArrayList<Expense> selectedExpenses = new ArrayList<>();
                            ArrayList<Expense> newExpensesList = new ArrayList<>();
                            for (Expense expense:getItems()) {
                                if (expense.getSelected()) {
                                    selectedExpenses.add(expense);
                                }
                                else {
                                    newExpensesList.add(expense);
                                }
                            }
                            setItems(newExpensesList);
                            notifyDataSetChanged();
                            DeleteExpenseTask deleteExpenseTask = new DeleteExpenseTask();
                            deleteExpenseTask.execute(selectedExpenses.toArray(new Expense[0]));
                            mode.finish();
                            return true;

                        case R.id.action_send_tickets:
                            ArrayList<Expense> selExpenses = new ArrayList<>();
                            for (Expense expense:getItems()) {
                                if (expense.getSelected()) {
                                    selExpenses.add(expense);
                                }
                            }
                            Intent intent = new Intent(activity, SurrenderActivity.class);
                            intent.putExtra(ExtraKeys.SURRENDER_EXPENSE, selExpenses);
                            activity.startActivity(intent);
                            mode.finish();
                            return true;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    for (Expense expense:getItems()) {
                        expense.setSelected(false);
                    }
                    notifyDataSetChanged();
                    actionMode = null;
                }
            });
        }

        actionMode.setTitle(Integer.toString(getSelectedItemsCount()));
        return true;
    }

    protected static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvSubtitle;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_ticket_rv_title);
            tvSubtitle = itemView.findViewById(R.id.tv_ticket_rv_subtitle);
        }
    }

    private class DeleteExpenseTask extends AsyncTask<Expense, Integer, Void> {
        public DeleteExpenseTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            for(Expense expense: expenses){
                expensesRepository.deleteExpense(activity, expense);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }

}
