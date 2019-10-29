package com.example.tasos.icalendare.Calendar

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import com.example.tasos.icalendare.Database.CalendarTypeConverter
import com.example.tasos.icalendare.Database.Events
import com.example.tasos.icalendare.Database.ICalendarDatabase
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent

class EventsToDrawableCalendarEventADAPTER {

    companion object Adapter{
        fun adaptToDrawableCalendarEvent(events : Events,context: Context): DrawableCalendarEvent{
            var drawableCalendarEvent: DrawableCalendarEvent

            var  typeOfEventList: List<String> = ICalendarDatabase.getInstance(context).typeOfEventDao().eventsList(events.typeOfEvent_ID)
            var typeOfEventTitle: String = ""
            if(typeOfEventList.isNotEmpty()){
                typeOfEventTitle = typeOfEventList[0]
            }



            drawableCalendarEvent = DrawableCalendarEvent(events.id,
                    Color.GRAY,
                    events.title,
                    events.description,
                    events.location,
                    CalendarTypeConverter.toCalendar(events.dateStart),
                    CalendarTypeConverter.toCalendar(events.dateEnd),
                    0,
                    "",
                    0,
                    events.typeOfEvent_ID,
                    events.contactName_ID,
                    typeOfEventTitle
                    )


            return drawableCalendarEvent
        }
        /*
        fun adaptToEvents(drawableCalendarEvent: DrawableCalendarEvent):Events{
            var events: Events

            events = Events(drawableCalendarEvent.id,
                    drawableCalendarEvent.title,
                    null,
                    null,
                    null,
                    null,
                    drawableCalendarEvent.startTime,
                    drawableCalendarEvent.endTime,
                    drawableCalendarEvent.isAllDay,
                    drawableCalendarEvent.duration,
                    drawableCalendarEvent.description,
                    drawableCalendarEvent.color)
        }
        */
    }
}