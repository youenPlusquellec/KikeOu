package com.example.kikeou.room.dao

import androidx.room.*
import com.example.kikeou.room.models.Contact

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAll(): MutableList<Contact>

    @Insert
    fun insert(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Update
    fun update(contact: Contact)
}