package fr.enssat.kikeou.pepin_plestan_plusquellec.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import fr.enssat.kikeou.pepin_plestan_plusquellec.AppApplication
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModel
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfilViewModelFactory
import fr.enssat.kikeou.pepin_plestan_plusquellec.R
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation

class AddLocalisationActivity : AppCompatActivity() {
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfilViewModelFactory((application as AppApplication).agendaRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_localisation)

        findViewById<Button>(R.id.Add_button).setOnClickListener {
            val key : String = findViewById<Spinner>(R.id.contact_key).selectedItem.toString()
            var day = 0

            when(key) {
                "Lundi"     ->  day = 1
                "Mardi"     ->  day = 2
                "Mercredi"  ->  day = 3
                "Jeudi"     ->  day = 4
                "Vendredi"  ->  day = 5
            }

            val value : String = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()

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