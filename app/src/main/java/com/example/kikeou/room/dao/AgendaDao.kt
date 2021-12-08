package com.example.kikeou.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kikeou.room.models.Agenda

@Dao
interface AgendaDao {
    @Query("SELECT * FROM agenda")
    fun getAll(): List<Agenda>

    @Insert
    fun insert(agenda: Agenda)

    @Delete
    fun delete(agenda: Agenda)

    @Query("SELECT * FROM agenda WHERE is_mine IS TRUE;")
    fun getMyAgenda(): Agenda
}