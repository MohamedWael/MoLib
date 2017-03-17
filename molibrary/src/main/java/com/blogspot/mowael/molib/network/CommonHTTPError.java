package com.blogspot.mowael.molib.network;

/**
 * Created by moham on 2/6/2017.
 */

public class CommonHTTPError {

    public static final int HTTP_CODE_SOMETHING_WRONG = 100;
    public static final int HTTP_CODE_AUTH_FAILED = 103;
    public static final int HTTP_CODE_INVALID_API_TOKEN = 111;
    public static final int HTTP_CODE_TOKEN_NOT_FOUND = 115;
    public static final int HTTP_CODE_SUCCESS = 200;
    public static final int HTTP_CODE_VALIDATION_ERRORS = 402;
    public static final int HTTP_CODE_NOT_FOUND = 404; //something not found like an ad
    public static final int HTTP_CODE_WRONG_PHONE_NUMBER = 405;
    public static final int HTTP_CODE_WRONG_FORGET_PASS_VERIFY_NUMBER = 406;
    public static final int HTTP_CODE_WAIT_BEFORE_RESEND = 410;
    public static final int HTTP_CODE_DO_NOT_HAVE_PERMISSION = 412;
    private String errorMsg;

    public CommonHTTPError(int errorCode) {
        switch (errorCode) {
            case HTTP_CODE_SUCCESS:
                errorMsg = "CODE_SUCCESS".replace("_", " ").toLowerCase();
                break;
            case HTTP_CODE_VALIDATION_ERRORS:
                errorMsg = "CODE_VALIDATION_ERRORS".replace("_", " ").toLowerCase();
                break;
            case HTTP_CODE_SOMETHING_WRONG:
                errorMsg = "CODE_SOMETHING_WRONG".replace("_", " ").toLowerCase();
                break;
            case HTTP_CODE_INVALID_API_TOKEN:
                errorMsg = "CODE_INVALID_API_TOKEN".replace("_", " ").toLowerCase();
                break;
            case HTTP_CODE_NOT_FOUND:
                errorMsg = "CODE_NOT_FOUND".replace("_", " ").toLowerCase();
                break;
            case HTTP_CODE_AUTH_FAILED:
                errorMsg = "CODE_AUTH_FAILED".replace("_", " ").toLowerCase();
                break;
            case HTTP_CODE_TOKEN_NOT_FOUND:
                errorMsg = "CODE_TOKEN_NOT_FOUND".replace("_", " ").toLowerCase();
                break;
            case HTTP_CODE_WRONG_PHONE_NUMBER:
                errorMsg = "CODE_WRONG_PHONE_NUMBER".replace("_", " ").toLowerCase();
                break;
            case HTTP_CODE_WRONG_FORGET_PASS_VERIFY_NUMBER:
                errorMsg = "CODE_WRONG_FORGET_PASS_VERIFY_NUMBER".replace("_", " ").toLowerCase();
                break;
            case HTTP_CODE_WAIT_BEFORE_RESEND:
                errorMsg = "CODE_WAIT_BEFORE_RESEND".replace("_", " ").toLowerCase();
                break;
            case HTTP_CODE_DO_NOT_HAVE_PERMISSION:
                errorMsg = "CODE_DO_NOT_HAVE_PERMISSION".replace("_", " ").toLowerCase();
                break;
        }
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
