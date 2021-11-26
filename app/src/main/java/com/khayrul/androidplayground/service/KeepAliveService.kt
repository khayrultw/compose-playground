package com.khayrul.androidplayground.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.khayrul.androidplayground.presentation.MainActivity

class KeepAliveService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("KeepAliveService", "KeepAliveService")
        intent?.let {
            Log.d("KeepAliveService", "KeepAliveService inside")
            val notificationIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
            val input = it.getStringExtra("input").toString()
            val notification = NotificationUtil.getNotificationBuilder(this,"Awesome", input)
                .setContentIntent(pendingIntent)
                .build()

            startForeground(0, notification)
        }
        return START_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        tryToReconnectService()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        tryToReconnectService()
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        tryToReconnectService()
        super.onTaskRemoved(rootIntent)
    }

    private fun tryToReconnectService() {
        val broadcastIntent = Intent(this, NotificationServiceRestartReceiver::class.java)
        broadcastIntent.action =  "RestartService-Broadcast"
        sendBroadcast(broadcastIntent);
    }
}