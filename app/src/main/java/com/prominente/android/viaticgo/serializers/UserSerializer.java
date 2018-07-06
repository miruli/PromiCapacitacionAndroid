package com.prominente.android.viaticgo.serializers;

import android.content.Context;

import com.prominente.android.viaticgo.constants.SerializerKeys;
import com.prominente.android.viaticgo.models.User;


public class UserSerializer extends ObjectSerializer {
    public void save(Context context, User user) {
        super.save(context, SerializerKeys.USER, user);
    }

    public User load(Context context, String fileName) {
        return (User)super.load(context, fileName);
    }
}
