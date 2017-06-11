package com.blogspot.mowael.molib.network.pojo;

import com.blogspot.mowael.molib.network.CommonHTTPCode;
import com.google.gson.annotations.SerializedName;

/**
 * Created by moham on 4/14/2017.
 */

public class GeneralResponse {
    @SerializedName("message")
    protected String mMessage;
    @SerializedName("code")
    protected int mCode;

    public GeneralResponse() {
    }

    public boolean isSuccess() {
        if (mCode == CommonHTTPCode.HTTP_CODE_SUCCESS) return true;
        return false;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        this.mCode = code;
    }
}
