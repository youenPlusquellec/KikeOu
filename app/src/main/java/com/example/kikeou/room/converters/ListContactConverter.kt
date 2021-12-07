package com.example.kikeou.room.converters

import androidx.room.TypeConverter
import com.example.kikeou.room.models.Contact
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class ListContactConverter {
    private val moshi = Moshi.Builder().build()
    private val locType = Types.newParameterizedType(List::class.java, Contact::class.java)
    private val locAdapter = moshi.adapter<List<Contact>>(locType)

    @TypeConverter
    fun stringToLocalisations(string: String): List<Contact> {
        return locAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun LocalisationsToString(loc: List<Contact>): String {
        return locAdapter.toJson(loc)
    }
}