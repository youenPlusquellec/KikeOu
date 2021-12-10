package com.example.kikeou.room.dao

import androidx.room.*
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact

@Dao
interface AgendaDao {
    @Query("SELECT * FROM agenda")
    fun getAll(): MutableList<Agenda>

    @Insert
    fun insert(agenda: Agenda)

    @Delete
    fun delete(agenda: Agenda)

    @Update
    fun update(agenda: Agenda)

    @Query("SELECT * FROM agenda WHERE is_mine IS TRUE;")
    fun getMyAgenda(): Agenda
}