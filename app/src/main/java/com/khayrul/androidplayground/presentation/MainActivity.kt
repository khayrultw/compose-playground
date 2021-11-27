package com.khayrul.androidplayground.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khayrul.androidplayground.core.Constants
import com.khayrul.androidplayground.presentation.notificaion_playground.NotificationPlayground
import com.khayrul.androidplayground.presentation.ui.theme.AndroidPlaygroundTheme
import com.khayrul.androidplayground.presentation.util.Screen
import com.khayrul.androidplayground.service.ForegroundNotificationService
import com.khayrul.androidplayground.service.NotificationListener
import com.khayrul.androidplayground.util.NotificationUtils
import com.khayrul.androidplayground.util.PreferencesManager

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    private val neededRuntimePermission = arrayOf(
        android.Manifest.permission.FOREGROUND_SERVICE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationUtils.createNotificationChannel(this)

        val preferencesManager = PreferencesManager.getInstance(this)

        setContent {
            AndroidPlaygroundTheme {
                Surface(color = MaterialTheme.colors.background) {
//                    val navController = rememberNavController()
//                    NavHost(navController = navController, startDestination = Screen.NotificationPlayground.route) {
//                        composable(
//                            route = Screen.NotificationPlayground.route
//                        ) {
//                            NotificationPlayground(
//                                createNotification = {title, text -> createNotification(title, text) }
//                            )
//                        }
//                    }

                    val checkedState = remember { mutableStateOf(preferencesManager?.isAutoReplyServiceEnabled() ?: false) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Auto Reply Service")
                            Spacer(modifier = Modifier.width(8.dp))
                            Switch(
                                checked = checkedState.value,
                                onCheckedChange = {
                                    checkedState.value = it
                                    when(it) {
                                        true -> preferencesManager.enableAutoReplyService()
                                        false -> preferencesManager.disableAutoReplyService()
                                    }
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { startForegroundTestService() }) {
                            Text(text = "Start Service in background")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { stopForegroundTestService() }) {
                            Text(text = "Stop Service")
                        }
                    }
                }
            }
        }
    }

    private fun startForegroundTestService() {
        askNotificationListenerPerm()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(this, ForegroundNotificationService::class.java)
            startForegroundService(intent)
        }
    }

    private fun stopForegroundTestService() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(this, ForegroundNotificationService::class.java)
            stopService(intent)

            val intentListener = Intent(this, NotificationListener::class.java)
            stopService(intentListener)
        }
    }

    private fun askNotificationListenerPerm() {
        if(NotificationUtils.notificationListenerEnabled(this)) {
            Log.d(Constants.TAG, "Enabled")
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            startActivity(intent)
        }
    }
}
