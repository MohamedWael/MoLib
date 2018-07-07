package com.blogspot.mowael.molib.utilities.imageUtil.imageDownloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.mowael.molib.R;


/**
 * Created by mohamed on 1/28/18.
 */

public class MoImageDownloader implements ImageDownloader<String> {

    private final GlideRequests glide;

    public MoImageDownloader(Context context) {
        glide = GlideApp.with(context);
    }

    public MoImageDownloader(View context) {
        glide = GlideApp.with(context);
    }

    public MoImageDownloader(Fragment context) {
        glide = GlideApp.with(context);
    }

    public MoImageDownloader(Activity context) {
        glide = GlideApp.with(context);
    }

    @Override
    public GlideRequest<Drawable> load(String uri) {
        return glide.load(uri);
    }

    @Override
    public void loadAvatar(String uri, @DrawableRes int avatarPlaceHolder, ImageView target) {
        loadImage(uri, avatarPlaceHolder, target);
    }

    @Override
    public <T extends ImageView> void loadImage(String uri, @DrawableRes int placeHolder, T target) {
        if (placeHolder != 0)
            load(uri).placeholder(placeHolder).into(target);
        else
            load(uri).into(target);
    }

    @Override
    public <T extends ImageView> void loadImage(String uri, T target) {
        loadImage(uri, 0, target);
    }

}
