package com.example.tasos.icalendare.Database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

import android.arch.persistence.room.OnConflictStrategy.REPLACE

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
