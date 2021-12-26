package fr.enssat.kikeou.pepin_plestan_plusquellec

import android.app.Application
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.AppDatabase
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.repository.AgendaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class AppApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val agendaRepository by lazy { AgendaRepository(database.agendaDao()) }
}