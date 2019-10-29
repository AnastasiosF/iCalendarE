package com.example.tasos.icalendare.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_type")
class TypeOfEvent(var title: String, var price: Float, var duration: Int) {

    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null
}
