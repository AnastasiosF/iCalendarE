package com.example.tasos.icalendare.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insert(Contact contact);
    @Insert
    void insertMulti(List<Contact> contactList);

    //https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#5
    @Query("SELECT * from contacts ORDER BY last_name ASC")
    List<Contact> getAllContacts();


}
