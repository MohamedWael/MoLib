package com.blogspot.mowael.molib.utilities.imageUtil;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * Created by mwael on 4/9/2017.
 */

public class ImageCropperUtil {

    private CropImageView cropImageView;

    public final static int PICK_IMAGE_CHOOSER_REQUEST_CODE = CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE;
    public final static int CROP_IMAGE_ACTIVITY_REQUEST_CODE = CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;

    public final static int PICK_IMAGE_PERMISSIONS_REQUEST_CODE = CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE;
    public final static int CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE = CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;

    public ImageCropperUtil(CropImageView cropImageView) {
        this.cropImageView = cropImageView;
    }

    public CropImageView getCropImageView() {
        return cropImageView;
    }

    public void setAspectRatio(int aspectRatioX, int aspectRatioY) {
        cropImageView.setAspectRatio(aspectRatioX, aspectRatioY);
    }

    /**
     * @param width  in pixels
     * @param height in pixels
     */
    public void setCropSize(int width, int height) {
        cropImageView.setMaxCropResultSize(width, height);
        cropImageView.setMinCropResultSize(width, height);
    }

    /**
     * @param width  in pixels
     * @param height in pixels
     */
    public void setMaxCropResultSize(int width, int height) {
        cropImageView.setMaxCropResultSize(width, height);
    }

    /**
     * @param width  in pixels
     * @param height in pixels
     */
    public void setMinCropResultSize(int width, int height) {
        cropImageView.setMinCropResultSize(width, height);
    }

    public static void startPickImageActivity(Activity activity) {
//        CropImage.startPickImageActivity(activity);
        if (CropImage.isExplicitCameraPermissionRequired(activity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
            } else CropImage.startPickImageActivity(activity);
        } else {
            CropImage.startPickImageActivity(activity);
        }
    }

    public static CropImage.ActivityBuilder getCropActivityBuilder(@Nullable Uri uri) {
        return CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true);
    }

    /**
     * @param uri    URI of the onActivityResult()
     * @param width  in pixels
     * @param height in pixels
     * @return
     */
    public static CropImage.ActivityBuilder getCropActivityBuilderWithFixedCropper(@Nullable Uri uri, int width, int height) {
        return getCropActivityBuilder(uri).setMaxCropResultSize(width, height)
                .setMinCropResultSize(width, height);
    }

    /**
     * @param uri          URI of the onActivityResult()
     * @param aspectRatioX Aspect Ratio X
     * @param aspectRatioY Aspect Ratio Y
     * @return
     */
    public static CropImage.ActivityBuilder getCropActivityBuilderWithFixedAspectRatio(@Nullable Uri uri, int aspectRatioX, int aspectRatioY) {
        return getCropActivityBuilder(uri).setAspectRatio(aspectRatioX, aspectRatioY);
    }


    public static CropImage.ActivityResult getActivityResult(@NonNull Intent intent) {
        return CropImage.getActivityResult(intent);
    }

    /**
     * @param context  used to access Android APIs, like content resolve, it is your activity/fragment/widget.
     * @param imageUri the result URI of image pick.
     * @return true - required permission is granted, false - required permission isn't granted
     */
    public static boolean isReadExternalStoragePermissionsRequired(Context context, @NonNull Uri imageUri) {

        boolean isPermissionGranted = CropImage.isReadExternalStoragePermissionsRequired(context, imageUri);
//        if (PermissionUtility.onRequestingPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE, PICK_IMAGE_CHOOSER_REQUEST_CODE, true)) {}
        if (isPermissionGranted)
            return !isPermissionGranted;
        else return true;
    }
}
