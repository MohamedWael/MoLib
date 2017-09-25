package com.blogspot.mowael.molib.dialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

import com.blogspot.mowael.molib.R;

/**
 * Created by moham on 5/27/2017.
 */

public class MoProgressDialog {
    private static MoProgressDialog instance;
    private AlertDialog dialog;

    private MoProgressDialog(Activity activity) {
        dialog = new AlertDialog.Builder(activity).setView(R.layout.loading_layout).setCancelable(false).create();
    }

    public static MoProgressDialog newInstance(Activity activity) {
        return new MoProgressDialog(activity);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public AlertDialog getDialog() {
        return dialog;
    }
}
