package com.example.kikeou

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kikeou.databinding.FragmentProfileBinding
import com.example.kikeou.profile.ContactAdapter
import com.example.kikeou.profile.LocalisationAdapter
import com.example.kikeou.room.models.Agenda
import java.util.*

class ProfileFragment:Fragment(R.layout.fragment_profile) {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var app: AppApplication

    private val profilViewModel: ProfilViewModel by viewModels {
        ProfilViewModelFactory((requireActivity().application as AppApplication).agendaRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        app = (requireActivity().application as AppApplication)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilViewModel.agenda.observe(this, { agenda ->
            if(agenda != null)
            {
                binding.nameZone.setText(agenda.name)
                binding.weekZone.setText(agenda.week.toString())
                binding.photoZone.setText(agenda.photo)

                val contactAdapter = ContactAdapter()
                binding.contactsList.adapter = contactAdapter
                contactAdapter.data = agenda.contact
                contactAdapter.viewModel = profilViewModel

                val locAdapter = LocalisationAdapter()
                binding.localisationsList.adapter = locAdapter
                locAdapter.data = agenda.loc
                locAdapter.viewModel = profilViewModel
            }
        })

        binding.addContactButton.setOnClickListener {
            val intent = Intent(activity, AddContactActivity::class.java)
            startActivity(intent)
        }

        binding.addLocButton.setOnClickListener {
            val intent = Intent(activity, AddLocalisationActivity::class.java)
            startActivity(intent)
        }

        binding.qrGenButton.setOnClickListener{
            val intent = Intent(activity, QRCodeGenActivity::class.java)
            startActivity(intent)
        }

        binding.validateButton.setOnClickListener {
            val myAgenda: Agenda? = profilViewModel.agenda.value

            val name = binding.nameZone.text.toString()
            val photo = binding.photoZone.text.toString()
            val weeknumber = binding.weekZone.text.toString().toInt()

            if(myAgenda != null)
            {
                try{
                    if(weeknumber < 1 || weeknumber > 52){
                        throw NumberFormatException()
                    }else{
                        myAgenda.name = name
                        myAgenda.photo = photo
                        myAgenda.week = weeknumber

                        profilViewModel.update(myAgenda)
                    }

                }catch(except: NumberFormatException){
                    binding.weekZone.setText(myAgenda.week.toString())
                    Toast.makeText(activity, "Vous devez rentrez un entier entre 1 et 52", Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(activity, "Vos données ont été enregistrées", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val agenda = Agenda(id = 0, name = name,
                photo = photo, week = weeknumber, contact = LinkedList(), loc = LinkedList(), is_mine = true)

                profilViewModel.insert(agenda)
            }
        }
    }

}