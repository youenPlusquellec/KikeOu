    package com.example.kikeou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kikeou.profile.ContactAdapter
import com.example.kikeou.profile.LocalisationAdapter
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation

    class ProfileDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)
        val agenda = getIntent().getSerializableExtra("agenda");
        Toast.makeText(this, "You clicked on ${agenda}", Toast.LENGTH_SHORT).show()


        val loc1 = Localisation(1,1, "dans ton cul")
        val loc2 = Localisation(2,3, "dans le cul de youen")
        val contact1 = Contact(1, "tel", "06.99.32.82.34")
        val contact2 = Contact(2, "email", "wilfried.pepin@outlook")
        val contactList = listOf(contact1, contact2)
        val locList = listOf(loc1, loc2)

        
        findViewById<TextView>(R.id.name_zone).setText("Moi")

        val contactAdapter = ContactAdapter()
        findViewById<RecyclerView>(R.id.contacts_list).adapter = contactAdapter
        //contactAdapter.data = agenda.contacts
        contactAdapter.data = contactList

        findViewById<TextView>(R.id.week).setText("52")

        val locAdapter = LocalisationAdapter()
        findViewById<RecyclerView>(R.id.localisations_list).adapter = locAdapter
        //locAdapter.data = agenda.loc
        locAdapter.data = locList
    }
}