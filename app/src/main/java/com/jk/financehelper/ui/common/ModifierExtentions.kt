package com.jk.financehelper.ui.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.jk.financehelper.ui.theme.FinanceHelperTheme

enum class ButtonState { PRESSED, IDLE }

fun Modifier.clickAnimation(onClick: () -> Unit): Modifier = composed {
    var state = remember { mutableStateOf(ButtonState.IDLE) }
    val scale by animateFloatAsState(if (state.value == ButtonState.PRESSED) 0.70f else 1f)
    val interactionSource = remember { MutableInteractionSource() }
   this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        ).pointerInput(state.value) {
            awaitPointerEventScope {
                state.value = if (state.value == ButtonState.PRESSED) {
                    waitForUpOrCancellation()
                    ButtonState.IDLE
                } else {
                    awaitFirstDown()
                    ButtonState.PRESSED
                }
            }
        }
}