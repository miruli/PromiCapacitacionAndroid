package com.prominente.android.viaticgo.components;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class TextInputLayoutFix extends TextInputLayout
{
    public TextInputLayoutFix(Context context)
    {
        super(context);
    }

    public TextInputLayoutFix(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params)
    {
        if(child instanceof EditText)
        {
            // Disable animation on add EditText to prevent view corruption when databinding is enabled
            // then turn on the animation again when view finish rendering - Alfredo Ariaudo
            setHintAnimationEnabled(false);
            super.addView(child, index, params);
            post(new Runnable() {
                @Override
                public void run() {
                    setHintAnimationEnabled(true);
                }
            });
        }
        else
        {
            super.addView(child, index, params);
        }
    }
}
