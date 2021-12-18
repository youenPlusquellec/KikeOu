package com.example.kikeou

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.example.kikeou.coworkers.CoworkerAdapter
import com.example.kikeou.databinding.FragmentCoworkersBinding
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.google.android.material.bottomnavigation.BottomNavigationView

class CoworkersFragment:Fragment(R.layout.fragment_coworkers) {

    private var _binding : FragmentCoworkersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoworkersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loc1 = Localisation(1,1, "dans ton cul")
        val loc2 = Localisation(2,3, "dans le cul de youen")
        val contact1 = Contact(1, "tel", "06.99.32.82.34")
        val contact2 = Contact(2, "email", "wilfried.pepin@outlook")
        val agenda = Agenda(1, "maman" ,50, "une_photo", mutableListOf(contact1, contact2), mutableListOf(loc1, loc2), false)
        val agenda2 = Agenda(2, "papa" ,52, "une_photo", mutableListOf(contact1, contact2), mutableListOf(loc1, loc2), false)
        val agenda3 = Agenda(3, "moi" ,50, "une_photo", mutableListOf(contact1, contact2), mutableListOf(loc1, loc2), false)
        val agenda4 = Agenda(4, "toi" ,52, "une_photo", mutableListOf(contact1, contact2), mutableListOf(loc1, loc2), false)

        val agendas = listOf(agenda, agenda2, agenda3, agenda4)

        val adapter = CoworkerAdapter()
        binding.coworkersList.adapter = adapter
        adapter.setOnItemClickListener(object : CoworkerAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(context, "You clicked on n°$position", Toast.LENGTH_SHORT).show()
            }

        })
        adapter.data = agendas

    }

}