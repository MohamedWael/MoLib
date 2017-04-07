package com.blogspot.mowael.molib.network;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by moham on 12/11/2016.
 */
public class VolleySingleton {


    private static VolleySingleton mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;
    private RequestQueue mDownloadRequestQueue;

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
