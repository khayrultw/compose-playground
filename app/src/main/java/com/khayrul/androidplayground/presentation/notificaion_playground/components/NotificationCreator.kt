package com.khayrul.androidplayground.presentation.notificaion_playground.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun NotificationCreator(
    create: (title: String, text: String) -> Unit
) {
    Popup(
        alignment = Alignment.Center,
        properties = PopupProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .background(Color.White)
        ) {
            Text(text = "Hello")
        }
    }
}