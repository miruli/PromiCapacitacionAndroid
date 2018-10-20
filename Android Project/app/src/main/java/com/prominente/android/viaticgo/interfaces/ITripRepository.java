package com.prominente.android.viaticgo.interfaces;
import android.content.Context;
import com.prominente.android.viaticgo.models.Trip;
import java.util.ArrayList;

public interface ITripRepository {
    void syncTrips(Context context, ArrayList<Trip> trips);

    ArrayList<Trip> getAllTrips(Context context);
}
