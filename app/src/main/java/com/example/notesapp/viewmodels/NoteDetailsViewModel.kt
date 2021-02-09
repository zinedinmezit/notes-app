package com.example.notesapp.viewmodels

import android.app.Application
import android.app.NotificationManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.notesapp.database.NoteRoomDatabase
import com.example.notesapp.entities.Note
import com.example.notesapp.fragments.NoteDetailsFragmentArgs
import com.example.notesapp.repositories.NoteRepository
import com.example.notesapp.utils.sendNotification
import kotlinx.coroutines.launch

class NoteDetailsViewModel(noteId : Int,application: Application) : ViewModel() {

    private val repository : NoteRepository
    val note : LiveData<Note>

    var hasHeadingTextChanged : MutableLiveData<Boolean> = MutableLiveData(false)
    var hasDetailsTextChanged : MutableLiveData<Boolean> = MutableLiveData(false)


    init {
        val noteDao = NoteRoomDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
            note = repository.getNote(noteId)

    }

    fun updateNote(noteId : Int, heading : String, details : String, dateTime : Long){
        viewModelScope.launch {
            repository.updateNote(noteId,heading,details, dateTime)
        }
    }

}