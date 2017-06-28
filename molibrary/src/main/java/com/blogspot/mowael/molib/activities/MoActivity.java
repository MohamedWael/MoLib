package com.blogspot.mowael.molib.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.blogspot.mowael.molib.R;
import com.blogspot.mowael.molib.fragments.MoFragment;

public class MoActivity extends AppCompatActivity {
    /*
    TODO consider adding ActionBarDrawerToggle (The Hamburger Button) in the tool bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
    * */
//    private FragmentManager fragmentManager;
    private MoFragment fragment;
    private boolean isRequestPermissionsResultForFragmentAllowed = false;
    private boolean isActivityResultFragmentAllowed = false;

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragment != null && isActivityResultFragmentAllowed)
            fragment.onActivityResult(requestCode, resultCode, data);
    }

    public void allowActivityResultForFragment(boolean isActivityResultFragmentAllowed) {
        this.isActivityResultFragmentAllowed = isActivityResultFragmentAllowed;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (fragment != null && isRequestPermissionsResultForFragmentAllowed)
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void allowRequestPermissionsResultForFragment(boolean isRequestPermissionsResultForFragmentAllowed) {
        this.isRequestPermissionsResultForFragmentAllowed = isRequestPermissionsResultForFragmentAllowed;
    }

    /**
     * find the toolbar by id
     * <p>
     * to be used if you want to setSupportActionBar(toolbar) in your activity
     *
     * @param id the resource of the toolbar in your activity
     * @return
     */
    public Toolbar setToolbarLayout(@IdRes int id) {
        Toolbar toolbar = (Toolbar) findViewById(id);
        return toolbar;
    }

    /**
     * add back arrow to toolbar
     */
    public void enableBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    public Toolbar enableToolbar(@IdRes int id) {
        Toolbar toolbar = setToolbarLayout(id);
        setSupportActionBar(toolbar);
        return toolbar;
    }


    /**
     * enables the Default Toolbar
     *
     * @return
     */
    public Toolbar enableToolbar() {
        findViewById(R.id.iToolbar).setVisibility(View.VISIBLE);
        return enableToolbar(R.id.toolbar);
    }


    /**
     * enables the Default Toolbar with back button
     *
     * @param listener       the Click listener of the back button associated with the toolbar
     * @param navigationIcon the Navigation Icon
     * @return
     */
    public Toolbar enableToolbarWithBackButton(@IdRes int id, View.OnClickListener listener, Drawable navigationIcon) {
        Toolbar toolbar = enableToolbar(id);
        if (navigationIcon != null) {
            toolbar.setNavigationIcon(navigationIcon);
        }
        enableBackButton();
        toolbar.setNavigationOnClickListener(listener);
        return toolbar;
    }

    /**
     * enables the Default Toolbar with back button
     *
     * @param listener       the Click listener of the back button associated with the toolbar
     * @param navigationIcon the Navigation Icon
     * @return
     */
    public Toolbar enableToolbarWithBackButton(View.OnClickListener listener, Drawable navigationIcon) {
        findViewById(R.id.iToolbar).setVisibility(View.VISIBLE);
        return enableToolbarWithBackButton(R.id.toolbar, listener, navigationIcon);
    }


    /**
     * @param listener the Click listener of the back button associated with the toolbar
     * @return
     */
    public Toolbar enableToolbarWithBackButton(View.OnClickListener listener) {
        return enableToolbarWithBackButton(listener, null);
    }

    public void makeFullScreen(boolean fullscreen) {
        if (fullscreen)
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * make the status bar to be entirely transparent for KitKat and above.
     */
    public void makeStatusbarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Nullable
    public MoFragment getCurrentFragment() {
        return fragment;
    }


    /**
     * load T fragment in the required resource in identified with the Tag tag
     *
     * @param fragment         the target fragment
     * @param in               the layout to open fragment in it
     * @param tag              the tag used to identify fragment
     * @param isAddToBackStack whether to add the target fragment to backStack or not
     * @param <T>              any fragment that extend the MoFragment
     */
    public <T extends MoFragment> void loadFragment(T fragment, @IdRes int in, String tag, boolean isAddToBackStack) {
        this.fragment = fragment;
        if (isAddToBackStack)
            getSupportFragmentManager().beginTransaction().addToBackStack(tag).replace(in, fragment, tag).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(in, fragment, tag).commit();
    }

    /**
     * the tag is set to class name
     *
     * @param fragment         the target fragment
     * @param in               the layout to open fragment in it
     * @param isAddToBackStack whether to add the target fragment to backStack or not
     * @param <T>              any fragment that extend the MoFragment
     */
    public <T extends MoFragment> void loadFragment(T fragment, @IdRes int in, boolean isAddToBackStack) {
        loadFragment(fragment, in, fragment.getClass().getSimpleName(), isAddToBackStack);
    }

    /**
     * the tag is set to class name
     * isAddToBackStack is set to true
     *
     * @param fragment the target fragment
     * @param in       the layout to open fragment in it
     * @param <T>      any fragment that extend the MoFragment
     */
    public <T extends MoFragment> void loadFragment(T fragment, @IdRes int in) {
        loadFragment(fragment, in, true);
    }

    /**
     * * load fragment in the default container of the MoActivity
     * <p>
     * the tag is set to class name
     * in is set to default fragment container in the MoActivity
     *
     * @param fragment         the target fragment
     * @param isAddToBackStack whether to add the target fragment to backStack or not
     * @param <T>              any fragment that extend the MoFragment
     */
    public <T extends MoFragment> void loadFragment(T fragment, boolean isAddToBackStack) {
        loadFragment(fragment, R.id.flFragment, isAddToBackStack);
    }

    /**
     * load fragment in the default container of the MoActivity
     * <p>
     * the tag is set to class name
     * isAddToBackStack is set to true
     *
     * @param fragment the target fragment
     * @param <T>      any fragment that extend the MoFragment
     */
    public <T extends MoFragment> void loadFragment(T fragment) {
        loadFragment(fragment, true);
    }

}
