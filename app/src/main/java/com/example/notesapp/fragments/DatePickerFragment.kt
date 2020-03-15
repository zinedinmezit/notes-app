package com.example.notesapp.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    lateinit var ctx : Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(ctx,this,year,month,day)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
       Log.i("DATEPICKER","Radi")
    }
}