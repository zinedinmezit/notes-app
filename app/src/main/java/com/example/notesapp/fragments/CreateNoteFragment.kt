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
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
            val _priority = binding.priorityInput.text.toString()
            val dateString = binding.dateInput.text.toString()
            val timeString = binding.timeInput.text.toString()

            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val timeFormat = SimpleDateFormat("hh:mm")


            var date : Long? = Calendar.getInstance().timeInMillis
            var time : Long? = Calendar.getInstance().timeInMillis

            if(dateString.isNotBlank())
            date = dateFormat.parse(dateString)?.time

            if(timeString.isNotBlank())
            time = timeFormat.parse(timeString)?.time

            if(!details.isBlank() && !heading.isBlank() && !_priority.isBlank()){

                val priorityInt = _priority.toInt()
                val note = Note(Details = details, Title = heading,Priority = priorityInt,Time = time,Date = date)
                model.insertNote(note)
                this.findNavController().navigate(CreateNoteFragmentDirections.actionCreateNoteToAppHome())
            }
            else
            {
                val note = Note(
                    Title = "Empty heading",
                    Details = "...",
                    Priority = 0,
                    Time = Calendar.getInstance().timeInMillis,
                    Date = Calendar.getInstance().timeInMillis)
                model.insertNote(note)
                this.findNavController().navigate(CreateNoteFragmentDirections.actionCreateNoteToAppHome())
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
