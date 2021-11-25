package com.khayrul.androidplayground.service

import android.app.*
import androidx.core.app.RemoteInput
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.khayrul.androidplayground.R
import com.khayrul.androidplayground.presentation.MainActivity

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

            val actionIntent = Intent(context, MainActivity::class.java)
            actionIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val pendingIntent = PendingIntent.getActivity(context, 0, actionIntent, PendingIntent.FLAG_ONE_SHOT)

            val remoteInput = RemoteInput.Builder("quick_reply")
                .setLabel("Reply")
                .build()

            val action = NotificationCompat.Action.Builder(
                R.drawable.ic_reply,
                "Reply",
                pendingIntent
            ).addRemoteInput(remoteInput).build()

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_fav)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(action)
                .setAutoCancel(true)
                .build()

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}