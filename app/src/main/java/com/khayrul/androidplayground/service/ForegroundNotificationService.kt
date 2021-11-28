package com.khayrul.androidplayground.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.khayrul.androidplayground.R
import com.khayrul.androidplayground.core.constants.Constants
import com.khayrul.androidplayground.presentation.MainActivity
import com.khayrul.androidplayground.core.preference.PreferencesManager
import com.khayrul.androidplayground.recever.NotificationServiceReceiver

class ForegroundNotificationService : Service() {

    override fun onCreate() {
        super.onCreate()
        startForegroundNotificationService()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startNotificationListenerService()
        return START_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(Constants.TAG, "onUnbind")
        tryToReconnectService()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        tryToReconnectService()
        Log.d(Constants.TAG, "destroyed")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.d(Constants.TAG, "onTaskRemoved")
        tryToReconnectService()
    }

    private fun startNotificationListenerService() {
        val listenerIntent = Intent(this, NotificationListener::class.java)
        startService(listenerIntent)
    }

    private fun startForegroundNotificationService() {
        val title = "Auto reply"
        val text = "Auto reply service is running"

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val notificationBuilder = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.auto_reply)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(pendingIntent)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationBuilder.priority = NotificationManager.IMPORTANCE_HIGH
        }

        startForeground(10, notificationBuilder.build())
        Log.d(Constants.TAG, "started")
    }

    private fun tryToReconnectService() {
        val preferencesManager = PreferencesManager.getInstance(this)
        if(preferencesManager.isAutoReplyServiceEnabled()) {
            val intent = Intent(this, NotificationServiceReceiver::class.java)
            intent.action = Constants.RESTART_BROADCAST
            sendBroadcast(intent)
        }
    }
}