package com.khayrul.playground.presentation.util

sealed class Screen(val route: String, val title: String) {
    data object NotificationPlayground : Screen("notification_playground", "Notification Playground")
    data object WorkManagerPlayground : Screen("work_manager_playground", "Work Manager Playground")
    data object AlarmManagerPlayground : Screen("alarm_manager_playground", "Alarm Manager Playground")
    data object MandelbrotPlayground : Screen("mandelbrot_playground", "Mandelbrot Playground")
}
