package com.example.kikeou

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.kikeou.coworkers.CoworkerAdapter
import com.example.kikeou.databinding.FragmentCoworkersBinding
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoworkersFragment:Fragment(R.layout.fragment_coworkers) {

    private var _binding : FragmentCoworkersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoworkersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loc1 = Localisation(1,"18 mai", "dans ton cul")
        val loc2 = Localisation(2,"8 aout", "dans le cul de youen")
        val contact1 = Contact(1, "tel", "0699328234")
        val contact2 = Contact(2, "email", "wilfried.pepin@outlook")
        val agenda = Agenda(1, "maman" ,50, "une_photo", listOf(contact1, contact2), listOf(loc1, loc2), false)
        val agenda2 = Agenda(2, "papa" ,52, "une_photo", listOf(contact1, contact2), listOf(loc1, loc2), false)

        val agendas = listOf(agenda, agenda2)

        val adapter = CoworkerAdapter()
        binding.coworkersList.adapter = adapter

        adapter.data = agendas

        //agendas

        /*val adapter = SleepNightAdapter()
        binding.sleepList.adapter = adapter
        sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}