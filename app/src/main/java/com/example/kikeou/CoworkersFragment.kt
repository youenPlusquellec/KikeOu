package com.example.kikeou

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Room
import com.example.kikeou.coworkers.CoworkerAdapter
import com.example.kikeou.databinding.FragmentCoworkersBinding
import com.example.kikeou.room.AppDatabase
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

/**
 * A simple [Fragment] subclass.
 * Use the [CoworkersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoworkersFragment:Fragment(R.layout.fragment_coworkers) {

    private var _binding : FragmentCoworkersBinding? = null
    private val binding get() = _binding!!
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if(result.resultCode == Activity.RESULT_OK)
            {
                val json = result.data?.getStringExtra("json")

                if (json != null)
                {
                    val moshi: Moshi = Moshi.Builder().build()
                    val jsonAdapter: JsonAdapter<Agenda> = moshi.adapter(Agenda::class.java)

                    val agenda = jsonAdapter.fromJson(json)

                    if(agenda != null)
                        Room.databaseBuilder(requireContext(), AppDatabase::class.java, "test").allowMainThreadQueries().build().agendaDao().insert(agenda)
                }
            }
        }

        _binding = FragmentCoworkersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loc1 = Localisation(1,1, "dans ton cul")
        val loc2 = Localisation(2,3, "dans le cul de youen")
        val contact1 = Contact(1, "tel", "06.99.32.82.34")
        val contact2 = Contact(2, "email", "wilfried.pepin@outlook")
        val agenda = Agenda(1, "maman" ,50, "une_photo", listOf(contact1, contact2), listOf(loc1, loc2), false)
        val agenda2 = Agenda(2, "papa" ,52, "une_photo", listOf(contact1, contact2), listOf(loc1, loc2), false)
        val agenda3 = Agenda(3, "moi" ,50, "une_photo", listOf(contact1, contact2), listOf(loc1, loc2), false)
        val agenda4 = Agenda(4, "toi" ,52, "une_photo", listOf(contact1, contact2), listOf(loc1, loc2), false)

        val agendas = listOf(agenda, agenda2, agenda3, agenda4)

        val adapter = CoworkerAdapter()
        binding.coworkersList.adapter = adapter
        adapter.setOnItemClickListener(object : CoworkerAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(activity, ProfileDetailsActivity::class.java)

                val moshi: Moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<Agenda> = moshi.adapter(Agenda::class.java)
                val json : String = jsonAdapter.toJson(adapter.data[position])

                intent.putExtra("agenda", json);
                startActivity(intent)
            }

        })
        adapter.data = agendas

        binding.qrGenButton.setOnClickListener {
            startForResult.launch(Intent(requireContext(), CameraActivity::class.java))
        }

    }

}