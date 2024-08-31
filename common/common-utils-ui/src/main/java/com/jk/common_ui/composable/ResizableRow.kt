package com.jk.common_ui.composable

import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp

@Composable
fun ResizeableRow(
    modifier: Modifier,
    content: @Composable () -> Unit
) {


}

//class ResizableRowMeasurePolicy : MeasurePolicy {
//    override fun MeasureScope.measure(
//        measurables: List<Measurable>,
//        constraints: Constraints
//    ): MeasureResult {
//        val placeables = measurables.map { it.measure(constraints) }
//
//    }
//
//}


data class ResizableRowMeasure(
    val arrangementSpacing: Dp,
    val placeables: Array<Placeable?>,
    val measurables: List<Measurable>,
) {
    fun measure(
        constraints: Constraints, measureScope: MeasureScope, startIndex: Int, endIndex: Int
    ) {
        val arrangementSpacingPx = with(measureScope) {
            arrangementSpacing.toPx().toLong()
        }

        for (i in startIndex until endIndex){
            val child = measurables[i]
            val placeable = child.measure(constraints)
        }


    }
}
//interface ResizableRowScope : RowScope {
//    fun Modifier.extendable(): Modifier
//}
//
//@Composable
//fun rememberExtendable(delta: (Float) -> Float): ExtendableState {
//    val updateState = rememberUpdatedState(newValue = delta)
//    return remember {
//        ExtendableState { updateState.value.invoke(it) }
//    }
//}
//
//class ExtendableState(onDelta: (Float) -> Float)
//
//fun Modifier.extendable(
//    state: ExtendableState,
//
//    )