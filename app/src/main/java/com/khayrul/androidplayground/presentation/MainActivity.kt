package com.khayrul.androidplayground.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.khayrul.androidplayground.service.NotificationServiceManager
import com.khayrul.androidplayground.presentation.navigation_screen.NavigationScreen
import com.khayrul.androidplayground.presentation.ui.theme.AndroidPlaygroundTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationServiceManager.createNotificationChannel(this)

        setContent {
            AndroidPlaygroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationScreen()
                }
            }
        }
    }

    companion object {
        init {
            System.loadLibrary("androidplayground")
        }
    }
}
