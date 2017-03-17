package com.blogspot.mowael.molib.activities;

import android.support.annotation.LayoutRes;

/**
 * Created by moham on 3/2/2017.
 */

public interface SplashScreenSettings {

    @LayoutRes
    public int getSplashScreenLayout();

    public int getSplashScreenTimeOut();
}
