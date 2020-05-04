package com.example.notesapp.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.*
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.drawable.DrawableCompat
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
import kotlinx.android.synthetic.main.fragment_create.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CreateNoteFragment : Fragment() {

    private val model : CreateViewModel by activityViewModels()
    private val colors = intArrayOf(
                argb(255,55,187,125),
                argb(255,92,165,66),
                argb(255,113,113,170),
                argb(255,173,78,169),
                argb(255,195,83,84))
   private val NOTE_LOW_PRIORITY : Int = 9

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
            var priority : Int = NOTE_LOW_PRIORITY
            var date : Long? = null
            var time : Long? = null
            var dateTime : Long? = null

            if(dateString_input.isNotBlank() && timeString_input.isNotBlank()) {
                time = timeFormat.parse(timeString_input)?.time
                date = dateFormat.parse(dateString_input)?.time
                val dateTimeString = "$dateString_input $timeString_input"
                val format = SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.UK)
                dateTime = format.parse(dateTimeString)!!.time
            }




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

                if(dateTime != null) {

                    createChannel(
                        getString(R.string.note_notification_channel_id),
                        getString(R.string.note_notification_channel_name)
                    )

                    val alarmManager =
                        context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
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
            notificationChannel.lightColor = Color.RED
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
