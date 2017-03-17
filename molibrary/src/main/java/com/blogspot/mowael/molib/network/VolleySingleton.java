package com.blogspot.mowael.molib.network;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by moham on 12/11/2016.
 */
public class VolleySingleton {

    private int GALLERY_INTENT_CALLED = 9;
    private int GALLERY_KITKAT_INTENT_CALLED = 10;
    private static VolleySingleton mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;
    private RequestQueue mDownloadRequestQueue;
    private DownloadManager downloadManager;

    private VolleySingleton(Context context) {
        this.mContext = context;
        this.mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
            return mInstance;
        } else {
            return mInstance;
        }
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public RequestQueue getDownloadRequestQueue() {
        if (mDownloadRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mDownloadRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext(), new HurlStack());
        }
        return mDownloadRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public Bitmap encodeStringImage(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedBitmap;
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }


    public String getStringImage(Bitmap bmp) {
        byte[] imageBytes = convertBitmaptoBytes(bmp);
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public byte[] convertBitmaptoBytes(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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


    public Bitmap handleResultFromChooser(Intent onActivityResultIntent) {

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


    public String handleResultFromChooserToGetPath(Intent onActivityResultIntent) {

        return onActivityResultIntent.getData().getPath();

    }

    /**
     * used for only
     *
     * @param query
     * @return
     */
    public String encodeURL(String query) {
        String encodedURL = "";
        try {
            encodedURL = URLEncoder.encode(query.trim(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedURL;
    }

    public String uriEncoder(String query) {
        final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
        String encodedURL = "";
        encodedURL = Uri.encode(query, ALLOWED_URI_CHARS);
        return encodedURL;
    }


    public void openEmailClient(Context mContext, /*String mailto,*/ String mail, String subject, String msg) {
        Intent mailIntent = new Intent(Intent.ACTION_SEND/*, Uri.parse("mailto:" + mailto)*/);
        mailIntent.setType("text/html");
        mailIntent.putExtra(Intent.EXTRA_EMAIL, mail);
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT, msg);
        try {
            mContext.startActivity(Intent.createChooser(mailIntent, "Send Email"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
