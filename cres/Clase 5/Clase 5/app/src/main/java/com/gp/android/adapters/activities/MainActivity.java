package com.gp.android.adapters.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.gp.android.adapters.R;
import com.gp.android.adapters.adapters.RvAdapter;
import com.gp.android.adapters.adapters.RvAdapterClickLister;
import com.gp.android.adapters.data.RestApi;
import com.gp.android.adapters.model.Sponsor;
import com.gp.android.adapters.model.Ticket;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private RvAdapter adapter;
    private LoadTask loadTask;

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
                Sponsor t = adapter.getItems().get(position);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
        rvTickets.setAdapter(adapter);
        //adapter.addAll(getItems());

        loadTask = new LoadTask();
        loadTask.execute();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if(loadTask != null && loadTask.getStatus().equals(AsyncTask.Status.RUNNING))
            loadTask.cancel(true);
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

    private class LoadTask extends AsyncTask<Void, Integer, ArrayList<Sponsor>>
    {
        public LoadTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Sponsor> doInBackground(Void... voids) {
            return RestApi.getInstance().getSponsors();
        }

        @Override
        protected void onPostExecute(ArrayList<Sponsor> sponsors) {
            if(sponsors != null)
                adapter.addAll(sponsors);
        }
    }
}
