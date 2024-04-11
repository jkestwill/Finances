package com.jk.financehelper.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.financehelper.ui.custom.AutoSizeText
import com.jk.financehelper.ui.theme.FinanceHelperTheme
import kotlinx.coroutines.delay
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Error(modifier: Modifier = Modifier, visible: Boolean, message: String) {

    val state = rememberSwipeToDismissBoxState()
    LaunchedEffect(key1 = visible) {
        if (visible) {
            state.reset()
        }
            delay(2000)
            state.dismiss(SwipeToDismissBoxValue.EndToStart)

    }

    SwipeToDismissBox(
        state = state,
        backgroundContent = {
        }
    ) {
        Box(
            modifier = modifier
                .background(
                    FinanceHelperTheme.colors.error,
                    RoundedCornerShape(topEndPercent = 20, bottomEndPercent = 20)
                )
                .width(100.dp)
                .height(30.dp)
                .padding(FinanceHelperTheme.shape.padding)
        ) {
            AutoSizeText(
                text = message,
                style = FinanceHelperTheme.typography.body,
                alignment = Alignment.Center,
                minTextSize = 10.sp,
                maxTextSize = 16.sp
            )
        }

    }

}

