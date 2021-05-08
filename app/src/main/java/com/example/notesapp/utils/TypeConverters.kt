package com.example.notesapp.utils

import androidx.core.text.isDigitsOnly
import androidx.room.TypeConverter
import com.example.notesapp.constants.Constants
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

    @TypeConverter
    fun stringToInt(stringNumber : String) : Int = if(stringNumber.isNotBlank()) stringNumber.toInt() else Constants.NOTE_LOW_PRIORITY

}