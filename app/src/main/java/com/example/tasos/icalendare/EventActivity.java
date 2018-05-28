package com.example.tasos.icalendare;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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


    WeekViewEvent event;
    Button dateButton;
    Button timeButton;
    HashMap eventData;
    MaterialEditText editTextMail;
    MaterialAutoCompleteTextView autoCompleteTextView;
    Spinner dropdown;
    Spinner dropdownToAlertUser;
    Spinner dropdownTimeToAlertContact;
    Contacts contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //Arxikopoihsi ths ActioBar bilio8ikh
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle("New Event");
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
        EventActivityPermissionsDispatcher.loadContactsWithPermissionCheck(this);
        if(contacts!=null){
            dropdown = (MaterialSpinner) findViewById(R.id.spinner_for_contacts);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, contacts.getContactArrayList());
            dropdown.setAdapter(adapter1);
        }

        //Values of periods
        String[] timesToAlert = getResources().getStringArray(R.array.periods_to_alert);



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
