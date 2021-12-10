package com.example.kikeou

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.kikeou.coworkers.CoworkerAdapter
import com.example.kikeou.databinding.FragmentCoworkersBinding
import com.example.kikeou.databinding.FragmentProfileBinding
import com.example.kikeou.profile.ContactAdapter
import com.example.kikeou.profile.LocalisationAdapter
import com.example.kikeou.room.AppDatabase
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment:Fragment(R.layout.fragment_profile) {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var agenda : Agenda = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "test").allowMainThreadQueries().build().agendaDao().getMyAgenda()

        binding.nameZone.setText(agenda.name)
        binding.weekZone.setText(agenda.week.toString())
        binding.photoZone.setText(agenda.photo)

        val contactAdapter = ContactAdapter()
        binding.contactsList.adapter = contactAdapter
        contactAdapter.data = agenda.contact

        val locAdapter = LocalisationAdapter()
        binding.localisationsList.adapter = locAdapter
        locAdapter.data = agenda.loc

        binding.addContactButton.setOnClickListener {
            val intent = Intent(activity, AddContactActivity::class.java)
            startActivity(intent)
        }

        binding.addLocButton.setOnClickListener {
            val intent = Intent(activity, AddLocalisationActivity::class.java)
            startActivity(intent)
        }

        binding.validateButton.setOnClickListener {
            agenda.name = binding.nameZone.text.toString()
            agenda.week = binding.weekZone.text.toString().toInt()
            agenda.photo = binding.photoZone.text.toString()
            Room.databaseBuilder(requireContext(), AppDatabase::class.java, "test").allowMainThreadQueries().build().agendaDao().update(agenda)
            Toast.makeText(activity, "Vos données ont été enregistrées", Toast.LENGTH_SHORT).show()
        }
    }

}