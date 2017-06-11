package com.blogspot.mowael.molib.network;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.blogspot.mowael.molib.network.listeners.OnServiceLoading;
import com.blogspot.mowael.molib.network.listeners.ServiceResponseListener;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//import com.koushikdutta.ion.builder.Builders;
//import com.koushikdutta.ion.builder.LoadBuilder;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by moham on 5/17/2017.
 */

public class IonService implements MoService {
    private static IonService instance;
    private OnServiceLoading onServiceLoading;
    private Context mContext;

    public static IonService getInstance() {
        if (instance == null) {
            instance = new IonService();
        }
        return instance;
    }

    private IonService() {
//        Ion.with(getContext())
//                .load("https://koush.clockworkmod.com/test/echo")
//                .uploadProgressBar(uploadProgressBar)
//                .setMultipartParameter("goop", "noop")
//                .setMultipartFile("archive", "application/zip", new File("/sdcard/filename.zip"))
//                .asJsonObject()
//                .setCallback(...)
    }

//    public LoadBuilder<Builders.Any.B> getIon() {
//        return Ion.with(mContext);
//    }
//
//    public LoadBuilder<Builders.Any.B> getIon(Context context) {
//        initService(context);
//        return Ion.with(context);
//    }
//
//    public LoadBuilder<Builders.Any.B> getIon(Fragment fragment) {
//        initService(fragment.getContext());
//        return Ion.with(fragment);
//    }

    @Override
    public void initService(Context mContext) {
        this.mContext = mContext.getApplicationContext();

    }

    @Override
    public void getResponsePOST(String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception, JsonSyntaxException {
        getResponseForType(GeneralResponse.class, Request.Method.POST, url, body, serviceResponse);
    }

    @Override
    public void getResponseGET(String url, ServiceResponseListener serviceResponse) throws Exception {
        getResponseForType(GeneralResponse.class, Request.Method.GET, url, new JSONObject(), serviceResponse);
    }

    @Override
    public void getResponseGET(String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception, JsonSyntaxException {
        getResponseForType(GeneralResponse.class, Request.Method.GET, url, body, serviceResponse);
    }

    @Override
    public <T extends GeneralResponse> void getResponsePOSTForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception, JsonSyntaxException {
        getResponseForType(typeResponse, Request.Method.POST, url, body, serviceResponse);
    }

    @Override
    public <T extends GeneralResponse> void getResponseGETForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception, JsonSyntaxException {
        getResponseForType(typeResponse, Request.Method.GET, url, body, serviceResponse);
    }

    @Override
    public <T extends GeneralResponse> void getResponseGETForType(Class<T> typeResponse, String url, ServiceResponseListener serviceResponse) throws Exception {
        getResponseForType(typeResponse, Request.Method.GET, url, new JSONObject(), serviceResponse);
    }

    @Override
    public <T extends GeneralResponse> void getResponseForType(Class<T> typeResponse, int method, String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception, JsonSyntaxException {
        throw new UnsupportedOperationException("this method is not implemented yed");
    }

    @Override
    public void setOnServiceLoading(OnServiceLoading onServiceLoading) {
        this.onServiceLoading = onServiceLoading;
    }

//    public void PostMultipart(String url, String name, String value, String fileName, String filePath, FutureCallback<JsonObject> callback) {
//        getIon().load(url)
//                .setMultipartParameter(name, value)
//                .setMultipartFile(fileName, new File(filePath))
//                .asJsonObject()
//                .setCallback(callback);
//    }
}
