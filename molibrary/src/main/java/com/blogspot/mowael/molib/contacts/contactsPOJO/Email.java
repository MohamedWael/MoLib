
package com.blogspot.mowael.molib.contacts.contactsPOJO;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Email {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("emailType")
    private String mEmailType;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getEmailType() {
        return mEmailType;
    }

    public void setEmailType(String emailType) {
        mEmailType = emailType;
    }

}
