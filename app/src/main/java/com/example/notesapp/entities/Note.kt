package com.example.notesapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id")
    val Id : Int = 0,
    @ColumnInfo(name = "Title")
    val Title : String = "Default heading",
    @ColumnInfo(name = "Details")
    val Details : String? = "Default details",
    val Priority : Int = 1
)