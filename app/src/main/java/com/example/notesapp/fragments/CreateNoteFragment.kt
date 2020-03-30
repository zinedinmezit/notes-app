package com.example.notesapp.fragments

import android.annotation.SuppressLint
import android.graphics.Color.*
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
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
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
    val colors = intArrayOf(RED, GREEN, BLUE)

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentCreateBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_create,container,false)

        binding.buttonSubmit.setOnClickListener {

            val heading_input = binding.headingInput.text.toString()
            val details_input = binding.detailsInput.text.toString()
            val priority_input = binding.priorityInput.text.toString()
            val dateString_input = binding.dateInput.text.toString()
            val timeString_input = binding.timeInput.text.toString()
            val noteColor_input = binding.colorTextView.currentTextColor

            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val timeFormat = SimpleDateFormat("HH:mm",Locale.UK)

            var heading : String = " "
            var details : String? = null
            var priority : Int = 9
            var date : Long? = null
            var time : Long? = null

            if(dateString_input.isNotBlank())
            date = dateFormat.parse(dateString_input)?.time

            if(timeString_input.isNotBlank())
            time = timeFormat.parse(timeString_input)?.time

            if(details_input.isNotBlank())
                details = details_input
            if(priority_input.isNotBlank())
                priority = priority_input.toInt()

            if(heading_input.isNotBlank()){

                heading = heading_input

                val note = Note(Details = details,
                    Title = heading,
                    Priority = priority,
                    Time = time,
                    Date = date,
                    Color = noteColor_input,
                    DateCreated = Calendar.getInstance().timeInMillis,
                    TimeCreated = Calendar.getInstance().timeInMillis)
                model.insertNote(note)
                this.findNavController().navigate(CreateNoteFragmentDirections.actionCreateNoteToAppHome())
            }
            else Toast.makeText(context,"Heading can't be empty",Toast.LENGTH_SHORT).show()
        }

        binding.dateInput.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(parentFragmentManager,"datePicker")
        }

        binding.timeInput.setOnClickListener{
            val newFragment = TimePickerFragment()
            newFragment.show(parentFragmentManager,"timePicker")
        }

        binding.colorTextView.setOnClickListener {

            MaterialDialog(context!!).show {
                title(R.string.colorPickerHeading)
                colorChooser(colors){ dialog, color ->

                    binding.colorTextView.setBackgroundColor(color)
                    binding.colorTextView.setTextColor(color)
                }
            }
        }
         return binding.root
    }
}
