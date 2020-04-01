package com.example.notesapp.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNotedetailsBinding
import com.example.notesapp.factories.NoteDetailsFactory
import com.example.notesapp.viewmodels.NoteDetailsViewModel

/**
 * A simple [Fragment] subclass.
 */
class NoteDetailsFragment : Fragment() {

    private lateinit var viewModel : NoteDetailsViewModel
    private lateinit var viewModelFactory : NoteDetailsFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = NoteDetailsFragmentArgs.fromBundle(arguments!!)

        val binding : FragmentNotedetailsBinding =
            DataBindingUtil
            .inflate(inflater,R.layout.fragment_notedetails,container,false)

        viewModelFactory = NoteDetailsFactory(args.noteId,activity?.application!!)
        viewModel = ViewModelProvider(this,viewModelFactory).get(NoteDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        Log.i("NOTE_INFO","VALUES : ${viewModel.note.value}")

        binding.details = viewModel

        createChannel(
            getString(R.string.note_notification_channel_id),
            getString(R.string.note_notification_channel_name)
        )

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
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
