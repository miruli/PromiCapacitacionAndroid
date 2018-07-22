package com.prominente.android.viaticgo.adapters;

import android.view.View;

public interface IExpensesRecyclerViewAdapterClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}
