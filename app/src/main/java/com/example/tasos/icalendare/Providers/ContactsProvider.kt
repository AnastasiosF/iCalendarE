package com.example.tasos.icalendare.Providers

import android.content.Context
import android.provider.ContactsContract
import com.example.tasos.icalendare.Database.Contact
import java.util.*

/**
 * Epistrefei oles tis epafes
 */

class ContactsProvider(context: Context) {
    internal var contactList: MutableList<Contact> = LinkedList()
    var contactArrayList = ArrayList<String>()
        internal set
    var contactHashMap = HashMap<String, String>()
        internal set
    internal var context: Context? = null
    internal lateinit var contact: Contact

    init {
        val cr = context.contentResolver
        val cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        if (cur!!.count > 0) {
            while (cur.moveToNext()) {
                val id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                if (Integer.parseInt(cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    val pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id), null)
                    var i = 0
                    while (pCur!!.moveToNext()) {
                        val phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        //Toast.makeText(NativeContentProvider.this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                        contactArrayList.add("$name $phoneNo")

                        contactHashMap!!["name"] = name
                        contactHashMap!!["phone"] = phoneNo

                        contact = Contact()

                        contact.name = name
                        contact.phone = phoneNo
                        contactList.add(contact)
                        i++
                    }
                    pCur.close()
                }
            }
        }
    }

    fun getContactList(): List<Contact> {
        return contactList
    }
}
