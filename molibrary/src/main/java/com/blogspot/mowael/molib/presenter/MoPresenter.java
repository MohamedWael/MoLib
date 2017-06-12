package com.blogspot.mowael.molib.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.blogspot.mowael.molib.business.MoBusiness;
import com.blogspot.mowael.molib.network.Service;
import com.blogspot.mowael.molib.network.listeners.OnServiceLoading;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;
import com.blogspot.mowael.molib.utilities.Logger;

import org.json.JSONObject;

/**
 * Created by moham on 4/26/2017.
 */

public class MoPresenter implements MoMVP.MoPresenter, Service.ServiceResponseListener, OnServiceLoading {

    private AlertDialog progressDialog;
    protected MoMVP.MoView view;
    private MoMVP.MoBusiness service;

    public MoPresenter(MoMVP.MoView view) {
        this.view = view;
        getService().setOnServiceLoadingListener(this);
    }

    public void setProgressDialog(AlertDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length == 0) {

            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    onPermissionGranted(requestCode);
                } else {
                    onPermissionDenied(requestCode);
                }
            }

            for (String permission : permissions) {

            }

//            Logger.d("PERMISSION_GRANTED", "true");
        }
    }


    @Override
    public void onPermissionGranted(int requestCode) {
        Logger.d("GrantedRequestCode", requestCode + "");
    }

    @Override
    public void onPermissionDenied(int requestCode) {
        Logger.d("DeniedRequestCode", requestCode + "");
    }

    public MoMVP.MoBusiness getService() {
        if (service == null) {
            service = new MoBusiness();
        }
        return service;
    }

    @Override
    public <T extends GeneralResponse> void onResponseSuccess(T response) {

    }

    @Override
    public void onResponse(JSONObject response, int responseCode) {

    }

    @Override
    public void onNetworkUnavailable(String noInternetMessage) {

    }

    @Override
    public void onStartLoadingDialog() {
        if (progressDialog != null) progressDialog.show();
    }

    @Override
    public void onLoadingDialogComplete() {
        if (progressDialog != null) progressDialog.dismiss();
    }
}
