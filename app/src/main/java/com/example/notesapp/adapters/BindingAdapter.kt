package com.example.notesapp.adapters

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.notesapp.entities.Note
import java.text.SimpleDateFormat
import java.util.*

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

@SuppressLint("SimpleDateFormat")
@BindingAdapter("DateSetter")
fun TextView.setNoteDate(item : Note?){
    item?.let {
        val date = Date(item.Date!!)
        val format = SimpleDateFormat("dd/MM/yyyy")
        text = format.format(date)
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("TimeSetter")
fun TextView.setTime(item : Note?){
    item?.let {
        val time = Date(item.Time!!)
        val format = SimpleDateFormat("HH:mm")
        text = format.format(time)
    }
}