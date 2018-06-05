package com.example.tasos.icalendare;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.example.tasos.icalendare.database.Contact;
import com.example.tasos.icalendare.database.Contacts;
import com.example.tasos.icalendare.database.ICalendarDatabase;
import com.example.tasos.icalendare.database.TypeOfEvent;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
/*
* Permission Library : https://github.com/permissions-dispatcher/PermissionsDispatcher
*
* */

@RuntimePermissions
public class EventActivity extends AppCompatActivity {


    final int READ_CONTACT_PERMISSION_REQUEST_CODE = 76;

    private static final int PERMISSION_REQUEST_CODE = 200;

    private ICalendarDatabase calendarDatabase;
    private static final String DATABASE_NAME = "icalendar_database";



    WeekViewEvent event;
    Button dateButton;
    Button timeButton;
    HashMap eventData;
    MaterialEditText editTextMail;
    MaterialAutoCompleteTextView autoCompleteTextView;
    Spinner dropdownContacts;
    Spinner dropdownEventTypes;
    Spinner dropdownToAlertUser;
    Spinner dropdownTimeToAlertContact;
    Contacts contacts;

    ArrayList<String> typesOfEventsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //Arxikopoihsi ths ActioBar bilio8ikh
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle(R.string.new_event);
        ActionBarInit actionBarInit = new ActionBarInit(mActionBar, getBaseContext());
        actionBarInit.initActionBar();
        //

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
        /*
        //Symmetexontes Aytomath symplhrwsh
        // Get a reference to the AutoCompleteTextView in the layout
        autoCompleteTextView = findViewById(R.id.autocomplete_contacts);

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts.getContactArrayList());
        autoCompleteTextView.setAdapter(adapter);
        */



        //Spinner for contacts
        /*
        Trexw thn kainoyrgia me8odo pou dimiourgei h biblio8iki PermissionDispatcher
        Anti na treksw thn loadContacts(); otan 8elw na treksei me dikaiwmata!!!!

        */
        calendarDatabase = Room.databaseBuilder(getApplicationContext(),ICalendarDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();

        EventActivityPermissionsDispatcher.loadContactsWithPermissionCheck(this);//arxikopoihsh tou Contacts
        calendarDatabase.contactDao().insertMulti(contacts.getContactList());

        List<Contact> contactsInArrayList = calendarDatabase.contactDao().getAllContacts();
        dropdownContacts = (MaterialSpinner) findViewById(R.id.spinner_for_contacts);

        ArrayAdapter<Contact> adapter1 = new ArrayAdapter<Contact>(this, android.R.layout.simple_spinner_dropdown_item,contactsInArrayList );
        dropdownContacts.setAdapter(adapter1);



        //Values of periods
        String[] timesToAlert = getResources().getStringArray(R.array.periods_to_alert);

        //Values for types
        //String[] typesOfEvent = getResources().getStringArray(R.array.type_of_event);


        //Spinner for Type Of Event
        typesOfEventsString = new ArrayList<>();
        List<TypeOfEvent> typeOfEventsList = calendarDatabase.typeOfEventDao().getAllTypeOfEvents();
        for(int i=0;i<typeOfEventsList.size();i++){
            typesOfEventsString.add(typeOfEventsList.get(i).getTitle());
            Toast.makeText(getBaseContext(),typesOfEventsString.get(i),Toast.LENGTH_SHORT).show();

        }
        dropdownEventTypes = (MaterialSpinner) findViewById(R.id.spinner_for_typeEvent);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typesOfEventsString);
        dropdownEventTypes.setAdapter(adapter);



        //Spinner to alert the USER
        dropdownToAlertUser =  (MaterialSpinner) findViewById(R.id.time_to_alert_user);
        ArrayAdapter<String> adapterAlertUser = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,timesToAlert);
        dropdownToAlertUser.setAdapter(adapterAlertUser);

        //Spinner to alert the Contact
        dropdownTimeToAlertContact =(MaterialSpinner) findViewById(R.id.time_to_alert_contact);
        ArrayAdapter<String> adapterAlertContact = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,timesToAlert);
        dropdownTimeToAlertContact.setAdapter(adapterAlertContact);


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
                        dateButton.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
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
                                                                    timeButton.setText(hourOfDay + ":" + minute);
                                                                }
                                                            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true
        );
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
        dpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        EventActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
    //H me8odos poy 8elw na exei dikaiwmata
    @NeedsPermission({Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS})
    public void loadContacts(){
        contacts = new Contacts(getBaseContext());
    }

    @OnShowRationale({Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS})
    void showRationaleForContacts(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.contact_permission_message)
                .setPositiveButton(R.string.button_allow, (dialog, button) -> request.proceed())//proxwraei kai ektelei thn me8odo pou
                .setNegativeButton(R.string.button_deny, (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void onContactsDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        Toast.makeText(this, R.string.permission_contacts_denied, Toast.LENGTH_SHORT).show();
    }

    /*
    @OnNeverAskAgain(Manifest.permission.READ_CONTACTS)
    protected void showNeverAskForCamera() {
        Toast.makeText(this, "OnNeverAskAgain for camera", Toast.LENGTH_SHORT).show();
    }
     */




}
