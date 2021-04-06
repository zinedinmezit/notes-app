package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY Priority ASC")
    fun getNotesByPriorityLiveData(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY Priority ASC")
    suspend fun getNotesByPriority(): List<Note>

    @Query("SELECT * FROM note_table ORDER BY Priority DESC")
    suspend fun getNotesByPriorityDescending(): List<Note>

    @Query("SELECT * FROM note_table ORDER BY Color DESC")
    suspend fun getNotesByColor(): List<Note>

    @Query("SELECT * FROM note_table ORDER BY DateCreated DESC")
    suspend fun getNotesByCreationDate(): List<Note>

    @Query("SELECT * FROM note_table ORDER BY DateScheduled DESC")
    suspend fun getNotesByScheduledDate(): List<Note>

    @Query("SELECT * FROM note_table")
    suspend fun getNotes(): List<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table WHERE Id=:Id")
    fun getNote(Id: Int): LiveData<Note>

    @Query("SELECT * FROM note_table WHERE Title LIKE '%' || :query || '%' ORDER BY Priority ASC ")
    suspend fun searchNote(query: String): List<Note>

    @Delete
    suspend fun delete(model: Note)

    @Query("UPDATE note_table SET Title=:title, Details=:details, Status='Modified', DateCreated=:dateTime WHERE Id=:noteId")
    suspend fun updateNote(noteId: Int, title: String, details: String, dateTime: Long)

    @Query("SELECT * FROM note_table WHERE DateScheduled IS NOT NULL")
    suspend fun getScheduledNotes(): List<Note>


}