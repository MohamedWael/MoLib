
package com.blogspot.mowael.molib.contacts.contactsPOJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ContactInfo {

    @SerializedName("emails")
    private List<Email> mEmails;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("note")
    private String mNote;
    @SerializedName("phoneNumbers")
    private List<String> mPhoneNumbers;

    public List<Email> getEmails() {
        return mEmails;
    }

    public void setEmails(List<Email> emails) {
        mEmails = emails;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public List<String> getPhoneNumbers() {
        return mPhoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        mPhoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return "id: " + getId() + " name: " + getName();
    }
}
