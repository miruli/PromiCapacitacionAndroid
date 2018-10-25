package com.prominente.android.viaticgo.interfaces;

import android.content.Context;

import com.prominente.android.viaticgo.models.Surrender;

import java.util.ArrayList;

public interface ISurrenderRepository {
    Boolean saveSurrender(Context context, Surrender surrender);

    void deleteSurrender(Context context, Surrender surrender);

    ArrayList<Surrender> getAllSurrenders(Context context);
}
