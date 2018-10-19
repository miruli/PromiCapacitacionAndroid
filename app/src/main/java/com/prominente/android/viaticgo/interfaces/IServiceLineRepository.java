package com.prominente.android.viaticgo.interfaces;

import android.content.Context;

import com.prominente.android.viaticgo.models.ServiceLine;

import java.util.ArrayList;

public interface IServiceLineRepository {
    void syncServiceLines(Context context, ArrayList<ServiceLine> serviceLines);

    ArrayList<ServiceLine> getAllServiceLines(Context context);
}
