package com.example.notesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notesapp.database.NoteRoomDatabase
import com.example.notesapp.entities.Note
import com.example.notesapp.repositories.NoteRepository

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository : NoteRepository

    val allNotes : LiveData<List<Note>>

    init{
        val notesDao = NoteRoomDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(notesDao)
        allNotes = repository.allNotes
    }

}