package com.khayrul.androidplayground.presentation.util

sealed class Screen(val route: String, val title: String) {
    object NotificationPlayground : Screen("notification_playground", "Notification Playground")
    object WorkManagerPlayground : Screen("work_manager_playground", "Work Manager Playground")
    object AlarmManagerPlayground : Screen("alarm_manager_playground", "Alarm Manager Playground")
}
