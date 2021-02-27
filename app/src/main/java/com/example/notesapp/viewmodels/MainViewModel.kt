package com.example.notesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NoteRoomDatabase
import com.example.notesapp.entities.Note
import com.example.notesapp.repositories.NoteRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository : NoteRepository

    private val _allNotes : MutableLiveData<List<Note>> = MutableLiveData()
    val allNotes : LiveData<List<Note>>
        get() = _allNotes

    init{
        val notesDao = NoteRoomDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(notesDao)
        getNotes()
    }

     fun deleteNote(note : Note){
         viewModelScope.launch {
             repository.delete(note)
         }
    }

   private fun getNotes(){
        viewModelScope.launch {
            _allNotes.postValue(repository.getNotesByPriority())
        }
    }

    fun searchNotes(query : String){
        viewModelScope.launch {
            _allNotes.postValue(repository.searchNote(query))
        }
    }

}