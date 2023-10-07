package com.khayrul.playground.core.util

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.set
import com.khayrul.playground.domain.model.Complex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.abs
import kotlin.math.min

external fun getIntArray(w: Int, h: Int, scale: Float): IntArray

fun getMandelNative(w: Int, h: Int, scale: Float): Bitmap {
    val arr = getIntArray(w, h, scale)
    return Bitmap.createBitmap(arr, w, h, Bitmap.Config.ARGB_8888)
}

fun getComplex(x: Int, w: Int, y: Int, h: Int, scale: Float): Complex {
    val x1 = 4f*x/w - 2
    val y1 = 4f*y/h - 2
    return Complex(-0.9f+x1/scale, 0.25f+y1/scale)
}

fun mandelbrot(z: Complex): Color {
    var zn = Complex(0f,0f)
    for(i in 1..200) {
        zn = zn*zn + z
        if(abs(zn.x) + abs(zn.y) > 100f) {
            val index = 8.0f*i/200
            return Color(1.0f*i/200, 0.0f, 0.0f)
        }
    }

    return Color(0.0f, 0.0f, 0.0f)
}


fun getMandel(w: Int, h: Int, scale: Float): Bitmap {
    val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val jobs = mutableListOf<Job>()
    val d = w / 1

    for(k in 0 until 1) {
        jobs.add(calc(bitmap, k*d, (k+1)*d, w, h, scale))
    }

    runBlocking {
        jobs.joinAll()
    }

    return bitmap
}

fun calc(bitmap: Bitmap, l: Int, r: Int, w: Int, h: Int, scale: Float): Job {
    val coroutineScope = CoroutineScope(Dispatchers.Default+ SupervisorJob())
    return coroutineScope.launch {
        for (i in l until min(w, r)) {
            for (j in 0 until h) {
                val color = mandelbrot(getComplex(i, w, j, h, scale))
                bitmap[i, j] = color.toArgb()
            }
        }
    }
}