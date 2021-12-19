package fr.enssat.kikeou.pepin_plestan_plusquellec.room.dao

import androidx.room.*
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAll(): Flow<Contact>

    @Insert
    fun insert(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Update
    fun update(contact: Contact)
}