package com.gp.android.adapters.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gp.android.adapters.R;
import com.gp.android.adapters.adapters.RvAdapter;
import com.gp.android.adapters.model.Ticket;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvTickets = findViewById(R.id.rv_tickets);
        rvTickets.setLayoutManager(new LinearLayoutManager(this));
        RvAdapter adapter = new RvAdapter();
        rvTickets.setAdapter(adapter);
        adapter.addAll(getItems());
    }

    private ArrayList<Ticket> getItems()
    {
        ArrayList<Ticket> tickets = new ArrayList<>();
        for(int i=0; i<20; i++)
        {
            Ticket t = new Ticket();
            t.setDescription("Desc "+i);
            t.setAmount(i);
            tickets.add(t);
        }

        return tickets;
    }
}
