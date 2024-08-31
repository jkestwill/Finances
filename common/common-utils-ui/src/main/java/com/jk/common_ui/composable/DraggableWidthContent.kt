package com.jk.common_ui.composable

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun DraggableWidthContent(width: Int, content: @Composable (Modifier) -> Unit) {
    val dragWidth = rememberSaveable() {
        mutableStateOf(width)
    }
    val startOffset = rememberSaveable() {
        mutableStateOf(0f)
    }
    val offset = rememberSaveable() {
        mutableStateOf(0f)
    }
    val state = rememberDraggableState {
        dragWidth.value = (dragWidth.value + it.roundToInt()).coerceIn(width,width + 100)
    }

    content(
        Modifier
            .width(dragWidth.value.dp)
            .graphicsLayer {
                translationX = offset.value
            }
            .draggable(
                state = state,
                orientation = Orientation.Horizontal,
                onDragStarted = {
                    startOffset.value = it.x
                }
            )
    )
}