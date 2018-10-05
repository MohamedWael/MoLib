package com.blogspot.mowael.molib.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.blogspot.mowael.molib.utilities.MoConfig;
import com.blogspot.mowael.utilslibrary.storage.SharedPreferencesManager;

/**
 * Created by moham on 4/20/2017.
 */

public class UserUtil implements IUserUtil {
    private static UserUtil instance;
    private SharedPreferencesManager manager;
    public static final String NOT_EXIST = MoConfig.NOT_EXIST;
    private final String HAVE_ACCOUNT = "UserHaveAccount";
    private final String API_TOKEN = "apiToken";
    private final String USER_NAME = "UserName";
    private final String EMAIL = "UserEmail";
    private final String FIREBASE_TOKEN = "firebaseToken";
    private final String USER_INTRO = "userSkipIntro";

    private String userName, email, apiToken, firebaseToken;

    protected UserUtil(Context context) {
        manager = SharedPreferencesManager.getInstance();
        manager.initSharedPreferences(context);
        if (isUserHaveAccount()) {
            userName = manager.getPrefs().getString(USER_NAME, NOT_EXIST);
            email = manager.getPrefs().getString(EMAIL, NOT_EXIST);
            apiToken = manager.getPrefs().getString(API_TOKEN, NOT_EXIST);
            firebaseToken = manager.getPrefs().getString(FIREBASE_TOKEN, NOT_EXIST);
        }
    }

    /**
     * use this if you didn't initialize the UserUtil before
     *
     * @param context
     * @return
     */
    public static UserUtil getInstance(Context context) {
        if (instance == null) {
            instance = new UserUtil(context);
        }
        return instance;
    }

    protected UserUtil() {
        manager = SharedPreferencesManager.getInstance();
        if (isUserHaveAccount()) {
            userName = manager.getPrefs().getString(USER_NAME, "");
            email = manager.getPrefs().getString(EMAIL, "");
            apiToken = manager.getPrefs().getString(API_TOKEN, "");
        }
    }

    public static UserUtil getInstance() {
        if (instance == null) {
            instance = new UserUtil();
        }
        return instance;
    }

    @Override
    public void setUser(String userName, String email) {
        if (manager != null) {
            if (notEmpty(userName) && notEmpty(email))
                putString(USER_NAME, userName).putString(EMAIL, email).putBoolean(HAVE_ACCOUNT, true).apply();
            else throw new RuntimeException("userName and email mustn't be empty or null");
        }
    }

    @Override
    public void setUser(String userName, String email, String apiToken) {
        if (manager != null) {
            if (notEmpty(userName) && notEmpty(email) && notEmpty(apiToken))
                putString(USER_NAME, userName).putString(EMAIL, email)
                        .putString(API_TOKEN, apiToken).putBoolean(HAVE_ACCOUNT, true).apply();
            else throw new RuntimeException("userName and email mustn't be empty or null");
        }
    }

    public void setUser(String userName, String email, String apiToken, String firebaseToken) {
        if (manager != null) {
            if (notEmpty(userName) && notEmpty(email) && notEmpty(apiToken) && notEmpty(firebaseToken))
                putString(USER_NAME, userName).putString(EMAIL, email).putString(FIREBASE_TOKEN, firebaseToken)
                        .putString(API_TOKEN, apiToken).putBoolean(HAVE_ACCOUNT, true).apply();
            else throw new RuntimeException("userName and email mustn't be empty or null");
        }
    }

    public void setTempUser(String userName, String email, String apiToken, String firebaseToken) {
        if (manager != null) {
            if (notEmpty(userName) && notEmpty(email) && notEmpty(apiToken) && notEmpty(firebaseToken))
                putString(USER_NAME, userName).putString(EMAIL, email).putString(FIREBASE_TOKEN, firebaseToken)
                        .putString(API_TOKEN, apiToken).putBoolean(HAVE_ACCOUNT, false).apply();
            else throw new RuntimeException("userName and email mustn't be empty or null");
        }
    }

    public void setTempUser(String userName, String email, String apiToken) {
        if (manager != null) {
            if (notEmpty(userName) && notEmpty(email) && notEmpty(apiToken))
                putString(USER_NAME, userName).putString(EMAIL, email)
                        .putString(API_TOKEN, apiToken).putBoolean(HAVE_ACCOUNT, false).apply();
            else throw new RuntimeException("userName and email mustn't be empty or null");
        }
    }

    private SharedPreferences.Editor putString(String key, String value) {
        return manager.getEditor().putString(key, value);
    }

    public SharedPreferences.Editor addMoreDetails(String key, String value) {
        return manager.getEditor().putString(key, value);
    }

    public SharedPreferences.Editor addMoreDetails(String key, long value) {
        return manager.getEditor().putLong(key, value);
    }

    public SharedPreferences.Editor addMoreDetails(String key, boolean value) {
        return manager.getEditor().putBoolean(key, value);
    }

    public String getSomeDetails(String key, String defValue) {
        return manager.getPrefs().getString(key, defValue);
    }

    public long getSomeDetails(String key, long defValue) {
        return manager.getPrefs().getLong(key, defValue);
    }

    public boolean getSomeDetails(String key, boolean defValue) {
        return manager.getPrefs().getBoolean(key, defValue);
    }

    private boolean notEmpty(String s) {
        return !s.trim().isEmpty();
    }

    public String getUserName() {
        return manager.getPrefs().getString(USER_NAME, NOT_EXIST);
    }

    public String getEmail() {
        return manager.getPrefs().getString(EMAIL, NOT_EXIST);
    }

    public String getApiToken() {
        return manager.getPrefs().getString(API_TOKEN, NOT_EXIST);
    }

    public String getFirebaseToken() {
        return manager.getPrefs().getString(FIREBASE_TOKEN, NOT_EXIST);
    }

    public boolean isUserHaveAccount() {
        return manager.getPrefs().getBoolean(HAVE_ACCOUNT, false);
    }

    public void setHaveAccount(boolean haveAccount) {
        manager.getEditor().putBoolean(HAVE_ACCOUNT, haveAccount);
    }

    public void skipIntro(boolean isUserSkipIntro) {
        manager.getEditor().putBoolean(USER_INTRO, isUserSkipIntro);
    }

    public boolean isUserSkipIntro() {
        return manager.getPrefs().getBoolean(USER_INTRO, false);
    }
}
