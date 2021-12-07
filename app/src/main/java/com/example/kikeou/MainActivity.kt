package com.example.kikeou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.Log
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val profileFragment=ProfileFragment();
        val coworkersFragment=CoworkersFragment();

        setCurrentFragment(profileFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.person->setCurrentFragment(profileFragment)
                R.id.group->setCurrentFragment(coworkersFragment)

            }
            true
        }
        val loc1 = Localisation(1,"18 mai", "dans ton cul")
        val loc2 = Localisation(2,"8 aout", "dans le cul de youen")

        val contact1 = Contact(1, "tel", "0699328234")
        val contact2 = Contact(2, "email", "wilfried.pepin@outlook")

        val agenda = Agenda(1, "Michel", 50, "unephoto", listOf(contact1, contact2), listOf(loc1, loc2), true)


        val moshi: Moshi = Moshi.Builder().build()

        val jsonAdapter: JsonAdapter<Agenda> = moshi.adapter(Agenda::class.java)

        val json : String = jsonAdapter.toJson(agenda)

        Log.d("BITE", json)




    }

    fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}