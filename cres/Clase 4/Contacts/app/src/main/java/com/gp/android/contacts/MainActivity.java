package com.gp.android.contacts;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private static final int PICK_CONTACT_REQUEST = 1;
    private static final int PICK_VERSION_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMainContact = findViewById(R.id.btn_main_contact);
        btnMainContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pickContact();
            }
        });

        Button btnMainCustom = findViewById(R.id.btn_main_custom);
        btnMainCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pickCustom();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Check which request it is that we're responding to
        switch (requestCode)
        {
            case PICK_CONTACT_REQUEST:
                // Make sure the request was successful
                if (resultCode == RESULT_OK)
                {
                    // Get the URI that points to the selected contact
                    Uri contactUri = data.getData();
                    // We only need the NUMBER column, because there will be only one row in the result
                    String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                    // Perform the query on the contact to get the NUMBER column
                    // We don't need a selection or sort order (there's only one result for the given URI)
                    // CAUTION: The query() method should be called from a separate thread to avoid blocking
                    // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                    // Consider using CursorLoader to perform the query.
                    Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    // Retrieve the phone number from the NUMBER column
                    int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = cursor.getString(column);

                    // Do something with the phone number...
                    Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "No selecciono nada", Toast.LENGTH_SHORT).show();
                }
                break;

            case PICK_VERSION_REQUEST:
                if (resultCode == RESULT_OK)
                {
                    String selection = data.getStringExtra(ExtraKeys.SELECTION);
                    Toast.makeText(this, selection, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void pickContact()
    {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    private void pickCustom()
    {
        Intent pickIntent = new Intent(this, PickActivity.class);
        startActivityForResult(pickIntent, PICK_VERSION_REQUEST);
    }
}
