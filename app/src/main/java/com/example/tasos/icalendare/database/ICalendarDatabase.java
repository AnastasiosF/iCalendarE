package com.example.tasos.icalendare.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

@Database(entities = {Contact.class,TypeOfEvent.class}, version = 1)
public abstract class ICalendarDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();
    public abstract TypeOfEventDao typeOfEventDao();

    public static ICalendarDatabase INSTANCE;

    static ICalendarDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (ICalendarDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ICalendarDatabase.class, "icalendar_database")
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
/*
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };
    */
}
