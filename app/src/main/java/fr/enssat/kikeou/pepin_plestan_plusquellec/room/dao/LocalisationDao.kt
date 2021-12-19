package fr.enssat.kikeou.pepin_plestan_plusquellec.room.dao

import androidx.room.*
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation
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