package com.prominente.android.viaticgo.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.constants.PreferenceKeys;

public class ItemPickerFragment extends android.support.v4.app.DialogFragment {
    private DialogInterface.OnClickListener onClickListener;
    private ArrayAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean darkTheme = sharedPreferences.getBoolean(PreferenceKeys.DARK_THEME, true);
        if (darkTheme)
            builder = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dark_Dialog);
        else
            builder = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog);
        builder.setAdapter(adapter, onClickListener);
        return builder.create();
    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
    }
}
