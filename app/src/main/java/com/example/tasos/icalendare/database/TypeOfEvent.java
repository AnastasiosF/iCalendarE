package com.example.tasos.icalendare.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "event_type")
public class TypeOfEvent {

    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "price")
    float price;

    @ColumnInfo(name = "duration")
    String duration;

    public TypeOfEvent(String title, float price, String duration) {
        this.title = title;
        this.price = price;
        this.duration = duration;
    }



    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
