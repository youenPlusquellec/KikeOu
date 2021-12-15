package com.example.kikeou.profile

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kikeou.R
import com.example.kikeou.room.models.Localisation

class LocalisationAdapter2 : RecyclerView.Adapter<LocalisationAdapter2.ViewHolder>() {

    var data =  listOf<Localisation>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalisationAdapter2.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.profile_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        val res = holder.itemView.context.resources
        val day = convertNumericDayToString(item.day, res)

        holder.info.text = "${day} - ${item.value}"
    }

    override fun getItemCount() = data.size

    fun convertNumericDayToString(day: Int, resources: Resources): String {
        var dayString = resources.getString(R.string.day5)
        when (day) {
            1 -> dayString = resources.getString(R.string.day1)
            2 -> dayString = resources.getString(R.string.day2)
            3 -> dayString = resources.getString(R.string.day3)
            4 -> dayString = resources.getString(R.string.day4)
        }
        return dayString
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val info: TextView = itemView.findViewById(R.id.info)
    }

}