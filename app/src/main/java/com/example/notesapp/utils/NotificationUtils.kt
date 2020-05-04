package com.example.notesapp.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import java.text.SimpleDateFormat
import java.util.*

fun createID() : Int{
    val date : Date = Date()
    return SimpleDateFormat("ddHHmmss",Locale.US).format(date).toInt()
}


fun NotificationManager.sendNotification(messageBody : String, appContext : Context){

    val notifyIntent = Intent(appContext, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val notifyPendingIntent = PendingIntent.getActivity(
        appContext, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )



    val builder = NotificationCompat.Builder(
        appContext,
        appContext.getString(R.string.note_notification_channel_id)

    )
        .setSmallIcon(R.drawable.ic_timetable)
        .setContentTitle(appContext.getString(R.string.note_notification_title))
        .setContentText(messageBody)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(notifyPendingIntent)
        .setAutoCancel(true)

    NotificationManagerCompat.from(appContext).apply {
        notify(createID(),builder.build())
    }

}