package com.example.notesapp.repositories

import androidx.lifecycle.LiveData
import com.example.notesapp.database.NoteDao
import com.example.notesapp.entities.Note

class NoteRepository(private val noteDao : NoteDao) {

    val allNotes : LiveData<List<Note>> = noteDao.getNotesByPriority()

    suspend fun insert(note : Note){
        noteDao.insert(note)
    }

    suspend fun delete(note : Note){
        noteDao.delete(note)
    }

      fun getNote(noteId : Int) : LiveData<Note>{
       return noteDao.getNote(noteId)
    }
}