package com.g2academy.training.mvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){
    abstract val noteDao : NoteDAO

    companion object{
        private var INSTANCE : NoteDatabase?= null
        fun getInstance(context: Context):NoteDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "database_note"
                    ).build()
                }
                return instance
            }
        }
    }
}