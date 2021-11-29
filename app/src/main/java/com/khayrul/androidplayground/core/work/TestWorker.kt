package com.khayrul.androidplayground.core.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.khayrul.androidplayground.core.constants.Constants
import com.khayrul.androidplayground.service.NotificationServiceManager

class TestWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val inputValue = inputData.getString("inputKey")
        Log.d(Constants.TAG, inputValue.toString())
        NotificationServiceManager.createNotification(this.applicationContext, "Hello", "Hello")
        return Result.success()
    }
}