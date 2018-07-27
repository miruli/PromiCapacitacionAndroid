package com.prominente.android.viaticgo.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.data.LocalStorageRepository;
import com.prominente.android.viaticgo.interfaces.ILoggedUserRepository;
import com.prominente.android.viaticgo.models.LoggedUser;

public class LoginActivity extends AppCompatActivity {
    private ILoggedUserRepository loggedUserRepository;

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
                // dummy login logic
                if (txtUserName.getText().toString().equals(txtPassword.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "hola " + txtUserName.getText().toString(), Toast.LENGTH_LONG).show();
                    loggedUserRepository.saveLoggedUser(LoginActivity.this, new LoggedUser(txtUserName.getText().toString(), txtPassword.getText().toString()));
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "error en credenciales", Toast.LENGTH_LONG).show();
                    loggedUserRepository.saveLoggedUser(LoginActivity.this, null);
                }
            }
        });
    }
}
