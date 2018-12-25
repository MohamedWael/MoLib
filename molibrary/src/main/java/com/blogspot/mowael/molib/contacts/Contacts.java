package com.blogspot.mowael.molib.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.mowael.molib.contacts.contactsPOJO.ContactInfo;
import com.blogspot.mowael.molib.utilities.MoConfig;
import com.blogspot.mowael.utilslibrary.Logger;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by moham on 2/17/2017.
 */


//<uses-permission android:name="android.permission.READ_CONTACTS" />

public class Contacts {

    private LoadListener loadListener;
    private Context mContext;
    private JSONObject contact;
    private JSONArray contacts;
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHOTO_URI = "PHOTO_URI";


    private Contacts(Context mContext, LoadListener loadListener) {
        this.mContext = mContext;
        this.loadListener = loadListener;
    }

    private boolean checkEmail(String name) {
        return name.matches(MoConfig.REGEX_Mail);
    }


    public Contacts() {
    }


    public void execute(AppCompatActivity activity, LoaderManager.LoaderCallbacks<Cursor> contactsLoader) {
        activity.getSupportLoaderManager().initLoader(999, new Bundle(), contactsLoader);
    }


    public JSONArray getProviderContacts(final AppCompatActivity activity, final LoadInfoListener loadListener) {
        contacts = new JSONArray();
        LoaderManager.LoaderCallbacks<Cursor> contactsLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//                ContentResolver contentResolver = activity.getContentResolver();
//                Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

//                Cursor managedCursor = getContentResolver()
//                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                                new String[] {Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER},
//                                null, null,  Phone.DISPLAY_NAME + " ASC");

                // Define the columns to retrieve
                String[] projectionFields = new String[]{ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.HAS_PHONE_NUMBER,
                        ContactsContract.Contacts.PHOTO_URI,
                        ContactsContract.Contacts.PHOTO_THUMBNAIL_URI};
                // Construct the loader
                CursorLoader cursorLoader = new CursorLoader(activity,
                        ContactsContract.Contacts.CONTENT_URI, // URI
//                        projectionFields, // projection fields
                        null,
                        null, // the selection criteria
                        null, // the selection args
                        null // the sort order
                );
                // Return the loader for use
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                try {
                    ArrayList<ContactInfo> contactsInfo = new ArrayList<>();
                    Gson gson = new Gson();
                    contacts = new JSONArray();
                    Logger.d("dataCount", data.getCount() + "  dataCount");
                    for (String x : data.getColumnNames()) {
                        Logger.d("getColumnName", x);
                    }
                    if (data.getCount() > 0) {
                        while (data.moveToNext()) {
                            contact = new JSONObject();
                            String id = data.getString(data.getColumnIndex(ContactsContract.Contacts._ID));
                            String name = data.getString(data.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            String photoUri = data.getString(data.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                            if (Integer.parseInt(data.getString(data.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                                Logger.d("Contact", "name : " + name + ", ID : " + id);


                                contact.put(ID, id);
                                contact.put(NAME, name);
                                contact.put(PHOTO_URI, photoUri);


                                JSONArray phoneNumbers = new JSONArray();

                                // get the phone number
//                                Cursor phoneNumberCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
//                                while (phoneNumberCursor.moveToNext()) {
//                                    JSONObject phoneNumberObject = new JSONObject();
//                                    String phoneNumber = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                                    Logger.d("phone", "number : " + phoneNumber);
//                                    phoneNumbers.put(phoneNumber);
//                                }
//                                contact.put("phoneNumbers", phoneNumbers);
//                                phoneNumberCursor.close();

                            }
                            Logger.d("oneContact", contact.toString());
                            ContactInfo info = gson.fromJson(contact.toString(), ContactInfo.class);
                            contactsInfo.add(info);
                            loadListener.getContacts(info);
                            contacts.put(contact);
                        }
                        loadListener.onLoadComplete(contactsInfo);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        };
        activity.getSupportLoaderManager().initLoader(999, new Bundle(), contactsLoader);
        return contacts;
    }

    private void readContacts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                contacts = new JSONArray();
                ContentResolver contentResolver = mContext.getContentResolver();
                Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                try {
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            contact = new JSONObject();
                            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                                Logger.d("Contact", "name : " + name + ", ID : " + id);

                                contact.put(ID, id);
                                contact.put(NAME, name);

                                JSONArray phoneNumbers = new JSONArray();

                                // get the phone number
                                Cursor phoneNumberCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                                while (phoneNumberCursor.moveToNext()) {
                                    JSONObject phoneNumberObject = new JSONObject();
                                    String phoneNumber = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    Logger.d("phone", "number : " + phoneNumber);
                                    phoneNumbers.put(phoneNumber);
                                }
                                contact.put("phoneNumbers", phoneNumbers);
                                phoneNumberCursor.close();

                                JSONArray emails = new JSONArray();
                                // get email and type
                                Cursor emailCursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                                while (emailCursor.moveToNext()) {
                                    // This would allow you get several email addresses
                                    // if the email addresses were stored in an array
                                    String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                    String emailType = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                                    Logger.d("Email", "Email " + email + " Email Type : " + emailType);
                                    JSONObject em = new JSONObject();
                                    em.put("email", email);
                                    em.put("emailType", emailType);
                                    emails.put(em);
//                        System.out.println("Email " + email + " Email Type : " + emailType);
                                }
                                contact.put("emails", emails);
                                emailCursor.close();

                                // Get note.......
                                String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                                String[] noteWhereParams = new String[]{id, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                                Cursor noteCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);
                                if (noteCursor.moveToFirst()) {
                                    String note = noteCursor.getString(noteCursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
//                        System.out.println("Note " + note);
                                    Logger.d("Note", note);
                                    contact.put("note", note);
                                }
                                noteCursor.close();

                                //Get Postal Address....
                                JSONArray postalAddresses = new JSONArray();
                                String postalAddressWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                                String[] addrWhereParams = new String[]{id, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                                Cursor postalAddressCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
                                while (postalAddressCursor.moveToNext()) {
                                    JSONObject postalAddress = new JSONObject();
                                    String poBox = postalAddressCursor.getString(postalAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                                    if (poBox != null) postalAddress.put("poBox", poBox);
                                    String street = postalAddressCursor.getString(postalAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                                    if (street != null) postalAddress.put("street", street);
                                    String city = postalAddressCursor.getString(postalAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                                    if (city != null) postalAddress.put("city", city);
                                    String state = postalAddressCursor.getString(postalAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                                    if (state != null) postalAddress.put("state", state);
                                    String postalCode = postalAddressCursor.getString(postalAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                                    if (postalCode != null)
                                        postalAddress.put("postalCode", postalCode);
                                    String country = postalAddressCursor.getString(postalAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                                    if (postalAddress != null)
                                        postalAddress.put("country", country);
                                    String type = postalAddressCursor.getString(postalAddressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                                    if (type != null) postalAddress.put("type", type);
//                        Logger.d("postalCode", postalCode);
                                    // Do something with these....
                                    postalAddresses.put(postalAddress);
                                }
//                        contact.put("postalAddresses", postalAddresses);
                                postalAddressCursor.close();

                                // Get Instant Messenger.........
                                String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                                String[] imWhereParams = new String[]{id,
                                        ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                                Cursor imCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                                        null, imWhere, imWhereParams, null);
                                if (imCur.moveToFirst()) {
                                    String imName = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                                    String imType;
                                    imType = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                                    if (imName != null) Logger.d("InstantMessageName", imName);
                                    if (imType != null) Logger.d("InstantMessageType", imType);
                                }
                                imCur.close();

                                // Get Organizations.........

                                String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                                String[] orgWhereParams = new String[]{id,
                                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                                Cursor orgCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, orgWhere, orgWhereParams, null);
                                if (orgCur.moveToFirst()) {
                                    String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                                    String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                                    Logger.d("OrganizationName", orgName);
                                    Logger.d("OrganizationTitle", title);
                                }
                                orgCur.close();
                            }
                            Logger.d("ContactInfo", contact.toString());
                            if (loadListener != null)
                                loadListener.getContacts(contact);
                            contacts.put(contact);

                        }
                        if (loadListener != null)
                            loadListener.onLoadComplete(contacts);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                return contacts;
            }
        }).start();
    }

    /**
     * TODO i have to study this method to improve readContacts method
     */
    private void getContacts() {
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String id = null, name = null, email = null, phone = null, note = null, orgName = null, title = null;
        String Phone1 = "unknown", Phone2 = "unknown", Phone3 = "unknown", type1 = "unknown", type2 = "unknown", type3 = "unknown";
        int size = cursor.getCount();
        if (cursor.getCount() > 0) {
            int cnt = 1;
            while (cursor.moveToNext()) {
                email = "";
                name = "";
                cnt++;
                id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (name != null && name != "") {
                    if (!checkEmail(name)) {
                        email = "";

                    } else {
                        email = name;
                        name = "";
                    }
                }
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                    System.out.println("name : " + name);
                    Cursor pCur = contentResolver
                            .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                            + " = ?", new String[]{id},
                                    null);

                    Phone1 = " ";
                    Phone2 = " ";
                    Phone3 = " ";
                    while (pCur.moveToNext()) {
                        String phonetype = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        String MainNumber = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (phonetype.equalsIgnoreCase("1")) {
                            Phone1 = MainNumber;
                            type1 = "home";
                        } else if (phonetype.equalsIgnoreCase("2")) {
                            Phone2 = MainNumber;
                            type2 = "mobile";
                        } else {
                            Phone3 = MainNumber;
                            type3 = "work";
                        }
                    }
                    pCur.close();

                }
                Cursor addrCur = contentResolver
                        .query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID
                                        + " = ?", new String[]{id},
                                null);
                if (addrCur.getCount() == 0) {
//                    addbuffer.append("unknown");
                } else {
                    int cntr = 0;
                    while (addrCur.moveToNext()) {

                        cntr++;
                        String poBox = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                        if (poBox == null) {
                            poBox = " ";
                        }
                        String street = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                        if (street == null) {
                            street = " ";
                        }
                        String neb = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD));
                        if (neb == null) {
                            neb = " ";
                        }
                        String city = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                        if (city == null) {
                            city = " ";
                        }
                        String state = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                        if (state == null) {
                            state = " ";
                        }
                        String postalCode = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                        if (postalCode == null) {
                            postalCode = " ";
                        }
                        String country = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                        if (country == null) {
                            country = " ";
                        }

                        String type = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                        if (type == null) {
                            type = " ";
                        }
                    }

                }

                addrCur.close();

                String noteWhere = ContactsContract.Data.CONTACT_ID
                        + " = ? AND " + ContactsContract.Data.MIMETYPE
                        + " = ?";
                String[] noteWhereParams = new String[]{
                        id,
                        ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                Cursor noteCur = contentResolver.query(
                        ContactsContract.Data.CONTENT_URI, null,
                        noteWhere, noteWhereParams, null);

                note = " ";

                if (noteCur.moveToFirst()) {
                    note = noteCur
                            .getString(noteCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));

                    if (note == null) {
                        note = " ";
                    }
                }
                noteCur.close();
                String orgWhere = ContactsContract.Data.CONTACT_ID
                        + " = ? AND " + ContactsContract.Data.MIMETYPE
                        + " = ?";
                String[] orgWhereParams = new String[]{
                        id,
                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                Cursor orgCur = contentResolver.query(
                        ContactsContract.Data.CONTENT_URI, null,
                        orgWhere, orgWhereParams, null);
                orgName = " ";
                if (orgCur.moveToFirst()) {
                    orgName = orgCur
                            .getString(orgCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));

                }
                if (orgName == null) {
                    orgName = " ";
                }
                orgCur.close();

                Cursor emailCur = contentResolver
                        .query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID
                                        + " = ?", new String[]{id},
                                null);
                email = "unknown";
                while (emailCur.moveToNext()) {

                    email = emailCur
                            .getString(emailCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    String emailType = emailCur
                            .getString(emailCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                    if (email == null) {
                        email = "unknown";
                    }
                    if (emailType.equalsIgnoreCase("1")) {
                    } else {
                    }
                }

                // add
                emailCur.close();
            }
        }
    }

    public interface LoadListener {
        /**
         * get a full list of contacts
         *
         * @param contacts
         */
        void onLoadComplete(JSONArray contacts);

        /**
         * get the loaded contact
         *
         * @param contact
         */
        void getContacts(JSONObject contact);
    }


    public interface LoadInfoListener {
        /**
         * get a full list of contacts
         *
         * @param contacts
         */
        void onLoadComplete(ArrayList<ContactInfo> contacts);

        /**
         * get the loaded contact
         *
         * @param contact
         */
        void getContacts(ContactInfo contact);
    }

}