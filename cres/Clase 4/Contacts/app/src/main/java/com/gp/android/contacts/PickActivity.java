package com.gp.android.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PickActivity extends AppCompatActivity
{
    private final String[] VERSIONS = new String[]{"Lollipop", "Marshmallow", "Nougat", "Oreo"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);

        final Spinner spPickVersions = findViewById(R.id.sp_pick_versions);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, VERSIONS);
        spPickVersions.setAdapter(adapter);

        Button btnPickSelect = findViewById(R.id.btn_pick_select);
        btnPickSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String selection = adapter.getItem(spPickVersions.getSelectedItemPosition());
                select(selection);
            }
        });
    }

    private void select(String selection)
    {
        Intent result = new Intent();
        result.putExtra(ExtraKeys.SELECTION, selection);
        setResult(RESULT_OK, result);
        finish();
    }
}
