package com.blogspot.mowael.molib.network.listeners;

import com.android.volley.VolleyError;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;

import org.json.JSONObject;

/**
 * Created by moham on 5/14/2017.
 */

public interface ServiceResponseListener {

    void onResponse(JSONObject response);

    <T extends GeneralResponse> void onResponseParsingSuccess(T response);

    void onError(VolleyError error);
}
