package com.blogspot.mowael.molib.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.blogspot.mowael.molib.R;
import com.blogspot.mowael.molib.fragments.MoFragment;

public class MoActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_mo);
    }

    @Override
    public void setContentView(@LayoutRes int layoutRes) {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup container = (ViewGroup) findViewById(R.id.flFragment);
        if (layoutRes != 0) {
            findViewById(R.id.tvMoActivityDescription).setVisibility(View.GONE);
            container.addView(inflater.inflate(layoutRes, null));
        }
    }


    public void makeFullScreen(boolean fullscreen) {
        if (fullscreen)
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public void loadFragment(MoFragment fragment, @IdRes int in, String tag, boolean isAddToBackStack) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        if (isAddToBackStack)
            fragmentManager.beginTransaction().addToBackStack(null).replace(in, fragment, tag).commit();
        else fragmentManager.beginTransaction().replace(in, fragment, tag).commit();
    }

    public void loadFragment(MoFragment fragment, @IdRes int in, boolean isAddToBackStack) {
        loadFragment(fragment, in, "", isAddToBackStack);
    }

    public void loadFragment(MoFragment fragment, @IdRes int in) {
        loadFragment(fragment, in, true);
    }

    public void loadFragment(MoFragment fragment, boolean isAddToBackStack) {
        loadFragment(fragment, R.id.flFragment, isAddToBackStack);
    }

    public void loadFragment(MoFragment fragment) {
        loadFragment(fragment, true);
    }

}
