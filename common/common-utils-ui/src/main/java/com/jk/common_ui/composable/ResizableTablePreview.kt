package com.jk.common_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun ResizableTable_Preview() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val columns = 4
        val rows = 8
        ResizableTable(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            initialColumnWidths = listOf(120.dp, 100.dp, 140.dp, 160.dp),
            columnCount = columns,
            rowCount = rows,
            header = { col, cellModifier ->
                Box(
                    cellModifier.background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Header $col",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            rows = List(rows) { r ->
                @Composable { _, col, cellModifier ->
                    Box(cellModifier, contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = "R${'$'}r C${'$'}col",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        )
    }
}

