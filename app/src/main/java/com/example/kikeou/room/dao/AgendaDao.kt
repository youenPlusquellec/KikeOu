package com.example.kikeou.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendaDao {
    @Query("SELECT * FROM agenda")
    fun getAll(): Flow<List<Agenda>>

    @Insert
    fun insert(agenda: Agenda)

    @Delete
    fun delete(agenda: Agenda)

    @Update
    fun update(agenda: Agenda)

    @Query("SELECT * FROM agenda WHERE is_mine IS TRUE;")
    fun getMyAgenda(): Flow<Agenda>

    @Query("SELECT contact FROM agenda WHERE is_mine IS TRUE;")
    fun getMyContact(): Flow<List<Contact>>

    @Query("SELECT loc FROM agenda WHERE is_mine IS TRUE;")
    fun getMyLoc(): Flow<List<Localisation>>
}