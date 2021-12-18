package com.example.kikeou.room.repository

import androidx.annotation.WorkerThread
import com.example.kikeou.room.dao.AgendaDao
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import kotlinx.coroutines.flow.Flow

class AgendaRepository(private val agendaDao: AgendaDao) {
    val myAgenda : Flow<Agenda> = agendaDao.getMyAgenda()

    val agenda : Flow<List<Agenda>> = agendaDao.getAll()

//    val myLoc : Flow<List<Localisation>> = agendaDao.getMyLoc()
//    val myContact : Flow<List<Contact>> = agendaDao.getMyContact()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(agenda: Agenda){
        agendaDao.insert(agenda)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(agenda: Agenda){
        agendaDao.update(agenda)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(agenda: Agenda){
        agendaDao.delete(agenda)
    }

}