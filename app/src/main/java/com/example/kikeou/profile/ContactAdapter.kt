package com.example.kikeou.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kikeou.R
import com.example.kikeou.coworkers.CoworkerAdapter
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import java.util.*

class ContactAdapter : ListAdapter<Contact, ContactAdapter.MyContactViewHolder>(ContactComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContactViewHolder {
        return MyContactViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MyContactViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.value)
    }


    class MyContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val contactInfo : TextView = itemView.findViewById(R.id.info)

        fun bind(text: String?){
            contactInfo.text = text
        }

        companion object {
            fun create(parent: ViewGroup): MyContactViewHolder {
                val view : View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.profile_item_view, parent, false)
                return MyContactViewHolder(view)
            }
        }
    }

    class ContactComparator : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.value == newItem.value
        }
    }

}

