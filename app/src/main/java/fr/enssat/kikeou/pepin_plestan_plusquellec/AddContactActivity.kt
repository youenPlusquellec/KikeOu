package fr.enssat.kikeou.pepin_plestan_plusquellec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.room.Room
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.AppDatabase
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation
import java.util.*

class AddContactActivity : AppCompatActivity() {
    private val profilViewModel: ProfilViewModel by viewModels {
        ProfilViewModelFactory((application as AppApplication).agendaRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        findViewById<Button>(R.id.Add_button).setOnClickListener {
            val key : String = findViewById<Spinner>(R.id.contact_key).selectedItem.toString()
            val value : String = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()

            profilViewModel.agenda.observe(this, { agenda ->
                val prev : Contact? = agenda.contact.find { e -> e.key == key }

                if(prev == null)
                    agenda.contact.add(Contact(0, key, value))
                else
                    prev.value = value

                profilViewModel.update(agenda)
            })

            finish()
        }
    }
}