package com.khayrul.androidplayground.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.khayrul.androidplayground.R

class NotificationManager {
    companion object {
        val CHANNEL_ID = "default_channel_id"
        val CHENNEL_NAME = "default_channel_name"
        val NOTIFICATION_ID = 0

        private fun createNotificationChannel(context: Context) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(CHANNEL_ID, CHENNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                    enableLights(true)
                }

                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
        }

        fun createNotification(context: Context, title: String, text: String) {
            createNotificationChannel(context)
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_fav)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}