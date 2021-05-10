package com.example.notesapp

import android.app.Application
import com.example.notesapp.database.NoteRoomDatabase
import com.example.notesapp.repositories.NoteRepository
import com.example.notesapp.utils.AlarmUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class NotesApplication : Application() {

    private val DispatcherIo = Dispatchers.IO

    val applicationScopeIO by lazy { CoroutineScope(DispatcherIo + Job()) }
    val database by lazy { NoteRoomDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.noteDao()) }

}