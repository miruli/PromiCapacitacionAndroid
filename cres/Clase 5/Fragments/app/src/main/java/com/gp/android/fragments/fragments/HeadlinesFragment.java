package com.gp.android.fragments.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gp.android.fragments.R;
import com.gp.android.fragments.adapters.RvAdapter;
import com.gp.android.fragments.adapters.RvAdapterClickLister;
import com.gp.android.fragments.model.Sponsor;

import java.util.ArrayList;

public class HeadlinesFragment extends Fragment
{
    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener
    {
        void onArticleSelected(Sponsor sponsor);
    }

    private OnHeadlineSelectedListener mCallback;
    private RvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_headlines, container, false);

        RecyclerView rvTickets = rootView.findViewById(R.id.rv_tickets);
        rvTickets.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RvAdapter(new RvAdapterClickLister() {
            @Override
            public void onItemClick(View v, int position)
            {
                mCallback.onArticleSelected(adapter.getItems().get(position));
            }

            @Override
            public void onItemLongClick(View v, int position)
            {
            }
        });
        rvTickets.setAdapter(adapter);
        adapter.addAll(getItems());

        return rootView;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            mCallback = (OnHeadlineSelectedListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement OnHeadlineSelectedListener");
        }
    }

    private ArrayList<Sponsor> getItems()
    {
        ArrayList<Sponsor> sponsors = new ArrayList<>();
        for(int i=0; i<20; i++)
        {
            Sponsor s = new Sponsor();
            s.setName("Nombre "+i);
            sponsors.add(s);
        }

        return sponsors;
    }
}
