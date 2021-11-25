package com.khayrul.androidplayground.service

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat

class NotificationListener : NotificationListenerService() {

    val TAG = "NotificationListener"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        sbn?.notification?.let {
            val wearNotification = NotificationCompat.WearableExtender(it)
            val actions = wearNotification.actions
            it.actions[0].actionIntent.send(this, 0, Intent())
            Log.d(TAG, it.actions[0].remoteInputs.size.toString())
        }
    }
}