package com.prominente.android.viaticgo.interfaces;

import android.content.Context;

import com.prominente.android.viaticgo.models.LoggedUser;

public interface ILoggedUserRepository {
    public void saveLoggedUser(Context context, LoggedUser loggedUser);

    public LoggedUser loadLoggedUser(Context context);
}
