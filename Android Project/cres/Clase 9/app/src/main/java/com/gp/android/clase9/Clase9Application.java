package com.gp.android.clase9;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class Clase9Application extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        FirebaseApp.initializeApp(this);
    }
}
