package com.g2academy.training.mvvm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.g2academy.training.R
import com.g2academy.training.databinding.NoteAdapterBinding

class NoteHolder(val binding : NoteAdapterBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(note: Note, clickListener:(Note)->Unit){
        binding.txtTitle.text = note.title
        binding.txtBody.text = note.body
        binding.listItemLayout.setOnClickListener{
            clickListener(note)
        }
    }
}

class NoteAdapter(private val clickListener: (Note) -> Unit):
    RecyclerView.Adapter<NoteHolder>() {
        private val noteList = ArrayList<Note>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding : NoteAdapterBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.note_adapter,parent,false)
            return NoteHolder(binding)
        }
        override fun getItemCount(): Int {
            return noteList.size
        }
        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            holder.bind(noteList[position],clickListener)
        }
        fun setList(notes : List<Note>){
            noteList.clear()
            noteList.addAll(notes)
        }
    }
