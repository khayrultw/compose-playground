package com.khayrul.androidplayground.domain.model

import android.app.PendingIntent
import android.app.RemoteInput
import android.os.Bundle

data class NotificationExtender(
    val tag: String?,
    val name: String,
    val pendingIntent: PendingIntent?,
    val remoteInputs: List<RemoteInput>,
    val bundle: Bundle?
)
