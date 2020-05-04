package com.example.notesapp.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.adapters.NoteAdapter
import com.example.notesapp.adapters.NoteListener
import com.example.notesapp.databinding.FragmentMainBinding
//import com.example.notesapp.utils.MyItemDetailsLookup
import com.example.notesapp.viewmodels.MainViewModel


class MainFragment : Fragment() {

    private val model : MainViewModel by activityViewModels()
//    private var tracker: SelectionTracker<Long>? = null
//     var mActionMode: ActionMode? = null


    /*private val actionModeCallback = object : ActionMode.Callback {
        // Called when the action mode is created; startActionMode() was called
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.contextual_action_mode, menu)
            return true
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.delete_notes -> {
                    mode.finish() // Action picked, so close the CAB
                    true
                }
                else -> false
            }
        }

        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {
            mActionMode = null
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,container,false)

        val adapter = NoteAdapter(NoteListener { id ->
            this.findNavController().navigate(MainFragmentDirections.actionAppHomeToNoteDetailsFragment(id)) })

        binding.notesList.adapter = adapter

        model.allNotes.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    adapter.submitList(it)
                 }
            })

       /* val recyclerView = binding.notesList

        tracker = buildTracker(recyclerView)
        addTrackerObserver(tracker)

        adapter.tracker = tracker*/

            return binding.root
        }


        //Need better place
        /*fun buildTracker(recyclerView : RecyclerView): SelectionTracker<Long> = SelectionTracker.Builder<Long>(
            "mySelection",
            recyclerView,
            StableIdKeyProvider(recyclerView),
            MyItemDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        private fun addTrackerObserver(tracker : SelectionTracker<Long>?){

            tracker?.addObserver(
                object : SelectionTracker.SelectionObserver<Long>() {
                    override fun onSelectionChanged() {
                        super.onSelectionChanged()
                        if(mActionMode == null)
                            mActionMode = activity?.startActionMode(actionModeCallback)
                    }
                })

        }*/

    }


