package com.jk.common_ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

class RotatablePanelInf {

}

@Composable
fun RotatablePanel(block:@Composable ()->Unit){
    val rotation  = animateFloatAsState(targetValue =0f, animationSpec = tween(100, easing = LinearEasing) )

    Modifier.graphicsLayer {
        rotationX = rotation.value
    }
}