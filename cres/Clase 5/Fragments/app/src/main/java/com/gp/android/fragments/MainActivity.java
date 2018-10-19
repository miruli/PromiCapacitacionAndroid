package com.gp.android.fragments;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gp.android.fragments.fragments.ArticlesFragment;
import com.gp.android.fragments.fragments.HeadlinesFragment;
import com.gp.android.fragments.model.Sponsor;

public class MainActivity extends AppCompatActivity implements HeadlinesFragment.OnHeadlineSelectedListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fl_container) != null)
        {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null)
                return;

            // Create a new Fragment to be placed in the activity layout
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container, firstFragment).commit();
        }
    }

    @Override
    public void onArticleSelected(Sponsor sponsor)
    {
        ArticlesFragment articleFrag = (ArticlesFragment) getSupportFragmentManager().findFragmentById(R.id.fr_articles);

        if (articleFrag != null)
        {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            articleFrag.updateArticleView(sponsor);
        }
        else
        {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            ArticlesFragment newFragment = new ArticlesFragment();
            Bundle args = new Bundle();
            args.putSerializable("Sponsor", sponsor);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fl_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
}
