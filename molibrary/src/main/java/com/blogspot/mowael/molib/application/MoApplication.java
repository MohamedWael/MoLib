package com.blogspot.mowael.molib.application;

import android.app.Application;

import com.blogspot.mowael.molib.network.Service;
import com.blogspot.mowael.molib.storage.CacheManager;
import com.blogspot.mowael.molib.storage.SharedPreferencesManager;
import com.blogspot.mowael.molib.storage.database.RealmDB;
import com.blogspot.mowael.molib.utilities.MoUiUtil;

/**
 * Created by moham on 3/2/2017.
 */

public class MoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.getInstance();
        RealmDB.getInstance(this, "mo_application", 1);
        SharedPreferencesManager.getInstance().setContext(this).initSharedPreferences();
        Service.getInstance().initService(getApplicationContext());
//        IonService.getInstance().getIon(getApplicationContext());
//        UserUtil.getInstance();
        MoUiUtil.getInstance().setContext(getApplicationContext());
    }

    public void onTerminate() {
        super.onTerminate();
    }
}
