package com.example.kikeou.room.converters

import androidx.room.TypeConverter
import com.example.kikeou.room.models.Localisation
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class ListLocalisationConverter {
    private val moshi = Moshi.Builder().build()
    private val locType = Types.newParameterizedType(List::class.java, Localisation::class.java)
    private val locAdapter = moshi.adapter<List<Localisation>>(locType)

    @TypeConverter
    fun stringToLocalisations(string: String): List<Localisation> {
        return locAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun LocalisationsToString(loc: List<Localisation>): String {
        return locAdapter.toJson(loc)
    }

}