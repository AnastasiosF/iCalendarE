package com.example.tasos.icalendare.Settings

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.tasos.icalendare.Database.TypeOfEvent
import com.example.tasos.icalendare.R

class MyAdapterRecycleView(private val values: List<TypeOfEvent>) : RecyclerView.Adapter<MyAdapterRecycleView.ViewHolder>() {

    /*
    fun add(position: Int, item: TypeOfEvent) {
        values.add(position, item)
        notifyItemInserted(position)
    }

    fun remove(position: Int) {
        values.removeAt(position)
        notifyItemRemoved(position)
    }
    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterRecycleView.ViewHolder {

        val inflater = LayoutInflater.from(
                parent.context)
        val v = inflater.inflate(R.layout.type_list_row, parent, false)
        // set the view's size, margins, paddings and layout parameters
        v.setPadding(15, 15, 15, 15)
        v.minimumHeight = 25
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: MyAdapterRecycleView.ViewHolder, position: Int) {

        val typeOfEvent = values[position]


        holder.txtTitle.text = typeOfEvent.title
        holder.txtCost.text = typeOfEvent.price.toString()
        holder.txtDuration.text = typeOfEvent.duration.toString()

        holder.txtCost.setOnClickListener {
            //holder.txtDuration.setText("Duration");
        }
        holder.layout.setOnClickListener { holder.txtTitle.text = "Pressed!!" }

    }

    override fun getItemCount(): Int {
        return values.size

    }

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        // each data item is just a string in this case
        var txtTitle: TextView
        var txtCost: TextView
        var txtDuration: TextView

        init {
            txtTitle = layout.findViewById<View>(R.id.type_title) as TextView
            txtCost = layout.findViewById<View>(R.id.cost) as TextView
            txtDuration = layout.findViewById<View>(R.id.duration) as TextView

        }
    }
}
