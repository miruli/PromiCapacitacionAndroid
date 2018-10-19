package com.gp.android.clase12;

import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;

public abstract class CloseActivity extends NavUpActivity
{
    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
        //Set close button
        getSupportActionBar().setHomeAsUpIndicator(ResourceUtil.tintDrawable(this, R.drawable.ic_close_black_24dp, ContextCompat.getColor(this, android.R.color.white)));
    }
}
