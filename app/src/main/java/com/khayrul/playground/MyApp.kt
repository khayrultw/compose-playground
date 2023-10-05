package com.khayrul.playground

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import com.khayrul.playground.core.util.getMandelNative
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

//        val dScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
//
//        dScope.launch {
//            var scale = 1
//            while (scale < 10000000) {
//                mandelbrot = getMandelNative(1000, 1000, scale)
//                scale += 100000
//                delay(2000)
//            }
//        }
    }


    companion object {
        init {
            System.loadLibrary("androidplayground")
        }

        var mandelbrot = getMandelNative(2000, 2000, 0.8f)
    }
}