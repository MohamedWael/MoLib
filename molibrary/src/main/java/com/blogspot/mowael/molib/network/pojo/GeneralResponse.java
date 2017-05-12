package com.blogspot.mowael.molib.network.pojo;

import com.blogspot.mowael.molib.network.CommonHTTPCode;

/**
 * Created by moham on 4/14/2017.
 */

public class GeneralResponse {
    protected String message;
    protected int code;

    public GeneralResponse() {
    }

    public boolean isSuccess() {
        if (code == CommonHTTPCode.HTTP_CODE_SUCCESS) return true;
        return false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
