package com.example.tasos.icalendare.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**Dimiourgia basis dedomenwn*/

public class DatabaseInit  extends SQLiteOpenHelper{

    //SQLiteDatabase basiDedomenwn;
    public static final String DATABASE_NAME = "iCalendarE.db";
    public static final String EVENTS_TABLE_NAME = "events";

    public DatabaseInit(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseInit(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
