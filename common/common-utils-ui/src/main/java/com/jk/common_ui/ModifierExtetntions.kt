package com.jk.common_ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.debugInspectorInfo

enum class ButtonState { PRESSED, IDLE }

fun Modifier.clickAnimation(onClick: () -> Unit): Modifier = composed {
    var state = remember { mutableStateOf(ButtonState.IDLE) }
    val scale by animateFloatAsState(if (state.value == ButtonState.PRESSED) 0.70f else 1f)
    val interactionSource = remember { MutableInteractionSource() }
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
        .pointerInput(state.value) {
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

fun Modifier.loadingAnimation(
    isVisible: Boolean,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
    color: Color
): Modifier = composed {
    if (isVisible) {
        val gradientColors = listOf(
            color.copy(0.3f),
            color.copy(0.5f),
            color.copy(1f),
            color.copy(0.5f),
            color.copy(0.3f),
        )
        val transition = rememberInfiniteTransition(label = "loading")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = durationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "loading"
        )
        this.background(
            Brush.linearGradient(
                colors = gradientColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY)
            )
        )
    } else this

}

fun Modifier.shake(enabled: Boolean) = composed(
    factory = {
        var targetValue = remember {
            mutableStateOf(20f)
        }
        val scale by animateFloatAsState(

            targetValue = if (enabled) 20f else 0f,
            animationSpec = repeatable(
                iterations = 5,
                animation = tween(durationMillis = 100, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        this.graphicsLayer {
            rotationZ=if (enabled) scale else 1f


        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "shake"
        properties["enabled"] = enabled
    }
)