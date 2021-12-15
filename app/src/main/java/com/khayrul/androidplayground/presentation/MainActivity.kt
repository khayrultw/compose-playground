package com.khayrul.androidplayground.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.work.*
import com.khayrul.androidplayground.core.constants.Constants
import com.khayrul.androidplayground.presentation.ui.theme.AndroidPlaygroundTheme
import com.khayrul.androidplayground.service.NotificationServiceManager
import com.khayrul.androidplayground.core.preference.PreferencesManager
import com.khayrul.androidplayground.core.work.TestWorker
import com.khayrul.androidplayground.presentation.navigation_screen.NavigationScreen
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationServiceManager.createNotificationChannel(this)

        val preferencesManager = PreferencesManager.getInstance(this)

        setContent {
            AndroidPlaygroundTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavigationScreen(
                        preferencesManager = preferencesManager,
                        createWork = {time -> createWorker(time)}
                    )
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
