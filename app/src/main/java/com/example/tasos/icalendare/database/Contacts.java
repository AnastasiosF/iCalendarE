package com.example.tasos.icalendare.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**Epistrefei oles tis epafes*/

public class Contacts {
    List<Contact> contactList = new LinkedList<>();
    ArrayList<String> contactArrayList = new ArrayList<>();
    HashMap<String,String> contactHashMap = new HashMap<>();
    Context context;
    Contact contact;

    public Contacts(Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    int i=0;
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //Toast.makeText(NativeContentProvider.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                        contactArrayList.add(name+" "+phoneNo);
                        contactHashMap.put(name,phoneNo);
                        contact = new Contact();
                        contact.setName(name);
                        contact.setPhone(phoneNo);
                        contactList.add(contact);
                        i++;
                    }
                    pCur.close();
                }
            }
        }
    }

    public ArrayList<String> getContactArrayList() {
        return contactArrayList;
    }

    public HashMap<String, String> getContactHashMap() {
        return contactHashMap;
    }

    public List<Contact> getContactList() {
        return contactList;
    }
}
