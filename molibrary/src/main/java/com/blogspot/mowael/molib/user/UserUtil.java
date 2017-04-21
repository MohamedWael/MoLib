package com.blogspot.mowael.molib.user;

import android.content.Context;

import com.blogspot.mowael.molib.storage.SharedPreferencesManager;

/**
 * Created by moham on 4/20/2017.
 */

public class UserUtil {
    private static UserUtil instance;
    private SharedPreferencesManager manager;
    private final String HAVE_ACCOUNT = "UserHaveAccount";
    private final String USER_NAME = "UserName";
    private final String Email = "UserEmail";
    private boolean haveAccount = false;
    private String userName, email;

    public UserUtil(Context context) {
        manager = SharedPreferencesManager.getInstance(context);
        haveAccount = manager.getPrefs().getBoolean(HAVE_ACCOUNT, false);
        if (haveAccount) {
            userName = manager.getPrefs().getString(USER_NAME, "");
            email = manager.getPrefs().getString(Email, "");
        }
    }

    public static UserUtil getInstance(Context context) {
        if (instance == null) {
            instance = new UserUtil(context);
        }
        return instance;
    }

    public void setUser(String userName, String email) {
        if (manager != null) {
            if (!userName.isEmpty() && !email.isEmpty())
                manager.getEditor().putString(USER_NAME, userName).putString(Email, email).putBoolean(HAVE_ACCOUNT, true).apply();
            else throw new RuntimeException("userName and email mustn't be empty or null");
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return Email;
    }

    public boolean isUserHaveAccount() {
        return haveAccount;
    }

    public void setHaveAccount(boolean haveAccount) {
        this.haveAccount = haveAccount;
    }
}
