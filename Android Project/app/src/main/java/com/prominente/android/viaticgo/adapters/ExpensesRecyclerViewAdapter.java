package com.prominente.android.viaticgo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.models.Expense;

public class ExpensesRecyclerViewAdapter extends ArrayRvAdapter<Expense,ExpensesRecyclerViewAdapter.ExpenseViewHolder>
{
    private IExpensesRecyclerViewAdapterClickListener clickLister;

    protected static class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private IExpensesRecyclerViewAdapterClickListener clickLister;

        TextView tvTitle;
        TextView tvSubtitle;

        public ExpenseViewHolder(View itemView, IExpensesRecyclerViewAdapterClickListener listener)
        {
            super(itemView);

            clickLister = listener;
            itemView.setOnClickListener(this);
            tvTitle = itemView.findViewById(R.id.tv_ticket_rv_title);
            tvSubtitle = itemView.findViewById(R.id.tv_ticket_rv_subtitle);
        }

        @Override
        public void onClick(View v)
        {
            clickLister.onItemClick(v, getLayoutPosition());
        }
    }

    public ExpensesRecyclerViewAdapter(IExpensesRecyclerViewAdapterClickListener listener)
    {
        clickLister = listener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_rv_item, parent, false);
        return new ExpenseViewHolder(v, clickLister);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position)
    {
        Expense t = getItems().get(position);
        holder.tvTitle.setText(t.getDescription());
        holder.tvSubtitle.setText(String.valueOf(t.getAmount()));
    }
}
