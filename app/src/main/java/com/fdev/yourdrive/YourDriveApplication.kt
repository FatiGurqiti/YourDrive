package com.fdev.yourdrive

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.fdev.yourdrive.common.Constant.Service.Backup.CHANNEL_ID
import com.fdev.yourdrive.common.Constant.Service.Backup.CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YourDriveApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        setupNotificationChannel()
    }

    private fun setupNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}