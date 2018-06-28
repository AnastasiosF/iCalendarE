package com.example.tasos.icalendare.Database

import android.arch.persistence.room.*
import android.content.Context

@Database(entities = arrayOf(Contact::class, TypeOfEvent::class, Events::class), version = 23)
@TypeConverters(CalendarTypeConverter::class)
abstract class ICalendarDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    abstract fun typeOfEventDao(): TypeOfEventDao

    abstract fun eventsDao(): EventsDao

    companion object {

        private var INSTANCE: ICalendarDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ICalendarDatabase {
            if (INSTANCE == null) {
                synchronized(ICalendarDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                ICalendarDatabase::class.java, "icalendar_database")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .build()

                    }
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
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
