package com.blogspot.mowael.molib.presenter;

/**
 * Created by moham on 4/26/2017.
 */

public class MoPresenter implements MoMVP.MoPresenter {

    private MoMVP.MoView view;

    public MoPresenter(MoMVP.MoView view) {
        this.view = view;
    }
}
