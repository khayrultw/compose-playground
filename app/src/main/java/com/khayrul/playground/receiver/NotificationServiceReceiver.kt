package com.khayrul.playground.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.khayrul.playground.core.constants.Constants
import com.khayrul.playground.service.ForegroundNotificationService
import com.khayrul.playground.core.preference.PreferencesManager

class NotificationServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action?.equals(Intent.ACTION_BOOT_COMPLETED) == true
            || action?.equals(Constants.RESTART_BROADCAST) == true
        ) {
            context?.let {
                restartService(it)
            }
        }
    }

    private fun restartService(context: Context) {
        val preferencesManager = PreferencesManager.getInstance(context)
        if (preferencesManager.isAutoReplyServiceEnabled()) {
            val serviceIntent = Intent(context, ForegroundNotificationService::class.java)
            try {
                context.startService(serviceIntent)
            } catch (e: IllegalStateException) {
                Log.e(Constants.TAG, "Unable to restart notification service")
            }
        }
    }
}