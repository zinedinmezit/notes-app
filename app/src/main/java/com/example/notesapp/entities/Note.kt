package com.example.notesapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id")
    val Id : Int,
    @ColumnInfo(name = "Title")
    val Title : String,
    @ColumnInfo(name = "Details")
    val Details : String?,
    val Priority : Int
)