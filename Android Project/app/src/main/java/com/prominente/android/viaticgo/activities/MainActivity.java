package com.prominente.android.viaticgo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.data.LocalStorageRepository;
import com.prominente.android.viaticgo.fragments.ExpensesFragment;
import com.prominente.android.viaticgo.interfaces.ILoggedUserRepository;
import com.prominente.android.viaticgo.models.LoggedUser;
import com.prominente.android.viaticgo.services.SyncService;

public class MainActivity extends LightDarkAppCompatActivity {
    private DrawerLayout drawerLayout;
    private ILoggedUserRepository loggedUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent msgIntent = new Intent(this, SyncService.class);
        msgIntent.setAction(SyncService.ACTION_SYNC_ALL);
        startService(msgIntent);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        loggedUserRepository = LocalStorageRepository.getInstance();
        LoggedUser loggedUser = loggedUserRepository.loadLoggedUser(this);
        if (loggedUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_expenses);
        TextView navHeaderTitleTextView = navigationView.getHeaderView(0).findViewById(R.id.nav_header_title);
        if (navHeaderTitleTextView != null) {
            navHeaderTitleTextView.setText(loggedUser.getUserName());
        }
        ExpensesFragment newFragment = new ExpensesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_frame, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        FragmentTransaction transaction;
                        Fragment newFragment;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_expenses:
                                drawerLayout.closeDrawers();
                                newFragment = new ExpensesFragment();
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content_frame, newFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                break;
                            case R.id.nav_logout:
                                loggedUserRepository.saveLoggedUser(MainActivity.this, null);
                                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                                finish();
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        Fragment activeFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (activeFragment instanceof ExpensesFragment) {
            navigationView.setCheckedItem(R.id.nav_expenses);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(findViewById(R.id.nav_view))) {
            drawerLayout.closeDrawers();
        }
        else {
            Fragment activeFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (activeFragment instanceof ExpensesFragment) {
                finish();
            }
            else {
                NavigationView navigationView = findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.nav_expenses);
                super.onBackPressed();
            }
        }
    }
}
