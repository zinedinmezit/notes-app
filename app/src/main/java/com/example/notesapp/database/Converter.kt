package com.example.notesapp.database

import androidx.room.TypeConverter
import java.util.*

class Converter {
    @TypeConverter
    fun fromTimeStamp(value : Long?) : Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimeStamp(value : Date?) : Long? = value?.time?.toLong()



}