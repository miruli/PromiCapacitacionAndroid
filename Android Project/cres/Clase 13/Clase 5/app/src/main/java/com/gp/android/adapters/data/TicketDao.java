package com.gp.android.adapters.data;

import com.gp.android.adapters.model.Ticket;
import com.orm.SugarRecord;

import java.util.List;

public class TicketDao
{
    public static void insert(Ticket t)
    {
        SugarRecord.save(t);
    }

    public static List<Ticket> listAll()
    {
        return SugarRecord.listAll(Ticket.class);
    }

    public static void delete(Ticket t)
    {
        t.delete();
    }
}
