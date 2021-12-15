package com.example.kikeou

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kikeou.room.AppDatabase
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation

class ProfileViewModel : ViewModel() {
    private val _contact = MutableLiveData<List<Contact>>()
    val contact: LiveData<List<Contact>>
        get() = _contact

    private val _loc = MutableLiveData<List<Localisation>>()
    val loc: LiveData<List<Localisation>>
        get() = _loc

    init{
    }
}