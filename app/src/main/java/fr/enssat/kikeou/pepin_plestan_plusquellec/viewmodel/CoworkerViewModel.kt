package fr.enssat.kikeou.pepin_plestan_plusquellec.viewmodel

import android.util.Log
import androidx.lifecycle.*
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.repository.AgendaRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class CoworkerViewModel(private val repository: AgendaRepository) : ViewModel() {
    val agendas: LiveData<List<Agenda>> = repository.others.asLiveData()

    fun insertOrUpdate(agenda: Agenda)
    {
        Log.d("SA MERE", "YO!")
        val test = viewModelScope.launch {
            val dbData = repository.findByName(agenda.name)

            when {
                dbData.isEmpty() -> {
                    Log.w("KIKEOU", "DB - Empty")
                    agenda.id = 0
                    repository.insert(agenda)
                }
                dbData[0].is_mine -> {
                    Log.w("KIKEOU", "DB - No empty / is_mine")
                    agenda.id = 0
                    repository.insert(agenda)
                }
                else -> {
                    Log.w("KIKEOU", "DB - Else")
                    agenda.id = dbData[0].id
                    repository.update(agenda)
                }
            }
        }

        test.invokeOnCompletion { cause ->
            if(cause == null)
                Log.d("NIQUE", "NULL sa mère")
            else if(cause is CancellationException)
                Log.d("NIQUE", cause.message ?: "")
            else
                Log.d("NIQUE", "BRANLE")
        }
    }

    fun delete(agenda: Agenda) = viewModelScope.launch {
        repository.delete(agenda)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("NIQUE", "On tire la chasse...")
    }
}

class CoworkerViewModelFactory(private val repository: AgendaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoworkerViewModel::class.java)) {
            Log.d("TROLOLO", "Création ?")
            @Suppress("UNCHECKED_CAST")
            return CoworkerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}