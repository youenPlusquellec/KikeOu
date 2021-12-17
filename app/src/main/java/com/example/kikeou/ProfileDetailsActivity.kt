    package com.example.kikeou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.kikeou.profile.ContactAdapter
import com.example.kikeou.profile.LocalisationAdapter
import com.example.kikeou.room.AppDatabase
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

    class ProfileDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)

        val agendaJson = intent.getSerializableExtra("agenda");

        if (agendaJson != null)
        {
            val moshi: Moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<Agenda> = moshi.adapter(Agenda::class.java)

            val agenda = jsonAdapter.fromJson(agendaJson.toString())

            val imageView = findViewById<CircleImageView>(R.id.coworker_picture)
            if (agenda != null) {
                Picasso.get()
                    .load(agenda.photo)
                    .placeholder(R.drawable.ic_person_foreground)
                    .error(R.drawable.ic_person_foreground)
                    .into(imageView)
            }

            if (agenda != null) {
                findViewById<TextView>(R.id.name_zone).text = agenda.name
            }

            val contactAdapter = ContactAdapter()
            findViewById<RecyclerView>(R.id.contacts_list).adapter = contactAdapter
            if (agenda != null) {
                contactAdapter.data = agenda.contact
            }

            if (agenda != null) {
                findViewById<TextView>(R.id.week).text = "Semaine ${agenda.week}"
            }

            val locAdapter = LocalisationAdapter()
            findViewById<RecyclerView>(R.id.localisations_list).adapter = locAdapter
            if (agenda != null) {
                locAdapter.data = agenda.loc
            }
        }

    }
}