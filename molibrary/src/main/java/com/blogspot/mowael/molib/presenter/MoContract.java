package com.blogspot.mowael.molib.presenter;

import android.support.annotation.NonNull;

import com.blogspot.mowael.molib.network.ErrorMessageHandler;
import com.blogspot.mowael.molib.network.listeners.OnServiceLoading;
import com.blogspot.mowael.molib.network.listeners.ServiceResponseListener;
import com.blogspot.mowael.molib.network.pojo.GeneralResponse;

import org.json.JSONObject;

/**
 * MoContract is just a container for the MVP listeners
 * Created by mohamed wael on 4/26/2017.
 */

public interface MoContract {

    interface MoView extends OnServiceLoading {

        void showProgress(boolean show);

        void showProgressDialog();

        void hideProgressDialog();

        void showSnakeMessage(String msg);

        void showSnakeMessage(int msgRes);

        void showSnakeMessage(ErrorMessageHandler errorMessageHandler);


    }

    interface MoPresenter {

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        void onDestroy();
    }

    interface MoBusiness {

    }

    interface MoBusinessWithService<T extends GeneralResponse> extends ServiceResponseListener {
        void executeGET(String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception;

        void executeGETForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception;

        void executePOST(String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception;

        void executePOSTForType(Class<T> typeResponse, String url, JSONObject body, ServiceResponseListener serviceResponse) throws Exception;

    }

}
