package com.g2academy.training.mvvm

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note:Note): Long

    @Update
    suspend fun updateNote(note:Note)

    @Delete
    suspend fun deleteNote(note:Note)

    @Query("SELECT * FROM table_note")
    fun getAllNotes(): Flow<List<Note>>

    @Query("DELETE FROM table_note")
    suspend fun deleteAll(): Int


}