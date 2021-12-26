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
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact

class AddContactActivity : AppCompatActivity() {
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfilViewModelFactory((application as AppApplication).agendaRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        findViewById<Button>(R.id.Add_button).setOnClickListener {
            val key : String = findViewById<Spinner>(R.id.contact_key).selectedItem.toString()
            val value : String = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()

            profileViewModel.agenda.observe(this, { agenda ->
                val prev : Contact? = agenda.contact.find { e -> e.key == key }

                if(prev == null)
                    agenda.contact.add(Contact(0, key, value))
                else
                    prev.value = value

                profileViewModel.update(agenda)
            })

            finish()
        }
    }
}