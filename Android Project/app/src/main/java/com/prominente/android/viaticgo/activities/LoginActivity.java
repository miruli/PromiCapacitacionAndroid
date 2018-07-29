package com.prominente.android.viaticgo.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.clients.ApiClient;
import com.prominente.android.viaticgo.data.LocalStorageRepository;
import com.prominente.android.viaticgo.interfaces.ILoggedUserRepository;
import com.prominente.android.viaticgo.models.LoginResponse;

public class LoginActivity extends AppCompatActivity {
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

    private class LoginTask extends AsyncTask<String, Integer, LoginResponse>
    {
        public LoginTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LoginResponse doInBackground(String... strings) {
            return ApiClient.getInstance().login(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(LoginResponse loginResponse) {
            if (loginResponse != null) {
                if (loginResponse.getCode() == 0) {
                    Toast.makeText(LoginActivity.this, "hola " + loginResponse.getData().getUserName(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, OldMainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
                loggedUserRepository.saveLoggedUser(LoginActivity.this, loginResponse.getData());
            }
        }
    }
}
