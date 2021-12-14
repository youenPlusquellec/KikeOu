package com.example.kikeou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.room.Room
import com.example.kikeou.room.AppDatabase
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import java.util.*

class AddLocalisationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_localisation)

        findViewById<Button>(R.id.Add_button).setOnClickListener {
            val key : String = findViewById<Spinner>(R.id.contact_key).getSelectedItem().toString()
            var day: Int = 0
            if(key == "Lundi") day = 1
            if(key == "Mardi") day = 2
            if(key == "Mercredi") day = 3
            if(key == "Jeudi") day = 4
            if(key == "Vendredi") day = 5
            val value : String = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()

            val localisation = Localisation(0, day, value)
            val agenda : Agenda = AppDatabase.getDatabase(this).agendaDao().getMyAgenda()

            val localisations : MutableList<Localisation> = mutableListOf()
            var newContactAlreadyHere : Boolean = false
            agenda.loc.forEach(){
                if(it.day == day){
                    localisations.add(localisation)
                    newContactAlreadyHere = true
                }else {
                    localisations.add(it)
                }
            }
            if(!newContactAlreadyHere) {
                localisations.add(localisation)
            }

            agenda.loc = Collections.unmodifiableList(localisations)
            AppDatabase.getDatabase(this).agendaDao().update(agenda)
            finish()

            finish()
        }
    }
}