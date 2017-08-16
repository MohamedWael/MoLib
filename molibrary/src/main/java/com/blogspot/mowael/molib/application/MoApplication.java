package com.blogspot.mowael.molib.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;

import com.blogspot.mowael.molib.network.Service;
import com.blogspot.mowael.molib.storage.CacheManager;
import com.blogspot.mowael.molib.storage.SharedPreferencesManager;
import com.blogspot.mowael.molib.storage.database.RealmDB;
import com.blogspot.mowael.molib.utilities.LocaleHelper;
import com.blogspot.mowael.molib.utilities.MoUiUtil;

import java.util.Locale;

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

    public void refreshLocale(@NonNull Context context, boolean force) {

        // TODO: 8/17/2017 a way to persist language locally other than LocalHelper
        // i might save it in a shared preferences but not in the LocalHelper

        final String language = LocaleHelper.getPersistedData(context).equals(LocaleHelper.Language.AR.getLanguage()) ? "ar" : null;

        final Locale locale;
        if ("ar".equals(language)) {
            locale = new Locale("ar");
        } else if (force) {
            // get the system locale (since we overwrote the default locale)
            locale = Resources.getSystem().getConfiguration().locale;
        } else {
            // nothing to do...
            return;
        }

        updateLocale(context, locale);
        final Context appContext = context.getApplicationContext();
        if (context != appContext) {
            updateLocale(appContext, locale);
        }
    }

    private void updateLocale(@NonNull Context context, @NonNull Locale locale) {
        final Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLayoutDirection(config.locale);
        }
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
