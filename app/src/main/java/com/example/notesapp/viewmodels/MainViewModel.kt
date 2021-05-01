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

    val selectedNotes : LiveData<List<Note>>

    private val _searchedNotes = MutableLiveData<List<Note>>()
            val searchedNotes get() = _searchedNotes

    var firstFragmentAppearance : Boolean = true


    init{
        val notesDao = NoteRoomDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(notesDao)

        selectedNotes = repository.allNotes

        firstFragmentAppearance = true
    }

     fun deleteNote(note : Note){
         viewModelScope.launch {
             repository.delete(note)
         }
    }

    fun searchNotes(query : String){
        viewModelScope.launch {
            if(query.isNotBlank()){
               searchedNotes.postValue(repository.searchNote(query))
            }
            else{
                searchedNotes.postValue(repository.getNotesByPriority())
            }
        }
    }

    fun getNotesByPriority(){
        viewModelScope.launch {
            searchedNotes.postValue(repository.getNotesByPriority())
        }
    }

    fun getNotesByCreatedDate(){
        viewModelScope.launch {
            searchedNotes.postValue(repository.getNotesByCreatedDate())
        }
    }

    fun getNotesByScheduledDate(){
        viewModelScope.launch {
            searchedNotes.postValue(repository.getNotesByScheduledDate())
        }
    }

    fun getNotesByColor(){
        viewModelScope.launch {
            searchedNotes.postValue(repository.getNotesByColor())
        }
    }
}