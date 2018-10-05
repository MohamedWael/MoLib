package com.blogspot.mowael.molib.utilities;

/**
 * Created by moham on 3/2/2017.
 */

public class MoConfig {

    private final static long NONE_LONG = Long.MIN_VALUE;
    private final static double NONE_DOUBLE = Double.MIN_VALUE;
    private final static int NONE_INT = Integer.MIN_VALUE;
    private final static float NONE_FLOAT = Float.MIN_VALUE;

    public static final int DEFAULT_SPLASH_TIME_OUT = 3000;

    public static final String SHARED_PREFERENCES_NAME = "mo_preferences";

    public static final String REGEX_Mail = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    public static final String NOT_EXIST = "notExist";
}
