package com.example.kikeou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.view.*
import net.glxn.qrgen.android.QRCode

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel

    private val profileFragment = ProfileFragment()
    private val coworkersFragment = CoworkersFragment()

    enum class Views {
        Profile, Coworkers
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        updateCurrentView()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.person -> viewModel.updateView(Views.Profile)
                R.id.group -> viewModel.updateView(Views.Coworkers)
            }
            updateCurrentView()
            true
        }


        val loc1 = Localisation(1, 1, "dans ton cul")
        val loc2 = Localisation(2, 3, "dans le cul de youen")

        val contact1 = Contact(1, "tel", "0699328234")
        val contact2 = Contact(2, "email", "wilfried.pepin@outlook")

        val agenda = Agenda(1, "Michel", 50, "unephoto", listOf(contact1, contact2), listOf(loc1, loc2), true)


        val moshi: Moshi = Moshi.Builder().build()

        val jsonAdapter: JsonAdapter<Agenda> = moshi.adapter(Agenda::class.java)

        val json : String = jsonAdapter.toJson(agenda)

        Log.d("BITE", json)


        /*val myBitmap = QRCode.from(json).bitmap()
        val myImage = findViewById<ImageView>(R.id.image_qr)
        myImage.setImageBitmap(myBitmap)*/
    }

    fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    private fun updateCurrentView() {
        when(viewModel.currentFragment) {
            Views.Profile -> setCurrentFragment(profileFragment)
            Views.Coworkers -> setCurrentFragment(coworkersFragment)
        }
    }
}