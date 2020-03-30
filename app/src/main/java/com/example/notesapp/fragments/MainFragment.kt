package com.example.notesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.adapters.NoteAdapter
import com.example.notesapp.adapters.NoteListener
import com.example.notesapp.databinding.FragmentMainBinding
import com.example.notesapp.viewmodels.MainViewModel

class MainFragment : Fragment() {

    private val model : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,container,false)

        val adapter = NoteAdapter(NoteListener { id ->
            this.findNavController().navigate(MainFragmentDirections.actionAppHomeToNoteDetailsFragment(id)) })

        binding.notesList.adapter = adapter

        model.allNotes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

            return binding.root
        }
    }


