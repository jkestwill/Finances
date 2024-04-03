package com.jk.financehelper.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.financehelper.R
import com.jk.financehelper.ui.theme.LightCreamy

@Composable
fun ButtonWithDropdownMenu(
    modifier: Modifier = Modifier,
    list: List<String>,
    icon:Painter,
    color: Color,
    onClick: (String) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier.width(30.dp).height(30.dp).padding(4.dp)) {
        IconButton(onClick = {
            expanded = !expanded
        }) {
            Icon(painter = icon, contentDescription = "sort_ic")
        }

        DropdownMenu(
            modifier = Modifier.background(LightCreamy),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }) {
            for (i in list) {

                DropdownMenuItem(text = {
                    ExpandedListItem(item = i, color = color, onClick = {})
                }, onClick = { onClick(i) })
            }


        }
    }
}

@Composable
fun ExpandedListItem(item: String, color: Color, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawLine(
                    color = color,
                    start = Offset(x = size.width / 8, y = size.height),
                    end = Offset(x = size.width - (size.width / 8), y = size.height)
                )
            }
            .clickable {
                onClick(item)
            }

    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = item,
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}

//@Preview
//@Composable
//fun Preview() {
//    SortButton(list = listOf("zxc", "qwe", "pizdec", "popa"), color = Celadon, onClick = {
//        println(it),
//    })
//}