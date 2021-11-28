package com.khayrul.androidplayground.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.khayrul.androidplayground.R
import com.khayrul.androidplayground.core.constants.Constants
import android.content.ComponentName
import android.content.Intent
import android.provider.Settings
import android.util.Log


class NotificationServiceManager private constructor() {

    companion object {

        fun createNotificationChannel(context: Context) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                    enableLights(true)
                }

                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
        }

        fun notificationListenerEnabled(context: Context): Boolean {
            val cn = ComponentName(context, NotificationListener::class.java)
            val flat = Settings.Secure.getString(
                context.contentResolver,
                "enabled_notification_listeners"
            )
            return flat != null && flat.contains(cn.flattenToString())
        }

        fun startForegroundTestService(context: Context) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = Intent(context, ForegroundNotificationService::class.java)
                context.startForegroundService(intent)
            }
        }

        fun stopForegroundTestService(context: Context) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = Intent(context, ForegroundNotificationService::class.java)
                context.stopService(intent)

                val intentListener = Intent(context, NotificationListener::class.java)
                context.stopService(intentListener)
            }
        }

        fun askNotificationListenerPerm(context: Context) {
            if(notificationListenerEnabled(context)) {
                Log.d(Constants.TAG, "Enabled")
                return
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                context.startActivity(intent)
            }
        }

        fun createNotification(context: Context, title: String, text: String) {
            val notification = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_fav)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            NotificationManagerCompat.from(context).notify(Constants.NOTIFICATION_ID, notification)
        }
    }
}