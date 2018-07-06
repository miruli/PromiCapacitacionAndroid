package com.gp.android.adapters.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gp.android.adapters.R;
import com.gp.android.adapters.model.Ticket;

public class RvAdapter extends ArrayRvAdapter<Ticket,RvAdapter.TicketViewHolder>
{
    protected static class TicketViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvTitle;
        TextView tvSubtitle;

        public TicketViewHolder(View itemView)
        {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_ticket_rv_title);
            tvSubtitle = itemView.findViewById(R.id.tv_ticket_rv_subtitle);
        }
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_rv_item, parent, false);
        return new TicketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position)
    {
        Ticket t = getItems().get(position);
        holder.tvTitle.setText(t.getDescription());
        holder.tvSubtitle.setText(String.valueOf(t.getAmount()));
    }
}
