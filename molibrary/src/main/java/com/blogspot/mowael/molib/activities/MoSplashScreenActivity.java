package com.blogspot.mowael.molib.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.blogspot.mowael.molib.R;
import com.blogspot.mowael.molib.utilities.Logger;
import com.blogspot.mowael.molib.utilities.MoConfig;

public class MoSplashScreenActivity extends MoActivity implements SplashScreenSettings {

    private SplashScreenSettings settings;
    private final String SPLASH_SCREEN_SETTINGS = SplashScreenSettings.class.getSimpleName();// "SplashScreenSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen(true);
        if (settings != null) Logger.d(SPLASH_SCREEN_SETTINGS, "Override");
        else Logger.d(SPLASH_SCREEN_SETTINGS, "Default");
        setContentView(settings != null ? settings.getSplashScreenLayout() : getSplashScreenLayout());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentIntro = new Intent(MoSplashScreenActivity.this,
                        settings != null ? settings.getMainActivityClass() : MoActivity.class);

                if (settings.getBundle() != null) {
                    intentIntro.putExtras(settings.getBundle());
                }

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
        return R.layout.activity_mo_splash_screen;
    }

    @Override
    public int getSplashScreenTimeOut() {
        return MoConfig.DEFAULT_SPLASH_TIME_OUT;
    }

    @Override
    public Class<? extends AppCompatActivity> getMainActivityClass() {
        return MoActivity.class;
    }

    @Override
    public Bundle getBundle() {
        return null;
    }
}
