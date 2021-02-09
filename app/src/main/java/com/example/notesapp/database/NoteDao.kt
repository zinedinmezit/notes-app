package com.example.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY Priority ASC")
    fun getNotesByPriority() : LiveData<List<Note>>

     @Query("SELECT * FROM note_table")
     suspend fun getNotes() : List<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table WHERE Id=:Id")
      fun getNote(Id : Int) : LiveData<Note>

    @Delete
    suspend fun delete(model : Note)

    @Query("UPDATE note_table SET Title=:title, Details=:details WHERE Id=:noteId")
    suspend fun updateNote(noteId : Int, title : String, details : String )

    @Query("SELECT * FROM note_table WHERE DateScheduled IS NOT NULL")
    suspend fun getScheduledNotes() : List<Note>


}