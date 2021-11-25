package com.khayrul.androidplayground.util

import android.app.PendingIntent
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import android.app.RemoteInput
import com.khayrul.androidplayground.domain.model.NotificationExtender

class NotificationUtils {

    companion object {
        fun getNotificationExtender(sbn: StatusBarNotification?): NotificationExtender? {
            return sbn?.notification?.let { notification ->
                val wearableExt = NotificationCompat.WearableExtender(notification)
                val actions = notification.actions
                Log.d("NotificationListener", actions.size.toString())
                val remoteInputs = ArrayList<RemoteInput>(actions.size)
                var pendingIntent: PendingIntent? = null

                for(action in actions) {
                    pendingIntent = action?.remoteInputs?.let { _remoteInputs ->
                        for(remoteInput in _remoteInputs) {
                            remoteInputs.add(remoteInput as RemoteInput)
                        }
                        action.actionIntent
                    }
                }
                NotificationExtender(
                    sbn.tag,
                    sbn.packageName,
                    pendingIntent,
                    remoteInputs.toList(),
                    notification.extras
                )
            }
        }
    }
}