package com.khayrul.androidplayground.presentation.work_manager_playground

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WorkManagerPlayground(
    createWork: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hello Work Manager")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { createWork() }) {
            Text(text = "Create Work")
        }
    }
}