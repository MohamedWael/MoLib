package com.blogspot.mowael.molib.application;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;

import com.blogspot.mowael.molib.network.Service;
import com.blogspot.mowael.molib.storage.CacheManager;
import com.blogspot.mowael.molib.storage.SharedPreferencesManager;
import com.blogspot.mowael.molib.storage.database.RealmDB;
import com.blogspot.mowael.molib.utilities.LocaleHelper;
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
        MoUiUtil.getInstance().setContext(getApplicationContext());
        LocaleHelper.onAttach(getBaseContext());
    }

    public void restartApplication() {
        Intent i = getBaseContext().getPackageManager().
                getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void exitApplication(Activity currentActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            currentActivity.finishAffinity();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            currentActivity.finishAndRemoveTask();
        }
        System.exit(0);
    }

    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
