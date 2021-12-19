package fr.enssat.kikeou.pepin_plestan_plusquellec.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.converters.ListContactConverter
import fr.enssat.kikeou.pepin_plestan_plusquellec.room.converters.ListLocalisationConverter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Agenda(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name="name") var name: String,
    @ColumnInfo(name="week") var week: Int,
    @ColumnInfo(name="photo") var photo: String?,

    @TypeConverters(ListContactConverter::class)
    @ColumnInfo(name="contact") var contact : MutableList<Contact>,

    @TypeConverters(ListLocalisationConverter::class)
    @ColumnInfo(name="loc") var loc : MutableList<Localisation>,

    @Transient
    @ColumnInfo(name="is_mine") var is_mine : Boolean = false,
)
