package com.khayrul.playground.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.khayrul.playground.core.constants.Constants
import com.khayrul.playground.service.NotificationServiceManager

class TaskReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(Constants.TAG, "Got task from Alarm Manager")
        if (context != null) {
            NotificationServiceManager.createNotification(
                context = context,
                title = "Alarm Manager",
                text = "Testing Alarm Manager"
            )
        }
    }
}