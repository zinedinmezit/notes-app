package com.example.notesapp.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.notesapp.NotesApplication
import kotlinx.coroutines.launch

class RebootBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val application = context?.applicationContext as NotesApplication
        val repository = application.repository

        val alarmManager = AlarmUtil(context)

        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {

            application.applicationScopeIO.launch {

                val notesAwaitingNotification =
                    repository.getAwaitingNotificationNotes(System.currentTimeMillis())

                notesAwaitingNotification.forEach { note ->
                    val notifyIntent =
                        Intent(context, NotificationBroadcast::class.java).let { intent ->
                            intent.putExtra("NOTIFICATION_HEADING", note.Title)
                            intent.action = note.DateTimeScheduled.toString()
                            PendingIntent.getBroadcast(
                                context, 1, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                            )
                        }
                    alarmManager.scheduleAlarmManager(
                        note.DateTimeScheduled!!,
                        notifyIntent
                    )
                }
            }
        }
    }
}