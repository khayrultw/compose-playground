package com.khayrul.androidplayground.service

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.khayrul.androidplayground.core.Constants
import com.khayrul.androidplayground.util.NotificationUtils
import com.khayrul.androidplayground.util.PreferencesManager
import java.lang.Exception

class NotificationListener : NotificationListenerService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(Constants.TAG, "Hello init")
        return START_STICKY
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {

        Log.d(Constants.TAG, "Hello status bar notification")
        super.onNotificationPosted(sbn)

        if(!PreferencesManager.getInstance(this).isAutoReplyServiceEnabled()) {
            return
        }

        val notificationExt = NotificationUtils.getWearableNotification(sbn) ?: return

        Log.d(Constants.TAG, "\n${notificationExt.name}\n${notificationExt.tag}\n${sbn?.notification?.actions?.size}")

        if(notificationExt.name != "com.microsoft.teams") {
            val bundle = Bundle()
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val title = notificationExt.bundle?.getString("android.title") ?: ""
            val text = notificationExt.bundle?.getString("android.text") ?: ""
            val reply = if (text.contains("(hello)|(hi)".toRegex()) ) "Hi $title" else "okay"
            bundle.putCharSequence(notificationExt.remoteInputs[0].resultKey, reply)
            RemoteInput.addResultsToIntent(notificationExt.remoteInputs.toTypedArray(), intent, bundle)

            try {
                notificationExt.pendingIntent?.let {
                    it.send(this, 0, intent)
                    cancelNotification(sbn?.key)
                    Log.d(Constants.TAG, "Hello Success")
                }
            } catch (e: Exception) {
                Log.d(Constants.TAG, "Hello exception")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Constants.TAG, "Destroyed")
    }
}