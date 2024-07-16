package com.jk.common_ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight


@Composable
fun TopLabel(
    label: String,
    content: @Composable RowScope.() -> Unit
) {
    Row(modifier=Modifier) {
        Text(
            modifier = Modifier.weight(0.3f),
            text = label,
            color = MidnightGreen,
            fontWeight = FontWeight.Bold
        )
        content(this)
    }
}