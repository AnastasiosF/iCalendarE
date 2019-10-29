package com.example.tasos.icalendare.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TypeOfEventDao {

    //https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#5
    @get:Query("SELECT * from event_type ORDER BY title ASC")
    val allTypeOfEvents: List<TypeOfEvent>

    @Query("SELECT title FROM event_type WHERE uid LIKE :id")
    fun eventsList(id: Int): List<String>

    @Insert
    fun insert(typeOfEvent: TypeOfEvent)

    @Insert
    fun insertMulti(typeOfEventsList: List<TypeOfEvent>)

    @Update
    fun update(typeOfEvent: TypeOfEvent)

    @Query("DELETE FROM event_type ")
    fun delete()


}
