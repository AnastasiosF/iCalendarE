package com.example.tasos.icalendare


import android.Manifest
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.example.tasos.icalendare.Database.Contact
import com.example.tasos.icalendare.Database.ICalendarDatabase
import com.example.tasos.icalendare.Providers.ContactsProvider
import permissions.dispatcher.*
import java.util.*

//TODO: ΝΑ ΒΑΖΩ ΤΙΣ ΕΠΑΦΕΣ ΣΤΗΝ ΒΑΣΗ ΜΟΛΙΣ ΞΕΚΙΝΑΕΙ Η ΕΦΑΡΜΟΓΗ
@RuntimePermissions
class MainActivity : AppCompatActivity() {

    internal var contactsList: List<Contact>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Arxikopoihsi ths ActioBar bilio8ikh
        val mActionBar = supportActionBar
        val actionBarInit = ActionBarInit(mActionBar, baseContext)
        actionBarInit.initActionBar()

        //Arxikopoish Bashs
        initDatabase()

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode,permissions,grantResults)
    }

    //H me8odos poy 8elw na exei dikaiwmata
    @NeedsPermission(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    fun initDatabase() {

        //Pairnw tis epafes apo ton provider tou kinhtou
        val contactsProvider = ContactsProvider(applicationContext)


        contactsList = contactsProvider.getContactList()
        for (i in contactsList!!){
            ICalendarDatabase.getInstance(this@MainActivity).contactDao().upsert(i)
        }


        //ICalendarDatabase.getInstance(MainActivity.this).contactDao().upsertMulti(contactsList);
        contactsList = ICalendarDatabase.getInstance(applicationContext)
                .contactDao()
                .allContacts

        if (contactsList == null) {

            Toast.makeText(this@MainActivity, "Cant find contacts!", Toast.LENGTH_LONG)
        }


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


    fun initCalendar() {
        val events = ArrayList<EventDay>()

        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -5)

        events.add(EventDay(calendar, R.drawable.ic_event_black_24dp))

        events.add(EventDay(calendar, R.drawable.ic_event_black_24dp))

        val calendar1 = Calendar.getInstance()
        calendar1.add(Calendar.DAY_OF_MONTH, 0)
        events.add(EventDay(calendar1, R.drawable.ic_event_black_24dp))

        val calendar2 = Calendar.getInstance()
        calendar2.add(Calendar.DAY_OF_MONTH, 5)
        events.add(EventDay(calendar2, R.drawable.ic_event_black_24dp))

        val min = Calendar.getInstance()
        min.add(Calendar.MONTH, -2)

        val max = Calendar.getInstance()
        max.add(Calendar.MONTH, 2)

        calendarView.setMinimumDate(min)
        calendarView.setMaximumDate(max)

        calendarView.setEvents(events)
    }


}