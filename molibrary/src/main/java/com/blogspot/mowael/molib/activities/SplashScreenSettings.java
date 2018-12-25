package com.blogspot.mowael.molib.activities;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by moham on 3/2/2017.
 */

public interface SplashScreenSettings {

    @LayoutRes
    int getSplashScreenLayout();

    int getSplashScreenTimeOut();

    Class<? extends AppCompatActivity> getMainActivityClass();

    Bundle getBundle();
}
