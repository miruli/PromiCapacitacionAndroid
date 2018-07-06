package com.tickettravel.grupo2.tickettravel.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tickettravel.grupo2.tickettravel.R;

public class LoginActivity extends AppCompatActivity {

    EditText userText, passText;
    Button buttonText;
    private final String USER="GRUPO1";
    private final String PASSWORD="123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userText = findViewById(R.id.login_userText);
        passText = findViewById(R.id.login_passText);
        buttonText=findViewById(R.id.login_buttonLogin);

        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void login() {
        Intent toHome=new Intent(LoginActivity.this,HomeActivity.class );
        startActivity(toHome);
    }

    private void validate() {

        if(USER.equals(userText.getText().toString().toUpperCase()) && PASSWORD.equals(passText.getText().toString()))
        {
            login();
        }
        else
        {
            Toast.makeText(this,"Usuario o Contraseña incorrectas", Toast.LENGTH_LONG).show();
        }
    }
}
