package com.blogspot.mowael.molib.network.pojo;

import com.blogspot.mowael.molib.network.CommonHTTPError;
import com.blogspot.mowael.molib.utilities.MoConstants;

/**
 * Created by moham on 4/14/2017.
 */

public class GeneralResponse {
    private String message;
    private int code;

    public GeneralResponse() {
    }

    public boolean isSuccess() {
        if (code == CommonHTTPError.HTTP_CODE_SUCCESS) return true;
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
