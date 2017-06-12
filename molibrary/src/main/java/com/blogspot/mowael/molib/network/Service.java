package com.blogspot.mowael.molib.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blogspot.mowael.molib.R;
import com.blogspot.mowael.molib.network.listeners.OnServiceLoading;
import com.blogspot.mowael.molib.network.listeners.ServiceResponseListener;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;
import com.blogspot.mowael.molib.utilities.Logger;
import com.blogspot.mowael.molib.utilities.ViewUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mohamed Wael on 2/2/2017.
 */
public class Service implements NetworkStateReceiver.NetworkStateReceiverListener {
    private static Service ourInstance;
    public static final String _METHOD = "_method";
    public static final String _METHOD_POST = "POST";
    public static final String _METHOD_PATCH = "PATCH";
    public static final String _METHOD_DESTROY = "DESTROY";
    public static final int SERVER_ERROR = -1;
    private final int TRYING_LIMIT = 2;
    private VolleyClient volley;
    private Context mContext;
    private ArrayList<ServiceRequest> requests;
    private ServiceRequest request;
    private int numberOfRetryingToGetResponse = 0;
    private int INITIAL_TIME_OUT_MS = 5000;
    private OnServiceLoading onServiceLoading;


    public static Service getInstance() {
        if (ourInstance == null) {
            Logger.d("Service", "new Service");
            ourInstance = new Service();
            return ourInstance;
        } else {
            Logger.d("Service", "old Service");
            return ourInstance;
        }
    }

    private Service() {
    }

    public void initService(Context mContext) {
        setContext(mContext);
        initService();
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void initService() {
        volley = VolleyClient.getInstance(mContext);
        volley.getRequestQueue().start();
        NetworkStateReceiver receiver = NetworkStateReceiver.getInstance();
        mContext.registerReceiver(receiver, receiver.getIntentFilter());
//        receiver.addListener(this);
        requests = new ArrayList<>();
    }


    public void getResponsePOST(String url, JSONObject body, ServiceResponseListener<GeneralResponse> serviceResponse) throws Exception, JsonSyntaxException {
        getResponseForType(GeneralResponse.class, Request.Method.POST, url, body, serviceResponse);
    }

    public void getResponseGET(String url, ServiceResponseListener<GeneralResponse> serviceResponse) throws Exception {
        getResponseForType(GeneralResponse.class, Request.Method.GET, url, new JSONObject(), serviceResponse);
    }

    public void getResponseGET(String url, JSONObject body, ServiceResponseListener<GeneralResponse> serviceResponse) throws Exception, JsonSyntaxException {
        getResponseForType(GeneralResponse.class, Request.Method.GET, url, body, serviceResponse);
    }

    public <T extends GeneralResponse> void getResponsePOSTForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener<T> serviceResponse) throws Exception, JsonSyntaxException {
        getResponseForType(typeResponse, Request.Method.POST, url, body, serviceResponse);
    }

    public <T extends GeneralResponse> void getResponseGETForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener<T> serviceResponse) throws Exception, JsonSyntaxException {
        getResponseForType(typeResponse, Request.Method.GET, url, body, serviceResponse);
    }

    public <T extends GeneralResponse> void getResponseGETForType(Class<T> typeResponse, String url, ServiceResponseListener<T> serviceResponse) throws Exception {
        getResponseForType(typeResponse, Request.Method.GET, url, new JSONObject(), serviceResponse);
    }

    public <T extends GeneralResponse> void getResponseForType(final Class<T> typeResponse, final int method, final String url, final JSONObject body, final ServiceResponseListener<T> serviceResponse) throws Exception, JsonSyntaxException {
        if (onServiceLoading != null) onServiceLoading.onStartLoadingDialog();
        Logger.d("request", volley.uriEncoder(url));
        request = new ServiceRequest(method, volley.uriEncoder(url), body != null ? body : new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (onServiceLoading != null) onServiceLoading.onLoadingDialogComplete();
                Logger.d("Response", response.toString());
                Gson gson = new Gson();
                T t = gson.fromJson(response.toString(), typeResponse);
                serviceResponse.onResponse(response, t.getCode());
                if (t.isSuccess()) {
                    serviceResponse.onResponseSuccess(t);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                serviceResponse.onResponse(new JSONObject(), SERVER_ERROR);
                if (onServiceLoading != null) onServiceLoading.onLoadingDialogComplete();
                String volleyErrorStr = error.toString();
                if (volleyErrorStr.contains("com.android.volley.TimeoutError") && numberOfRetryingToGetResponse <= TRYING_LIMIT) {
                    Logger.e("if", volleyErrorStr);

                    volley.addToRequestQueue(request);

                    numberOfRetryingToGetResponse++;
                } else if (volleyErrorStr.contains("com.android.volley.NoConnectionError")) {
                    Logger.e("elseIf", volleyErrorStr);
                    ViewUtils.toastMsg(mContext, mContext.getString(R.string.no_connection));
                    serviceResponse.onNetworkUnavailable(mContext.getString(R.string.no_connection));
                } else {
                    Logger.e("volleyErrorStr", volleyErrorStr);
                    ViewUtils.toastMsg(mContext, mContext.getString(R.string.connection_error));
                }
                Logger.d("VolleyError", error.toString());
            }
        }) {
            @Override
            public byte[] getBody() {
                Logger.d("body", body.toString());
                return super.getBody();
            }
        };
//        request.setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIME_OUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volley.addToRequestQueue(request);
//        System.setProperty("http.keepAlive", "false");
    }

    /**
     * this method is deprecated use getResponseForType() instead
     *
     * @param method
     * @param url
     * @param body
     * @param serviceResponse
     * @throws Exception
     * @throws JsonSyntaxException
     */
    @Deprecated
    public void getResponse(final int method, final String url, final JSONObject body, final ServiceResponseListener serviceResponse) throws Exception, JsonSyntaxException {
        Logger.d("request", volley.uriEncoder(url));
        request = new ServiceRequest(method, volley.uriEncoder(url), body != null ? body : new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.d("Response", response.toString());
                serviceResponse.onResponse(response, 200);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                serviceResponse.onError();
                String volleyErrorStr = error.toString();
                if (volleyErrorStr.contains("com.android.volley.TimeoutError") && numberOfRetryingToGetResponse <= TRYING_LIMIT) {
                    Logger.e("if", volleyErrorStr);

                    volley.addToRequestQueue(request);

                    numberOfRetryingToGetResponse++;
                } else if (volleyErrorStr.contains("com.android.volley.NoConnectionError")) {
                    Logger.e("elseIf", volleyErrorStr);
                    ViewUtils.toastMsg(mContext, mContext.getString(R.string.no_connection));
                    serviceResponse.onNetworkUnavailable(mContext.getString(R.string.no_connection));
//                    if (!cached) cacheRequest(request);
                } else {
                    Logger.e("volleyErrorStr", volleyErrorStr);
                    ViewUtils.toastMsg(mContext, mContext.getString(R.string.connection_error));
                }
                Logger.d("VolleyError", error.toString());
            }
        }) {
            @Override
            public byte[] getBody() {
                Logger.d("body", body.toString());
                return super.getBody();
            }
        };
        volley.addToRequestQueue(request);
    }

    public ServiceRequest getRequest() {
        return request;
    }


    public VolleyClient getVolleyClient() {
        return volley;
    }

    public void setOnServiceLoading(OnServiceLoading onServiceLoading) {
        this.onServiceLoading = onServiceLoading;
    }

    /**
     * cacheRequest() option is still under development
     *
     * @param request
     * @param cached
     */
    private void cacheRequest(ServiceRequest request, boolean cached) {
        if (!cached) cacheRequest(request);
    }

    /**
     * cache option is still under development
     *
     * @param objectRequest
     */
    private void cacheRequest(ServiceRequest objectRequest) {
        if (objectRequest != null && !objectRequest.isCached()) {
            if (!requests.contains(objectRequest)) {
                requests.add(objectRequest);
                objectRequest.setCached(true);
            }
        } else {
            Logger.e("CachedRequestError", "request Object is null");
        }
        Logger.d("NumberOfCachedRequests", requests.size() + "");
    }

    @Override
    public void networkAvailable() {
        if (request != null) {
            volley.addToRequestQueue(request);
        }
//        if (!requests.isEmpty()) {
//            for (ServiceRequest objectRequest : requests) {
//                volley.addToRequestQueue(request);
//            }
//        }

    }

    @Override
    public void networkUnavailable() {
        ViewUtils.toastMsg(mContext, mContext.getString(R.string.no_connection));
    }

}


