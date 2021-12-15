package com.example.kikeou

import android.app.Application
import com.example.kikeou.room.AppDatabase
import com.example.kikeou.room.repository.AgendaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class AppApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val agendaRepository by lazy { database.agendaDao() }
}