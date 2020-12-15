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
import com.example.notesapp.databinding.FragmentCreateBinding
import com.example.notesapp.entities.Note
import com.example.notesapp.utils.NotificationBroadcast
import com.example.notesapp.viewmodels.CreateViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CreateNoteFragment : Fragment() {

    private val model : CreateViewModel by activityViewModels()
    private val colors = intArrayOf(
                argb(255,55,187,125),
                argb(255,51,156,180),
                argb(255,113,113,170),
                argb(255,195,83,84),
                argb(255,201,105,157),
                argb(255,217,108,47)
                )

    private val NOTE_LOW_PRIORITY : Int = 9

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentCreateBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_create,container,false)


        binding.buttonSubmit.setOnClickListener {

            val headingInput = binding.headingInput.text.toString()
            val detailsInput = binding.detailsInput.text.toString()
            val priorityInput = binding.priorityInput.text.toString()
            val dateStringInput = binding.dateInput.text.toString()
            val timeStringInput = binding.timeInput.text.toString()
            val noteColorInput = binding.colorTextView.currentTextColor

            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val timeFormat = SimpleDateFormat("HH:mm",Locale.UK)

            val heading: String?
            var details : String? = null
            var priority : Int = NOTE_LOW_PRIORITY
            var date : Long? = null
            var time : Long? = null
            var dateTime : Long? = null

            if(dateStringInput.isNotBlank() && timeStringInput.isNotBlank()) {
                time = timeFormat.parse(timeStringInput)?.time
                date = dateFormat.parse(dateStringInput)?.time
                val dateTimeString = "$dateStringInput $timeStringInput"
                val format = SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.getDefault())
                dateTime = format.parse(dateTimeString)!!.time
            }




            if(detailsInput.isNotBlank())
                details = detailsInput
            if(priorityInput.isNotBlank())
                priority = priorityInput.toInt()

            if(headingInput.isNotBlank()){

                heading = headingInput

                val note = Note(Details = details,
                    Title = heading,
                    Priority = priority,
                    TimeScheduled = time,
                    DateScheduledString = dateStringInput,
                    DateScheduled = date,
                    Color = noteColorInput,
                    DateCreated = Calendar.getInstance().timeInMillis)
                model.insertNote(note)

                if(dateTime != null) {

                    createChannel(
                        getString(R.string.note_notification_channel_id),
                        getString(R.string.note_notification_channel_name)
                    )

                    val alarmManager =
                        context?.getSystemService(ALARM_SERVICE) as AlarmManager
                    val notifyIntent  =
                        Intent(context, NotificationBroadcast::class.java).let { intent ->
                            intent.putExtra("NOTIFICATION_HEADING",heading)
                            intent.action = System.currentTimeMillis().toString()
                            PendingIntent.getBroadcast(context, 1, intent, FLAG_UPDATE_CURRENT)
                        }

                    setAlarmManager(alarmManager,dateTime,notifyIntent)

                }

                this.findNavController().navigate(CreateNoteFragmentDirections.actionCreateNoteToAppHome())
            }
            else Toast.makeText(context,"Heading can't be empty",Toast.LENGTH_SHORT).show()
        }

        binding.dateInput.setOnClickListener {
           showDatePickerDialog()
        }

        binding.timeInput.setOnClickListener{
            showTimePickerDialog()
        }

        binding.colorTextView.setOnClickListener {
            showColorPickerDialog(binding)
        }

         return binding.root
    }

    private fun createChannel(channelId : String, channelName : String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "You got things to do"

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )

            notificationManager?.createNotificationChannel(notificationChannel)
        }
    }

    private fun showColorPickerDialog(binding : FragmentCreateBinding){

        MaterialDialog(requireContext()).show {
            title(R.string.colorPickerHeading)
            colorChooser(colors){ _, color ->

                binding.colorTextView.setBackgroundColor(color)
                binding.colorTextView.setTextColor(color)
            }
        }
    }

    private fun showTimePickerDialog(){

        val newFragment = TimePickerFragment()
        newFragment.show(parentFragmentManager,"timePicker")
    }

    private fun showDatePickerDialog(){
        val newFragment = DatePickerFragment()
        newFragment.show(parentFragmentManager,"datePicker")
    }

    private fun setAlarmManager(alarmManager : AlarmManager, dateTime : Long, notifyIntent: PendingIntent)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (dateTime - System.currentTimeMillis()),
                notifyIntent
            )
        }
        else{
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (dateTime - System.currentTimeMillis()),
                notifyIntent
            )
        }
    }
}
