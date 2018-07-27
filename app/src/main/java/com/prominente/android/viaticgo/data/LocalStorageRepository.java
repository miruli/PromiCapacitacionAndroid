package com.prominente.android.viaticgo.data;

import android.content.Context;

import com.prominente.android.viaticgo.constants.SerializerKeys;
import com.prominente.android.viaticgo.interfaces.ILoggedUserRepository;
import com.prominente.android.viaticgo.models.LoggedUser;
import com.prominente.android.viaticgo.serializers.ObjectSerializer;

public final class LocalStorageRepository implements ILoggedUserRepository {
    private static LocalStorageRepository instance;

    public static LocalStorageRepository getInstance()
    {
        if (instance == null)
            instance = new LocalStorageRepository();
        return instance;
    }

    private LocalStorageRepository()
    {
        if (instance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public void saveLoggedUser(Context context, LoggedUser loggedUser) {
        ObjectSerializer.save(context, SerializerKeys.USER, loggedUser);
    }

    public LoggedUser loadLoggedUser(Context context){
        return (LoggedUser)ObjectSerializer.load(context, SerializerKeys.USER);
    }
}
