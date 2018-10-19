package com.gp.android.adapters.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gp.android.adapters.R;
import com.gp.android.adapters.model.Sponsor;
import com.gp.android.adapters.model.Ticket;

public class RvAdapter extends ArrayRvAdapter<Sponsor,RvAdapter.TicketViewHolder>
{
    private RvAdapterClickLister clickLister;

    protected static class TicketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private RvAdapterClickLister clickLister;

        TextView tvTitle;
        ImageView tvSubtitle;

        public TicketViewHolder(View itemView, RvAdapterClickLister listener)
        {
            super(itemView);

            clickLister = listener;
            itemView.setOnClickListener(this);
            tvTitle = itemView.findViewById(R.id.tv_ticket_rv_title);
            tvSubtitle = itemView.findViewById(R.id.iv_ticket_rv_image);
        }

        @Override
        public void onClick(View v)
        {
            clickLister.onItemClick(v, getLayoutPosition());
        }
    }

    public RvAdapter(RvAdapterClickLister listener)
    {
        clickLister = listener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_rv_item, parent, false);
        return new TicketViewHolder(v, clickLister);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position)
    {
        Sponsor t = getItems().get(position);
        holder.tvTitle.setText(t.getName());
        Glide.with(holder.tvSubtitle.getContext()).load(t.getUrlLogo()).into(holder.tvSubtitle);
        //holder.tvSubtitle.setText(String.valueOf(t.getAmount()));
    }
}
