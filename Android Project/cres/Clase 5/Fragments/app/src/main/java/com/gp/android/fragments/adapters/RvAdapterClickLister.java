package com.gp.android.fragments.adapters;

import android.view.View;

public interface RvAdapterClickLister
{
    void onItemClick(View v, int position);
    void onItemLongClick(View v, int position);
}
