package com.example.notesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNotedetailsBinding

/**
 * A simple [Fragment] subclass.
 */
class NoteDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentNotedetailsBinding =
            DataBindingUtil
            .inflate(inflater,R.layout.fragment_notedetails,container,false)

        return binding.root

    }

}
