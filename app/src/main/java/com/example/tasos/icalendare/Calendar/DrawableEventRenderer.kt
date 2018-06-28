package com.example.tasos.icalendare.Calendar

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import com.github.tibolte.agendacalendarview.render.EventRenderer
import com.nightonke.boommenu.Util.setTextColor
import com.nightonke.boommenu.Util.setText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.tasos.icalendare.Database.ICalendarDatabase
import com.example.tasos.icalendare.R


class DrawableEventRenderer : EventRenderer<DrawableCalendarEvent>() {

    // region Class - EventRenderer

    override fun render(view: View, event: DrawableCalendarEvent) {
        val imageView = view.findViewById(R.id.view_agenda_event_image) as ImageView
        val txtTitle = view.findViewById(R.id.view_agenda_event_title) as TextView
        val txtLocation = view.findViewById(R.id.view_agenda_event_location) as TextView
        val descriptionContainer = view.findViewById(R.id.view_agenda_event_description_container) as LinearLayout
        val locationContainer = view.findViewById(R.id.view_agenda_event_location_container) as LinearLayout
        val description = view.findViewById(R.id.view_agenda_event_description) as TextView
        val typeOfEvent = view.findViewById(R.id.view_agenda_event_typeOfEvent) as TextView
        val contact_ID = view.findViewById(R.id.view_agenda_event_contact) as TextView
        descriptionContainer.visibility = View.VISIBLE


        //imageView.setImageDrawable(view.context.getDrawable(event.drawableId))


        txtTitle.setTextColor(ContextCompat.getColor(view.context,android.R.color.black))

        description.setText(event.description)
        typeOfEvent.setText(event.typeOfEventTitle)
        contact_ID.setText(event.contactNameID)


        txtTitle.setText(event.title)
        txtLocation.setText(event.location)
        if(event.location != null){
            if (event.location.length > 0) {
                locationContainer.visibility = View.VISIBLE
                txtLocation.setText(event.getLocation())
            } else {
                locationContainer.visibility = View.GONE
            }
        }


        if (event.getTitle().equals(view.getResources().getString(R.string.agenda_event_no_events))) {
            txtTitle.setTextColor(ContextCompat.getColor(view.context,android.R.color.black))
        } else {
            txtTitle.setTextColor(ContextCompat.getColor(view.context,android.R.color.primary_text_light))
        }
        descriptionContainer.setBackgroundColor(event.getColor())
        txtLocation.setTextColor(ContextCompat.getColor(view.context,android.R.color.primary_text_dark))
    }

    override fun getEventLayout(): Int {
        return R.layout.view_agenda_drawable_event
    }

    override fun getRenderType(): Class<DrawableCalendarEvent> {
        return DrawableCalendarEvent::class.java
    }

    // endregion
}
