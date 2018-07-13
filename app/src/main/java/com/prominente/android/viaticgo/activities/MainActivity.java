package com.prominente.android.viaticgo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.prominente.android.viaticgo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}
