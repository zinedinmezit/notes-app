package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notesapp.entities.Note

@Database(entities = arrayOf(Note::class),version = 1,exportSchema = false)
@TypeConverters(Converter::class)
abstract class NoteRoomDatabase : RoomDatabase()  {

    abstract fun noteDao (): NoteDao

    companion object{

        @Volatile
        private var INSTANCE : NoteRoomDatabase? = null

        fun getDatabase(context : Context): NoteRoomDatabase{

            val tempInstance = INSTANCE
            if(tempInstance != null)
            {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteRoomDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }


    }
}