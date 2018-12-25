package com.blogspot.mowael.molib.presenter;


import androidx.annotation.NonNull;

import com.android.volley.VolleyError;
import com.blogspot.mowael.molib.network.listeners.ServiceResponseListener;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;

import org.json.JSONObject;

/**
 * Created by moham on 4/26/2017.
 */

public abstract class MoPresenter<T extends MoContract.MoView> implements MoContract.MoPresenter, ServiceResponseListener {


    public MoPresenter(T view) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    public abstract MoContract.MoBusiness getService();

    public abstract T getView();

    @Override
    public <T extends GeneralResponse> void onResponseParsingSuccess(T response) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public void onDestroy() {
        T view = getView();
        view = null;
        MoContract.MoBusiness service = getService();
        service = null;
    }
}
