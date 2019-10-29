package com.example.tasos.icalendare.Database

import androidx.room.TypeConverter
import java.util.*


class CalendarTypeConverter {

    companion object{
        fun toCalendar(value: Long?): Calendar? {
            if(value == null){
                return null
            }else{
                var date:Date = Date(value)

                var calendar:Calendar = Calendar.getInstance()
                calendar.time = date

                return calendar
            }
        }

        fun toLong(value: Calendar?): Long? {
            if (value == null){
                return null
            }else{
                var date:Date = value.time
                return date.time
            }


        }
    }

    @TypeConverter
    fun toCalendar(value: Long?): Calendar? {
        if(value == null){
            return null
        }else{
            var date:Date = Date(value)

            var calendar:Calendar = Calendar.getInstance()
            calendar.time = date

            return calendar
        }
    }

    @TypeConverter
    fun toLong(value: Calendar?): Long? {
        if (value == null){
            return null
        }else{
            var date:Date = value.time
            return date.time
        }


    }

}