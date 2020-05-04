package com.example.notesapp.utils

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.adapters.NoteAdapter
import com.example.notesapp.adapters.NoteViewHolder

/*
class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as NoteViewHolder)
                .getItemDetails()
        }
        return null
    }
}*/
