package com.blogspot.mowael.molib.utilities;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.fragment.app.Fragment;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;

import com.blogspot.mowael.utilslibrary.Logger;

import java.io.File;
import java.util.List;

/**
 * this class is copied form
 * <a href="https://github.com/jenly1314/Base/blob/master/src/main/java/com/king/base/util/SystemUtils.java">Source</>
 */

public class SystemUtil {

    private SystemUtil() {
        throw new AssertionError();
    }

    /**
     *
     * @param runnable
     */
    public static void asyncThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    /**
     *
     * @param keyCode
     */
    public static void sendKeyCode(final int keyCode) {
        asyncThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(keyCode);
                } catch (Exception e) {
                    Logger.e("Exception when sendPointerSync");
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     *
     * @param et
     */
    public static void deleteClick(EditText et) {
        final KeyEvent keyEventDown = new KeyEvent(KeyEvent.ACTION_DOWN,
                KeyEvent.KEYCODE_DEL);
        et.onKeyDown(KeyEvent.KEYCODE_DEL, keyEventDown);
    }


    /**
     *
     * @param context
     * @param phoneNumber
     */
    public static void call(Context context, String phoneNumber) {

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(String.format("tel:%s", phoneNumber)));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }


    /**
     *
     * @param context
     * @param phoneNumber
     */
    public static void sendSMS(Context context, String phoneNumber) {

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(String.format("smsto:%s", phoneNumber)));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    /**
     *
     * @param phoneNumber
     * @param msg
     */
    public static void sendSMS(String phoneNumber, String msg) {

        SmsManager sm = SmsManager.getDefault();

        List<String> msgs = sm.divideMessage(msg);

        for (String text : msgs) {
            sm.sendTextMessage(phoneNumber, null, text, null, null);
        }

    }

    /**
     *
     * @param activity
     * @param requestCode
     */
    public static void imageCapture(Activity activity, int requestCode) {
        imageCapture(activity, null, requestCode);
    }

    /**
     *
     * @param activity
     * @param path
     * @param requestCode
     */
    public static void imageCapture(Activity activity, String path,
                                    int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (!TextUtils.isEmpty(path)) {
            Uri uri = Uri.fromFile(new File(path));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     *
     * @param fragment
     * @param path
     * @param requestCode
     */
    public static void imageCapture(Fragment fragment, String path,
                                    int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (!TextUtils.isEmpty(path)) {
            Uri uri = Uri.fromFile(new File(path));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
//            Logger.e(e);
        } catch (Exception e) {
            e.printStackTrace();
//            Logger.e(e);
        }
        return packageInfo;
    }

    /**
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        return packageInfo != null ? packageInfo.versionName : null;
    }

    /**
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        return packageInfo != null ? packageInfo.versionCode : 0;
    }

    /**
     *
     * @param context
     */
    public static void startAppDetailSetings(Context context) {
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        context.startActivity(intent);
    }

    /**
     *
     * @param context
     * @param path
     */
    public static void installApk(Context context, String path) {
        installApk(context, new File(path));
    }

    /**
     *
     * @param context
     * @param file
     */
    public static void installApk(Context context, File file) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uriData = Uri.fromFile(file);
        String type = "application/vnd.android.package-archive";

        intent.setDataAndType(uriData, type);
        context.startActivity(intent);
    }


    /**
     *
     * @param context
     * @param packageName
     */
    public static void uninstallApk(Context context, String packageName) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uriData = Uri.parse("package:" + packageName);
        intent.setData(uriData);
        context.startActivity(intent);
    }

    /**
     *
     * @param context
     */
    public static void uninstallApk(Context context) {
        uninstallApk(context, context.getPackageName());
    }

}
