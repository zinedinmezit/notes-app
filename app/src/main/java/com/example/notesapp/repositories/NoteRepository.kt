package com.example.notesapp.repositories

import androidx.lifecycle.LiveData
import com.example.notesapp.database.NoteDao
import com.example.notesapp.entities.Note

class NoteRepository(private val noteDao : NoteDao) {

    val allNotes = noteDao.getNotesByPriorityLiveData()

    suspend fun insert(note : Note){
        noteDao.insert(note)
    }

    /*suspend fun getNotesByPriority() : List<Note>?{
        return noteDao.getNotesByPriority()
    }*/

    suspend fun delete(note : Note){
        noteDao.delete(note)
    }

      fun getNote(noteId : Int) : LiveData<Note>{
       return noteDao.getNote(noteId)
    }

    suspend fun getNotes() : List<Note>{
        return noteDao.getNotes()
    }

    suspend fun getNotesByPriority() : List<Note>{
        return noteDao.getNotesByPriority()
    }

    suspend fun getNotesByScheduledDate() : List<Note>{
        return noteDao.getNotesByScheduledDate()
    }

    suspend fun getNotesByCreatedDate() : List<Note>{
        return noteDao.getNotesByCreationDate()
    }

    suspend fun getNotesByColor() : List<Note>{
        return noteDao.getNotesByColor()
    }

    suspend fun getScheduledNotes() : List<Note>{
        return noteDao.getScheduledNotes()
    }

    suspend fun updateNote(noteId : Int, heading : String, details : String, dateTime : Long){
        noteDao.updateNote(noteId,heading,details, dateTime)
    }

    suspend fun searchNote(query : String) : List<Note>{
       return noteDao.searchNote(query)
    }

}