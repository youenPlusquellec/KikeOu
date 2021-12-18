package com.example.kikeou.profile

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kikeou.ProfilViewModel
import com.example.kikeou.R
import com.example.kikeou.room.models.Localisation

class LocalisationAdapter : RecyclerView.Adapter<LocalisationAdapter.ViewHolder>() {

    var data =  mutableListOf<Localisation>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var viewModel: ProfilViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.profile_item_view, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        val res = holder.itemView.context.resources
        val day = convertNumericDayToString(item.day, res)

        holder.info.text = "${day} - ${item.value}"
        holder.delete.setOnClickListener { deleteItem(position) }
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
        val delete: ImageView = itemView.findViewById(R.id.icon_delete)
    }

    fun deleteItem(position: Int) {
        data.removeAt(position)
        viewModel?.updateLocList()

        notifyDataSetChanged()
    }
}