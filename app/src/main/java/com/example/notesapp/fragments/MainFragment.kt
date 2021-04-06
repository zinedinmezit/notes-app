package com.example.notesapp.fragments


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.adapters.NoteAdapter
import com.example.notesapp.adapters.NoteListener
import com.example.notesapp.databinding.FragmentMainBinding
import com.example.notesapp.entities.Note
import com.example.notesapp.viewmodels.MainViewModel


class MainFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()

    lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val adapter = NoteAdapter(NoteListener { id ->
            this.findNavController()
                .navigate(MainFragmentDirections.actionAppHomeToNoteDetailsFragment(id))
        })

        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val position = viewHolder.bindingAdapterPosition
                    val myNote: Note = adapter.getNoteAtPosition(position)

                    model.deleteNote(myNote)
                }
            })

        val recyclerView = binding.notesList
        helper.attachToRecyclerView(recyclerView)

        binding.notesList.adapter = adapter

        model.selectedNotes.observe(
            viewLifecycleOwner, {
                it?.let {
                    adapter.submitList(it)
                }
            })

        model.searchedNotes.observe(
            viewLifecycleOwner,{
                it?.let{
                    adapter.submitList(it)
                }
            }
        )

        /*searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                Log.i("searchQueryTest","COLLAPSED")
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                Log.i("searchQueryTest","EXPANDED")
                return true
            }
        })*/

        Log.i("lcTest", "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val searchItem = binding.topAppBar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("searchQueryTest","onQueryTextChange")
                model.searchNotes(newText ?: "")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("searchQueryTest","onTextSubmit")
                return true
            }
        })

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sort_priority -> {
                    Log.i("searchQueryTest","PRIO")
                    model.getNotesByPriority()
                    true
                }
                R.id.sort_date_scheduled -> {
                    Log.i("searchQueryTest","SCHE")
                    model.getNotesByScheduledDate()
                    true
                }
                R.id.sort_date_created -> {
                    Log.i("searchQueryTest","CRE")
                    model.getNotesByCreatedDate()
                    true
                }
                R.id.sort_color -> {
                    Log.i("searchQueryTest","COL")
                    model.getNotesByColor()
                    true
                }
                else -> false
            }
        }
    }
    
}


