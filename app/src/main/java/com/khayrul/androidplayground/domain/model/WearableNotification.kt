package com.khayrul.androidplayground.domain.model

import android.app.PendingIntent
import android.app.RemoteInput
import android.os.Bundle
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import com.khayrul.androidplayground.core.constants.Constants

data class WearableNotification(
    val tag: String?,
    val name: String,
    val pendingIntent: PendingIntent?,
    val remoteInputs: List<RemoteInput>,
    val bundle: Bundle?
)
