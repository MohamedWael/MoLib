package com.blogspot.mowael.molib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

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
        setContentView(R.layout.layout_mo_dialog);
    }

    @Override
    public int getContentView() {
        return R.layout.layout_mo_dialog;
    }
}
