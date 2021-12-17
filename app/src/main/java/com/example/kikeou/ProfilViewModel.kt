package com.example.kikeou

import androidx.lifecycle.*
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import com.example.kikeou.room.repository.AgendaRepository
import kotlinx.coroutines.launch

class ProfilViewModel(private val repository: AgendaRepository) : ViewModel() {
    val agenda: LiveData<Agenda> = repository.myAgenda.asLiveData()
    val contacts : LiveData<List<Contact>> = repository.myContact.asLiveData()
    val locs : LiveData<List<Localisation>> = repository.myLoc.asLiveData()

    fun insert(agenda: Agenda) = viewModelScope.launch {
        repository.insert(agenda)
    }
}

class ProfilViewModelFactory(private val repository: AgendaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfilViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfilViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}