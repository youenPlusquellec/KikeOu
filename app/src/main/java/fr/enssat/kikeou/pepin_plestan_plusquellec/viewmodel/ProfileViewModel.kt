package fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel

import androidx.lifecycle.*
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.repository.AgendaRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: AgendaRepository) : ViewModel() {
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

class ProfileViewModelFactory(private val repository: AgendaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}