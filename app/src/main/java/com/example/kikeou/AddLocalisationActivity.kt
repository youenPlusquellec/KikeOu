package com.example.kikeou

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.kikeou.room.models.Localisation

class AddLocalisationActivity : AppCompatActivity() {
    private val profilViewModel: ProfilViewModel by viewModels {
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

            profilViewModel.agenda.observe(this, { agenda ->
                if(agenda != null)
                {
                    val prev : Localisation? = agenda.loc.find { e -> e.day == day }

                    if(prev == null)
                        agenda.loc.add(Localisation(0, day, value))
                    else
                        prev.value = value

                    profilViewModel.update(agenda)
                }
            })

            finish()
        }
    }
}