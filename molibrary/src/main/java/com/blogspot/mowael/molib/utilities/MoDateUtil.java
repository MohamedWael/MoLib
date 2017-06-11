package com.blogspot.mowael.molib.utilities;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by moham on 5/19/2017.
 */

public class MoDateUtil {

    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;

    public static String getFormated(Date date) {
        return getString(date, "yyyy-MM-dd HH:mm");//new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    public static String getFormated(long date, String format) {
        Date d = new Date(date);
        return getString(d, format);
    }


    public static String getString(Date d, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }


    public static Date parseDate(String date) throws ParseException {
        return SimpleDateFormat.getInstance().parse(date);
//        return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
    }

    /**
     * @param time                 some time in the past.
     * @param minResolution        the minimum elapsed time (in milliseconds) to report
     *                             when showing relative times. For example, a time 3 seconds in
     *                             the past will be reported as "0 minutes ago" if this is set to
     *                             {@link #MINUTE_IN_MILLIS}.
     * @param transitionResolution the elapsed time (in milliseconds) at which
     *                             to stop reporting relative measurements. Elapsed times greater
     *                             than this resolution will default to normal date formatting.
     *                             For example, will transition from "7 days ago" to "Dec 12"
     *                             when using {@link #WEEK_IN_MILLIS}.
     */
    public static CharSequence getRelativeDateTimeString(Context c, long time, long minResolution, long transitionResolution) {
        return DateUtils.getRelativeDateTimeString(c, time, minResolution, transitionResolution, 0);
    }

}
