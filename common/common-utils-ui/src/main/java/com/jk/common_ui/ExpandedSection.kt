package com.jk.common_ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun ExpandedSection(
    modifier: Modifier,
    expandedContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val expanded = rememberSaveable() {
        mutableStateOf(false)
    }
    val rotationState = animateFloatAsState(targetValue = if (expanded.value) 90f else 0f)
    Column(
        modifier = modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    expanded.value = !expanded.value
                },
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            content()
            Box(
                modifier = Modifier.rotate(rotationState.value)
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "ic_drop_down"
                )
            }
        }
        if (expanded.value)
            expandedContent()
    }
}