package com.khayrul.androidplayground.presentation.notificaion_playground

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.khayrul.androidplayground.core.preference.PreferencesManager
import com.khayrul.androidplayground.service.NotificationServiceManager

@Composable
fun NotificationPlayground(
    preferencesManager: PreferencesManager
) {

    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val checkedState = remember { mutableStateOf(preferencesManager.isAutoReplyServiceEnabled() ?: false) }
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
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = { NotificationServiceManager.stopForegroundTestService(context)
            }
        ) {
            Text(text = "Stop Service")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = {
                NotificationServiceManager.startForegroundTestService(context)
            }
        ) {
            Text(text = "Start Service in background")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = {
                NotificationServiceManager.createNotification(
                    context = context,
                    title = "Awesome",
                    text = "Awesome Notification"
                )
            }
        ) {
            Text(text = "Create Notification")
        }
    }
}