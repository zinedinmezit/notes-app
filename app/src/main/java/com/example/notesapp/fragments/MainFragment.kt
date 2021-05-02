package com.example.notesapp.fragments


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    lateinit var binding: FragmentMainBinding

    var searchItemz : MenuItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val adapter = createNoteAdapter()

        val helper = establishItemTouchHelper(adapter)

        val recyclerView = binding.notesList
        helper.attachToRecyclerView(recyclerView)

        binding.notesList.adapter = adapter
        model.firstFragmentAppearance = true
        model.searchedNotes.observe(
            viewLifecycleOwner, {
                it?.let {
                    if(!model.firstFragmentAppearance) {
                        adapter.submitList(it)
                    }
                }
            }
        )

        model.selectedNotes.observe(
            viewLifecycleOwner, {
                it?.let {
                    adapter.submitList(it)
                }
            })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

         searchItemz = binding.topAppBar.menu.findItem(R.id.action_search)
        val searchView = searchItemz?.actionView as SearchView

        setOnMenuItemExpand(searchItemz)

        setSearchViewOnQueryListener(searchView)

        setAppBarMenuItemsClickListeners()

    }


    private fun setOnMenuItemExpand(searchItem: MenuItem?){

        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                model.firstFragmentAppearance = false
                return true
            }
        })
    }

    private fun setSearchViewOnQueryListener(searchView : SearchView){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                model.searchNotes(newText ?: "")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
    }

    private fun setAppBarMenuItemsClickListeners() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sort_priority -> {
                    model.firstFragmentAppearance = false
                    model.getNotesByPriority()
                    true
                }
                R.id.sort_date_scheduled -> {
                    model.firstFragmentAppearance = false
                    model.getNotesByScheduledDate()
                    true
                }
                R.id.sort_date_created -> {
                    model.firstFragmentAppearance = false
                    model.getNotesByCreatedDate()
                    true
                }
                R.id.sort_color -> {
                    model.firstFragmentAppearance = false
                    model.getNotesByColor()
                    true
                }
                else -> false
            }
        }
    }

    private fun establishItemTouchHelper(adapter: NoteAdapter): ItemTouchHelper = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
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

    private fun createNoteAdapter() : NoteAdapter = NoteAdapter(NoteListener { id ->
        this.findNavController()
            .navigate(MainFragmentDirections.actionAppHomeToNoteDetailsFragment(id))
    })

}


