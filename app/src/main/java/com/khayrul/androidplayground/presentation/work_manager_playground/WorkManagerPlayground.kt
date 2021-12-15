package com.khayrul.androidplayground.presentation.work_manager_playground

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.*
import com.khayrul.androidplayground.core.work.TestWorker
import java.util.*
import java.util.concurrent.TimeUnit

@Composable
fun WorkManagerPlayground() {
    val time = remember { mutableStateOf(0L)}
    val context = LocalContext.current
    val timePicker = TimePickerDialog(
        context,
        { view, hour, minute ->
            val _h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val _m = Calendar.getInstance().get(Calendar.MINUTE)
            time.value = hour*60L + minute - _h*60L - _m
            if(time.value < 0) {
                time.value += 24*60
            }
        },
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        Calendar.getInstance().get(Calendar.MINUTE),
        false
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = { timePicker.show()}) {
            Text(text = "Time: ${time.value} minutes" )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = { MyWorker.createWorker(context, time.value) }
        ) {
            Text(text = "Create Work")
        }
    }
}

class MyWorker {
    companion object {
        fun createWorker(context: Context, time: Long) {
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

            WorkManager.getInstance(context).enqueue(sampleWork)
        }
    }
}

