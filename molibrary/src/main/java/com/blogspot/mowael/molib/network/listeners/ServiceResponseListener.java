package com.blogspot.mowael.molib.network.listeners;

import com.blogspot.mowael.molib.network.pojo.GeneralResponse;

import org.json.JSONObject;

/**
 * Created by moham on 5/14/2017.
 */

public interface ServiceResponseListener<T extends GeneralResponse> {

    void onResponseSuccess(T response);

    void onResponse(JSONObject response, int responseCode);

    void onNetworkUnavailable(String noInternetMessage);
}
