package com.example.kikeou

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kikeou.room.AppDatabase
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.example.kikeou.room.repository.AgendaRepository

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

class ProfilViewModelFactory(private val repository: AgendaRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfilViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfilViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}