package fr.enssat.kikeou.pepin_plestan_plusquellec.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.lifecycle.ViewModelProvider
import fr.enssat.kikeou.pepin_plestan_plusquellec.fragment.CoworkersFragment
import fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel.MainActivityViewModel
import fr.enssat.kikeou.pepin_plestan_plusquellec.fragment.ProfileFragment
import fr.enssat.kikeou.pepin_plestan_plusquellec.R
import kotlinx.android.synthetic.main.activity_main.view.*

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
            replace(R.id.flFragment, fragment)
            commit()
        }

    private fun updateCurrentView() {
        when(viewModel.currentFragment) {
            Views.Profile -> setCurrentFragment(profileFragment)
            Views.Coworkers -> setCurrentFragment(coworkersFragment)
        }
    }
}