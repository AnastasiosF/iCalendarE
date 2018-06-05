package com.example.tasos.icalendare.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "contacts")
public class Contact {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "first_name")
    String name;

    @ColumnInfo(name = "last_name")
    String surname;

    @ColumnInfo(name = "phone")
    String phone;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
