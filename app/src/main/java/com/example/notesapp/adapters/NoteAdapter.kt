package com.example.notesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.entities.Note
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class NoteAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is NoteViewHolder -> holder.bind(data[position])
        }
    }
}

class NoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    val noteHeading = itemView.note_heading
    val noteDetails = itemView.note_text
    val notePriority = itemView.note_priority

    fun bind(n : Note)
    {
        noteHeading.text = n.Title
        noteDetails.text = n.Details
        notePriority.text = n.Priority.toString()
    }
}