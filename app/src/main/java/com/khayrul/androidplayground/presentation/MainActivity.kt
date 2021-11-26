package com.khayrul.androidplayground.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khayrul.androidplayground.presentation.notificaion_playground.NotificationPlayground
import com.khayrul.androidplayground.presentation.ui.theme.AndroidPlaygroundTheme
import com.khayrul.androidplayground.presentation.util.Screen
import com.khayrul.androidplayground.service.KeepAliveService
import com.khayrul.androidplayground.service.NotificationManager

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity0"

    @RequiresApi(Build.VERSION_CODES.P)
    private val neededRuntimePermission = arrayOf(
        android.Manifest.permission.FOREGROUND_SERVICE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.d(TAG, "onResume")

        setContent {
            AndroidPlaygroundTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.NotificationPlayground.route) {
                        composable(
                            route = Screen.NotificationPlayground.route
                        ) {
                            NotificationPlayground(
                                createNotification = {title, text -> createNotification(title, text) }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun createNotification(title: String, text: String) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
//            startActivity(intent)
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            checkPermission(android.Manifest.permission.FOREGROUND_SERVICE)
        }
        NotificationManager.createNotification(this, title, text)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun checkPermission(permission: String) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "!!!!!Permission")
            ActivityCompat.requestPermissions(this, neededRuntimePermission, 0)
        } else {
            Log.d(TAG, "Permission!!!!!")
        }
    }
}
