package com.blogspot.mowael.molib.presenter;


import android.content.Intent;
import android.support.annotation.NonNull;

import com.blogspot.mowael.molib.network.listeners.OnServiceLoading;
import com.blogspot.mowael.molib.network.listeners.ServiceResponseListener;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;

import org.json.JSONObject;

/**
 * Created by moham on 4/26/2017.
 */

public abstract class MoPresenter<T extends MoContract.MoView> implements MoContract.MoPresenter<T>, ServiceResponseListener, OnServiceLoading {

    protected T view;

    public MoPresenter(T view) {
        this.view = view;
        setOnServiceLoadingListener();
    }

    public void setOnServiceLoadingListener() {
        if (getService() != null)
            getService().setOnServiceLoadingListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    public abstract MoContract.MoBusiness getService();


    @Override
    public void onResponseSuccess(GeneralResponse response) {

    }

    @Override
    public void onResponse(JSONObject response, int responseCode) {

    }

    @Override
    public void onNetworkUnavailable(String noInternetMessage) {

    }

    @Override
    public void onStartLoadingProgress() {
        if (view != null) view.onStartLoadingProgress();
    }

    @Override
    public void onLoadingProgressComplete() {
        if (view != null) view.onLoadingProgressComplete();
    }
}
