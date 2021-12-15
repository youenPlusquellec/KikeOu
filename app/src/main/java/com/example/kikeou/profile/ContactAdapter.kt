package com.example.kikeou.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kikeou.R
import com.example.kikeou.coworkers.CoworkerAdapter
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import java.util.*

class ContactAdapter : ListAdapter<Agenda, ContactAdapter.MyContactViewHolder>() {


    class MyContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val agendaNameZone : EditText = itemView.findViewById(R.id.name_zone)

        fun bind(text: String?){
            agendaNameZone.setText(text)
        }

        companion object {
            fun create(parent: ViewGroup): MyContactViewHolder {
                val view : View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_add_contact, parent, false)
                return MyContactViewHolder(view)
            }
        }
    }

}