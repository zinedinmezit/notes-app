package com.example.notesapp.utils

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.notesapp.R

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(messageBody : String, appContext : Context){

    val builder = NotificationCompat.Builder(
        appContext,
        appContext.getString(R.string.note_notification_channel_id)

    )
        .setSmallIcon(R.drawable.ic_timetable)
        .setContentTitle(appContext.getString(R.string.note_notification_title))
        .setContentText(messageBody)

    notify(NOTIFICATION_ID,builder.build())
}