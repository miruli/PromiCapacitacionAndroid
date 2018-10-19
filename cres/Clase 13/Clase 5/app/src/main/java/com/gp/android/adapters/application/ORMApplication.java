package com.gp.android.adapters.application;

import android.app.Application;

import com.orm.SugarContext;

public class ORMApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        SugarContext.init(this);
    }
}
