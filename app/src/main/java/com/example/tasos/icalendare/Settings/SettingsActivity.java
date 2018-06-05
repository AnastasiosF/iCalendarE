package com.example.tasos.icalendare.Settings;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.tasos.icalendare.ActionBarInit;
import com.example.tasos.icalendare.R;
import com.example.tasos.icalendare.database.Contact;
import com.example.tasos.icalendare.database.ICalendarDatabase;
import com.example.tasos.icalendare.database.TypeOfEvent;

import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends AppCompatActivity {
    Button addNewEvType;
    RecyclerView rv;
    List<TypeOfEvent> input;
    ICalendarDatabase calendarDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Arxikopoihsi ths ActioBar
        ActionBar mActionBar = getSupportActionBar();
        ActionBarInit actionBarInit = new ActionBarInit(mActionBar, getBaseContext());
        actionBarInit.initActionBar();

        //Button gia new Event
        addNewEvType = findViewById(R.id.add_new_event_button);
        addNewEvType.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),AddNewEventType.class);
            startActivity(intent);
            //calendarDatabase.typeOfEventDao().delete();

            /*
            calendarDatabase.typeOfEventDao().delete();
            input = calendarDatabase.typeOfEventDao().getAllTypeOfEvents();

            rv.setAdapter(new MyAdapterRecycleView(input));
            */
        });


        //Bash dedomenwn
        calendarDatabase = Room.databaseBuilder(getBaseContext(),ICalendarDatabase.class,"iCalandarDatabase")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();


        rv = (RecyclerView)findViewById(R.id.recycleView);

        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);

        input = calendarDatabase.typeOfEventDao().getAllTypeOfEvents();

        rv.setAdapter(new MyAdapterRecycleView(input));




    }
}
