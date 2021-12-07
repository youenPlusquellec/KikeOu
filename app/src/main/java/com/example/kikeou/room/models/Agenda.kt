package com.example.kikeou.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.kikeou.room.converters.ListContactConverter
import com.example.kikeou.room.converters.ListLocalisationConverter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Agenda(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name="week") val week: Int,
    @ColumnInfo(name="photo") val photo: String,

    @TypeConverters(ListContactConverter::class)
    @ColumnInfo(name="contact") val contact : List<Contact>,

    @TypeConverters(ListLocalisationConverter::class)
    @ColumnInfo(name="loc") val loc : List<Localisation>,

    @Transient
    @ColumnInfo(name="is_mine") val is_mine : Boolean = false,
)
