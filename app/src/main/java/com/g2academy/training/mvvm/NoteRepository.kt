package com.g2academy.training.mvvm

class NoteRepository(private val dao: NoteDAO){

    val notes = dao.getAllNotes()

    suspend fun insert(note:Note): Long {
        return dao.insertNote(note)
    }

    suspend fun update(note:Note){
        return dao.updateNote(note)
    }

    suspend fun delete(note:Note){
        return dao.deleteNote(note)
    }

    suspend fun deleteAll():Int{
        return dao.deleteAll()
    }
}