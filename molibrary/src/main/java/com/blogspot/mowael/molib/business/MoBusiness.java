package com.blogspot.mowael.molib.business;

import com.blogspot.mowael.molib.network.Service;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;
import com.blogspot.mowael.molib.presenter.MoMVP;

import org.json.JSONObject;

/**
 * Created by moham on 4/30/2017.
 */

public class MoBusiness implements MoMVP.MoBusiness {

    public MoBusiness() {

    }

    public void executeGET(String url, JSONObject body, Service.ServiceResponse serviceResponse) throws Exception {
        Service.getInstance().getResponseGET(url, body, serviceResponse);
    }

    public <T extends GeneralResponse> void executeGETForType(Class<T> typeResponse, String url, JSONObject body, Service.ServiceResponseForType serviceResponse) throws Exception {
        Service.getInstance().getResponseGETForType(typeResponse, url, body, serviceResponse);
    }

    public void executePOST(String url, JSONObject body, Service.ServiceResponse serviceResponse) throws Exception {
        Service.getInstance().getResponsePOST(url, body, serviceResponse);
    }

    public <T extends GeneralResponse> void executePOSTForType(Class<T> typeResponse, String url, JSONObject body, Service.ServiceResponseForType serviceResponse) throws Exception {
        Service.getInstance().getResponsePOSTForType(typeResponse, url, body, serviceResponse);
    }

}
