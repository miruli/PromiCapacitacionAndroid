package com.prominente.android.viaticgo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.prominente.android.viaticgo.data.LocalStorageRepository;
import com.prominente.android.viaticgo.interfaces.ILoggedUserRepository;
import com.prominente.android.viaticgo.models.LoggedUser;
import com.prominente.android.viaticgo.services.SyncService;

public class SplashActivity extends AppCompatActivity {
    private ILoggedUserRepository loggedUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*loggedUserRepository = LocalStorageRepository.getInstance();
        LoggedUser user = loggedUserRepository.loadLoggedUser(this);
        if (user != null && user.getToken() != null && !user.getToken().isEmpty()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }*/
    }
}
