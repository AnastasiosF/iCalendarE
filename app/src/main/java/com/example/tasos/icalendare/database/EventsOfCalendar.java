package com.example.tasos.icalendare.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "events")
public class EventsOfCalendar {

    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "title")
    String title;
}
