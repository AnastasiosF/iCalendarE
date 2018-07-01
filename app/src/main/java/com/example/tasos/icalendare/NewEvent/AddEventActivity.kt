package com.example.tasos.icalendare.NewEvent

import android.Manifest
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

import com.example.tasos.icalendare.ActionBarInit
import com.example.tasos.icalendare.Database.*
import com.example.tasos.icalendare.Providers.ContactsProvider
import com.example.tasos.icalendare.R
import com.rengwuxian.materialedittext.MaterialEditText
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog

import fr.ganfra.materialspinner.MaterialSpinner
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions
import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList

/*
 * Permission Library : https://github.com/permissions-dispatcher/PermissionsDispatcher
 *
 * */
@RuntimePermissions
class AddEventActivity : AppCompatActivity() {


    internal  lateinit var dateButton: Button
    internal lateinit var timeButton: Button
    internal lateinit var addEventButton: Button

    //internal lateinit var eventData: HashMap<*, *>
    internal  var editTextMail: MaterialEditText? = null
    internal lateinit var editTextTitle: MaterialEditText
    internal lateinit var editTextNotes: MaterialEditText

    internal lateinit var dropdownContacts: Spinner
    internal lateinit var dropdownEventTypes: Spinner
    internal lateinit var dropdownToAlertUser: Spinner
    internal lateinit var dropdownTimeToAlertContact: Spinner
    internal lateinit var contactsProvider: ContactsProvider

    internal var contactsList: List<Contact>? = null
    internal lateinit var typeOfEventList: List<TypeOfEvent>
    internal var contactsInArrayList = ArrayList<String>()
    internal var typeOfEventInArrayList = ArrayList<String>()


    //Data For Date/Time Picker
    var year:Int = 0
    var month:Int = 0
    var day:Int = 0
    var hour:Int = 0
    var minute:Int = 0
    var second:Int = 0


    //Data From Forms
    var title:String = ""
    var description:String = ""
    var contactName_ID:String = ""
    var typeOfEvent_ID:Int = 0
    var timeToAlertUser:String =""
    var timeToAlertParticipant = ""
    var allDay:Int = 0
    var duration = null
    var color:Int =0
    lateinit var startDate:Calendar
    lateinit var endDate:Calendar


    //TO KAINOURGIO EVENT POU 8A MPEI STHN BASH!!!!
    lateinit var newEvent: Events

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        //Arxikopoihsi ths ActioBar bilio8ikh
        val mActionBar = supportActionBar
        mActionBar!!.setTitle(R.string.new_event)
        val actionBarInit = ActionBarInit(mActionBar, baseContext)
        actionBarInit.initActionBar()

        newEvent = Events("",
                "" ,
                0,
                "",
                null,
                null,
                null,
                null,
                0,
                ""
                ,""
                ,0
                )

        startDate = Calendar.getInstance()
        endDate = Calendar.getInstance()


        dateButton = findViewById(R.id.date_button)
        dateButton.setOnClickListener {
            //MEsa edw ginetai kai h katxwrisi sto antikeimeno "newEvent" to opoio
            //xrisimopoieitai gia na mpei sthn bash to kainourgio event
            emfanisiEpilogisHmerom()
        }

        timeButton = findViewById(R.id.time_button)
        timeButton.setOnClickListener {
            //MEsa edw ginetai kai h katxwrisi sto antikeimeno "newEvent" to opoio
            //xrisimopoieitai gia na mpei sthn bash to kainourgio event
            emfanisiEpilogisWras()
        }
        /*
        //Symmetexontes Aytomath symplhrwsh
        // Get a reference to the AutoCompleteTextView in the layout
        autoCompleteTextView = findViewById(R.id.autocomplete_contacts);

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts.getContactArrayList());
        autoCompleteTextView.setAdapter(adapter);
        */


        /*
        Trexw thn kainoyrgia me8odo pou dimiourgei h biblio8iki PermissionDispatcher
        Anti na treksw thn loadContacts(); otan 8elw na treksei me dikaiwmata!!!!
        */
        //Ftiaxnw ayta ta spinner mesa se Thread giati epikoinwnoun me thn bash
        //arxikopoihsh tou ContactProvider
        loadContacts()

        Thread(DimiourgiaSpinner()).start()

        editTextTitle = findViewById(R.id.event_title)

        editTextNotes = findViewById(R.id.event_notes)


        //Values of periods
        val timesToAlert = resources.getStringArray(R.array.periods_to_alert)

        //Spinner to alert the USER
        dropdownToAlertUser = findViewById<View>(R.id.time_to_alert_user) as MaterialSpinner

        val adapterAlertUser = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, timesToAlert)

        dropdownToAlertUser.adapter = adapterAlertUser

        dropdownToAlertUser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                timeToAlertUser = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        //Spinner to alert the Contact
        dropdownTimeToAlertContact = findViewById<View>(R.id.time_to_alert_contact) as MaterialSpinner
        val adapterAlertContact = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, timesToAlert)
        dropdownTimeToAlertContact.adapter = adapterAlertContact
        dropdownTimeToAlertContact.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                timeToAlertParticipant = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        addEventButton = findViewById(R.id.add_event_button)
        addEventButton.setOnClickListener {
            //Dimiourgv thread gia na exw prosbash sthn bash dedomenwn
            Thread(Runnable {


                startDate = Calendar.getInstance()
                startDate.set(year,month,day,hour,minute,second)

                endDate = Calendar.getInstance()
                endDate.set(year,month,day,hour+1,minute,second)

                newEvent = Events(
                        editTextTitle.text.toString(),
                        dropdownContacts.selectedItem.toString(),
                        typeOfEvent_ID,
                        dropdownToAlertUser.selectedItem.toString(),
                        dropdownTimeToAlertContact.selectedItem.toString(),
                        CalendarTypeConverter.toLong(startDate),
                        CalendarTypeConverter.toLong(endDate),
                        "Athens",
                        0,
                        "",
                        editTextNotes.text.toString())
/*
                newEvent.id = -1
                newEvent.title = editTextTitle.text.toString()
                newEvent.description = editTextNotes.text.toString()
                newEvent.contactName_ID = dropdownContacts.selectedItem.toString()
                newEvent.dateStart = startDate
                newEvent.dateEnd = endDate
                newEvent.timeToAlerUser = dropdownToAlertUser.selectedItem.toString()
                newEvent.timeToAlertParticipant = dropdownTimeToAlertContact.selectedItem.toString()
                newEvent.allDay = 0
                newEvent.color = 0

*/


                Log.d("TESt DATA: ", "EVENTS ID:" + newEvent.id.toString())
                Log.d("TESt DATA: ", "CONTACT ID:" + newEvent.contactName_ID.toString())
                Log.d("TESt DATA: ", "TYPE OF EVENT ID:" + newEvent.typeOfEvent_ID.toString())
                Log.d("TESt DATA: ", "TITLE:" + newEvent.title.toString())
                Log.d("TESt DATA: ", "START_DATE:" + newEvent.dateStart.toString())
                Log.d("TESt DATA: ", "END_DATE:" + newEvent.dateEnd.toString())
                Log.d("TESt DATA: ", "ALLDAY:" + newEvent.allDay.toString())

                //Log.d("TESt DATA: ", "TIME:" + newEvent.time.toString())
                Log.d("TESt DATA: ", "NOTES:" + newEvent.description.toString())
                Log.d("TESt DATA: ", "TIME TO ALERT CONTACT:" + newEvent.timeToAlertParticipant.toString())
                Log.d("TESt DATA: ", "TIME TO ALERT USER:" + newEvent.timeToAlerUser.toString())
                /*ICalendarDatabase.getInstance(getApplicationContext())
                                .contactDao().delete();
*/

                ICalendarDatabase.getInstance(applicationContext).eventsDao().insert(newEvent)

            }).start()
        }


    }

    fun emfanisiEpilogisHmerom() {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
                { view, year, monthOfYear, dayOfMonth ->
                    dateButton.text = dayOfMonth.toString() + "/" + monthOfYear + "/" + year
                    this.year = year
                    this.month = monthOfYear
                    this.day = dayOfMonth
                    //startDate.set(year,monthOfYear,dayOfMonth)
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
            timeButton.text = hourOfDay.toString() + ":" + minute
            this.hour = hourOfDay
            this.minute = minute
            this.second = second

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
        contactsProvider = ContactsProvider(applicationContext)
    }

    @OnShowRationale(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    fun showRationaleForContacts(request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setMessage(R.string.contact_permission_message)
                .setPositiveButton(R.string.button_allow) { dialog, button -> request.proceed() }//proxwraei kai ektelei thn me8odo pou
                .setNegativeButton(R.string.button_deny) { dialog, button -> request.cancel() }
                .show()
    }

    @OnPermissionDenied(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    fun onContactsDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        Toast.makeText(this, R.string.permission_contacts_denied, Toast.LENGTH_SHORT).show()
    }

    /*
    @OnNeverAskAgain(Manifest.permission.READ_CONTACTS)
    protected void showNeverAskForCamera() {
        Toast.makeText(this, "OnNeverAskAgain for camera", Toast.LENGTH_SHORT).show();
    }
     */

    private inner class DimiourgiaSpinner : Runnable {

        override fun run() {
            //Spinner for Contacts

            contactsList = ICalendarDatabase.getInstance(applicationContext)
                    .contactDao()
                    .allContacts

            if (contactsList == null) {

                Toast.makeText(this@AddEventActivity, "Cant find contacts!", Toast.LENGTH_LONG)

            }

            for (i in contactsList!!.indices) {
                contactsInArrayList.add(contactsList!![i].name + " "
                        + contactsList!![i].phone)
            }


            dropdownContacts = findViewById<View>(R.id.spinner_for_contacts) as MaterialSpinner

            val adapter1 = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, contactsInArrayList)

            dropdownContacts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }


            //Spinner for Type Of Event
            typeOfEventList = ICalendarDatabase.getInstance(applicationContext).typeOfEventDao().allTypeOfEvents



            for (i in typeOfEventList.indices) {

                typeOfEventInArrayList.add(typeOfEventList[i].title)
                //Toast.makeText(getBaseContext(), typesOfEventsString.get(i), Toast.LENGTH_SHORT).show();

            }

            dropdownEventTypes = findViewById<View>(R.id.spinner_for_typeEvent) as MaterialSpinner

            val adapter = ArrayAdapter(this@AddEventActivity, android.R.layout.simple_spinner_dropdown_item, typeOfEventInArrayList)

            dropdownEventTypes.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    var typeOfEventsIDs: ArrayList<Int> = ArrayList()
                    if(typeOfEventList.isNotEmpty() && position >= 0){
                        for(i in typeOfEventList){
                            typeOfEventsIDs.add(i.uid!!)
                        }
                        typeOfEvent_ID = typeOfEventsIDs[position]
                    }

                }
            }

            //BAzw tous adaptores na treksoun mesa sto uiThread giati petaei exception
            this@AddEventActivity.runOnUiThread {
                dropdownContacts.adapter = adapter1
                dropdownEventTypes.adapter = adapter
            }


        }
    }
}
