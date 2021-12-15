package com.example.kikeou

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.room.Room
import com.example.kikeou.room.AppDatabase
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import java.util.*

class AddContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        findViewById<Button>(R.id.Add_button).setOnClickListener {
            val key : String = findViewById<Spinner>(R.id.contact_key).getSelectedItem().toString()
            val value : String = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()

            val contact = Contact(0, key, value)
            val agenda : Agenda = AppA.agendaDao().getMyAgenda()

            val contacts : MutableList<Contact> = mutableListOf()
            var newContactAlreadyHere : Boolean = false
            agenda.contact.forEach(){
                if(it.key == key){
                    contacts.add(contact)
                    newContactAlreadyHere = true
                }else {
                    contacts.add(it)
                }
            }
            if(!newContactAlreadyHere) {
                contacts.add(contact)
            }

            agenda.contact = Collections.unmodifiableList(contacts)
            AppDatabase.getDatabase(this).agendaDao().update(agenda)
            finish()
        }
    }
}