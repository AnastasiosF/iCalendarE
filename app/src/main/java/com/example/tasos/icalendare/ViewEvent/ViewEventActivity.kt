package com.example.tasos.icalendare.ViewEvent

import android.Manifest
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

import com.example.tasos.icalendare.Database.Contact
import com.example.tasos.icalendare.Providers.ContactsProvider
import com.example.tasos.icalendare.R
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView
import com.rengwuxian.materialedittext.MaterialEditText
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog

import java.util.ArrayList
import java.util.Calendar
import java.util.HashMap

import fr.ganfra.materialspinner.MaterialSpinner
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions

//TODO: ΝΑ ΦΤΙΑΞΩ ΤΑ SPINNER
@RuntimePermissions
class ViewEventActivity : AppCompatActivity() {

    internal var title: String? = null
    internal var type: String? = null
    internal var startDate: String? = null
    internal var endDate: String? = null
    internal var timeToAlert: String? = null
    internal var repeat: String? = null
    internal var notes: String? = null
    internal var Participant: String? = null
    internal var timeBefSendMes: String? = null
    internal lateinit var eventData: HashMap<String, Int>//Ta data tou event

    internal lateinit var contactsList: List<Contact>
    internal var contactsInArrayList = ArrayList<String>()

    internal lateinit var dateButton: Button
    internal lateinit var timeButton: Button

    internal lateinit var editTextMail: MaterialEditText
    internal lateinit var autoCompleteTextView: MaterialAutoCompleteTextView
    internal lateinit var dropdownContacts: Spinner
    internal lateinit var dropdownToAlertUser: Spinner
    internal lateinit var dropdownTimeToAlertContact: Spinner
    internal lateinit var contactsProvider: ContactsProvider

    //Dhlwnetai edw gia na mporesei na xrismopoih8ei se olh thn klassi
    var EVENT_ID:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_event)

        //To PopUp->View/Edit stelnei to id toy event
        //Arxikopoiw to id mesa sthn EVENTS_ID gia na mporesw na to xrisimopoihsw otan kalw
        //to event apo thn BashDedomenwn
        EVENT_ID = intent.getLongExtra("EVENT_ID",0)
        Log.d("ID-ID", String.format("Event id: %d ", EVENT_ID));


        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "Title of Event"
        setSupportActionBar(toolbar)
        toolbar.setLogo(R.drawable.ic_event_white_24dp)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setImageResource(R.drawable.ic_edit_grey_24dp)
        fab.setBackgroundColor(resources.getColor(R.color.fab_background))
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        dateButton = findViewById(R.id.date_button)
        dateButton.setOnClickListener { emfanisiEpilogisHmerom() }

        timeButton = findViewById(R.id.time_button)
        timeButton.setOnClickListener { emfanisiEpilogisWras() }


        loadContacts()

        //Values of periods
        val timesToAlert = resources.getStringArray(R.array.periods_to_alert)

        //Values for types
        val typesOfEvent = resources.getStringArray(R.array.type_of_event)


        //Spinner for Type Of Event
        /*
        dropdownContacts = (MaterialSpinner) findViewById(R.id.spinner_for_typeEvent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                contactsList = ICalendarDatabase.getInstance(getApplicationContext())
                        .contactDao()
                        .getAllContacts();
                for (int i = 0; i < contactsList.size(); i++) {
                    contactsInArrayList.add(contactsList.get(i).getName() + " " + contactsList.get(i).getPhone());
                }
                ArrayAdapter<String> adapter1 =
                        new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,contactsInArrayList);
                dropdownContacts.setAdapter(adapter1);
            }
        }).start();
        */


        //Spinner to alert the USER
        dropdownToAlertUser = findViewById<View>(R.id.time_to_alert_user) as MaterialSpinner
        val adapterAlertUser = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, timesToAlert)
        dropdownToAlertUser.adapter = adapterAlertUser

        //Spinner to alert the Contact
        dropdownTimeToAlertContact = findViewById<View>(R.id.time_to_alert_contact) as MaterialSpinner
        val adapterAlertContact = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, timesToAlert)
        dropdownTimeToAlertContact.adapter = adapterAlertContact


    }

    fun emfanisiEpilogisHmerom() {

        val now = Calendar.getInstance()

        val dpd = DatePickerDialog.newInstance(
                { view, year, monthOfYear, dayOfMonth ->
                    eventData!!.set("year",year)
                    eventData!!.set("month", monthOfYear)
                    eventData!!.set("day",dayOfMonth)
                    dateButton.text = dayOfMonth.toString() + "/" + monthOfYear + "/" + year
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        )

        dpd.accentColor = resources.getColor(R.color.colorPrimary)

        dpd.show(fragmentManager, "Datepickerdialog")
    }

    fun emfanisiEpilogisWras() {
        val now = Calendar.getInstance()
        val dpd = TimePickerDialog.newInstance({ view, hourOfDay, minute, second ->
            val time = "You picked the following time: " + hourOfDay + "h" + minute + "m" + second
            eventData!!["hour"] = hourOfDay
            eventData!!["minute"] = minute
            timeButton.text = hourOfDay.toString() + ":" + minute
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true
        )
        dpd.accentColor = resources.getColor(R.color.colorPrimary)
        dpd.show(fragmentManager, "Timepickerdialog")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //H me8odos poy 8elw na exei dikaiwmata
    @NeedsPermission(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    fun loadContacts() {

        contactsProvider = ContactsProvider(baseContext)

        dropdownContacts = findViewById<View>(R.id.spinner_for_contacts) as MaterialSpinner

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, contactsProvider.contactArrayList)

        dropdownContacts.adapter = adapter1
    }

    @OnShowRationale(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    internal fun showRationaleForContacts(request: PermissionRequest) {

        AlertDialog.Builder(this)
                .setMessage(R.string.contact_permission_message)
                .setPositiveButton(R.string.button_allow) { dialog, button -> request.proceed() }//proxwraei kai ektelei thn me8odo pou
                .setNegativeButton(R.string.button_deny) { dialog, button -> request.cancel() }
                .show()
    }

    @OnPermissionDenied(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    internal fun onContactsDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        Toast.makeText(this, R.string.permission_contacts_denied, Toast.LENGTH_SHORT).show()
    }

}


