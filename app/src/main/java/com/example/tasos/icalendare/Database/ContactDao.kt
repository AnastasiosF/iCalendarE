package com.example.tasos.icalendare.Database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import android.database.sqlite.SQLiteConstraintException

import android.arch.persistence.room.OnConflictStrategy.FAIL

@Dao
abstract class ContactDao {

    //https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#5
    @get:Query("SELECT * from contacts ORDER BY name ASC")
    internal abstract val allContactsAbstract: List<Contact>


    val allContacts: List<Contact>
        get() = allContactsAbstract

    @Insert(onConflict = FAIL)
    abstract fun insert(contact: Contact)

    @Insert(onConflict = FAIL)
     abstract fun insertMulti(contactList: List<Contact>)

    @Update
     abstract fun update(contact: Contact)

    @Update
     abstract fun updateMulti(contactList: List<Contact>)

    @Query("SELECT * from contacts WHERE name LIKE :name ")
    abstract fun getContactAbstract(name: String): List<Contact>

    @Query("DELETE FROM event_type ")
    abstract fun delete()


    fun upsert(contact: Contact) {
        try {
            insert(contact)
        } catch (exception: SQLiteConstraintException) {
            update(contact)
        }

    }

    fun getContact(name: String): List<Contact> {
        return getContactAbstract(name)
    }


}
