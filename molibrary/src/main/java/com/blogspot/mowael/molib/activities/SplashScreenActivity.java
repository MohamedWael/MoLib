package com.blogspot.mowael.molib.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.blogspot.mowael.molib.R;
import com.blogspot.mowael.molib.utilities.Logger;
import com.blogspot.mowael.molib.utilities.MoConstants;

public class SplashScreenActivity extends MoActivity implements SplashScreenSettings {

    private SplashScreenSettings settings;
    private final String SPLASH_SCREEN_SETTINGS = "SplashScreenSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (settings != null) Logger.d(SPLASH_SCREEN_SETTINGS, "Override");
        else Logger.d(SPLASH_SCREEN_SETTINGS, "Default");
        setContentView(settings != null ? settings.getSplashScreenLayout() : getSplashScreenLayout());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentIntro = new Intent(SplashScreenActivity.this, MoActivity.class);
                startActivity(intentIntro);
                finish();
            }
        }, settings != null ? settings.getSplashScreenTimeOut() : getSplashScreenTimeOut());
    }

    public void setSplashScreenSettings(SplashScreenSettings settings) {
        this.settings = settings;
    }

    @Override
    public int getSplashScreenLayout() {
        return R.layout.activity_splash_screen;
    }

    @Override
    public int getSplashScreenTimeOut() {
        return MoConstants.SPLASH_TIME_OUT;
    }
}
