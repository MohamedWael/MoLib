package com.blogspot.mowael.molib.network.listeners;

import android.content.Context;

/**
 * Created by moham on 5/12/2017.
 */

public interface OnServiceLoading {
    /**
     * called when the request is going to be sent
     */
    void onStartLoadingDialog(Context appContext);

    /**
     * called when the request come with response or when there are server error
     */
    void onLoadingDialogComplete();
}
