package com.example.tasos.icalendare;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;

public class EventActivity extends AppCompatActivity {

    final int READ_CONTACT_PERMISSION_REQUEST_CODE = 76;

    WeekViewEvent event;
    Button dateButton;
    Button timeButton;
    HashMap eventData;
    MaterialEditText editTextMail;
    MaterialAutoCompleteTextView autoCompleteTextView;
    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //Arxikopoihsi ths ActioBar bilio8ikh
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle("New Event");
        ActionBarInit actionBarInit = new ActionBarInit(mActionBar, getBaseContext());
        actionBarInit.initActionBar();

        //elegxos dikaiwmatwn
        permissionsCheck();

        eventData = new HashMap();

        //editTextMail = findViewById(R.id.editText_email);
        //editTextMail.setError("Use a valid E-mail adress");

        dateButton = findViewById(R.id.date_button);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emfanisiEpilogisHmerom();
            }
        });

        timeButton = findViewById(R.id.time_button);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emfanisiEpilogisWras();
            }
        });

        //Symmetexontes Aytomath symplhrwsh
        // Get a reference to the AutoCompleteTextView in the layout
        autoCompleteTextView = findViewById(R.id.autocomplete_contacts);

        // Create the adapter and set it to the AutoCompleteTextView
        Contacts contacts = new Contacts(getBaseContext());
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts.getContactArrayList());
        autoCompleteTextView.setAdapter(adapter);

        /*
        //Spinner
        dropdown = (MaterialSpinner) findViewById(R.id.spinner);

        String[] items = getResources().getStringArray(R.array.countries_array);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, contacts.getContactArrayList());
        dropdown.setOnItemSelectedListener(this);
        dropdown.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getBaseContext(), "Selected: ", Toast.LENGTH_LONG).show();
                dropdown.setEnabled(true);
                dropdown.refreshDrawableState();
                return false;
            }
        });
        dropdown.setAdapter(adapter1);
        */

    }

    public void emfanisiEpilogisHmerom() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        eventData.put("year", year);
                        eventData.put("month", monthOfYear);
                        eventData.put("day", dayOfMonth);
                        dateButton.setText("Date:" + dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void emfanisiEpilogisWras() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                                                                @Override
                                                                public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                                                    String time = "You picked the following time: " + hourOfDay + "h" + minute + "m" + second;
                                                                    eventData.put("hour", hourOfDay);
                                                                    eventData.put("minute", minute);
                                                                    timeButton.setText("Time:" + hourOfDay + ":" + minute);
                                                                }
                                                            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true
        );
        dpd.show(getFragmentManager(), "Timepickerdialog");
    }

    public void permissionsCheck(){
                // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                android.support.v4.app.ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        READ_CONTACT_PERMISSION_REQUEST_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }


    }





}
