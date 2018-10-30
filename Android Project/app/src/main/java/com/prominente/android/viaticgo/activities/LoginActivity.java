package com.prominente.android.viaticgo.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.clients.ApiClient;
import com.prominente.android.viaticgo.data.LocalStorageRepository;
import com.prominente.android.viaticgo.fragments.ItemPickerFragment;
import com.prominente.android.viaticgo.interfaces.ILoggedUserRepository;
import com.prominente.android.viaticgo.models.LoggedUser;
import com.prominente.android.viaticgo.models.LoginResponse;
import com.prominente.android.viaticgo.models.LoginResponseOld;

public class LoginActivity extends LightDarkAppCompatActivity {
    private ILoggedUserRepository loggedUserRepository;
    private LoginTask loginTask;
    private int shortAnimationDuration;
    private ContentLoadingProgressBar barLogin;
    private LinearLayoutCompat linearLayoutInputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedUserRepository = LocalStorageRepository.getInstance();
        setContentView(R.layout.activity_login);
        shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        linearLayoutInputs = findViewById(R.id.linearLayoutInputs);
        barLogin = findViewById(R.id.pbarLogin);
        final Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatEditText txtUserName = findViewById(R.id.txtUserName);
                AppCompatEditText txtPassword = findViewById(R.id.txtPassword);
                loginTask = new LoginTask();
                loginTask.execute(txtUserName.getText().toString(), txtPassword.getText().toString());
            }
        });
    }

    private void showLoading() {
        barLogin.setAlpha(0f);
        barLogin.setVisibility(View.VISIBLE);
        barLogin.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        linearLayoutInputs.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        linearLayoutInputs.setVisibility(View.GONE);
                    }
                });
    }

    private void hideLoading() {
        linearLayoutInputs.setVisibility(View.VISIBLE);
        linearLayoutInputs.setAlpha(0f);
        linearLayoutInputs.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        barLogin.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        barLogin.setVisibility(View.GONE);
                    }
                });
    }

    private class LoginTask extends AsyncTask<String, Integer, LoginResponse> {
        public LoginTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected LoginResponse doInBackground(String... strings) {
            return ApiClient.getInstance(LoginActivity.this).login(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(LoginResponse loginResponse) {
            LoggedUser loggedUser = null;
            boolean loggingSuccess = false;
            if (loginResponse != null) {
                loggedUser = new LoggedUser(loginResponse.getUserName());
                loggingSuccess = (loginResponse.getError() == null);
                if (loggingSuccess) {
                    Toast.makeText(LoginActivity.this, getString(R.string.welcome) + " " + loginResponse.getUserName(), Toast.LENGTH_LONG).show();
                    loggedUser.setToken(loginResponse.getAccessToken());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, loginResponse.getErrorDescription(), Toast.LENGTH_LONG).show();
                }
            }
            loggedUserRepository.saveLoggedUser(LoginActivity.this, loggedUser);
            if (!loggingSuccess)
                hideLoading();
        }
    }
}
