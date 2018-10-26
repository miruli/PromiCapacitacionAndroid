package com.prominente.android.viaticgo.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import com.prominente.android.viaticgo.R;
import com.prominente.android.viaticgo.constants.PreferenceKeys;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Dialog dialog;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean darkTheme = sharedPreferences.getBoolean(PreferenceKeys.DARK_THEME, true);
       /* if (darkTheme)
            dialog = new DatePickerDialog(getActivity(), R.style.AppTheme_Dark_Dialog, onDateSetListener, year, month, day);
        else*/
            dialog = new DatePickerDialog(getActivity(), R.style.AppTheme_Dialog, onDateSetListener, year, month, day);
        return dialog;
    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }
}
