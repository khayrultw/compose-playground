package com.khayrul.androidplayground.util

import android.app.PendingIntent
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import com.khayrul.androidplayground.core.constants.Constants
import com.khayrul.androidplayground.domain.model.WearableNotification

class NotificationUtils {
    companion object {
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
    }
}