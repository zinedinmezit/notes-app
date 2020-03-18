package com.example.notesapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentCreateBinding
import com.example.notesapp.entities.Note
import com.example.notesapp.viewmodels.CreateViewModel
import kotlinx.android.synthetic.main.fragment_create.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CreateNoteFragment : Fragment() {

    private val model : CreateViewModel by activityViewModels()


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentCreateBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_create,container,false)

        binding.buttonSubmit.setOnClickListener {
            val heading = binding.headingInput.text.toString()
            val details = binding.detailsInput.text.toString()
            val priority = binding.priorityInput.text.toString().toInt()

            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val timeFormat = SimpleDateFormat("hh:mm")
            val dateString = binding.dateInput.text.toString()
            val timeString = binding.timeInput.text.toString()
            val date : Long = dateFormat.parse(dateString)?.time ?: Calendar.getInstance().timeInMillis
            val time : Long = timeFormat.parse(timeString)?.time ?: Calendar.getInstance().time.time

            if(!details.isBlank() && !heading.isBlank()) {
                val note: Note = Note(Details = details, Title = heading,Priority = priority,Time = time,Date = date)
                model.insertNote(note)
            }
        }

        binding.dateInput.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(parentFragmentManager,"datePicker")
        }

        binding.timeInput.setOnClickListener{
            val newFragment = TimePickerFragment()
            newFragment.show(parentFragmentManager,"timePicker")
        }

         return binding.root
    }
}
