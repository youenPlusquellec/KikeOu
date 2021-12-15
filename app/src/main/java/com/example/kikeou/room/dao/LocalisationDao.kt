package com.example.kikeou.room.dao

import androidx.room.*
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Localisation
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalisationDao {
    @Query("SELECT * FROM localisation")
    fun getAll(): Flow<Localisation>

    @Insert
    fun insert(localisation: Localisation)

    @Delete
    fun delete(localisation: Localisation)

    @Update
    fun update(localisation: Localisation)
}