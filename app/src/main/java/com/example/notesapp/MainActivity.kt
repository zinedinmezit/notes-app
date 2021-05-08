package com.example.notesapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
       val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
            bottomNav.setupWithNavController(navController)
            bottomNav.setOnNavigationItemReselectedListener {  }

        createChannel(
            getString(R.string.note_notification_channel_id),
            getString(R.string.note_notification_channel_name)
        )

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

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )

            notificationManager?.createNotificationChannel(notificationChannel)
        }
    }
}
