package com.khayrul.androidplayground.presentation.util

sealed class Screen(val route: String) {
    object NotificationPlayground : Screen("notification_playground")
    object WorkManagerPlayground : Screen("work_manager_playground")
    object Home : Screen("home")
}
