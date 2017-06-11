package com.blogspot.mowael.molib.network;

import android.content.Context;

import com.blogspot.mowael.molib.network.listeners.OnServiceLoading;
import com.blogspot.mowael.molib.network.listeners.ServiceResponseListener;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

/**
 * Created by moham on 5/17/2017.
 */

public interface MoService {

    void initService(Context mContext);

    void getResponsePOST(String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception, JsonSyntaxException;

    void getResponseGET(String url, ServiceResponseListener serviceResponse) throws Exception;

    void getResponseGET(String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception, JsonSyntaxException;

    <T extends GeneralResponse> void getResponsePOSTForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception, JsonSyntaxException;

    <T extends GeneralResponse> void getResponseGETForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception, JsonSyntaxException;


    <T extends GeneralResponse> void getResponseGETForType(Class<T> typeResponse, String url, ServiceResponseListener serviceResponse) throws Exception;

    <T extends GeneralResponse> void getResponseForType(final Class<T> typeResponse, final int method, final String url, final JSONObject body, final ServiceResponseListener serviceResponse) throws Exception, JsonSyntaxException;

    void setOnServiceLoading(OnServiceLoading onServiceLoading);

}
