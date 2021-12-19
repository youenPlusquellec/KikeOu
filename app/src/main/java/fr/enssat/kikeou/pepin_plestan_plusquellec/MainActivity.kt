package fr.enssat.kikeou.pepin_plestan_plusquellec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.room.Room
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.AppDatabase
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation
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