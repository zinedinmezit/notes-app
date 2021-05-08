package com.example.notesapp.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Color.*
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.example.notesapp.R
import com.example.notesapp.constants.Constants
import com.example.notesapp.databinding.FragmentCreateBinding
import com.example.notesapp.entities.Note
import com.example.notesapp.utils.AlarmUtil
import com.example.notesapp.utils.NotificationBroadcast
import com.example.notesapp.utils.TypeConverters
import com.example.notesapp.viewmodels.CreateViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CreateNoteFragment : Fragment() {

    private lateinit var alarmManager: AlarmUtil

    private val model: CreateViewModel by activityViewModels()

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentCreateBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false)

        alarmManager = AlarmUtil(requireContext())

        binding.buttonSubmit.setOnClickListener {

            val titleInput = binding.headingInput.text.toString()
            val detailsInput = binding.detailsInput.text.toString()
            val priorityInput = binding.priorityInput.text.toString()
            val dateStringInput = binding.dateInput.text.toString()
            val timeStringInput = binding.timeInput.text.toString()
            val noteColorInput = binding.colorTextView.currentTextColor

            var date: Long? = null
            var time: Long? = null
            var dateTime: Long? = null

            if (dateStringInput.isNotBlank() && timeStringInput.isNotBlank()) {
                time = timeFormat.parse(timeStringInput)?.time
                date = dateFormat.parse(dateStringInput)?.time
                val dateTimeString = "$dateStringInput $timeStringInput"

                dateTime = dateTimeFormat.parse(dateTimeString)!!.time
            }

            if (titleInput.isNotBlank()) {

                val note = Note(
                    Title = titleInput,
                    Details = detailsInput,
                    Priority = TypeConverters.stringToInt(priorityInput),
                    DateScheduled = date,
                    TimeScheduled = time,
                    DateTimeScheduled = dateTime,
                    DateScheduledString = dateStringInput,
                    DateCreated = Calendar.getInstance().timeInMillis,
                    Color = noteColorInput,
                    Status = "Created"
                )
                model.insertNote(note)

                if (dateTime != null) {

                    val notifyIntent =
                        Intent(context, NotificationBroadcast::class.java).let { intent ->
                            intent.putExtra("NOTIFICATION_HEADING", titleInput)
                            intent.action = System.currentTimeMillis().toString()
                            PendingIntent.getBroadcast(context, 1, intent, FLAG_UPDATE_CURRENT)
                        }

                    alarmManager.scheduleAlarmManager(dateTime, notifyIntent)

                }

                this.findNavController()
                    .navigate(CreateNoteFragmentDirections.actionCreateNoteToAppHome())
            } else Toast.makeText(context, "Heading can't be empty", Toast.LENGTH_SHORT).show()
        }

        binding.dateInput.setOnClickListener {
            showDatePickerDialog()
        }

        binding.timeInput.setOnClickListener {
            showTimePickerDialog()
        }

        binding.colorTextView.setOnClickListener {
            showColorPickerDialog(binding)
        }

        return binding.root
    }

    private fun showColorPickerDialog(binding: FragmentCreateBinding) {

        MaterialDialog(requireContext()).show {
            title(R.string.colorPickerHeading)
            colorChooser(Constants.noteColors) { _, color ->

                binding.colorTextView.setBackgroundColor(color)
                binding.colorTextView.setTextColor(color)
            }
        }
    }

    private fun showTimePickerDialog() {

        val newFragment = TimePickerFragment()
        newFragment.show(parentFragmentManager, "timePicker")
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment()
        newFragment.show(parentFragmentManager, "datePicker")
    }

}
