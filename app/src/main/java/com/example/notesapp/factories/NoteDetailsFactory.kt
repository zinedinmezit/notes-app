package com.example.notesapp.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.viewmodels.NoteDetailsViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class NoteDetailsFactory(private val noteId : Int, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteDetailsViewModel::class.java)){
            return NoteDetailsViewModel(noteId,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}