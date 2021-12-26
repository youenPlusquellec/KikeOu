package fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel

import androidx.lifecycle.*
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.repository.AgendaRepository
import kotlinx.coroutines.launch

class CoworkerViewModel(private val repository: AgendaRepository) : ViewModel() {
    val agendas: LiveData<List<Agenda>> = repository.others.asLiveData()

    fun insertOrUpdate(agenda: Agenda) = viewModelScope.launch {
        val dbData = repository.findByName(agenda.name)

        when {
            dbData.isEmpty() -> {
                agenda.id = 0
                repository.insert(agenda)
            }
            dbData[0].is_mine -> {
                agenda.id = 0
                repository.insert(agenda)
            }
            else -> {
                agenda.id = dbData[0].id
                repository.update(agenda)
            }
        }
    }

    fun delete(agenda: Agenda) = viewModelScope.launch {
        repository.delete(agenda)
    }
}

class CoworkerViewModelFactory(private val repository: AgendaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoworkerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoworkerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}