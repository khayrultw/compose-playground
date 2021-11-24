package com.khayrul.androidplayground.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat

class NotificationListener : NotificationListenerService() {

    val TAG = "NotificationListener"

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        sbn?.notification?.let {
            val wearNotification = NotificationCompat.WearableExtender(it)
            val actions = wearNotification.actions
            Log.d(TAG, actions.size.toString())
        }
    }
}