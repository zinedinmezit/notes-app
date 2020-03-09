package com.example.notesapp.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.notesapp.entities.Note

@BindingAdapter("TitleSetter")
fun TextView.setNoteTitle(item : Note?){

    item?.let {
        text = item.Title
    }
}

@BindingAdapter("DetailsSetter")
fun TextView.setNoteDetails(item : Note?){

    item?.let {
        text = item.Details
    }
}

@BindingAdapter("PrioritySetter")
fun TextView.setNotePriority(item : Note?){

    item?.let {
        text = item.Priority.toString()
    }
}