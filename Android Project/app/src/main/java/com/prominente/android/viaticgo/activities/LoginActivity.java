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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedUserRepository = LocalStorageRepository.getInstance();
        setContentView(R.layout.activity_login);
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
        int mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        final ContentLoadingProgressBar pbarLogin = findViewById(R.id.pbarLogin);
        pbarLogin.setAlpha(0f);
        pbarLogin.setVisibility(View.VISIBLE);
        pbarLogin.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);
        final LinearLayoutCompat linearLayoutInputs = findViewById(R.id.linearLayoutInputs);
        linearLayoutInputs.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        linearLayoutInputs.setVisibility(View.GONE);
                    }
                });
    }

    private void hideLoading() {
        int mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);


        final LinearLayoutCompat linearLayoutInputs = findViewById(R.id.linearLayoutInputs);
        linearLayoutInputs.setVisibility(View.VISIBLE);
        linearLayoutInputs.setAlpha(0f);
        linearLayoutInputs.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);

        final ContentLoadingProgressBar pbarLogin = findViewById(R.id.pbarLogin);
        pbarLogin.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        pbarLogin.setVisibility(View.GONE);
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
            if (loginResponse != null) {
                loggedUser = new LoggedUser(loginResponse.getUserName());
                if (loginResponse.getError() == null) {
                    //TODO: ver si no se puede pedirle a la api unos datos mas del usuario para poner en el drawer
                    Toast.makeText(LoginActivity.this, "hola " + loginResponse.getUserName(), Toast.LENGTH_LONG).show();
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
            hideLoading();
        }
    }
}
