package com.example.notesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.notesapp.R
import com.example.notesapp.adapters.CalendarAdapter
import com.example.notesapp.databinding.CalendarDayFieldBinding
import com.example.notesapp.databinding.CalendarHeaderBinding
import com.example.notesapp.databinding.FragmentCalendarBinding
import com.example.notesapp.entities.Note
import com.example.notesapp.viewmodels.MainViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*


//TODO Sortirati notes putem mapa kako bi se jednostavnije izvele radnje rasporedjivanja
class CalendarFragment : Fragment() {

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val selectionFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")

    private val currentMonth: YearMonth = YearMonth.now()
    private val firstMonth: YearMonth = currentMonth.minusMonths(10)
    private val lastMonth: YearMonth = currentMonth.plusMonths(10)
    private val firstDayOfWeek = daysOfWeekFromLocale()


    private var notes: List<Note>? = null


    private val adapter = CalendarAdapter()


    private lateinit var binding: FragmentCalendarBinding
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)
        binding.lifecycleOwner = this


        model.selectedNotes.observe(viewLifecycleOwner, {
            notes = it.filter { n -> n.DateScheduledString.isNotBlank() }
        })

        val recycler = binding.planRecyclerView
        recycler.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding.appCalendar.dayBinder = object : DayBinder<DayViewContainer> {

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
                            dotView.isVisible = notes?.any { note ->
                                LocalDate.parse(
                                    note.DateScheduledString,
                                    selectionFormatter
                                ) == day.date
                            } ?: false
                        }
                    }
                } else {
                    textView.isVisible = false
                    dotView.isVisible = false
                }
            }

            override fun create(view: View) = DayViewContainer(view)
        }

        binding.appCalendar.monthScrollListener = {
            binding.dayDisplay.text =
                titleFormatter.format(it.yearMonth)
        }

        binding.appCalendar.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                    if (container.legendLayout.tag == null) {
                        container.legendLayout.tag = month.yearMonth
                    }
                }

                override fun create(view: View) = MonthViewContainer(view)
            }
    }


    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.appCalendar.notifyDateChanged(it) }
            binding.appCalendar.notifyDateChanged(date)
            val notesToSubmit = notes?.filter { note ->
                LocalDate.parse(
                    note.DateScheduledString,
                    selectionFormatter
                ) == selectedDate
            }
            adapter.submitList(notesToSubmit)
            binding.textView.text = getString(R.string.scheduled_notes_date,selectionFormatter.format(date))
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

    inner class MonthViewContainer(view: View) : ViewContainer(view) {
        val legendLayout: View = CalendarHeaderBinding.bind(view).legendLayout.rootView
    }

}




