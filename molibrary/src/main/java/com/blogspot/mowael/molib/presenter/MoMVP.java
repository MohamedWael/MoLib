package com.blogspot.mowael.molib.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.blogspot.mowael.molib.fragments.MoFragment;

/**
 * Created by moham on 4/26/2017.
 */

public interface MoMVP {

    interface MoView {

        Context getContext();

        Activity getActivity();

        MoFragment getFragment();

//        void updateUIOnNetworkUnavailable(String noInternetMessage);
    }

    interface MoPresenter {
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        void onPermissionGranted(int requestCode);

        void onPermissionDenied(int requestCode);

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    interface MoBusiness {

    }
}
