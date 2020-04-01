package com.example.notesapp.viewmodels

import android.app.Application
import android.app.NotificationManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NoteRoomDatabase
import com.example.notesapp.entities.Note
import com.example.notesapp.fragments.NoteDetailsFragmentArgs
import com.example.notesapp.repositories.NoteRepository
import com.example.notesapp.utils.sendNotification
import kotlinx.coroutines.launch

class NoteDetailsViewModel(noteId : Int,application: Application) : ViewModel() {

    private val repository : NoteRepository
    val note : LiveData<Note>

    init {
        val noteDao = NoteRoomDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
            note = repository.getNote(noteId)

        val notificationManager = ContextCompat.getSystemService(
            application,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification("aaaa",application)

    }

}