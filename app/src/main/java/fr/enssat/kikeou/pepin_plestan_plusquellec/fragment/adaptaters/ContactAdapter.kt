package fr.enssat.kikeou.pepin_plestan_plusquellec.fragment.adaptaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModel
import fr.enssat.kikeou.pepin_plestan_plusquellec.R
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    var data =  mutableListOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var isReadOnly = false

    var viewModel: ProfileViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.profile_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.info.text = "${item.key} - ${item.value}"

        if(isReadOnly)
            holder.delete.visibility = View.GONE
        else
            holder.delete.setOnClickListener { deleteItem(position) }
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val info: TextView = itemView.findViewById(R.id.info)
        val delete : ImageView = itemView.findViewById(R.id.icon_delete)
    }

    fun deleteItem(position: Int) {
        data.removeAt(position)
        viewModel?.updateContactList()

        notifyDataSetChanged()
    }
}