package com.jk.financehelper.ui.custom
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

enum class ButtonState{PRESSED,IDLE}
@Composable
fun Modifier.clickAnimation(onClick:()->Unit)=composed {
    var state by remember { mutableStateOf(ButtonState.IDLE)}
    val scale by animateFloatAsState(if (state == ButtonState.PRESSED) 0.70f else 1f)
    val interactionSource = remember { MutableInteractionSource()}
    this.graphicsLayer {
        scaleX=scale
        scaleY=scale
    }.clickable(
        interactionSource=interactionSource,
        indication = null,
        onClick = onClick
    ).pointerInput(state){
        awaitPointerEventScope {
            state = if (state == ButtonState.PRESSED) {
                waitForUpOrCancellation()
                ButtonState.IDLE
            } else {
                awaitFirstDown(false)
                ButtonState.PRESSED
            }
        }
    }
}