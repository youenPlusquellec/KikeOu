package com.example.kikeou.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kikeou.room.models.Localisation

@Dao
interface LocalisationDao {
    @Query("SELECT * FROM localisation")
    fun getAll(): List<Localisation>

    @Insert
    fun insert(localisation: Localisation)

    @Delete
    fun delete(localisation: Localisation)
}