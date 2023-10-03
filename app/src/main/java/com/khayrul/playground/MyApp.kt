package com.khayrul.playground

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import com.khayrul.playground.core.util.getMandelNative
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val dScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

        dScope.launch {
            var scale = 1
            while (scale < 100) {
                mandelbrot = getMandelNative(800, 800, scale)
                scale += 2
            }
        }
    }

    companion object {
        init {
            System.loadLibrary("androidplayground")
        }

        var mandelbrot = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888)
    }
}