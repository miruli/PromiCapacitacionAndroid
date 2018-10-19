package com.gp.android.adapters.adapters;

import android.view.View;

public interface RvAdapterClickLister
{
    void onItemClick(View v, int position);
    void onItemLongClick(View v, int position);
}
