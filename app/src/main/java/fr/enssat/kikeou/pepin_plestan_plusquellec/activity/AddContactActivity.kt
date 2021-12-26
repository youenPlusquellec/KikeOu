package fr.enssat.kikeou.pepin_plestan_plusquellec.activity

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import fr.enssat.kikeou.pepin_plestan_plusquellec.AppApplication
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModel
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.ProfileViewModelFactory
import fr.enssat.kikeou.pepin_plestan_plusquellec.R
import fr.enssat.kikeou.pepin_plestan_plusquellec.databinding.ActivityAddContactBinding
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact

class AddContactActivity : AppCompatActivity(), OnItemSelectedListener {
    private lateinit var binding: ActivityAddContactBinding

    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory((application as AppApplication).agendaRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddContactBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.contactKey.onItemSelectedListener = this

        binding.AddButton.setOnClickListener {
            val key : String = findViewById<Spinner>(R.id.contact_key).selectedItem.toString()
            val value : String = findViewById<EditText>(R.id.contact_value).text.toString()

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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        when(parent?.getItemAtPosition(pos).toString()) {
            "Téléphone" -> binding.contactValue.inputType = InputType.TYPE_CLASS_PHONE
            "E-mail" -> binding.contactValue.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_CLASS_TEXT
            "Site web" -> binding.contactValue.inputType = InputType.TYPE_TEXT_VARIATION_URI or InputType.TYPE_CLASS_TEXT
            else -> binding.contactValue.inputType = InputType.TYPE_CLASS_TEXT
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
