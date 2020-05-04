package com.example.notesapp.adapters


import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.RecyclerviewItemBinding
import com.example.notesapp.entities.Note

class NoteAdapter(private val clickListener : NoteListener): ListAdapter<Note,NoteViewHolder>(NoteDiffCallback()) {

  //  var tracker: SelectionTracker<Long>? = null


/*
    init {
        setHasStableIds(true)
    }
*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

           // tracker?.let {
                //holder.bind(clickListener,getItem(position),it.isSelected(position.toLong()))
                holder.bind(clickListener,getItem(position))

        //}
          /*  holder.itemView.setOnLongClickListener {

                it.setBackgroundColor(Color.parseColor("#ffffff"))
                Log.i("ITEM/CLICK_TEST","OKAAAAY")
                 true
            }*/

    }

//    override fun getItemId(position: Int): Long = position.toLong()


}


class NoteViewHolder(private val binding : RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root){


    /*fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): Long? = itemId
        }*/

    fun bind(clickListener : NoteListener, n : Note) //isActivated : Boolean = false
    {
       // itemView.isActivated=isActivated
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


