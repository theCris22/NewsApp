package com.crisnavarro.newsapp.data.db.converters

import androidx.room.TypeConverter
import com.crisnavarro.newsapp.data.network.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String = source.name.toString()

    @TypeConverter
    fun toSource(name: String): Source = Source(name, name)

}