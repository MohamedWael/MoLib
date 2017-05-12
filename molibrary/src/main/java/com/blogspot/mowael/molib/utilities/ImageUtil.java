package com.blogspot.mowael.molib.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by mohamed wael on 4/7/2017.
 */

public class ImageUtil {
    private static ImageUtil instance;
    public static int GALLERY_INTENT_REQUEST = 100;
    private int GALLERY_INTENT_CALLED = 9;
    private int GALLERY_KITKAT_INTENT_CALLED = 10;

    public static ImageUtil getInstance() {
        if (instance == null) {
            instance = new ImageUtil();
        }
        return instance;
    }

    public Bitmap encodeStringImage(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }


    public String getStringImageJPG(Bitmap bmp) {
        return Base64.encodeToString(convertBitmapToBytesJPG(bmp), Base64.DEFAULT);
    }

    public String getStringImagePNG(Bitmap bmp) {
        return Base64.encodeToString(convertBitmapToBytesPNG(bmp), Base64.DEFAULT);
    }

    public String getReducedStringImagePNG(Bitmap bmp) {
        return Base64.encodeToString(convertBitmapToBytesPNG(reduceImageSizePNG(bmp)), Base64.DEFAULT);
    }

    public String getReducedStringImageJPG(Bitmap bmp) {
        return Base64.encodeToString(convertBitmapToBytesJPG(reduceImageSizeJPG(bmp)), Base64.DEFAULT);
    }

    public Bitmap reduceImageSizePNG(Bitmap bmp) {
        return reduceImageSize(convertBitmapToBytesPNG(bmp));
    }

    public Bitmap reduceImageSizeJPG(Bitmap bmp) {
        return reduceImageSize(convertBitmapToBytesPNG(bmp));
    }

    public Bitmap reduceImageSize(byte[] data) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public byte[] convertBitmapToBytesJPG(Bitmap bmp) {
        return convertBitmapToBytes(bmp, Bitmap.CompressFormat.JPEG, 0);
    }

    public byte[] convertBitmapToBytesPNG(Bitmap bmp) {
        return convertBitmapToBytes(bmp, Bitmap.CompressFormat.PNG, 0);
    }

    public byte[] convertBitmapToBytes(Bitmap bmp, Bitmap.CompressFormat format, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(format, quality, stream);
        return stream.toByteArray();
    }


    public Intent getPictureFromGalleryIntent() {
        //This takes images directly from gallery
        Intent gallerypickerIntent;
        if (Build.VERSION.SDK_INT < 19) {
            gallerypickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
            //this line is not used you can delete it
            gallerypickerIntent.putExtra("BuildVersion", GALLERY_INTENT_CALLED);
        } else {
            gallerypickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            gallerypickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
            //this line is not used you can delete it
            gallerypickerIntent.putExtra("BuildVersion", GALLERY_KITKAT_INTENT_CALLED);
        }
        gallerypickerIntent.setType("image/*");
        return gallerypickerIntent;
    }

    public Intent getPictureFromGalleryIntent(Fragment fragment) {
        Intent gallerypickerIntent = getPictureFromGalleryIntent();
        fragment.startActivityForResult(gallerypickerIntent, GALLERY_INTENT_REQUEST);
        return gallerypickerIntent;
    }

    public Intent getPictureFromGalleryIntent(Activity activity) {
        Intent gallerypickerIntent = getPictureFromGalleryIntent();
        activity.startActivityForResult(gallerypickerIntent, GALLERY_INTENT_REQUEST);
        return gallerypickerIntent;
    }


    public static String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap getBitmapFromURI(Context context, Uri imageUri) throws IOException {
        return MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
    }

    public static Bitmap decodeResource(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public Bitmap handleResultFromChooser(Intent onActivityResultIntent, Context mContext) {

        Bitmap takenPictureData = null;

        Uri photoUri = onActivityResultIntent.getData();
        if (photoUri != null) {
            try {
                takenPictureData = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), photoUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return takenPictureData;
    }

    public Bitmap getBitmap(ImageView imageView) {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }

}
