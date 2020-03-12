package com.example.notesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.RecyclerviewItemBinding
import com.example.notesapp.entities.Note

class NoteAdapter(val clickListener : NoteListener): ListAdapter<Note,NoteViewHolder>(NoteDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(clickListener,getItem(position))
    }
}

class NoteViewHolder(val binding : RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(clickListener : NoteListener,n : Note)
    {
        binding.notes = n
        binding.clickListener = clickListener
        binding.executePendingBindings()

    }

    companion object {
        fun from(parent: ViewGroup): NoteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewItemBinding.inflate(layoutInflater, parent, false)

            return NoteViewHolder(binding)
        }
    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.Id==newItem.Id
    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem==newItem
}

class NoteListener(val clickListener: (noteId : Int) -> Unit){
    fun onClick(note : Note) = clickListener(note.Id)
}