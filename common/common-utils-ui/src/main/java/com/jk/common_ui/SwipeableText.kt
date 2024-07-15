package com.jk.common_ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp


@Composable
fun SwipeableText(
    text: String,
    onTextCLick:()->Unit,
    onForward: () -> Unit,
    onBack: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(modifier = Modifier.weight(1f), onClick = onBack) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_l),
                contentDescription = "arrow_left"
            )
        }

        AutoSizeText(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .clickable(onClick = onTextCLick),
            text = text,
            minTextSize = 12.sp,
            maxTextSize = 16.sp,
            alignment = Alignment.Center
        )

        IconButton(modifier = Modifier.weight(1f), onClick = onForward) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_r),
                contentDescription = "arrow_right"
            )
        }
    }
}