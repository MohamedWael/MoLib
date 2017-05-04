package com.blogspot.mowael.molib.presenter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.blogspot.mowael.molib.utilities.Logger;

/**
 * Created by moham on 4/26/2017.
 */

public class MoPresenter implements MoMVP.MoPresenter {

    private MoMVP.MoView view;

    public MoPresenter(MoMVP.MoView view) {
        this.view = view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length == 0) {

            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    onPermissionGranted(requestCode);
                } else {
                    onPermissionDenied(requestCode);
                }
            }

            for (String permission : permissions) {

            }

//            Logger.d("PERMISSION_GRANTED", "true");
        }
    }


    @Override
    public void onPermissionGranted(int requestCode) {
        Logger.d("GrantedRequestCode", requestCode + "");
    }

    @Override
    public void onPermissionDenied(int requestCode) {
        Logger.d("DeniedRequestCode", requestCode + "");
    }


}
