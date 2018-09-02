package com.prominente.android.viaticgo.application;

import android.app.Application;

import com.orm.SugarContext;

public class ViaticGoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }
}
