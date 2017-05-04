package com.blogspot.mowael.molib.application;

import android.app.Application;

import com.blogspot.mowael.molib.network.Service;
import com.blogspot.mowael.molib.network.VolleyClient;
import com.blogspot.mowael.molib.storage.CacheManager;
import com.blogspot.mowael.molib.storage.SharedPreferencesManager;
import com.blogspot.mowael.molib.user.UserUtil;
import com.blogspot.mowael.molib.utilities.MoUiUtil;

/**
 * Created by moham on 3/2/2017.
 */

public class MoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.getInstance();
        SharedPreferencesManager.getInstance(this);
        VolleyClient.getInstance(this).getRequestQueue().start();
        Service.getInstance().setContextAndInitService(getApplicationContext());
        UserUtil.getInstance(this);
        MoUiUtil.getInstance().setContext(getApplicationContext());
    }
}
