package com.example.kikeou.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kikeou.room.converters.ListContactConverter
import com.example.kikeou.room.converters.ListLocalisationConverter
import com.example.kikeou.room.dao.AgendaDao
import com.example.kikeou.room.dao.ContactDao
import com.example.kikeou.room.dao.LocalisationDao
import com.example.kikeou.room.models.Agenda
import com.example.kikeou.room.models.Contact
import com.example.kikeou.room.models.Localisation

@TypeConverters(ListContactConverter::class, ListLocalisationConverter::class)
@Database(entities = [Agenda::class, Contact::class, Localisation::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun agendaDao(): AgendaDao
    abstract fun contactDao(): ContactDao
    abstract fun localisationDao(): LocalisationDao
}