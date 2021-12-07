package com.example.kikeou.coworkers

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kikeou.R
import com.example.kikeou.room.models.Agenda

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

class CoworkerAdapter : RecyclerView.Adapter<TextItemViewHolder>()  {

    var data =  listOf<Agenda>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.name
    }

    override fun getItemCount() = data.size

}