package com.khayrul.androidplayground.presentation.alarm_manager_playground

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.util.Log
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
import com.khayrul.androidplayground.core.constants.Constants
import com.khayrul.androidplayground.receiver.TaskReceiver
import java.util.*

@Composable
fun AlarmManagerPlayground() {
    val time = remember { mutableStateOf(0L) }
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
            onClick = { MyAlarmManager.scheduleTask(context, time.value)}
        ) {
            Text(text = "Create Work")
        }
    }
}

class MyAlarmManager {
    companion object {
        fun scheduleTask(context: Context, time: Long) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TaskReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.set(AlarmManager.RTC_WAKEUP, 5000, pendingIntent)
            Log.d(Constants.TAG, "Task set using Alarm Manager")
        }
    }
}