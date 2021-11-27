package com.khayrul.androidplayground.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.khayrul.androidplayground.R
import com.khayrul.androidplayground.core.Constants
import com.khayrul.androidplayground.domain.model.WearableNotification
import android.content.ComponentName
import android.provider.Settings
import android.service.notification.NotificationListenerService
import com.khayrul.androidplayground.service.NotificationListener


class NotificationUtils {
    companion object{

        fun createNotificationChannel(context: Context) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                    enableLights(true)
                }

                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
        }

        fun getNotificationBuilder(context: Context): NotificationCompat.Builder {
            return NotificationCompat.Builder(context, Constants.CHANNEL_ID)
        }

        fun createNotification(context: Context, title: String, text: String) {
            val notificationManager = NotificationManagerCompat.from(context)
            val notification = getNotificationBuilder(context)
                .setSmallIcon(R.drawable.ic_fav)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            notificationManager.notify(Constants.NOTIFICATION_ID, notification)
        }

        fun getWearableNotification(sbn: StatusBarNotification?): WearableNotification? {
            return sbn?.notification?.let { notification ->
                val wearableExt = NotificationCompat.WearableExtender(notification)

                val actions = notification.actions ?: return null

                val remoteInputs = ArrayList<android.app.RemoteInput>(actions.size)
                var pendingIntent: PendingIntent? = null

                for(action in actions) {
                    action?.remoteInputs?.let { _remoteInputs ->
                        for(remoteInput in _remoteInputs) {
                            remoteInputs.add(remoteInput as android.app.RemoteInput)
                            Log.d(Constants.TAG, "Found an RemoteInput")
                            pendingIntent = action.actionIntent
                        }
                    }
                }
                WearableNotification(
                    sbn.tag,
                    sbn.packageName,
                    pendingIntent,
                    remoteInputs.toList(),
                    notification.extras
                )
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
    }
}