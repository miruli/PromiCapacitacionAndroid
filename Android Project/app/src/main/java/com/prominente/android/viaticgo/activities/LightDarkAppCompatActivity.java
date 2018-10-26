package com.prominente.android.viaticgo.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.constants.PreferenceKeys;

public class LightDarkAppCompatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkTheme = sharedPreferences.getBoolean(PreferenceKeys.DARK_THEME, true);
        if (darkTheme)
            setTheme(R.style.AppTheme_Dark);
        else
            setTheme(R.style.AppTheme);*/
        super.onCreate(savedInstanceState);
    }
}
