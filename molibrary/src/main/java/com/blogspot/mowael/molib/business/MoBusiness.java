package com.blogspot.mowael.molib.business;

import com.blogspot.mowael.molib.network.Service;
import com.blogspot.mowael.molib.network.listeners.OnServiceLoading;
import com.blogspot.mowael.molib.network.listeners.ServiceResponseListener;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;
import com.blogspot.mowael.molib.presenter.MoMVP;

import org.json.JSONObject;

/**
 * Created by moham on 4/30/2017.
 */

public class MoBusiness<T extends GeneralResponse> implements MoMVP.MoBusiness, MoMVP.MoBusinessWithService<T>, OnServiceLoading {

    private ServiceResponseListener serviceResponse;
    private OnServiceLoading onServiceLoading;

    public MoBusiness() {
    }

    @Override
    public void executeGET(String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponseGET(url, body, (ServiceResponseListener<GeneralResponse>) this);
        setOnServiceLoading(this);
    }

    @Override
    public void executeGETForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponseGETForType(typeResponse, url, body, this);
        setOnServiceLoading(this);
    }

    @Override
    public void executePOST(String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponsePOST(url, body, (ServiceResponseListener<GeneralResponse>) this);
        setOnServiceLoading(this);
    }

    @Override
    public void executePOSTForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponsePOSTForType(typeResponse, url, body, this);
        setOnServiceLoading(this);
    }

    @Override
    public void onResponseSuccess(T response) {
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

    protected void setOnServiceLoading(OnServiceLoading onServiceLoading) {
        Service.getInstance().setOnServiceLoading(onServiceLoading);
    }

    @Override
    public void setOnServiceLoadingListener(OnServiceLoading onServiceLoading) {
        this.onServiceLoading = onServiceLoading;
    }

    @Override
    public void onStartLoadingProgress() {
        if (onServiceLoading != null) onServiceLoading.onStartLoadingProgress();
    }

    @Override
    public void onLoadingProgressComplete() {
        if (onServiceLoading != null) onServiceLoading.onLoadingProgressComplete();
    }
}
