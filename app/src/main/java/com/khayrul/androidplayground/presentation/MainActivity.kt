package com.khayrul.androidplayground.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khayrul.androidplayground.presentation.ui.theme.AndroidPlaygroundTheme
import com.khayrul.androidplayground.service.NotificationServiceManager
import com.khayrul.androidplayground.core.preference.PreferencesManager
import com.khayrul.androidplayground.presentation.notificaion_playground.NotificationPlayground
import com.khayrul.androidplayground.presentation.util.Screen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationServiceManager.createNotificationChannel(this)

        val preferencesManager = PreferencesManager.getInstance(this)

        setContent {
            AndroidPlaygroundTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotificationPlayground.route
                    ) {
                        composable(
                            route = Screen.NotificationPlayground.route
                        ) {
                            NotificationPlayground(
                                preferencesManager = preferencesManager
                            )
                        }
                    }
                }
            }
        }
    }
}
