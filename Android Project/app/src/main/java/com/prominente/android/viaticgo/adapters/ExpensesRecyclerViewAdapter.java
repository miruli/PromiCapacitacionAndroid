package com.prominente.android.viaticgo.adapters;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        @ColorInt final int color = typedValue.data;
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        @ColorInt final int colorDark = typedValue.data;
        holder.tvTitle.setText(expense.getDescription());
        holder.tvSubtitle.setText(String.valueOf(expense.getAmount()));
        holder.itemView.setBackgroundColor(expense.getSelected() ? colorDark : color);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getSelectedItemsCount() == 0) {
                    Intent intent = new Intent(activity, ExpenseActivity.class);
                    intent.putExtra(ExtraKeys.EXPENSE, expense);
                    intent.putExtra(ExtraKeys.MODE_EXPENSE_ACTIVITY,RequestCodes.EDIT_EXPENSE);
                    activity.startActivity(intent);
                }

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                expense.setSelected(!expense.getSelected());
                holder.itemView.setBackgroundColor(expense.getSelected() ? colorDark: color);
                if (actionMode != null) {
                    actionMode.setTitle(Integer.toString(getSelectedItemsCount()));
                    return false;
                }
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
                                deleteExpenseTask.execute(selectedExpenses);
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
                actionMode.setTitle(Integer.toString(getSelectedItemsCount()));
                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        return getItems().size();
    }

    public int getSelectedItemsCount() {
        int count = 0;
        for (Expense expense:getItems()) {
            if (expense.getSelected()) {
                count++;
            }
        }
        return count;
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

    private class DeleteExpenseTask extends AsyncTask<ArrayList<Expense>, Integer, Void> {
        public DeleteExpenseTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(ArrayList<Expense>... arrayLists) {
            for(int i= 0; i<= arrayLists.length -1; i++){
                expensesRepository.deleteExpenses(activity, arrayLists[i]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }

}
