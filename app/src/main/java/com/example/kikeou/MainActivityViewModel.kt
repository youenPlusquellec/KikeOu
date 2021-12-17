package com.example.kikeou

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    var currentFragment : MainActivity.Views = MainActivity.Views.Profile
        private set

    fun updateView(newView: MainActivity.Views) {
        currentFragment = newView
    }
}