package com.example.notesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.CalendarNotesRecyclerviewItemBinding
import com.example.notesapp.databinding.RecyclerviewItemBinding
import com.example.notesapp.entities.Note

class CalendarAdapter : ListAdapter<Note, CalendarNoteViewHolder>(CalendarNoteDiffUtil()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarNoteViewHolder {
        return CalendarNoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CalendarNoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class CalendarNoteViewHolder(val binding : CalendarNotesRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(n : Note)
    {
        binding.myNote = n
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CalendarNoteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CalendarNotesRecyclerviewItemBinding.inflate(layoutInflater, parent, false)

            return CalendarNoteViewHolder(binding)
        }
    }
}

class CalendarNoteDiffUtil : DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}