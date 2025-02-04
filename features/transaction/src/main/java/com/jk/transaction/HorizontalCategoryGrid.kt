package com.jk.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jk.category_common_ui.CategoryUI
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.clickAnimation
import com.jk.common_ui.composable.AutoSizeText

@Composable
fun HorizontalCategoryGrid(
    modifier: Modifier,
    items: List<CategoryUI>,
    onChooseCategory: (List<String>) -> Unit,
    onDelete: (CategoryUI) -> Unit
) {
    val buffList = remember(items) {
        mutableStateOf<List<CategoryUI>>(items)
    }

    LazyHorizontalGrid(modifier = modifier, rows = GridCells.Adaptive(50.dp)) {
        item {
            IconButton(modifier = Modifier
                .background(
                    color = FinanceHelperTheme.colors.defaultButtonColor,
                    shape = FinanceHelperTheme.shape.shapeRoundMedium
                )
                .border(2.dp, color = Color.Black, shape = RoundedCornerShape(20))
                .padding(5.dp)
                .width(50.dp),
                onClick = {
                    onChooseCategory(items.map { it.id })
                }) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Filled.Add,
                    contentDescription = "ic_add"
                )

            }
        }

        items(buffList.value.size) { i ->
            HorizontalCategoryGridItem(
                modifier = Modifier
                    .padding(2.dp)
                    .border(2.dp, color = Color.Black, shape = RoundedCornerShape(20)),

                categoryUI = buffList.value[i], onDelete = onDelete
            )
        }
    }
}

@Composable
fun HorizontalCategoryGridItem(
    modifier: Modifier = Modifier,
    categoryUI: CategoryUI,
    onDelete: (CategoryUI) -> Unit
) {
    Row(
        modifier = modifier
            .background(
                color = Color(categoryUI.color?:0x000000),
                shape = FinanceHelperTheme.shape.shapeRoundMedium
            )
            .padding(5.dp)
            .width(100.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        AutoSizeText(
            modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterVertically),
            text = categoryUI.name,
            maxLines = 2,
            minTextSize = FinanceHelperTheme.typography.h4.fontSize,
            maxTextSize = FinanceHelperTheme.typography.h3.fontSize,
            style = FinanceHelperTheme.typography.h4,
        )
        Icon(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .clickAnimation {
                    onDelete(categoryUI)
                },
            imageVector = Icons.Filled.Delete,
            contentDescription = "ic_delete"
        )
    }
}