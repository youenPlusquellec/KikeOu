package fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel

import androidx.lifecycle.ViewModel
import fr.enssat.kikeou.pepin_plestan_plusquellec.activity.MainActivity

class MainActivityViewModel : ViewModel() {
    var currentFragment : MainActivity.Views = MainActivity.Views.Profile
        private set

    fun updateView(newView: MainActivity.Views) {
        currentFragment = newView
    }
}