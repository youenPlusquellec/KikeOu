    package fr.enssat.kikeou.pepin_plestan_plusquellec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import fr.enssat.kikeou.pepin_plestan_plusquellec.profile.ContactAdapter
import fr.enssat.kikeou.pepin_plestan_plusquellec.profile.LocalisationAdapter
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.AppDatabase
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation
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

                findViewById<TextView>(R.id.name_zone).text = agenda.name

                val contactAdapter = ContactAdapter()
                findViewById<RecyclerView>(R.id.contacts_list).adapter = contactAdapter
                contactAdapter.data = agenda.contact

                findViewById<TextView>(R.id.week).text = "Semaine ${agenda.week}"

                val locAdapter = LocalisationAdapter()
                findViewById<RecyclerView>(R.id.localisations_list).adapter = locAdapter
                locAdapter.data = agenda.loc

                findViewById<Button>(R.id.delete_button).setOnClickListener {
                    // TODO Delete profile in database
                    Log.w("coucou", "coucou")
                    finish()
                }
            }
        }

    }
}