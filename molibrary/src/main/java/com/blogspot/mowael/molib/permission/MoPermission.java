package com.blogspot.mowael.molib.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by moham on 4/30/2017.
 */

public class MoPermission {

    public MoPermission() {

    }

    /**
     *
     * @param mContext
     * @param permission
     * @return true if the permission is not granted otherwise false
     */
    public static boolean check(Context mContext, String permission) {
        if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED)
            return true;
        else return false;
    }

    /**
     *
     * @param activity
     * @param permission
     * @param requestCode
     * @return true if the permission is not granted otherwise false
     */
    public static boolean checkAndRequestPermission(Activity activity, String permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{permission}, requestCode);
                return true;
            } else return false;
        }
        return false;
    }

    /**
     * @param activity
     * @param permissions
     * @param requestCode
     * @return arrayList of granted permissions
     */
    public static ArrayList<String> checkAndRequestGroupPermission(Activity activity, String[] permissions, int requestCode) {
        ArrayList<String> grantedPermissions = new ArrayList<>();
        ArrayList<String> permissionList = (ArrayList<String>) Arrays.asList(permissions);
        for (String permission : permissionList) {
            if (!checkAndRequestPermission(activity, permission, requestCode)) {
                grantedPermissions.add(permission);
            }
        }
        return grantedPermissions;
    }
}
