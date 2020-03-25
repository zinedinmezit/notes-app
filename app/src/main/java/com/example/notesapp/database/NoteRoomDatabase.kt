package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notesapp.entities.Note

@Database(entities = arrayOf(Note::class),version = 4,exportSchema = false)
abstract class NoteRoomDatabase : RoomDatabase()  {

    abstract fun noteDao (): NoteDao



    companion object{
        private val MIGRATION_1_2 : Migration = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE note_table ADD COLUMN Date INTEGER")
                database.execSQL("ALTER TABLE note_table ADD COLUMN Time INTEGER")
            }
        }

        private val MIGRATION_2_3 : Migration = object : Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE note_table ADD COLUMN Color INTEGER")
            }
        }

        private val MIGRATION_3_4 : Migration = object : Migration(3,4){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE note_table ADD COLUMN DateCreated INTEGER")
                database.execSQL("ALTER TABLE note_table ADD COLUMN TimeCreated INTEGER")            }
        }

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
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()
                INSTANCE = instance
                return instance
            }

        }


    }
}