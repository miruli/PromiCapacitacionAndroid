package com.gp.android.adapters.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.gp.android.adapters.R;
import com.gp.android.adapters.adapters.RvAdapter;
import com.gp.android.adapters.adapters.RvAdapterClickLister;
import com.gp.android.adapters.model.Ticket;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private RvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvTickets = findViewById(R.id.rv_tickets);
        rvTickets.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RvAdapter(new RvAdapterClickLister() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(MainActivity.this, "Position "+position, Toast.LENGTH_SHORT).show();
                Ticket t = adapter.getItems().get(position);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
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
