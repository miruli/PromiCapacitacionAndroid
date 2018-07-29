package com.grupoprominente.payme.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.grupoprominente.payme.Model.User;

import com.grupoprominente.payme.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button btnLog = findViewById(R.id.btnLog);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User u = new User();
                //u.setName("hmlevrino");

               // Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                //intent.putExtra("USER", u);
                //startActivity(intent);
            }
        });

    }

}
