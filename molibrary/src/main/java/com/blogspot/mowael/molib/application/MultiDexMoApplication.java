package com.blogspot.mowael.molib.application;

import android.content.Context;
import androidx.multidex.MultiDex;

/**
 * Created by moham on 3/2/2017.
 */

public class MultiDexMoApplication extends MoApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
