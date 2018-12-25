package com.blogspot.mowael.molib.utilities.imageUtil.imageDownloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by mohamed on 1/28/18.
 */

public interface ImageDownloader<UriType> {

    GlideRequest<Drawable> load(UriType uri);

    void loadAvatar(UriType uri, int avatarPlaceHolder, ImageView target);

    <T extends ImageView> void loadImage(UriType uri, @DrawableRes int placeHolder, T target);

    <T extends ImageView> void loadImage(UriType uri, T target);

    /**
     * create an instance of the default image downloader of the application
     */
    class Factory {
        public static MoImageDownloader create(Context context) {
            return new MoImageDownloader(context);
        }

        public static MoImageDownloader create(Activity context) {
            return new MoImageDownloader(context);
        }

        public static MoImageDownloader create(Fragment context) {
            return new MoImageDownloader(context);
        }

        public static MoImageDownloader create(View context) {
            return new MoImageDownloader(context);
        }
    }
}
