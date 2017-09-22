package com.blogspot.mowael.molib.business;

import com.android.volley.VolleyError;
import com.blogspot.mowael.molib.network.Service;
import com.blogspot.mowael.molib.network.listeners.ServiceResponseListener;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;
import com.blogspot.mowael.molib.presenter.MoContract;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

/**
 * Created by moham on 4/30/2017.
 */

public class MoBusiness<T extends GeneralResponse> implements MoContract.MoBusiness, MoContract.MoBusinessWithService<T>{

    private ServiceResponseListener serviceResponse;

    public MoBusiness() {
    }

    @Override
    public void executeGET(String url, JSONObject body, ServiceResponseListener serviceResponse) throws JsonSyntaxException {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponseGET(url, body, (ServiceResponseListener) this);
    }

    @Override
    public void executeGETForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws JsonSyntaxException {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponseGETForType(typeResponse, url, body, this);
    }

    @Override
    public void executePOST(String url, JSONObject body, ServiceResponseListener serviceResponse) throws JsonSyntaxException {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponsePOST(url, body, (ServiceResponseListener) this);
    }

    @Override
    public void executePOSTForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws JsonSyntaxException {
        this.serviceResponse = serviceResponse;
        Service.getInstance().getResponsePOSTForType(typeResponse, url, body, this);
    }

    @Override
    public <T extends GeneralResponse> void onResponseParsingSuccess(T response) {
        if (serviceResponse != null) serviceResponse.onResponseParsingSuccess(response);
    }

    @Override
    public void onResponse(JSONObject response) {
        if (serviceResponse != null) serviceResponse.onResponse(response);
    }

    @Override
    public void onError(VolleyError error) {
        if (serviceResponse != null) serviceResponse.onError(error);
    }

}
