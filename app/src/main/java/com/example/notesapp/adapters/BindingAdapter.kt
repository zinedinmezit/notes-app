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
        text = if(item.Title.isBlank()) "not defined"
               else item.Title
    }
}

@BindingAdapter("DetailsSetter")
fun TextView.setNoteDetails(item : Note?){

    item?.let {
        text = item.Details ?: " "
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
        text = if(item.DateScheduled != null) {
            val date = Date(item.DateScheduled)
            val format = SimpleDateFormat("dd/MM/yyyy")
            format.format(date)
        }
        else "not defined"
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("TimeSetter")
fun TextView.setTime(item : Note?){
    item?.let {
        text = if(item.TimeScheduled != null) {
            val time = Date(item.TimeScheduled)
            val format = SimpleDateFormat("HH:mm", Locale.UK)
            format.format(time)
        }
        else "not defined"
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("DateCreatedSetter")
fun TextView.setCreatedNoteDate(item : Note?){
    item?.let {
        val date = Date(item.DateCreated!!)
        val format = SimpleDateFormat("dd/MM/yyyy")
        text = format.format(date)
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("TimeCreatedSetter")
fun TextView.setCreatedTime(item : Note?){
    item?.let {
        val time = Date(item.DateCreated!!)
        val format = SimpleDateFormat("HH:mm",Locale.UK)
        text = format.format(time)
    }
}