package com.example.tasos.icalendare.Settings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button

import com.example.tasos.icalendare.ActionBarInit
import com.example.tasos.icalendare.Database.ICalendarDatabase
import com.example.tasos.icalendare.Database.TypeOfEvent
import com.example.tasos.icalendare.R

import java.util.ArrayList


class SettingsActivity : AppCompatActivity() {
    lateinit var addNewEvType: Button
    lateinit var rv: RecyclerView
    var input: List<TypeOfEvent> = ArrayList()
    var iCalendarDatabase: ICalendarDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        //Arxikopoihsi ths ActioBar
        val mActionBar = supportActionBar
        val actionBarInit = ActionBarInit(mActionBar, baseContext)
        actionBarInit.initActionBar()

        //Button gia new Event
        addNewEvType = findViewById(R.id.add_new_event_button)
        addNewEvType.setOnClickListener { v ->
            val intent = Intent()
            intent.setClass(applicationContext, AddNewEventType::class.java)
            startActivity(intent)
            //calendarDatabase.typeOfEventDao().delete();

            /*
            calendarDatabase.typeOfEventDao().delete();
            input = calendarDatabase.typeOfEventDao().getAllTypeOfEvents();

            rv.setAdapter(new MyAdapterRecycleView(input));
            */
        }


        //Bash dedomenwn


        rv = findViewById(R.id.recycleView) as RecyclerView

        rv.setHasFixedSize(true)

        val llm = LinearLayoutManager(baseContext)
        rv.layoutManager = llm

        Thread(Runnable {
            try {
                input = ICalendarDatabase.getInstance(applicationContext)
                        .typeOfEventDao()
                        .allTypeOfEvents
                rv.adapter = MyAdapterRecycleView(input)

            } catch (e: NullPointerException) {

            }
        }).start()


    }
}
