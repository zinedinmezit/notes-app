package com.example.notesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NoteRoomDatabase
import com.example.notesapp.entities.Note
import com.example.notesapp.noterepository.NoteRepository
import kotlinx.coroutines.launch

class CreateViewModel(application: Application): AndroidViewModel(application) {

    private val repository : NoteRepository

    init{
        val notesDao = NoteRoomDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(notesDao)
    }

    fun insertNote(note : Note)
    {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

}