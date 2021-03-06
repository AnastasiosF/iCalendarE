package com.example.tasos.icalendare.Calendar

import com.example.tasos.icalendare.Database.CalendarTypeConverter
import com.github.tibolte.agendacalendarview.models.CalendarEvent
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent
import java.util.*


class DrawableCalendarEvent : BaseCalendarEvent {
    // endregion

    // region Public methods
    var drawableId: Int = 0
    var contactNameID: String
    var typeOfEventID: Int
    var typeOfEventTitle: String



    // region Constructors

    constructor(id: Long,
                color: Int,
                title: String,
                description: String,
                location: String?,
                dateStart: Calendar?,
                dateEnd: Calendar?,
                allDay: Int,
                duration: String,
                drawableId: Int,
                typeOfEvent_ID: Int,
                contactName_ID: String,
                typeOfEvent_Title: String
    ) : super(id, color, title, description, location, CalendarTypeConverter.toLong(dateStart)!!, CalendarTypeConverter.toLong(dateEnd)!!, allDay, duration) {
        this.drawableId = drawableId
        typeOfEventID = typeOfEvent_ID
        contactNameID = contactName_ID
        typeOfEventTitle = typeOfEvent_Title
    }


    constructor(title: String,
                description: String,
                location: String?,
                color: Int,
                startTime: Calendar?,
                endTime: Calendar?,
                allDay: Boolean,
                drawableId: Int,
                typeOfEventID: Int,
                contactNameID: String,
                typeOfEventTitle: String) : super(title, description, location, color, startTime, endTime, allDay) {
        this.drawableId = drawableId
        this.typeOfEventID = typeOfEventID
        this.contactNameID = contactNameID
        this.typeOfEventTitle = typeOfEventTitle

    }

    constructor(calendarEvent: DrawableCalendarEvent) : super(calendarEvent) {
        this.drawableId = calendarEvent.drawableId
        this.typeOfEventID = calendarEvent.typeOfEventID
        this.contactNameID = calendarEvent.contactNameID
        this.typeOfEventTitle = calendarEvent.typeOfEventTitle
    }

    // endregion

    // region Class - BaseCalendarEvent

    override fun copy(): CalendarEvent {
        return DrawableCalendarEvent(this)
    }

    // endregion
}