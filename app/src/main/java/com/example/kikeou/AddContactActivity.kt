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
            Log.d("key",key)
            val value : String = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
            Log.d("value", value)

            val contact = Contact(0, key, value)
            val agenda : Agenda = Room.databaseBuilder(this.applicationContext, AppDatabase::class.java, "test")
                .allowMainThreadQueries().build().agendaDao().getMyAgenda()

            val contacts : MutableList<Contact> = mutableListOf()
            agenda.contact.forEach(){
                contacts.add(it)
            }

            contacts.add(contact)

            Log.d("BITE", Collections.unmodifiableList(contacts).toString())

            agenda.contact = Collections.unmodifiableList(contacts)
            Room.databaseBuilder(this, AppDatabase::class.java, "test").allowMainThreadQueries().build().agendaDao().update(agenda)
            finish()
        }
    }
}