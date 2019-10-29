package com.example.tasos.icalendare


import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.CalendarView
import android.widget.Toast
import com.example.tasos.icalendare.Database.Contact
import com.example.tasos.icalendare.Database.Events
import com.example.tasos.icalendare.Database.ICalendarDatabase
import com.example.tasos.icalendare.Providers.ContactsProvider
import com.github.tibolte.agendacalendarview.AgendaCalendarView
import com.github.tibolte.agendacalendarview.CalendarManager
import permissions.dispatcher.*
import java.util.*
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent
import com.github.tibolte.agendacalendarview.models.WeekItem
import com.github.tibolte.agendacalendarview.models.DayItem
import android.R.attr.maxDate
import android.R.attr.minDate
import com.github.tibolte.agendacalendarview.CalendarPickerController
import com.github.tibolte.agendacalendarview.models.CalendarEvent
import androidx.core.content.ContextCompat
import android.util.Log
import com.example.tasos.icalendare.Calendar.DrawableCalendarEvent
import com.example.tasos.icalendare.Calendar.DrawableEventRenderer
import com.example.tasos.icalendare.Calendar.EventsToDrawableCalendarEventADAPTER
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import android.R.attr.maxDate
import android.R.attr.minDate
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import androidx.appcompat.app.ActionBar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.example.tasos.icalendare.NewEvent.AddEventActivity
import com.example.tasos.icalendare.ViewEvent.ViewEventActivity
import com.nightonke.boommenu.Animation.BoomEnum
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomButtons.HamButton
import com.nightonke.boommenu.BoomButtons.OnBMClickListener
import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.ButtonEnum
import com.nightonke.boommenu.Piece.PiecePlaceEnum
import com.nightonke.boommenu.Util.setTextColor
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem


@RuntimePermissions
class MainActivity : AppCompatActivity(),CalendarPickerController {


    private var contactsList: List<Contact>? = null


    private val LOG_TAG = MainActivity::class.java.simpleName


    val mActionBar:ActionBar? = null

    var agendaCalendarView: AgendaCalendarView? = null

    var powerMenu:PowerMenu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Arxikopoihsi ths ActioBar bilio8ikh
        val mActionBar = supportActionBar
        val actionBarInit = ActionBarInit(mActionBar, baseContext)
        actionBarInit.initActionBar()

        //Arxikopoish Bashs
        initDatabaseWithPermissionCheck()

        //Arxikopoihsh Calendar
        initCalendar()

        //Arxikopoihsh PopUpMenu
        initPopUpMenu()

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
            ICalendarDatabase.getInstance(this@MainActivity)
                    .contactDao()
                    .upsert(i)
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







    override fun onDaySelected(dayItem: DayItem?) {
        Log.d(LOG_TAG, String.format("Selected day: %s", dayItem))

    }

    override fun onScrollToDate(calendar: Calendar?) {
        if (mActionBar != null) {
            //getSupportActionBar()?.setTitle(calendar?.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
            mActionBar.title = calendar?.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        }
    }

    override fun onEventSelected(event: CalendarEvent?) {
        Log.d(LOG_TAG, String.format("Selected event: %s", event))

        powerMenu =  PowerMenu.Builder(applicationContext)
                .addItem(PowerMenuItem(getString(R.string.add_event),false))
                .addItem( PowerMenuItem(getString(R.string.view_edit), false))
                .addItem( PowerMenuItem(getString(R.string.delete), false))
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setWidth(1000)
                .setTextColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                .setSelectedTextColor(Color.WHITE)
                .setMenuColor(Color.WHITE)
                .setSelectedMenuColor(ContextCompat.getColor(applicationContext,R.color.colorPrimary))
                .setOnMenuItemClickListener(OnMenuItemClickListener { position, item ->
                   if (position ==0){
                       val intent:Intent = Intent(applicationContext,AddEventActivity::class.java)
                       intent.putExtra("EVENT_START_TIME",event?.startTime)
                       intent.putExtra("EVENT_END_TIME",event?.endTime)
                       intent.putExtra("EVENT_DATE",event?.instanceDay)
                       intent.putExtra("EVENT_DAY_REFERENCE",event?.dayReference)
                       powerMenu!!.dismiss()
                       startActivity(intent)
                   }else if (position == 1){
                        //Προβολη/Εξεργασία event
                        Log.d(LOG_TAG, String.format("Event id: %d ", event?.id));
                        Log.d(LOG_TAG, String.format("Event id: %s ", event?.title));
                        //ViewEventActivity starts
                        val intent:Intent = Intent(applicationContext,ViewEventActivity::class.java)
                        intent.putExtra("EVENT_ID",event?.id)
                        powerMenu!!.dismiss()
                        startActivity(intent)

                    }else if(position == 2) {
                        // Διαγραφή event
                        var events: List<Events> = ICalendarDatabase.getInstance(applicationContext).eventsDao().getEvent(event!!.id)
                        ICalendarDatabase.getInstance(applicationContext).eventsDao().delete(events.get(0))
                        initCalendar()
                        powerMenu!!.dismiss()

                    }
                })
                .build()
        powerMenu!!.showAtCenter(findViewById(R.id.agenda_calendar_view))
    }

    private fun initCalendar() {
        val events = ArrayList<Events>()

        agendaCalendarView = findViewById(R.id.agenda_calendar_view)

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        val minDate = Calendar.getInstance()
        val maxDate = Calendar.getInstance()

        minDate.add(Calendar.MONTH, -2)
        minDate.set(Calendar.DAY_OF_MONTH, 1)
        maxDate.add(Calendar.MONDAY, 2)

        // Sync way

        //agendaCalendarView!!.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        //agendaCalendarView!!.addEventRenderer( DrawableEventRenderer());

        //Async way
        Handler().postDelayed(Runnable {
            var eventsDatabase:List<Events>
            eventsDatabase = ICalendarDatabase.getInstance(applicationContext).eventsDao().allEvents

            var eventList:List<DrawableCalendarEvent>
            eventList = ArrayList()

            for (i in eventsDatabase){
                eventList.add(EventsToDrawableCalendarEventADAPTER.adaptToDrawableCalendarEvent(i,applicationContext))
            }
            agendaCalendarView!!.init(eventList, minDate, maxDate, Locale.getDefault(), this)
            agendaCalendarView!!.addEventRenderer( DrawableEventRenderer())

            val calendarManager = CalendarManager.getInstance(applicationContext)
            calendarManager.buildCal(minDate, maxDate, Locale.getDefault())
            calendarManager.loadEvents(eventList)

        }, 100)




    }

    private fun initPopUpMenu() {


    }




}