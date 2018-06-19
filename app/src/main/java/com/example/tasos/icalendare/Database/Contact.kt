package com.example.tasos.icalendare.Database


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "contacts")
class Contact {

    /*@NonNull
    @PrimaryKey(autoGenerate = true)
    int contactID;
    */
    /*
    @NonNull
    public int getContactID() {
        return contactID;
    }

    public void setContactID(@NonNull int contactID) {
        this.contactID = contactID;
    }
    */

    @PrimaryKey
    lateinit var name: String

    //lateinit var surname: String

    lateinit var phone: String
}
