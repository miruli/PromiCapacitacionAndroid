package com.gp.android.fragments.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gp.android.fragments.R;
import com.gp.android.fragments.model.Sponsor;

public class ArticlesFragment extends Fragment
{
    private TextView tvTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_articles, container, false);
        tvTitle = rootView.findViewById(R.id.tv_title);

        if(getArguments() != null)
        {
            Sponsor sponsor = (Sponsor) getArguments().getSerializable("Sponsor");
            tvTitle.setText(sponsor.getName());
        }

        return rootView;
    }

    public void updateArticleView(Sponsor sponsor)
    {
        tvTitle.setText(sponsor.getName());
    }
}
