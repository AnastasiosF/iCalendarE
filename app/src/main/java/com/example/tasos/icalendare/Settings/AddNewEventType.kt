package com.example.tasos.icalendare.Settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText

import com.example.tasos.icalendare.ActionBarInit
import com.example.tasos.icalendare.Database.ICalendarDatabase
import com.example.tasos.icalendare.Database.TypeOfEvent
import com.example.tasos.icalendare.R

class AddNewEventType : AppCompatActivity() {

    lateinit var title: EditText
    lateinit var price: EditText
    lateinit var duration: EditText
    lateinit var button: Button

    internal var iCalendarDatabase: ICalendarDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_event_type)

        //Arxikopoihsi ths ActioBar
        val mActionBar = supportActionBar
        val actionBarInit = ActionBarInit(mActionBar, baseContext)
        actionBarInit.initActionBar()


        title = findViewById(R.id.title_of_NewEvent)
        price = findViewById(R.id.price_of_NewEvent)
        duration = findViewById(R.id.duration_of_NewEvent)

        button = findViewById(R.id.add_event_type_button)


        button.setOnClickListener {
            val txtTitle = title.text.toString()
            val intDuration = Integer.valueOf(duration.text.toString())
            val floatPrice = java.lang.Float.valueOf(price.text.toString())!!

            Thread(Runnable {
                ICalendarDatabase.getInstance(applicationContext).typeOfEventDao().insert(
                        TypeOfEvent(txtTitle, floatPrice, intDuration))

                val intent = Intent()
                intent.setClass(applicationContext, SettingsActivity::class.java)
                startActivity(intent)
            }).start()
        }


    }
}
