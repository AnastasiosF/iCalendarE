package com.example.tasos.icalendare;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tasos.icalendare.database.Contacts;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class ViewEventActivity extends AppCompatActivity {

    String title;
    String type;
    String startDate;
    String endDate;
    String timeToAlert;
    String repeat;
    String notes;
    String Participant;
    String timeBefSendMes;

    Button dateButton;
    Button timeButton;
    HashMap eventData;
    MaterialEditText editTextMail;
    MaterialAutoCompleteTextView autoCompleteTextView;
    Spinner dropdownContacts;
    Spinner dropdownToAlertUser;
    Spinner dropdownTimeToAlertContact;
    Contacts contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Title of Event");
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_event_white_24dp);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_edit_grey_24dp);
        fab.setBackgroundColor(getResources().getColor(R.color.fab_background));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


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


        ViewEventActivityPermissionsDispatcher.loadContactsWithPermissionCheck(this);

        //Values of periods
        String[] timesToAlert = getResources().getStringArray(R.array.periods_to_alert);

        //Values for types
        String[] typesOfEvent = getResources().getStringArray(R.array.type_of_event);


        //Spinner for Type Of Event
        dropdownContacts = (MaterialSpinner) findViewById(R.id.spinner_for_typeEvent);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typesOfEvent);
        dropdownContacts.setAdapter(adapter1);



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
        ViewEventActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
    //H me8odos poy 8elw na exei dikaiwmata
    @NeedsPermission({Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS})
    public void loadContacts(){
        contacts = new Contacts(getBaseContext());
        dropdownContacts = (MaterialSpinner) findViewById(R.id.spinner_for_contacts);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, contacts.getContactArrayList());
        dropdownContacts.setAdapter(adapter1);
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

}
