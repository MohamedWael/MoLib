package com.blogspot.mowael.molib.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.blogspot.mowael.molib.network.listeners.OnServiceLoading;
import com.blogspot.mowael.molib.network.listeners.ServiceResponseListener;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;

import org.json.JSONObject;

/**
 * MoMVP is just a container for the MVP listeners
 * Created by mohamed wael on 4/26/2017.
 */

public interface MoMVP {

    interface MoView extends OnServiceLoading{

    }

    interface MoPresenter {
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        void onActivityResult(int requestCode, int resultCode, Intent data);

        MoMVP.MoBusiness getService();
    }

    interface MoBusiness {

        void setOnServiceLoadingListener(OnServiceLoading onServiceLoading);
    }

    interface MoBusinessWithService<T extends GeneralResponse> extends ServiceResponseListener<T> {
        void executeGET(String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception;

        void executeGETForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception;

        void executePOST(String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception;

        void executePOSTForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception;

    }

}
