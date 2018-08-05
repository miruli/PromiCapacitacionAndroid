package com.prominente.android.viaticgo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.models.Expense;

import java.util.ArrayList;

public class ExpensesRecyclerViewAdapter extends ArrayRvAdapter<Expense, ExpensesRecyclerViewAdapter.ExpenseViewHolder> {
    private Activity activity;

    public ExpensesRecyclerViewAdapter(Activity context) {
        this.activity = context;
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
                ArrayList<Expense> expenses = getItems();
                for (Expense e:expenses) {
                    if (e.getSelected()){
                        expense.setSelected(!expense.getSelected());
                        holder.itemView.setBackgroundColor(expense.getSelected() ? colorDark: color);
                        break;
                    }
                }
                activity.invalidateOptionsMenu();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO: mark the item as selected, add options to actionbar like view and delete
                expense.setSelected(!expense.getSelected());
                holder.itemView.setBackgroundColor(expense.getSelected() ? colorDark: color);
                activity.invalidateOptionsMenu();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return getItems().size();
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


}
