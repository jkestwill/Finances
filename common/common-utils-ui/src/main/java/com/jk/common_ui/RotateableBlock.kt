package com.jk.common_ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.common_ui.RotationOptions.RotationAxis
import com.jk.common_ui.RotationOptions.RotationEvent
import kotlinx.coroutines.delay

@Composable
fun RotateableBlock(modifier:Modifier = Modifier,state:RotationOptions,block: @Composable ()->Unit, block2: @Composable ()->Unit) {
    val prevAngle = rememberSaveable(state) {
        mutableFloatStateOf(state.initialAngle)
    }
//    LaunchedEffect(key1 = state) {
//        if(state.rotationEvent == RotationOptions.RotationEvent.NEXT) {
//            prevAngle.floatValue += state.angle % 360f
//        }
//        else{
//            prevAngle.floatValue = state.initialAngle
//        }
//    }
    val animationDuration = 1000L
    val visibility = remember {
        mutableStateOf(false)
    }
    val angleOffset = remember{
        mutableStateOf(0f)
    }
    val rotate = animateFloatAsState(targetValue =if(state.rotationEvent == RotationEvent.NEXT) state.angle else state.initialAngle, animationSpec = tween(durationMillis = animationDuration.toInt()))
    LaunchedEffect(key1 = rotate.value ) {
        angleOffset.value = 90f
        delay(animationDuration)
        visibility.value = !visibility.value
        angleOffset.value = 180f
    }
    Box(modifier = modifier) {

        Box(modifier = Modifier.graphicsLayer {
            rotate(angle = rotate.value+angleOffset.value, axis =state.rotationAxis )
        }) {

            block()
        }
        Box(modifier = Modifier.graphicsLayer {
            rotate(angle = rotate.value, axis =state.rotationAxis )
        }) {

            block2()
        }
    }

}
@Composable
@Preview
fun Preview(){
    FinanceHelperTheme{

        val flag = remember {
            mutableStateOf(false)
        }
        val state = rememberRotationState(initialAngle = 0f, angle = 90f,if(flag.value) RotationEvent.NEXT else RotationEvent.IDLE)
        Column {
            RotateableBlock(state = state, block = {
                Text(modifier = Modifier.size(100.dp,60.dp).background(color = Color.Blue),text = "State 1",fontSize = 24.sp)
            }) {
                Text(modifier = Modifier.size(100.dp,60.dp).background(color = Color.Red),text = "State 2",fontSize = 24.sp)
            }

            Button(modifier = Modifier.size(100.dp,60.dp),onClick = {
                flag.value = !flag.value
            }){
                Text(text = "Rotate", fontSize = 24.sp)
            }
        }
    }
}

private fun GraphicsLayerScope.rotate(angle: Float, axis:RotationAxis){
    when(axis){
        RotationAxis.X-> rotationX = angle
        RotationAxis.Y -> rotationY = angle
        RotationAxis.Z -> rotationZ = angle
    }
}
data class RotationOptions internal constructor(
    var initialAngle:Float,
    var angle:Float,
    var rotationEvent: RotationEvent,
    var rotationAxis: RotationAxis
) {
    enum class RotationEvent {
        IDLE, NEXT
    }
    enum class RotationAxis{
        X,Y,Z
    }
}

@Composable
fun rememberRotationState(
                         initialAngle: Float=0f,
                         angle:Float = 90f,
                         rotationEvent: RotationEvent = RotationEvent.IDLE,
                         rotationAxis: RotationAxis = RotationAxis.X):RotationOptions{
    return  remember(angle,rotationEvent,rotationAxis) {
           RotationOptions(initialAngle = initialAngle ,angle,rotationEvent,rotationAxis)
    }
}

val RotationOptionsSaver = run {
    mapSaver<RotationOptions>(save = { mapOf("1" to it.rotationAxis,"2" to it.rotationEvent,"3" to it.angle, "4" to it.initialAngle)},
        restore ={RotationOptions(initialAngle = it["4"] as Float,angle = it["3"] as Float, rotationEvent = it["2"] as RotationEvent,it["3"] as RotationAxis)} )
}