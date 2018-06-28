package com.example.tasos.icalendare.Database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "event_type")
class TypeOfEvent(var title: String, var price: Float, var duration: Int) {

    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null
}
