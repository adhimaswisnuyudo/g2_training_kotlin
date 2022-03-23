package com.g2academy.training

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.g2academy.training.databinding.ActivityNoteBinding
import com.g2academy.training.mvvm.*

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    private lateinit var noteViewModel : NoteViewModel
    private lateinit var adapter : NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_note)
        val dao = NoteDatabase.getInstance(application).noteDao
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this,factory).get(NoteViewModel::class.java)
        binding.noteViewModel = noteViewModel
        binding.lifecycleOwner  = this
        initRecycleView()
    }

    private fun displayNotes(){
        noteViewModel.getAllNotes().observe(this) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun listItemClicked(note: Note){
        noteViewModel.initUpdateAndDelete(note)
    }

    private fun initRecycleView(){
        binding.noteRecycleView.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter({selectedItem:Note->listItemClicked(selectedItem)})
        binding.noteRecycleView.adapter = adapter
        displayNotes()
    }

}