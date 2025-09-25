package com.jk.common_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * Simple resizable table with Excel-like column resizing.
 * - Columns have independent widths the user can drag to resize.
 * - Provide headers and rows as composable lambdas receiving constrained [Modifier].
 */
@Composable
fun ResizableTable(
    modifier: Modifier = Modifier,
    initialColumnWidths: List<Dp>,
    minColumnWidth: Dp = 64.dp,
    maxColumnWidth: Dp = 600.dp,
    header: @Composable (columnIndex: Int, cellModifier: Modifier) -> Unit,
    rows: List<@Composable (rowIndex: Int, columnIndex: Int, cellModifier: Modifier) -> Unit>,
    rowCount: Int,
    columnCount: Int,
) {
    val minPx = with(androidx.compose.ui.platform.LocalDensity.current) { minColumnWidth.toPx() }
    val maxPx = with(androidx.compose.ui.platform.LocalDensity.current) { maxColumnWidth.toPx() }

    val widths = rememberSaveable(saver = DpListSaver) {
        mutableStateListOf(*initialColumnWidths.toTypedArray())
    }

    LaunchedEffect(columnCount) {
        if (widths.size != columnCount) {
            widths.clear()
            repeat(columnCount) { i ->
                widths.add(initialColumnWidths.getOrNull(i) ?: minColumnWidth)
            }
        }
    }

    ColumnNoRipple(modifier) {
        // Header row
        Row(verticalAlignment = Alignment.CenterVertically) {
            for (c in 0 until columnCount) {
                val width = widths[c]
                Box(Modifier.width(width).heightIn(min = 36.dp)) {
                    header(c, Modifier.matchParentSize())
                    // Drag handle at right edge
                    if (c < columnCount - 1) {
                        DragHandle(
                            onDelta = { deltaPx ->
                                val current = with(androidx.compose.ui.platform.LocalDensity.current) { widths[c].toPx() }
                                val newPx = (current + deltaPx).coerceIn(minPx, maxPx)
                                widths[c] = with(androidx.compose.ui.platform.LocalDensity.current) { newPx.toDp() }
                            }
                        )
                    }
                }
                if (c < columnCount - 1) Divider(modifier = Modifier.heightIn(min = 36.dp).width(1.dp))
            }
        }
        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

        // Body rows
        for (r in 0 until rowCount) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                for (c in 0 until columnCount) {
                    val width = widths[c]
                    Box(Modifier.width(width).heightIn(min = 32.dp)) {
                        rows.getOrNull(r)?.invoke(r, c, Modifier.matchParentSize())
                        if (c < columnCount - 1) {
                            DragHandle(
                                onDelta = { deltaPx ->
                                    val current = with(androidx.compose.ui.platform.LocalDensity.current) { widths[c].toPx() }
                                    val newPx = (current + deltaPx).coerceIn(minPx, maxPx)
                                    widths[c] = with(androidx.compose.ui.platform.LocalDensity.current) { newPx.toDp() }
                                }
                            )
                        }
                    }
                    if (c < columnCount - 1) Divider(modifier = Modifier.heightIn(min = 32.dp).width(1.dp))
                }
            }
            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
        }
    }
}

@Composable
private fun DragHandle(
    onDelta: (Float) -> Unit,
) {
    val state = rememberDraggableState { delta -> onDelta(delta) }
    Box(Modifier.matchParentSize()) {
        Box(
            Modifier
                .align(Alignment.CenterEnd)
                .width(8.dp)
                .fillMaxHeight()
                .background(Color.Transparent)
                .draggable(state = state, orientation = Orientation.Horizontal)
        )
    }
}

// Helpers

@Composable
private fun ColumnNoRipple(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    androidx.compose.foundation.layout.Column(modifier) { content() }
}

// Saver for Dp list in rememberSaveable
private val DpListSaver = androidx.compose.runtime.saveable.listSaver<List<Dp>, Float>(
    save = { list -> list.map { it.value } },
    restore = { floats -> floats.map { it.dp } }
)

