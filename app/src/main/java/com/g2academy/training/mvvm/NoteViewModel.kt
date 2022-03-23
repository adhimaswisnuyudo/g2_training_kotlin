package com.g2academy.training.mvvm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel(){

    private var isUpdateOrDelete = false
    private lateinit var noteToUpdateOrDelete : Note

    val inputTitle = MutableLiveData<String>()
    val inputBody = MutableLiveData<String>()
    val saveButtonText = MutableLiveData<String>()
    val clearButtonText = MutableLiveData<String>()

    init {
        saveButtonText.value = "Save"
        clearButtonText.value = "Clear"
        Log.d("TESTING","NoteModelView Initial...")
    }

    fun initUpdateAndDelete(note:Note){
        inputTitle.value = note.title
        inputBody.value = note.body
        isUpdateOrDelete = true
        noteToUpdateOrDelete = note
        saveButtonText.value = "Update"
        clearButtonText.value = "Delete"
        Log.d("TESTING","NoteModelView initUpdateAndDelete")
    }

    private fun insertNote(note: Note) = viewModelScope.launch {
        val newData = repository.insert(note)
        Log.d("TESTING","NoteModelView insertNote, ID: "+newData.toString())
    }

    private fun updateNote(note:Note) = viewModelScope.launch {
        val updateData = repository.update(note)
        inputTitle.value = ""
        inputBody.value = ""
        isUpdateOrDelete = false
        saveButtonText.value = "Save"
        clearButtonText.value = "Clear"
        Log.d("TESTING","NoteModelView updateNote "+updateData.toString())
    }

    private fun deleteNote(note:Note) = viewModelScope.launch {
        val deletedData = repository.delete(note)
        inputTitle.value = ""
        inputBody.value = ""
        isUpdateOrDelete = false
        saveButtonText.value = "Save"
        clearButtonText.value = "Clear"
        Log.d("TESTING","NoteModelView deleteNote "+deletedData.toString())

    }

    private fun deleteAll() = viewModelScope.launch {
        val deletedData = repository.deleteAll()
        Log.d("TESTING","NoteModelView deleteAll "+deletedData.toString())
    }

    fun getAllNotes() = liveData {
        repository.notes.collect {
            emit(it)
            Log.d("TESTING","NoteModelView getAllNotes"+ it.toString())
        }
    }

    fun saveOrUpdate(){
        if(inputTitle.value == null){
            Log.d("TESTING","NoteModelView saveOrUpdate inputTitle Blank")
        }
        else if(inputBody.value == null){
            Log.d("TESTING","NoteModelView saveOrUpdate inputBody Blank")
        }
        else{
            if(isUpdateOrDelete){
                noteToUpdateOrDelete.title = inputTitle.value!!
                noteToUpdateOrDelete.body = inputBody.value!!
                updateNote(noteToUpdateOrDelete)
                Log.d("TESTING","NoteModelView updateNote")
            }
            else{
                val title = inputTitle.value!!
                val body = inputBody.value!!
                insertNote(Note(0,title,body))
                inputTitle.value = ""
                inputBody.value = ""
                Log.d("TESTING","NoteModelView insertNote")
            }
        }
    }

    fun deleteOrDeleteAll(){
        if(isUpdateOrDelete){
            deleteNote(noteToUpdateOrDelete)
            Log.d("TESTING","deleteOrDeleteAll deleteNote")
        }
        else{
            deleteAll()
            Log.d("TESTING","deleteOrDeleteAll deleteAll")
        }
    }
}