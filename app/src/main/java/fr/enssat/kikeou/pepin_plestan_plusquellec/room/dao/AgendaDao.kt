package fr.enssat.kikeou.pepin_plestan_plusquellec.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendaDao {
    @Query("SELECT * FROM agenda")
    fun getAll(): Flow<List<Agenda>>

    @Insert
    suspend fun insert(agenda: Agenda)

    @Delete
    fun delete(agenda: Agenda)

    @Update
    suspend fun update(agenda: Agenda)

    @Query("SELECT * FROM agenda WHERE is_mine IS TRUE;")
    fun getMyAgenda(): Flow<Agenda>

    /*@Query("SELECT contact FROM agenda WHERE is_mine IS TRUE;")
    fun getMyContact(): Flow<List<Contact>>

    @Query("SELECT loc FROM agenda WHERE is_mine IS TRUE;")
    fun getMyLoc(): Flow<List<Localisation>>*/
}