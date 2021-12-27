package fr.enssat.kikeou.pepin_plestan_plusquellec.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import fr.enssat.kikeou.pepin_plestan_plusquellec.AppApplication
import fr.enssat.kikeou.pepin_plestan_plusquellec.R
import fr.enssat.kikeou.pepin_plestan_plusquellec.databinding.ActivityProfileDetailsBinding
import fr.enssat.kikeou.pepin_plestan_plusquellec.fragment.adaptaters.ContactAdapter
import fr.enssat.kikeou.pepin_plestan_plusquellec.fragment.adaptaters.LocalisationAdapter
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.CoworkerViewModel
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.CoworkerViewModelFactory

class ProfileDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailsBinding

    private val coworkerViewModel: CoworkerViewModel by viewModels {
        CoworkerViewModelFactory((application as AppApplication).agendaRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val agendaJson = intent.getSerializableExtra("agenda")

        if (agendaJson != null)
        {
            val moshi: Moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<Agenda> = moshi.adapter(Agenda::class.java)

            val agenda = jsonAdapter.fromJson(agendaJson.toString())

            val imageView =  binding.coworkerPicture

            if (agenda != null) {
                Picasso.get()
                    .load(agenda.photo)
                    .placeholder(R.drawable.ic_person_foreground)
                    .error(R.drawable.ic_person_foreground)
                    .into(imageView)

                binding.nameZone.text = agenda.name

                val contactAdapter = ContactAdapter()
                binding.contactsList.adapter = contactAdapter
                contactAdapter.data = agenda.contact
                contactAdapter.isReadOnly = true

                binding.week.text = String.format(getString(R.string.week_number), agenda.week)

                val locAdapter = LocalisationAdapter()
                binding.localisationsList.adapter = locAdapter
                locAdapter.data = agenda.loc
                locAdapter.isReadOnly = true

                binding.deleteButton.setOnClickListener {
                    coworkerViewModel.delete(agenda)
                    finish()
                }
            }
        }

    }
}