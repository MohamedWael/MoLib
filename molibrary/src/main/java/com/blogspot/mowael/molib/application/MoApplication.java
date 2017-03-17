package com.blogspot.mowael.molib.application;

import android.app.Application;

import com.blogspot.mowael.molib.database.RealmDB;
import com.blogspot.mowael.molib.network.VolleySingleton;
import com.blogspot.mowael.molib.utilities.CacheManager;
import com.blogspot.mowael.molib.utilities.SharedPreferencesManager;

/**
 * Created by moham on 3/2/2017.
 */

public class MoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.getInstance();
        SharedPreferencesManager.getInstance(this);
        RealmDB.getInstance(this);
        VolleySingleton.getInstance(this).getRequestQueue().start();
    }
}
