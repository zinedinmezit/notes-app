package com.example.notesapp.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNotedetailsBinding
import com.example.notesapp.factories.NoteDetailsFactory
import com.example.notesapp.viewmodels.NoteDetailsViewModel

/**
 * A simple [Fragment] subclass.
 */
class NoteDetailsFragment : Fragment() {

    private lateinit var viewModel : NoteDetailsViewModel
    private lateinit var viewModelFactory : NoteDetailsFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = NoteDetailsFragmentArgs.fromBundle(requireArguments())

        val binding : FragmentNotedetailsBinding =
            DataBindingUtil
            .inflate(inflater,R.layout.fragment_notedetails,container,false)

        viewModelFactory = NoteDetailsFactory(args.noteId,activity?.application!!)
        viewModel = ViewModelProvider(this,viewModelFactory).get(NoteDetailsViewModel::class.java)
        binding.lifecycleOwner = this

        binding.details = viewModel

        binding.itemHeading.addTextChangedListener {
            viewModel.hasHeadingTextChanged.value = it.toString() != viewModel.note.value?.Title
        }

        binding.itemDetails.addTextChangedListener {
            viewModel.hasDetailsTextChanged.value = it.toString() != viewModel.note.value?.Details
        }

        binding.saveButton.setOnClickListener {

            val heading = binding.itemHeading.text.toString()
            val details = binding.itemDetails.text.toString()

            viewModel.updateNote(viewModel.note.value?.Id!!, heading, details)

            this.findNavController().popBackStack()
        }

        return binding.root
    }


}
