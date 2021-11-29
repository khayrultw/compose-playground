package com.khayrul.androidplayground.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import com.khayrul.androidplayground.core.constants.Constants
import com.khayrul.androidplayground.presentation.ui.theme.AndroidPlaygroundTheme
import com.khayrul.androidplayground.service.NotificationServiceManager
import com.khayrul.androidplayground.core.preference.PreferencesManager
import com.khayrul.androidplayground.core.work.TestWorker
import com.khayrul.androidplayground.presentation.notificaion_playground.NotificationPlayground
import com.khayrul.androidplayground.presentation.util.Screen
import com.khayrul.androidplayground.presentation.work_manager_playground.WorkManagerPlayground
import java.util.concurrent.TimeUnit

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
                        startDestination = Screen.WorkManagerPlayground.route
                    ) {
                        composable(
                            route = Screen.NotificationPlayground.route
                        ) {
                            NotificationPlayground(
                                preferencesManager = preferencesManager
                            )
                        }
                        composable(
                            route = Screen.WorkManagerPlayground.route
                        ) {
                            WorkManagerPlayground(
                                createWork = { time -> createWorker(time) }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun createWorker(time: Long) {
        val workRequestConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val data = Data.Builder()
        data.putString("inputKey", "Input value")

        val sampleWork = OneTimeWorkRequest.Builder(TestWorker::class.java)
            .setInputData(data.build())
            .setConstraints(workRequestConstraints)
            .setInitialDelay(time*60, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(sampleWork)
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(sampleWork.id)
            .observe(this, { workInfo ->
                if(workInfo != null) {
                    when(workInfo.state) {
                        WorkInfo.State.ENQUEUED -> {
                            Log.d(Constants.TAG, "ENQUEUED")
                        }
                        WorkInfo.State.RUNNING -> {
                            Log.d(Constants.TAG, "RUNNING")
                        }
                        WorkInfo.State.SUCCEEDED -> {
                            Log.d(Constants.TAG, "SUCCEEDED")
                        }
                        WorkInfo.State.FAILED -> {
                            Log.d(Constants.TAG, "FAILED")
                        }
                        WorkInfo.State.BLOCKED -> {
                            Log.d(Constants.TAG, "BLOCKED")
                        }
                        WorkInfo.State.CANCELLED -> {
                            Log.d(Constants.TAG, "CANCELLED")
                        }
                    }
                }
            })
    }
}
