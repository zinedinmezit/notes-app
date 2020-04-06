package com.example.notesapp.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat

class NotificationBroadcast : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

        val notificationManager = p0?.let {
            ContextCompat.getSystemService(
                it,
                NotificationManager::class.java
            )
        }

        notificationManager?.sendNotification("aaaa",p0) ?: Log.i("SEND_NOTIFICATION","Not working")
    }
}