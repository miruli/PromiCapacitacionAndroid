package com.prominente.android.viaticgo.models;

import com.orm.SugarRecord;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ViaticGoModel extends SugarRecord implements Serializable {

    private void writeObject(ObjectOutputStream o) throws IOException {
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
