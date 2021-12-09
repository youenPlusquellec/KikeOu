package com.example.kikeou.coworkers

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kikeou.R
import com.example.kikeou.room.models.Agenda
import org.w3c.dom.Text

class CoworkerAdapter : RecyclerView.Adapter<CoworkerAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener
    var data =  listOf<Agenda>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface onItemClickListener {

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.coworker_item_view, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.coworkerName.text = item.name
        holder.coworkerContact.text = item.contact[0].value
        //holder.coworkerPicture.text = item.name
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val coworkerPicture: ImageView = itemView.findViewById(R.id.coworker_picture)
        val coworkerName: TextView = itemView.findViewById(R.id.coworker_name)
        val coworkerContact: TextView = itemView.findViewById(R.id.coworker_contact)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}