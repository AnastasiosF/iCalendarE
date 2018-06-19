package com.example.tasos.icalendare.Database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface TypeOfEventDao {

    //https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#5
    @get:Query("SELECT * from event_type ORDER BY title ASC")
    val allTypeOfEvents: List<TypeOfEvent>

    @Insert
    fun insert(typeOfEvent: TypeOfEvent)

    @Insert
    fun insertMulti(typeOfEventsList: List<TypeOfEvent>)

    @Update
    fun update(typeOfEvent: TypeOfEvent)

    @Query("DELETE FROM event_type ")
    fun delete()
}
