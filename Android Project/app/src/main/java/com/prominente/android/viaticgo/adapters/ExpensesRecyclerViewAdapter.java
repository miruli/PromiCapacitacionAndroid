package com.prominente.android.viaticgo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.models.Expense;

public class ExpensesRecyclerViewAdapter extends ArrayRvAdapter<Expense,ExpensesRecyclerViewAdapter.ExpenseViewHolder>
{
    private Context context;

    public ExpensesRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    protected static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private IExpensesRecyclerViewAdapterClickListener clickLister;

        TextView tvTitle;
        TextView tvSubtitle;

        public ExpenseViewHolder(View itemView)
        {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_ticket_rv_title);
            tvSubtitle = itemView.findViewById(R.id.tv_ticket_rv_subtitle);
        }

        public void onClick(View v)
        {
            clickLister.onItemClick(v, getLayoutPosition());
        }
    }

    @NonNull
    @Override
    public ExpensesRecyclerViewAdapter.ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_rv_item, parent, false);

        ExpenseViewHolder viewHolder = new ExpenseViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, final int position){
        Expense t = getItems().get(position);
        holder.tvTitle.setText(t.getDescription());
        holder.tvSubtitle.setText(String.valueOf(t.getAmount()));
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return getItems().size();
    }




//    private IExpensesRecyclerViewAdapterClickListener clickLister;
//
//    public ExpensesRecyclerViewAdapter(IExpensesRecyclerViewAdapterClickListener listener) {
//        clickLister = listener;
//    }
//
//    public void setClickLister(IExpensesRecyclerViewAdapterClickListener clickLister) {
//        this.clickLister = clickLister;
//    }


}
