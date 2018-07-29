package com.prominente.android.viaticgo.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.data.LocalStorageRepository;
import com.prominente.android.viaticgo.fragments.BlankFragment;
import com.prominente.android.viaticgo.interfaces.ILoggedUserRepository;
import com.prominente.android.viaticgo.models.LoggedUser;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {
    private DrawerLayout drawerLayout;
    private ILoggedUserRepository loggedUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = findViewById(R.id.drawer_layout);
        loggedUserRepository = LocalStorageRepository.getInstance();
        LoggedUser loggedUser = loggedUserRepository.loadLoggedUser(this);

        // este control extra no se si hace falta
        if (loggedUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        TextView navHeaderTitleTextView = navigationView.getHeaderView(0).findViewById(R.id.nav_header_title);
        if (navHeaderTitleTextView != null) {
            navHeaderTitleTextView.setText(loggedUser.getUserName());
        }

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        switch (menuItem.getItemId()) {
                            case R.id.nav_camera:
                                Toast.makeText(MainActivity.this, "nav camera", Toast.LENGTH_SHORT).show();
                                // Create new fragment and transaction
                                BlankFragment newFragment = BlankFragment.newInstance("tuuuu", "mami");
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                                // Replace whatever is in the fragment_container view with this fragment,
                                // and add the transaction to the back stack
                                transaction.replace(R.id.content_container, newFragment);
                                transaction.addToBackStack(null);

                                // Commit the transaction
                                transaction.commit();
                                break;
                            case R.id.nav_gallery:
                                Toast.makeText(MainActivity.this, "nav gallery", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_slideshow:
                                Toast.makeText(MainActivity.this, "nav slideshow", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_logout:
                                loggedUserRepository.saveLoggedUser(MainActivity.this, null);
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }

                        return true;
                    }
                });
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

    public void onFragmentInteraction(Uri uri)
    {

    }
}
