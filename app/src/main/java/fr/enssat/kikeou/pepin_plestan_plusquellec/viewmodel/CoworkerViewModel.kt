package fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel

import androidx.lifecycle.*
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.repository.AgendaRepository
import kotlinx.coroutines.launch

class CoworkerViewModel(private val repository: AgendaRepository) : ViewModel() {
    val agendas: LiveData<List<Agenda>> = repository.others.asLiveData()

    fun insertOrUpdate(agenda: Agenda) = viewModelScope.launch {
        val dbData = repository.findByName(agenda.name)

        if(dbData.isEmpty())
        {
            agenda.id = 0
            repository.insert(agenda)
        }
        else
        {
            var agendaToUpdate = dbData[0].id

            if(dbData.size > 1) // Case self scanning - with already on record in DB
            {
                for(i in dbData)
                {
                    if(!i.is_mine)
                        agendaToUpdate = i.id
                }
            }
            else if(dbData[0].is_mine) // Case self scanning - Not yet in DB
                agendaToUpdate = 0

            agenda.id = agendaToUpdate

            if(agendaToUpdate == 0) // Self scanning - insert
                repository.insert(agenda)
            else // Else it's an update
                repository.update(agenda)
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