package com.blogspot.mowael.molib.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.blogspot.mowael.molib.utilities.MoConstants;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by moham on 2/1/2017.
 */
public class SharedPreferencesManager {
    private static SharedPreferencesManager ourInstance;
    private final Context mContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    public static SharedPreferencesManager getInstance(Context mContext) {
        if (ourInstance == null) ourInstance = new SharedPreferencesManager(mContext);
        return ourInstance;
    }

    private SharedPreferencesManager(Context mContext) {
        this.mContext = mContext;
        initSharedPreferences();
    }


    public SharedPreferences initSharedPreferences() {
        return prefs = mContext.getSharedPreferences(MoConstants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }

    public SharedPreferences.Editor initEditor() {
        if (prefs == null) {
            initSharedPreferences();
        }
        return editor = prefs.edit();
    }

    /**
     * Returns true if the new values were successfully written to persistent storage
     * or returns false if editor is null or the values weren't written to persistent storage
     */
    public boolean closeEditor() {
        if (editor != null) return editor.commit();
        return false;
    }

    public SharedPreferences.Editor getEditor() {
        if (editor == null) {
            return initEditor();
        }
        return editor;
    }

    public SharedPreferences getPrefs() {
        if (prefs == null) {
            return initSharedPreferences();
        }
        return prefs;
    }
}
