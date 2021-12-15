package com.example.kikeou.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kikeou.R
import com.example.kikeou.coworkers.CoworkerAdapter
import com.example.kikeou.room.models.Contact
import java.util.*

class ContactAdapter2 : RecyclerView.Adapter<ContactAdapter2.ViewHolder>() {

    var data =  listOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter2.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.profile_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.info.text = "${item.key} - ${item.value}"
        holder.delete.setOnClickListener { deleteItem(position) }
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val info: TextView = itemView.findViewById(R.id.info)
        val delete : ImageView = itemView.findViewById(R.id.icon_delete)
    }

    fun deleteItem(position: Int){
        val contacts : MutableList<Contact> = mutableListOf()
        var newContactAlreadyHere : Boolean = false
        data.forEach(){
                contacts.add(it)
        }
        contacts.removeAt(position)
        data = Collections.unmodifiableList(contacts)
        notifyDataSetChanged()
    }

}