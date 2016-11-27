package com.autofactory.smilebuy.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Phebe on 16. 1. 2..
 */
public class ContactData {
    protected String mID;
    protected String mName;
    protected String[] mPhoneNum;

    @Override
    public String toString() {
        return "ContactData{" +
                "mID='" + mID + '\'' +
                ", mName='" + mName + '\'' +
                ", mPhoneNum=" + Arrays.toString(mPhoneNum) +
                '}';
    }

    public ContactData(String id, String name, ArrayList<String> phoneNum) {
        mID = id;
        mName = name;
        if(phoneNum != null) {
            mPhoneNum = new String[phoneNum.size()];
            for(int i=0; i<phoneNum.size(); i++) {
                mPhoneNum[i] = phoneNum.get(i);
            }
        } else {
            mPhoneNum = null;
        }
    }

    public static ArrayList<ContactData> getContactDataList(Context context) {
        ArrayList<ContactData> list = new ArrayList<>();

        Uri uriContacts = ContactsContract.Contacts.CONTENT_URI;

        String[] contactProjection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,};

        Cursor cursorContacts = context.getContentResolver().query(uriContacts, contactProjection, null, null, null);

        if (cursorContacts.getCount() == 0) {
            Log.e("CONTACT DOES NOT EXIST");
            cursorContacts.close();
            return null;
        } else {
            cursorContacts.moveToFirst();
            do {
                String id = cursorContacts.getString(cursorContacts.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursorContacts.getString(cursorContacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String hasPhoneNum = cursorContacts.getString(cursorContacts.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (hasPhoneNum.equals("1")) {
                    Uri uriPhoneNum = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                    String selectPhoneNum = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= " + id;

                    String[] phoneNumProjection = new String[]{
                            ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };

                    Cursor cursorPhoneNum = context.getContentResolver().query(uriPhoneNum, phoneNumProjection, selectPhoneNum, null, null);

                    if (cursorPhoneNum.getCount() == 0) {
                        Log.e(id + " : CURSOR_PHONE_NUM COUNT(0)");
                    } else {
                        cursorPhoneNum.moveToFirst();

                        ArrayList<String> phoneNumList = new ArrayList<>();
                        do {
                            phoneNumList.add(cursorPhoneNum.getString(cursorPhoneNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        } while (cursorPhoneNum.moveToNext());

                        // Add Contact Data To List
                        list.add(new ContactData(id, name, phoneNumList));
                    }
                    cursorPhoneNum.close();
                } else {
                    Log.e(id + " : HAS_PHONE_NUMBER(" + hasPhoneNum + ")");
                }
            } while (cursorContacts.moveToNext());
        }
        cursorContacts.close();

        Log.d("GET CONTACT(" + list.size() + ") : " + Arrays.toString(list.toArray()));

        return list;
    }
}
