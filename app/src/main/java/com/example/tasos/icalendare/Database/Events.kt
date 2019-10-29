package com.example.tasos.icalendare.Database


import androidx.room.Entity
import androidx.room.PrimaryKey
import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent
import java.util.*

@Entity(tableName = "events")

data class Events(

        var title: String,
        var contactName_ID: String,
        var typeOfEvent_ID: Int,
        var timeToAlerUser: String?,
        var timeToAlertParticipant: String?,
        var dateStart: Long?,
        var dateEnd: Long?,
        var location: String?,
        var allDay: Int,
        var duration: String="",
        var description:String="",
        var color:Int= Color.LTGRAY)
{
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0



}