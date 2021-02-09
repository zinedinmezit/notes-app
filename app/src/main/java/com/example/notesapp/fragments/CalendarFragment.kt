package com.example.notesapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.notesapp.R
import com.example.notesapp.adapters.CalendarAdapter
import com.example.notesapp.databinding.CalendarDayFieldBinding
import com.example.notesapp.databinding.CalendarHeaderBinding
import com.example.notesapp.databinding.FragmentCalendarBinding
import com.example.notesapp.entities.Note
import com.example.notesapp.viewmodels.CalendarViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

class CalendarFragment : Fragment() {

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        lateinit var day: CalendarDay // Will be set when this container is bound.
        val myBinding = CalendarDayFieldBinding.bind(view)

        init {
            view.setOnClickListener {
                if (day.owner == DayOwner.THIS_MONTH) {
                    selectDate(day.date)
                }
            }
        }
    }

    class MonthViewContainer(view: View) : ViewContainer(view) {
        val legendLayout : View = CalendarHeaderBinding.bind(view).legendLayout.rootView
    }

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val selectionFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private var notes : List<Note>? = null

    private val adapter = CalendarAdapter()


    private lateinit var binding : FragmentCalendarBinding
    private val model : CalendarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        binding.lifecycleOwner = this
        binding.executePendingBindings()


        val recycler = binding.planRecyclerView
        recycler.adapter = adapter

        lifecycleScope.launchWhenStarted {
            model.getScheduledNotes()
            notes = model.getScheduledNotesFromDefferable()

            val currentMonth = YearMonth.now()
            val firstMonth = currentMonth.minusMonths(10)
            val lastMonth = currentMonth.plusMonths(10)
            val firstDayOfWeek = daysOfWeekFromLocale()
            binding.appCalendar.apply {
                setup(firstMonth, lastMonth, firstDayOfWeek.first())
                scrollToMonth(currentMonth)
            }

            if (savedInstanceState == null) {
                binding.appCalendar.post {
                    // Show today's events initially.
                    selectDate(today)
                }
            }

            binding.appCalendar.dayBinder = object : DayBinder<DayViewContainer>{

                override fun bind(container: DayViewContainer, day: CalendarDay) {
                    container.day = day
                    val textView = container.myBinding.fieldDay
                    val dotView = container.myBinding.fieldStatus

                    textView.text = day.date.dayOfMonth.toString()
                    if (day.owner == DayOwner.THIS_MONTH) {
                        textView.isVisible = true
                        when (day.date) {

                            today -> {
                                textView.setBackgroundResource(R.color.primaryColor)
                                dotView.isVisible = false
                            }
                            selectedDate -> {
                                textView.setBackgroundResource(R.color.primaryColor)
                                dotView.isVisible = false
                            }
                            else -> {
                                textView.background = null
                                dotView.isVisible = notes?.any { note -> LocalDate.parse(note.DateScheduledString,selectionFormatter) == day.date } ?: false
                            }
                        }
                    } else {
                        textView.isVisible = false
                        dotView.isVisible = false
                    }

                    if (day.owner == DayOwner.THIS_MONTH) {
                        textView.setTextColor(Color.BLACK)
                    } else {
                        textView.setTextColor(Color.GRAY)
                    }
                }

                override fun create(view: View) = DayViewContainer(view)
            }

            binding.appCalendar.monthScrollListener = {
                binding.dayDisplay.text =
                    titleFormatter.format(it.yearMonth)
            }

            binding.appCalendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer>{
                override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                    if (container.legendLayout.tag == null) {
                        container.legendLayout.tag = month.yearMonth
                    }
                }

                override fun create(view: View) = MonthViewContainer(view)
            }
        }

        return binding.root
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.appCalendar.notifyDateChanged(it) }
            binding.appCalendar.notifyDateChanged(date)
            val notesToSubmit = notes?.filter { note -> LocalDate.parse(note.DateScheduledString,selectionFormatter) == selectedDate }
            adapter.submitList(notesToSubmit)
        }
    }

    private fun daysOfWeekFromLocale(): Array<DayOfWeek> {
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        var daysOfWeek = DayOfWeek.values()
        // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
        // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            daysOfWeek = rhs + lhs
        }
        return daysOfWeek
    }

}




