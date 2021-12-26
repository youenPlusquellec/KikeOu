package fr.enssat.kikeou.pepin_plestan_plusquellec.room.repository

import androidx.annotation.WorkerThread
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.dao.AgendaDao
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation
import kotlinx.coroutines.flow.Flow

class AgendaRepository(private val agendaDao: AgendaDao) {
    val myAgenda : Flow<Agenda> = agendaDao.getMyAgenda()

    val agenda : Flow<List<Agenda>> = agendaDao.getAll()

//    val myLoc : Flow<List<Localisation>> = agendaDao.getMyLoc()
//    val myContact : Flow<List<Contact>> = agendaDao.getMyContact()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(agenda: Agenda) {
        agendaDao.insert(agenda)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(agenda: Agenda) {
        agendaDao.update(agenda)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(agenda: Agenda) {
        agendaDao.delete(agenda)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun findByName(name: String): List<Agenda> {
        return agendaDao.findByName(name)
    }
}