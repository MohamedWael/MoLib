package com.blogspot.mowael.molib.utilities;

import android.util.Patterns;

/**
 * Created by moham on 5/5/2017.
 */

public class PatternMatcher {

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidPhone(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.PHONE.matcher(target).matches();
        }
    }

    public static boolean isValidIPAddress(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.IP_ADDRESS.matcher(target).matches();
        }
    }
}
