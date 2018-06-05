package com.example.tasos.icalendare.Settings;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tasos.icalendare.R;
import com.example.tasos.icalendare.database.ICalendarDatabase;
import com.example.tasos.icalendare.database.TypeOfEvent;

public class AddNewEventType extends AppCompatActivity {

    EditText title;
    EditText price;
    EditText duration;
    Button button;

    ICalendarDatabase iCalendarDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event_type);


        title = findViewById(R.id.title_of_NewEvent);
        price = findViewById(R.id.price_of_NewEvent);
        duration = findViewById(R.id.duration_of_NewEvent);

        button = findViewById(R.id.add_event_type_button);
        iCalendarDatabase = Room.databaseBuilder(getApplicationContext(),ICalendarDatabase.class,"iCalandarDatabase")
                .allowMainThreadQueries()
                .build();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtTitle = title.getText().toString();
                String txtDuration = duration.getText().toString();
                float floatPrice = Float.valueOf(price.getText().toString());

                if(txtTitle !="" && txtDuration !="" && floatPrice >= 0 ){
                    iCalendarDatabase.typeOfEventDao().insert(
                            new TypeOfEvent(txtTitle,floatPrice,txtDuration));
                }
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);

            }
        });


    }
}
