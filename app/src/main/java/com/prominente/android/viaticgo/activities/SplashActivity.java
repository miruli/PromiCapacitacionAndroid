package com.prominente.android.viaticgo.activities;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.prominente.android.viaticgo.models.User;
import com.prominente.android.viaticgo.serializers.UserSerializer;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserSerializer serializer = new UserSerializer();
        User user = serializer.load(this);
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
