package com.blogspot.mowael.molib.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.blogspot.mowael.molib.dialog.listener.DialogListener;

import java.lang.ref.WeakReference;

public abstract class DialogProvider {

    private WeakReference<DialogListener> weakDialogListener;

    /**
     * Create a dialog object on given context
     *
     * @param context in which the dialog should run
     * @return dialog object to display
     */
    public abstract Dialog getDialog(@NonNull Context context);

    /**
     * Sets a {@linkplain DialogListener} to provide pre-defined actions to the component which uses this dialog
     *
     * @param dialogListener will be used to notify on specific actions
     */
    public void setDialogListener(@Nullable DialogListener dialogListener) {
        this.weakDialogListener = new WeakReference<>(dialogListener);
    }

    @Nullable
    public DialogListener getDialogListener() {
        return weakDialogListener.get();
    }
}
