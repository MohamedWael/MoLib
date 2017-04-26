package com.blogspot.mowael.molib.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.mowael.molib.activities.MoActivity;

/**
 * Created by moham on 3/17/2017.
 */

public class ViewUtils {

    private ViewUtils instance;
    private Context context;

    public ViewUtils getInstance() {
        if (instance == null) {
            instance = new ViewUtils();
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    /**
     * @param context
     * @return LayoutInflater from the provided context
     */
    public static LayoutInflater getLayoutInflater(Context context) {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static void setMaxLength(int maxLength, EditText editText) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(fArray);
    }

    public static SpannableString makeSpanString(Context context, String str, int fontSize, @ColorRes int color) {
        return makeSpan(str, fontSize, getColor(context, color));
    }

    public static SpannableString makeSpan(String str, int fontSize, @ColorInt int color) {
        SpannableString spannableString = new SpannableString(str);
        int length = str.length();
        spannableString.setSpan(new AbsoluteSizeSpan(fontSize, true), 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE); // set size
        spannableString.setSpan(new ForegroundColorSpan(color), 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);// set color
        return spannableString;
    }

    public static SpannableString makeUnderLineString(String str, int fontSize, @ColorInt int color) {
        SpannableString string = makeSpan(str, fontSize, color);
        string.setSpan(new UnderlineSpan(), 0, str.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    /**
     * @param context
     * @param id                of a drawable
     * @param verticalAlignment eg. ImageSpan.ALIGN_BOTTOM
     * @param str
     * @param fontSize          font size in dp
     * @param color
     * @return SpannableString sb
     */
    public static SpannableString makeSpanWithImage(Context context, @DrawableRes int id, int verticalAlignment, String str, int fontSize, @ColorInt int color) {
        Drawable image = getDrawable(context, id);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = makeSpan(str, fontSize, color);
        ImageSpan imageSpan = new ImageSpan(image, verticalAlignment);
        sb.setSpan(imageSpan, 0, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    public static void startActivity(Context context, Class<? extends Activity> aClass, boolean finish) {
        startActivity(context, aClass, null, finish);
    }

    public static void startActivity(Context context, Class<? extends Activity> aClass) {
        startActivity(context, aClass, null, false);
    }

    public static void startActivity(Context context, Class<? extends Activity> aClass, @Nullable Bundle extras, boolean finish) {
        Intent intent = new Intent(context, aClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
        if (finish)
            ((Activity) context).finish();
    }

    public static int getColor(Context context, @ColorRes int id) {
        return ContextCompat.getColor(context, id);
    }

    public static String getString(Context context, int id) {
        return context.getString(id);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int id) {
        return ContextCompat.getDrawable(context, id);
    }

    public static void makeFullScreen(MoActivity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static Snackbar snakeMsg(View view, String msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        return snackbar;
    }

    public static Toast toastMsg(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();
        return toast;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
