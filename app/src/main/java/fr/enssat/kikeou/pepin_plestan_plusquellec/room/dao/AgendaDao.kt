package fr.enssat.kikeou.pepin_plestan_plusquellec.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendaDao {
    @Insert
    suspend fun insert(agenda: Agenda)

    @Delete
    suspend fun delete(agenda: Agenda)

    @Update
    suspend fun update(agenda: Agenda)

    @Query("SELECT * FROM agenda WHERE is_mine = 1;")
    fun getMyAgenda(): Flow<Agenda>

    @Query("SELECT * FROM agenda WHERE is_mine = 0")
    fun getAllOther(): Flow<List<Agenda>>

    @Query("SELECT * FROM agenda")
    fun getAll(): Flow<List<Agenda>>

    @Query("SELECT * FROM agenda WHERE name = :name;")
    suspend fun findByName(name: String): List<Agenda>
}