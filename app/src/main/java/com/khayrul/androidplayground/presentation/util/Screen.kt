package com.khayrul.androidplayground.presentation.util

sealed class Screen(val route: String) {
    object NotificationPlayground : Screen("notification_playground")
    object Screen2 : Screen("screen2")
}
