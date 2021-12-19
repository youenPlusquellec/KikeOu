package com.example.kikeou

import androidx.lifecycle.*
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.repository.AgendaRepository
import kotlinx.coroutines.launch

class ProfilViewModel(private val repository: AgendaRepository) : ViewModel() {
    val agenda: LiveData<Agenda> = repository.myAgenda.asLiveData()

    fun insert(agenda: Agenda) = viewModelScope.launch {
        repository.insert(agenda)
    }

    fun update(agenda: Agenda) = viewModelScope.launch {
        repository.update(agenda)
    }

    fun updateLocList() = viewModelScope.launch {
        val agenda = agenda.value

        if (agenda != null) update(agenda)
    }

    fun updateContactList() = viewModelScope.launch {
        val agenda = agenda.value

        if(agenda != null) update(agenda)
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