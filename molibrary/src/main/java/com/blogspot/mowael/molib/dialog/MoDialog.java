package com.blogspot.mowael.molib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.mowael.molib.R;
import com.blogspot.mowael.molib.dialog.listener.DialogSetting;

/**
 * Created by moham on 4/9/2017.
 */

public class MoDialog extends Dialog implements DialogSetting {


    public MoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.layout_mo_dialog);
    }

    @Override
    public void setContentView(@LayoutRes int layoutRes) {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup container = (ViewGroup) findViewById(R.id.flDialogLayout);
        if (layoutRes != 0) {
            findViewById(R.id.tvMoActivityDescription).setVisibility(View.GONE);
            container.addView(inflater.inflate(layoutRes, null));
        }
    }



    @Override
    public int getContentView() {
        return R.layout.layout_mo_dialog;
    }
}
