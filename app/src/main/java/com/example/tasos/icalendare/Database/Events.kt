package com.example.tasos.icalendare.Database


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "events")
class Events(@field:PrimaryKey(autoGenerate = true)
             var eventsID: Long?,
             var contactName_ID: String,
             var typeOfEvent_ID: Int?,
             var title: String,
             var date: String,
             var time: String,
             var timeToAlerUser: String,
             var timeToAlertParticipant: String,
             var notes: String)

{
    constructor():this(null,"",null,"","","",
            "","","")
}