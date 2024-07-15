package com.jk.common_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TransparentTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean
) {

        var enabledBut by remember() { mutableStateOf(enabled) }
        var fontSize by remember{ mutableStateOf(24.sp)}

        var readyToDraw by remember { mutableStateOf(false) }
        BasicTextField(
            modifier = modifier,
            value = value,
            onValueChange =onValueChange,
            textStyle = Bold.copy(fontSize = fontSize),
            enabled = enabledBut,
            singleLine = true,
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = if (enabledBut) MaterialTheme.colorScheme.secondary else Color.Transparent,
                        shape = RoundedCornerShape(20)
                    )
                    .padding(10.dp)
                    .width(80.dp)


            ) {
                it()
                Text(
                    modifier = Modifier
                        .offset(x = 6.dp, y = (-5).dp)
                        .align(Alignment.TopEnd)
                        .clickable { enabledBut=!enabledBut  }
                    ,
                    text = "Edit",
                    style = TextStyle(fontSize=8.sp)
                )
            }

        }

}