package com.khayrul.androidplayground.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.khayrul.androidplayground.presentation.util.Screen

@Composable
fun Home(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = {
                navController.navigate(route = Screen.NotificationPlayground.route)
            }
        ) {
            Text(text = "Notification Playground")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = {
                navController.navigate(route = Screen.WorkManagerPlayground.route)
            }
        ) {
            Text(text = "Work Manager Playground")
        }
    }
}