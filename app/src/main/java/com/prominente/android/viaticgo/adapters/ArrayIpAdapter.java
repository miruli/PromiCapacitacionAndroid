package com.prominente.android.viaticgo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import java.util.List;

public class ArrayIpAdapter<T> extends ArrayAdapter<T> {
    private int selectedIndex;

    public ArrayIpAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
    }

    public T getSelectedItem() {
        return getItem(selectedIndex);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
}
