package com.gp.android.adapters.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@Table
public class Ticket extends SugarRecord implements Serializable
{
    private static final long serialVersionUID = 5530255968065458983L;

    private String description;
    private double amount;

    public Ticket() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    private void writeObject(ObjectOutputStream o) throws IOException
    {
        o.defaultWriteObject();

        try
        {
            o.writeLong(getId());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException
    {
        o.defaultReadObject();

        try
        {
            setId(o.readLong());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
