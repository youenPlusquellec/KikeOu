package com.example.kikeou.room

import android.content.Context
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kikeou.room.converters.ListContactConverter
import com.example.kikeou.room.converters.ListLocalisationConverter
import com.example.kikeou.room.dao.AgendaDao
import com.example.kikeou.room.dao.ContactDao
import com.example.kikeou.room.dao.LocalisationDao
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation
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
                    var agendaDao = database.agendaDao()
                    val agenda = agendaDao.getMyAgenda()


                    if (agenda == null){
                        Log.d("Mon agenda","Y'a R fraire")
                        val myAgenda : Agenda = Agenda(0, "Unknown", 0, "votrephoto", listOf<Contact>(), listOf<Localisation>(), true)
                        agendaDao.insert(myAgenda)
                    }else{
                        Log.d("Mon agenda", (agenda.asLiveData().value?.name ?: "[No data]"))
                        val moshi: Moshi = Moshi.Builder().build()
                        val jsonAdapter: JsonAdapter<Agenda> = moshi.adapter(Agenda::class.java)
                        val json : String = jsonAdapter.toJson(agenda.asLiveData().value)
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