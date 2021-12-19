package fr.enssat.kikeou.pepin_plestan_plusquellec.room

import android.content.Context
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.converters.ListContactConverter
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.converters.ListLocalisationConverter
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.dao.AgendaDao
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.dao.ContactDao
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.dao.LocalisationDao
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Agenda
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Contact
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.models.Localisation
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@TypeConverters(ListContactConverter::class, ListLocalisationConverter::class)
@Database(entities = [Agenda::class, Contact::class, Localisation::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun agendaDao(): AgendaDao
    abstract fun contactDao(): ContactDao
    abstract fun localisationDao(): LocalisationDao

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
                        Log.d("Mon agenda","Y'a R fraire")
                        val myAgenda = Agenda(0, "Unknown", 1, "Votre photo", mutableListOf<Contact>(), mutableListOf<Localisation>(), true)
                        agendaDao.insert(myAgenda)
                    }else{
                        Log.d("Mon agenda", agenda.name)
                        val moshi: Moshi = Moshi.Builder().build()
                        val jsonAdapter: JsonAdapter<Agenda> = moshi.adapter(Agenda::class.java)
                        val json : String = jsonAdapter.toJson(agenda)
                        Log.d("JSON", json)
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