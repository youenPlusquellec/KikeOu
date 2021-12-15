package com.example.kikeou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.kikeou.room.AppDatabase
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import net.glxn.qrgen.android.QRCode

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
    }

    fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}