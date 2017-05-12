package com.blogspot.mowael.molib.business;

import android.content.Context;

import com.blogspot.mowael.molib.network.OnServiceLoading;
import com.blogspot.mowael.molib.network.Service;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;
import com.blogspot.mowael.molib.presenter.MoMVP;

import org.json.JSONObject;

/**
 * Created by moham on 4/30/2017.
 */

public class MoBusiness implements MoMVP.MoBusiness, MoMVP.MoBusinessWithService, OnServiceLoading {

    private Service.ServiceResponseListener serviceResponse;

    public MoBusiness() {

    }

    @Override
    public void executeGET(String url, JSONObject body, Service.ServiceResponseListener serviceResponse) throws Exception {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponseGET(url, body, this);
        setOnServiceLoading(this);
    }

    @Override
    public <T extends GeneralResponse> void executeGETForType(Class<T> typeResponse, String url, JSONObject body, Service.ServiceResponseListener serviceResponse) throws Exception {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponseGETForType(typeResponse, url, body, this);
        setOnServiceLoading(this);
    }

    @Override
    public void executePOST(String url, JSONObject body, Service.ServiceResponseListener serviceResponse) throws Exception {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponsePOST(url, body, this);
        setOnServiceLoading(this);
    }

    @Override
    public <T extends GeneralResponse> void executePOSTForType(Class<T> typeResponse, String url, JSONObject body, Service.ServiceResponseListener serviceResponse) throws Exception {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponsePOSTForType(typeResponse, url, body, this);
        setOnServiceLoading(this);
    }

    @Override
    public <T extends GeneralResponse> void onResponseSuccess(T response) {
        if (serviceResponse != null) serviceResponse.onResponseSuccess(response);
    }

    @Override
    public void onResponse(JSONObject response, int responseCode) {
        if (serviceResponse != null) serviceResponse.onResponse(response, responseCode);
    }

    @Override
    public void onNetworkUnavailable(String noInternetMessage) {
        if (serviceResponse != null) serviceResponse.onNetworkUnavailable(noInternetMessage);
    }

    public void setOnServiceLoading(OnServiceLoading onServiceLoading) {
        Service.getInstance().setOnServiceLoading(onServiceLoading);
    }

    @Override
    public void onStartLoadingDialog(Context appContext) {

    }

    @Override
    public void onLoadingDialogComplete() {

    }
}
