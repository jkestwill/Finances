package com.jk.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.clickAnimation

@Composable
fun CategoryVerticalList(
    modifier: Modifier = Modifier,
    items: List<CategoryUI>,
    preselectedItems: List<CategoryUI>? = null,
    onListChanged: (CategoryUI,Boolean) -> Unit
) {
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(items.size) { index ->
            val selected = rememberSaveable(preselectedItems?.size) {
                mutableStateOf(value = preselectedItems?.contains(items[index])?:false)
            }
            CategoryVerticalListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickAnimation {
                        onListChanged(items[index], selected.value)
                    },
                category = items[index],
                selected = selected.value,
            )
        }
    }
}

@Composable
fun CategoryVerticalListItem(
    modifier: Modifier,
    category: CategoryUI,
    selected: Boolean,
) {
    val checked by remember(selected,category) {
        mutableStateOf(selected)
    }
    val checkedColors = FinanceHelperTheme.colors.defaultButtonColor
    Box(
        modifier = modifier
            .border(
                border = FinanceHelperTheme.shape.borderStroke,
                shape = FinanceHelperTheme.shape.shapeRoundMedium
            )
            .background(
                color = Color(category.color),
                shape = FinanceHelperTheme.shape.shapeRoundMedium
            )
            .padding(5.dp)

    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = category.name,
            style = FinanceHelperTheme.typography.h3
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .drawBehind {
                    val offset = Offset(x = 2f, y = 2f)
                    if (checked) {
                        drawRoundRect(
                            color = checkedColors,
                            topLeft = offset,
                            size = Size(size.width - 2, size.height - 2),
                            cornerRadius = CornerRadius(20f)
                        )
                    }
                }
                .background(
                    color = Color.Transparent,
                    shape = FinanceHelperTheme.shape.shapeRoundMedium
                )
                .width(30.dp)
                .height(30.dp)
                .border(
                    border = FinanceHelperTheme.shape.borderStroke,
                    shape = FinanceHelperTheme.shape.shapeRoundMedium
                ),

            )
    }
}