package com.example.tasos.icalendare;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Arxikopoihsi ths ActioBar bilio8ikh
        ActionBar mActionBar = getSupportActionBar();
        ActionBarInit actionBarInit = new ActionBarInit(mActionBar, getBaseContext());
        actionBarInit.initActionBar();

        List<EventDay> events = new ArrayList<>();

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-5);

        events.add(new EventDay(calendar, R.drawable.ic_event_black_24dp));

        events.add(new EventDay(calendar, R.drawable.ic_event_black_24dp));

        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, 0);
        events.add(new EventDay(calendar1, R.drawable.ic_event_black_24dp));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, 5);
        events.add(new EventDay(calendar2, R.drawable.ic_event_black_24dp));

        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -2);

        Calendar max = Calendar.getInstance();
        max.add(Calendar.MONTH, 2);

        calendarView.setMinimumDate(min);
        calendarView.setMaximumDate(max);

        calendarView.setEvents(events);

    }
}