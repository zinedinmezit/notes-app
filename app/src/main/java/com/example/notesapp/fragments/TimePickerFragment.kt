package com.example.notesapp.fragments

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_create.*
import java.text.SimpleDateFormat
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    lateinit var ctx : Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(ctx,this,hour,minute,true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {

        val activity : Activity = ctx as Activity
        val ti : EditText = activity.timeInput
        Log.i("Time/Info","\nmin - $minute\nhours - $hourOfDay\n")
        ti.setText("$hourOfDay:$minute")

    }
}