package com.gp.android.adapters.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gp.android.adapters.R;
import com.gp.android.adapters.constants.ExtraKeys;
import com.gp.android.adapters.data.TicketDao;
import com.gp.android.adapters.model.Ticket;

public class NewTicketActivity extends AppCompatActivity
{
    private EditText etNewTicketDesc;
    private EditText etNewTicketAmount;

    private Ticket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        etNewTicketDesc = findViewById(R.id.et_new_ticket_desc);
        etNewTicketAmount = findViewById(R.id.et_new_ticket_amount);

        if(getIntent().getSerializableExtra(ExtraKeys.TICKET) != null)
        {
            ticket = (Ticket) getIntent().getSerializableExtra(ExtraKeys.TICKET);
            etNewTicketDesc.setText(ticket.getDescription());
            etNewTicketAmount.setText(String.valueOf(ticket.getAmount()));
        }

        Button btnNewTicketAdd = findViewById(R.id.btn_new_ticket_add);
        Button btnNewTicketDelete = findViewById(R.id.btn_new_ticket_delete);

        btnNewTicketAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                add();
            }
        });

        if(ticket != null)
        {
            btnNewTicketDelete.setVisibility(View.VISIBLE);
            btnNewTicketDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete();
                }
            });
        }
    }

    private void add()
    {
        if(ticket == null)
            ticket = new Ticket();

        ticket.setDescription(etNewTicketDesc.getText().toString());
        ticket.setAmount(Double.parseDouble(etNewTicketAmount.getText().toString()));
        TicketDao.insert(ticket);
        finish();
    }

    private void delete()
    {
        if(ticket != null) {
            TicketDao.delete(ticket);
            finish();
        }
    }
}
