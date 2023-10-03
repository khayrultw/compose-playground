package com.khayrul.playground.presentation.mandelbrot

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.asImageBitmap
import coil.compose.AsyncImage
import com.khayrul.playground.MyApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Mandelbrot() {
    val coroutineScope = rememberCoroutineScope()

    var counter by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = null) {
        coroutineScope.launch(Dispatchers.Default) {
            while (true) {
                delay(500)
                counter++
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Mandelbrot $counter")
        AsyncImage(model = MyApp.mandelbrot, contentDescription = "")
    }
}