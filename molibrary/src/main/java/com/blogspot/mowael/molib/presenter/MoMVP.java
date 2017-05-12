package com.blogspot.mowael.molib.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.blogspot.mowael.molib.fragments.MoFragment;
import com.blogspot.mowael.molib.network.Service;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;

import org.json.JSONObject;

/**
 * Created by moham on 4/26/2017.
 */

public interface MoMVP {

    interface MoView {

        Context getContext();

        Activity getActivity();

        MoFragment getFragment();

//        void onNetworkUnavailable(String noInternetMessage);
    }

    interface MoPresenter {
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        void onPermissionGranted(int requestCode);

        void onPermissionDenied(int requestCode);

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    interface MoBusiness {
        void executeGET(String url, JSONObject body, Service.ServiceResponseListener serviceResponse) throws Exception;

        <T extends GeneralResponse> void executeGETForType(Class<T> typeResponse, String url, JSONObject body, Service.ServiceResponseListener serviceResponse) throws Exception;

        void executePOST(String url, JSONObject body, Service.ServiceResponseListener serviceResponse) throws Exception;

        <T extends GeneralResponse> void executePOSTForType(Class<T> typeResponse, String url, JSONObject body, Service.ServiceResponseListener serviceResponse) throws Exception;
    }

    interface MoBusinessWithService extends Service.ServiceResponseListener {

    }

}
