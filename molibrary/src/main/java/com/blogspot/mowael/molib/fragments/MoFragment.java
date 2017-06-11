package com.blogspot.mowael.molib.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.blogspot.mowael.molib.R;
import com.blogspot.mowael.molib.presenter.MoMVP;
import com.blogspot.mowael.molib.presenter.MoPresenter;
import com.blogspot.mowael.molib.utilities.Logger;

import java.util.Locale;

//// TODO: 6/12/2017 Make sure to remove any allocated variable on the on destroy method

public class MoFragment extends Fragment implements MoMVP.MoView, SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentManager fragmentManager;
    private MoMVP.MoPresenter presenter;
    private View rootView;
    private ProgressBar pbProgress;
    private RelativeLayout rlFragmentRoot;
    private SwipeRefreshLayout srlRoot;
    private LinearLayout llBlockView;

    public MoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoFragment newInstance(String param1, String param2) {
        MoFragment fragment = new MoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        presenter = new MoPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mo, container, false);
        bindViewToRootView(rootView);
        return rootView;
    }

    /**
     * @param rootView
     */
    protected void bindViewToRootView(View rootView) {
        srlRoot = (SwipeRefreshLayout) rootView.findViewById(R.id.srlRoot);
        rlFragmentRoot = (RelativeLayout) rootView.findViewById(R.id.rlFragmentRoot);
        pbProgress = (ProgressBar) rootView.findViewById(R.id.pbProgress);
        llBlockView = (LinearLayout) rootView.findViewById(R.id.llBlockView);
        setRefreshing(false);
        srlRoot.setOnRefreshListener(this);
        llBlockView.setVisibility(View.GONE);
    }

    public void setRefreshing(boolean refreshing) {
        if (srlRoot != null)
            srlRoot.setRefreshing(refreshing);
    }

    public void addBlockView(@LayoutRes int viewRes) {
        addBlockView(LayoutInflater.from(getContext()).inflate(viewRes, null));
    }

    public void addBlockView(View view) {
        if (llBlockView != null)
            llBlockView.addView(view);
    }

    public void showBlockView(boolean show) {
        if (llBlockView != null)
            llBlockView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * You have to call on super.onCreateView() in your onCreateView before inflating your View
     *
     * @return the root view inflated with the MoFragment
     */
    @Nullable
    public View attachToRootView(View view) {
        if (rootView == null)
            throwMoFragmentException();
        rlFragmentRoot.addView(view);
        return rootView;
    }

    /**
     * @return the root progress bar attached in the root view
     */
    @Nullable
    protected ProgressBar getRootProgressBar() {
        return pbProgress;
    }

    protected void showRootProgress(boolean show) {
        if (getRootProgressBar() != null) {
            getRootProgressBar().setVisibility(show ? View.VISIBLE : View.GONE);
        } else {
            Logger.e("getRootProgressBar()", "is null");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * start the required Activity and finish the origin
     *
     * @param aClass
     */
    public void startActivity(Class<? extends Activity> aClass) {
        startActivity(aClass, true);
    }

    public void startActivity(Class<? extends Activity> aClass, boolean finish) {
        startActivity(getContext(), aClass, null, finish);
    }

    public void startActivity(Class<? extends Activity> aClass, Bundle extras, boolean finish) {
        startActivity(getContext(), aClass, extras, finish);
    }

    public void startActivity(Context context, Class<? extends Activity> aClass, boolean finish) {
        startActivity(context, aClass, null, finish);
    }

    public void startActivity(Context context, Class<? extends Activity> aClass) {
        startActivity(context, aClass, null, false);
    }

    public void startActivity(Context context, Class<? extends Activity> aClass, @Nullable Bundle extras, boolean finish) {
        Intent intent = new Intent(context, aClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
        if (finish)
            ((Activity) context).finish();
    }

    public <T extends MoFragment> void loadFragment(T fragment, @IdRes int in, String tag, boolean isAddToBackStack) {
        if (fragmentManager == null) {
            fragmentManager = getFragmentManager();
        }
        if (isAddToBackStack)
            fragmentManager.beginTransaction().addToBackStack(null).replace(in, fragment, tag).commit();
        else fragmentManager.beginTransaction().replace(in, fragment, tag).commit();
    }

    public <T extends MoFragment> void loadFragment(T fragment, @IdRes int in, boolean isAddToBackStack) {
        loadFragment(fragment, in, fragment.getClass().getSimpleName(), isAddToBackStack);
    }

    /**
     * load fragment in the provided layout and add it to the backStack
     *
     * @param fragment
     * @param in
     * @param <T>
     */
    public <T extends MoFragment> void loadFragment(T fragment, @IdRes int in) {
        loadFragment(fragment, in, true);
    }

    public <T extends MoFragment> void loadFragment(T fragment, boolean isAddToBackStack) {
        loadFragment(fragment, R.id.flFragment, isAddToBackStack);
    }

    /**
     * it adds the fragment to backStack by default.
     *
     * @param fragment
     * @param <T>
     */
    public <T extends MoFragment> void loadFragment(T fragment) {
        loadFragment(fragment, true);
    }


    public void setHasOptionsMenu(boolean hasOptionsMenu) {
        super.setHasOptionsMenu(hasOptionsMenu);
    }

    /**
     * @param lang e.g. (en|ar)
     */
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getContext().getApplicationContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.locale = myLocale;
        } else {
            config.setLocale(myLocale);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getContext().createConfigurationContext(config);
            return;
        }
        getContext().getApplicationContext().getResources().updateConfiguration(config, dm);
        res.updateConfiguration(config, dm);
    }

    private void throwMoFragmentException() {
        throw new RuntimeException("You have to call on super.onCreateView() in your onCreateView before inflating your View");
    }

    @Override
    public MoFragment getFragment() {
        return this;
    }

    @Override
    public void onRefresh() {

    }



}
