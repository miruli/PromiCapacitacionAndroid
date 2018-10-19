package com.gp.android.adapters.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gp.android.adapters.R;
import com.gp.android.adapters.adapters.RvAdapter;
import com.gp.android.adapters.adapters.RvAdapterClickLister;
import com.gp.android.adapters.constants.ExtraKeys;
import com.gp.android.adapters.data.TicketDao;
import com.gp.android.adapters.model.Ticket;

import java.util.List;

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
        rvTickets.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new RvAdapter(new RvAdapterClickLister() {
            @Override
            public void onItemClick(View v, int position)
            {
                Ticket t = adapter.getItems().get(position);
                addTicket(t);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
        rvTickets.setAdapter(adapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_add:
                addTicket(null);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addTicket(Ticket t)
    {
        Intent intent = new Intent(this, NewTicketActivity.class);

        if(t != null)
            intent.putExtra(ExtraKeys.TICKET, t);

        startActivity(intent);
    }

    private class LoadTask extends AsyncTask<Void, Integer, List<Ticket>>
    {
        public LoadTask()
        {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Ticket> doInBackground(Void... voids)
        {
            return TicketDao.listAll();
        }

        @Override
        protected void onPostExecute(List<Ticket> tickets) {
            if(tickets != null)
                adapter.addAll(tickets);
        }
    }
}
