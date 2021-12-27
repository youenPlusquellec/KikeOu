package fr.enssat.kikeou.pepin_plestan_plusquellec.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import fr.enssat.kikeou.pepin_plestan_plusquellec.AppApplication
import fr.enssat.kikeou.pepin_plestan_plusquellec.databinding.ActivityAddLocalisationBinding
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModel
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModelFactory

class AddLocalisationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddLocalisationBinding

    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory((application as AppApplication).agendaRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddLocalisationBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.AddButton.setOnClickListener {
            val key : String = binding.localisationKey.selectedItem.toString()
            var day = 0

            when(key) {
                "Lundi"     ->  day = 1
                "Mardi"     ->  day = 2
                "Mercredi"  ->  day = 3
                "Jeudi"     ->  day = 4
                "Vendredi"  ->  day = 5
            }

            val value : String = binding.localisationValue.text.toString()

            profileViewModel.agenda.observe(this, { agenda ->
                if(agenda != null)
                {
                    val prev : Localisation? = agenda.loc.find { e -> e.day == day }

                    if(prev == null)
                        agenda.loc.add(Localisation(0, day, value))
                    else
                        prev.value = value

                    profileViewModel.update(agenda)
                }
            })

            finish()
        }
    }
}