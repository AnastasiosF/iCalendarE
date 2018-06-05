package com.example.tasos.icalendare.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TypeOfEventDao {

    @Insert
    void insert(TypeOfEvent typeOfEvent);

    @Insert
    void insertMulti(List<TypeOfEvent> typeOfEventsList);

    //https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#5
    @Query("SELECT * from event_type ORDER BY title ASC")
    List<TypeOfEvent> getAllTypeOfEvents();

    @Query("DELETE FROM event_type ")
    void delete();
}
