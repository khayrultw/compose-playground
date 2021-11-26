package com.khayrul.androidplayground.service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.util.Log

class KeepAliveService : Service() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Log.d("KeepAliveService", "onResume00000")
            startForeground(this)
        }
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startNotificationService()
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

    private fun startNotificationService() {
        val intent = Intent(this, NotificationListenerService::class.java)
        startService(intent)
    }

    private fun startForeground(service: Service) {
        Log.d("DEBUG", "startForeground")
        val notification = NotificationManager.getNotification(this, "hello", "hello")
        service.startForeground(10, notification)
    }
}