package com.example.notesapp.viewmodels

import android.app.Application
import android.media.Image
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NoteRoomDatabase
import com.example.notesapp.entities.Note
import com.example.notesapp.repositories.NoteRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch

class CalendarViewModel(app : Application) : AndroidViewModel(app) {

    private val repository : NoteRepository

    private val notesDefferable : CompletableDeferred<List<Note>> = CompletableDeferred()

    init {
        val dao = NoteRoomDatabase.getDatabase(app).noteDao()
        repository = NoteRepository((dao))
        getAllNotes()
    }

    private fun getAllNotes(){
        viewModelScope.launch {
            notesDefferable.complete(repository.getNotes())
        }
    }

    suspend fun getNotesFromDefferable() : List<Note> = notesDefferable.await()

}