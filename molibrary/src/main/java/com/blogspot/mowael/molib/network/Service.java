package com.blogspot.mowael.molib.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blogspot.mowael.molib.R;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;
import com.blogspot.mowael.molib.utilities.Logger;
import com.blogspot.mowael.molib.utilities.ViewUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by moham on 2/2/2017.
 */
public class Service implements NetworkStateReceiver.NetworkStateReceiverListener {
    private static Service ourInstance;
    public static final String _METHOD = "_method";
    public static final String _METHOD_POST = "POST";
    public static final String _METHOD_PATCH = "PATCH";
    public static final String _METHOD_DESTROY = "DESTROY";
    private final VolleyClient volley;
    private final Context mContext;
    private ArrayList<ServiceRequest> requests;
    private ServiceRequest request;
    private int numberOfRetryingToGetResponse = 0;
    private final int TRYING_LIMIT = 9;


    public static Service getInstance(Context mContext) {
        if (ourInstance == null) {
            Logger.d("Service", "new Service");
            ourInstance = new Service(mContext);
            return ourInstance;
        } else {
            Logger.d("Service", "old Service");
            return ourInstance;
        }
    }

    private Service(Context mContext) {
        this.mContext = mContext;
        volley = VolleyClient.getInstance(mContext);
        NetworkStateReceiver receiver = NetworkStateReceiver.getInstance();
        mContext.registerReceiver(receiver, receiver.getIntentFilter());
        requests = new ArrayList<>();
    }

    public <T extends GeneralResponse> void getResponsePOSTForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseForType serviceResponse) throws Exception, JsonSyntaxException {
        getResponseForType(typeResponse, Request.Method.POST, url, body, serviceResponse);
    }

    public <T extends GeneralResponse> void getResponseGETForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseForType serviceResponse) throws Exception, JsonSyntaxException {
        getResponseForType(typeResponse, Request.Method.GET, url, body, serviceResponse);
    }

    public <T extends GeneralResponse> void getResponseGETForType(Class<T> typeResponse, String url, ServiceResponseForType serviceResponse) throws Exception {
        getResponseForType(typeResponse, Request.Method.GET, url, new JSONObject(), serviceResponse);
    }

    public <T extends GeneralResponse> void getResponseForType(final Class<T> typeResponse, final int method, final String url, final JSONObject body, final ServiceResponseForType serviceResponse) throws Exception, JsonSyntaxException {
        Logger.d("request", volley.uriEncoder(url));
        request = new ServiceRequest(method, volley.uriEncoder(url), body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.d("Response", response.toString());
                Gson gson = new Gson();
                T t = gson.fromJson(response.toString(), typeResponse);
                serviceResponse.onResponse(response, t.getCode());
                if (t.isSuccess()) {
                    serviceResponse.onResponseCode200(t);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                serviceResponse.onErrorResponse(error);
                String volleyErrorStr = error.toString();
                if (volleyErrorStr.contains("com.android.volley.TimeoutError") && numberOfRetryingToGetResponse <= TRYING_LIMIT) {
                    Logger.e("if", volleyErrorStr);

                    volley.addToRequestQueue(request);

                    numberOfRetryingToGetResponse++;
                } else if (volleyErrorStr.contains("com.android.volley.NoConnectionError")) {
                    Logger.e("elseIf", volleyErrorStr);
                    ViewUtils.toastMsg(mContext, mContext.getString(R.string.no_connection));
                    serviceResponse.updateUIOnNetworkUnavailable(mContext.getString(R.string.no_connection));
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

    public void getResponsePOST(String url, JSONObject body, ServiceResponse serviceResponse) throws Exception, JsonSyntaxException {
        getResponse(Request.Method.POST, url, body, serviceResponse);
    }

    public void getResponseGET(String url, ServiceResponse serviceResponse) throws Exception {
        getResponse(Request.Method.GET, url, new JSONObject(), serviceResponse);
    }

    public void getResponseGET(String url, JSONObject body, ServiceResponse serviceResponse) throws Exception, JsonSyntaxException {
        getResponse(Request.Method.GET, url, body, serviceResponse);
    }

    public void getResponse(final int method, final String url, final JSONObject body, final ServiceResponse serviceResponse) throws Exception, JsonSyntaxException {
        Logger.d("request", volley.uriEncoder(url));
        request = new ServiceRequest(method, volley.uriEncoder(url), body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.d("Response", response.toString());
                serviceResponse.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                serviceResponse.onErrorResponse(error);
                String volleyErrorStr = error.toString();
                if (volleyErrorStr.contains("com.android.volley.TimeoutError") && numberOfRetryingToGetResponse <= TRYING_LIMIT) {
                    Logger.e("if", volleyErrorStr);

                    volley.addToRequestQueue(request);

                    numberOfRetryingToGetResponse++;
                } else if (volleyErrorStr.contains("com.android.volley.NoConnectionError")) {
                    Logger.e("elseIf", volleyErrorStr);
                    ViewUtils.toastMsg(mContext, mContext.getString(R.string.no_connection));
                    serviceResponse.updateUIOnNetworkUnavailable(mContext.getString(R.string.no_connection));
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

    /**
     * cache option is still under development
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

    public interface ServiceResponse {
        void onResponse(JSONObject response);

        void onErrorResponse(VolleyError error);

        void updateUIOnNetworkUnavailable(String noInternetMessage);
    }


    public interface ServiceResponseForType extends ServiceResponse {

        <T extends GeneralResponse> void onResponseCode200(T response);

        void onResponse(JSONObject response, int responseCode);
    }

}


