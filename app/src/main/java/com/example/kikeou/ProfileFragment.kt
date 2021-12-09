package com.example.kikeou

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.kikeou.coworkers.CoworkerAdapter
import com.example.kikeou.databinding.FragmentCoworkersBinding
import com.example.kikeou.databinding.FragmentProfileBinding
import com.example.kikeou.profile.ContactAdapter
import com.example.kikeou.profile.LocalisationAdapter
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

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

        val loc1 = Localisation(1,1, "dans ton cul")
        val loc2 = Localisation(2,3, "dans le cul de youen")
        val contact1 = Contact(1, "tel", "06.99.32.82.34")
        val contact2 = Contact(2, "email", "wilfried.pepin@outlook")
        val agenda = Agenda(1, "moi zebi" ,50, "une_photo", listOf(contact1, contact2), listOf(loc1, loc2), false)

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
    }

}