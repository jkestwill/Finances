package com.jk.common_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.common_data.Selectable
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.LightCreamy
import com.jk.common_ui.Rotation
import com.jk.common_ui.clickAnimation
import com.jk.common_ui.rotationAnimation

@Composable
fun <T: Selectable> ButtonWithDropdownMenu(
    modifier: Modifier = Modifier,
    list: List<T>,
    icon: Painter,
    color: Color,
    onClick: (T) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .width(30.dp)
            .height(30.dp)
            .padding(4.dp)
    ) {
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
fun <T:Selectable> TextWithDropDownMenu(
    modifier: Modifier = Modifier,
    list: List<T>,
    color: Color,
    placeholderText: String,
    onClick: (T) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val selectedItem = remember {
        mutableStateOf(placeholderText)
    }
    var expandedListArrowRotationState by remember {
        mutableStateOf(Rotation.IDLE)
    }
    Column(
        modifier = modifier
            .clickAnimation {
                expandedListArrowRotationState = Rotation.ROTATE
                expanded = !expanded
            }
            .border(2.dp, color = Color.Black, shape = FinanceHelperTheme.shape.shape20)
            .background(
                color = FinanceHelperTheme.colors.defaultButtonColor.copy(0.5f),
                FinanceHelperTheme.shape.shape20
            )
            .padding(7.dp)
    ) {
        Row {
            AutoSizeText(
                modifier = Modifier
                    .weight(2f)
                    .align(Alignment.CenterVertically),
                text = selectedItem.value,
                minTextSize = 10.sp,
                maxTextSize = FinanceHelperTheme.typography.h2.fontSize,
                style = FinanceHelperTheme.typography.h3,
                maxLines = 1
            )
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .rotationAnimation(expandedListArrowRotationState)
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "ic_dropdown"
            )
        }
        DropdownMenu(
            modifier = Modifier
                .width(100.dp)
                .background(LightCreamy),
            expanded = expanded,
            onDismissRequest = {
                expandedListArrowRotationState = Rotation.IDLE
                expanded = false
            }) {
            for (i in list) {
                ExpandedListItem(
                    modifier = Modifier.fillMaxWidth(),
                    item = i,
                    color = color,
                    onClick = {
                        selectedItem.value = it.value
                        onClick(i)
                        expanded = false
                    })
            }
        }
    }
}

@Composable
fun <T : Selectable> ExpandedListItem(
    modifier: Modifier = Modifier,
    item: T,
    color: Color,
    onClick: (T) -> Unit
) {
    Box(
        modifier = modifier

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
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            text = item.value,
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}
