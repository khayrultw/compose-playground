package com.khayrul.androidplayground.service

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.khayrul.androidplayground.util.NotificationUtils
import java.lang.Exception

class NotificationListener : NotificationListenerService() {

    val TAG = "NotificationListener"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        val notificationExt = NotificationUtils.getNotificationExtender(sbn) ?: return

        Log.d(TAG, "\n${notificationExt.name}\n${notificationExt.tag}\n${sbn?.notification?.actions?.size}")

        if(notificationExt.name != "com.microsoft.teams") {
            val bundle = Bundle()
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            bundle.putCharSequence(notificationExt.remoteInputs[0].resultKey, "Hello shuvo")
            RemoteInput.addResultsToIntent(notificationExt.remoteInputs.toTypedArray(), intent, bundle)

            try {
                notificationExt.pendingIntent?.send(this, 0, intent)
                Log.d(TAG, "Hello Success")
            } catch (e: Exception) {
                Log.d(TAG, "Hello exception")
            }
        }
    }
}