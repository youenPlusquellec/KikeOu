package fr.enssat.kikeou.pepin_plestan_plusquellec.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import fr.enssat.kikeou.pepin_plestan_plusquellec.AppApplication
import fr.enssat.kikeou.pepin_plestan_plusquellec.R
import fr.enssat.kikeou.pepin_plestan_plusquellec.activity.CameraActivity
import fr.enssat.kikeou.pepin_plestan_plusquellec.activity.ProfileDetailsActivity
import fr.enssat.kikeou.pepin_plestan_plusquellec.databinding.FragmentCoworkersBinding
import fr.enssat.kikeou.pepin_plestan_plusquellec.fragment.adaptaters.CoworkerAdapter
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.CoworkerViewModel
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.CoworkerViewModelFactory

class CoworkersFragment:Fragment(R.layout.fragment_coworkers) {
    private lateinit var binding : FragmentCoworkersBinding

    private val coworkerViewModel: CoworkerViewModel by viewModels {
        CoworkerViewModelFactory((requireActivity().application as AppApplication).agendaRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("MERDOUILLE", "OnCreateView")

        binding = FragmentCoworkersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("MERDOUILLE", "ViewCreated")

        val adapter = CoworkerAdapter()
        binding.coworkersList.adapter = adapter
        adapter.setOnItemClickListener(object : CoworkerAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(activity, ProfileDetailsActivity::class.java)

                val moshi: Moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<Agenda> = moshi.adapter(Agenda::class.java)
                val json : String = jsonAdapter.toJson(adapter.data[position])

                intent.putExtra("agenda", json)
                startActivity(intent)
            }

        })

        coworkerViewModel.agendas.observe(requireActivity(), { agendas ->
            adapter.data = agendas
        })

        binding.qrGenButton.setOnClickListener {
            startActivity(Intent(requireContext(), CameraActivity::class.java))
        }
    }

}