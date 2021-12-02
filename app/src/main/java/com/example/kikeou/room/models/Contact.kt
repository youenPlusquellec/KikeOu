package com.example.kikeou.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Contact(
    @Transient
    @ColumnInfo(name="id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name="key") val key: String,
    @ColumnInfo(name="value") val value : String
)
