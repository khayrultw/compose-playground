package com.khayrul.playground

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import com.khayrul.playground.core.util.getMandel
import com.khayrul.playground.core.util.getMandelNative
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val dScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

        dScope.launch {
            var scale = 1
            Log.d("debugging", "START")
            while (scale < 2) {
                mandelbrot = getMandel(1000, 1000, scale.toFloat())
                scale += 1
            }
            Log.d("debugging", "DONE")
        }
    }


    companion object {
        init {
            System.loadLibrary("androidplayground")
        }

        var mandelbrot = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    }
}