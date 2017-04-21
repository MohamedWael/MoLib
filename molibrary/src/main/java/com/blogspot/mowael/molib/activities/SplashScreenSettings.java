package com.blogspot.mowael.molib.activities;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by moham on 3/2/2017.
 */

public interface SplashScreenSettings {

    @LayoutRes
    int getSplashScreenLayout();

    int getSplashScreenTimeOut();

    Class<? extends AppCompatActivity> getMainActivityClass();
}
