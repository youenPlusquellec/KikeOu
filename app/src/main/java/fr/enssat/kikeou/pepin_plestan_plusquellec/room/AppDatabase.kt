package fr.enssat.kikeou.pepin_plestan_plusquellec.room

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.converters.ListContactConverter
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.converters.ListLocalisationConverter
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.dao.AgendaDao
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@TypeConverters(ListContactConverter::class, ListLocalisationConverter::class)
@Database(entities = [Agenda::class, Contact::class, Localisation::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun agendaDao(): AgendaDao

    private class AgendaDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val agendaDao = database.agendaDao()

                    val agenda: Agenda? = agendaDao.getMyAgenda().asLiveData().value

                    if (agenda == null) {
                        val myAgenda = Agenda(0, "Unknown", 1, "Votre photo", mutableListOf<Contact>(), mutableListOf<Localisation>(), true)
                        agendaDao.insert(myAgenda)
                    }
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addCallback(AgendaDatabaseCallback(scope)).build()
                INSTANCE = instance

                instance
            }
        }
    }
}