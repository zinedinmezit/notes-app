package com.example.notesapp.utils

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

object TypeConverters{


    @TypeConverter
    fun toDate(dateString: String?): LocalDate? {
        return if (dateString == null) {
            null
        } else {
            LocalDate.parse(dateString)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDateTime?): String? {
        return date?.toString()
    }

}