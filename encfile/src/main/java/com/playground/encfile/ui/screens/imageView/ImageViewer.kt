package com.playground.encfile.ui.screens.imageView

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import coil.compose.rememberAsyncImagePainter
import com.khtw.encfile.core.utils.decryptFile
import com.khtw.encfile.core.utils.isSecImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun ImageViewer(
    imagePath: String
) {
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    var zoom by remember {
        mutableFloatStateOf(1f)
    }
    var image by remember {
        mutableStateOf(BitmapFactory.decodeFile(imagePath))
    }


    LaunchedEffect(key1 = Unit) {
        val file = File(imagePath)
        if(file.isSecImage()) {
            withContext(Dispatchers.IO) {
                image = decryptFile(File(imagePath))
            }
        }
    }

    Box(
        modifier = Modifier
            .clip(RectangleShape),
        contentAlignment = Alignment.Center,

    ) {
        Image(
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = "",
            modifier = Modifier
                .graphicsLayer {
                    translationX = -offset.x * zoom
                    translationY = -offset.y * zoom
                    scaleX = zoom; scaleY = zoom
                    transformOrigin = TransformOrigin(0f, 0f)
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            zoom = if (zoom > 1f) 1f else 2f
                            offset = calculateDoubleTapOffset(zoom, size, it)
                        }
                    )
                }.pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, gestureZoom, _ ->
                        offset = offset.calculateNewOffset(centroid, pan, zoom, gestureZoom, size)
                        zoom = maxOf(1f, zoom * gestureZoom)
                    }
                }
        )
    }
}

fun calculateDoubleTapOffset(
    zoom: Float,
    size: IntSize,
    tapOffset: Offset
): Offset {
    val newOffset = Offset(tapOffset.x, tapOffset.y)
    return Offset(
        newOffset.x.coerceIn(0f, (size.width / zoom) * (zoom - 1f)),
        newOffset.y.coerceIn(0f, (size.height / zoom) * (zoom - 1f))
    )
}

fun Offset.calculateNewOffset(
    centroid: Offset,
    pan: Offset,
    zoom: Float,
    gestureZoom: Float,
    size: IntSize
): Offset {
    val newScale = maxOf(1f, zoom * gestureZoom)
    val newOffset = (this + centroid / zoom) -
            (centroid / newScale + pan / zoom)
    return Offset(
        newOffset.x.coerceIn(0f, (size.width / zoom) * (zoom - 1f)),
        newOffset.y.coerceIn(0f, (size.height / zoom) * (zoom - 1f))
    )
}