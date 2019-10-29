package com.example.tasos.icalendare.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface EventsDao {

    @get:Query("SELECT * FROM events")
    val allEvents: List<Events>


    @Insert(onConflict = REPLACE)
    fun insert(vararg events: Events)

    @Insert(onConflict = REPLACE)
    fun insertMulti(eventsList: List<Events>)

    @Query("SELECT * FROM events WHERE id LIKE :id")
    fun getEvent(id: Long): List<Events>

    @Delete
    fun delete(events: Events)


}
