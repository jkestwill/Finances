package com.jk.financehelper.ui.calendar.data

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun pagedFlingBehavior(state: LazyListState): FlingBehavior {
    val snappingLayout = remember(state) {
        val provider = SnapLayoutInfoProvider(state)
        CalendarSnapLayoutInfoProvider(provider)
    }
    return rememberSnapFlingBehavior(snappingLayout)
}

@ExperimentalFoundationApi
@Suppress("FunctionName")
private fun CalendarSnapLayoutInfoProvider(
    snapLayoutInfoProvider: SnapLayoutInfoProvider,
): SnapLayoutInfoProvider = object : SnapLayoutInfoProvider by snapLayoutInfoProvider {

    override fun calculateApproachOffset(velocity: Float, decayOffset: Float): Float = 0f
}
