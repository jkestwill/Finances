package com.jk.goods

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.Red
import com.jk.common_ui.clickAnimation
import com.jk.common_ui.loadingAnimation
import com.jk.goods_common_ui.GoodsUI
import com.jk.shared_res.R

@Composable
fun SelectGoodsScreen(
    viewModel: SelectGoodsViewModel,
    preselectedId: List<String>,
    onDismiss: () -> Unit,

    ) {


}

@Composable
fun SelectGoodsDialog(
    viewModel: SelectGoodsViewModel,
    preselectedIdList: List<String>? = null,
    onSelect: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    val goodsList = viewModel.goodsListFlow.collectAsLazyPagingItems()
    SelectableItemsDialog<GoodsUI>(
        viewModel = viewModel,
        preselectedId = preselectedIdList,
        items = goodsList,
        onSelect = {
            onSelect(it.map { goods -> goods.id })
        },
        onDismiss = onDismiss,
        itemListContent = { index, item, selected ->
            GoodsListItem(
                modifier = Modifier.fillMaxWidth(),
                goodsUI = goodsList.itemSnapshotList.items[index],
                selected = selected
            )
        }
    )
}

@Composable
fun <T : Any> SelectableItemsDialog(
    modifier: Modifier = Modifier,
    viewModel: Selectable<T>,
    items: LazyPagingItems<T>,
    preselectedId: List<String>? = null,
    onSelect: (List<T>) -> Unit,
    itemListContent: @Composable LazyItemScope.(Int, T, Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Box(
            modifier = modifier
                .height(400.dp)
                .fillMaxWidth()
                .background(
                    color = FinanceHelperTheme.colors.primaryBackground,
                    shape = FinanceHelperTheme.shape.shapeRoundedLow
                )
                .border(
                    border = FinanceHelperTheme.shape.borderStroke,
                    shape = FinanceHelperTheme.shape.shapeRoundedLow
                )
                .padding(15.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter), verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(
                        modifier = Modifier
                            .weight(2f)
                            .align(Alignment.CenterVertically),
                        text = stringResource(id = R.string.goods),
                        style = FinanceHelperTheme.typography.h1,
                        textAlign = TextAlign.Start
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickAnimation {
                                if (viewModel.selectableItems.isEmpty() || viewModel.selectableItems.size < items.itemSnapshotList.items.size) {
                                    viewModel.selectableItems.clear()
                                    viewModel.selectableItems.addAll(items.itemSnapshotList.items)

                                } else
                                    viewModel.selectableItems.clear()
                            }
                            .background(
                                FinanceHelperTheme.colors.defaultButtonColor,
                                FinanceHelperTheme.shape.shapeRoundMedium
                            )
                            .border(
                                border = FinanceHelperTheme.shape.borderStroke,
                                shape = FinanceHelperTheme.shape.shapeRoundMedium
                            )
                            .align(Alignment.Bottom)
                            .padding(10.dp),
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = stringResource(id = R.string.select_all),
                            style = FinanceHelperTheme.typography.h3,
                            maxLines = 1,
                        )
                    }
                }

                ItemVerticalList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .loadingAnimation(
                            isVisible = items.loadState.refresh is LoadState.Loading,
                            color = FinanceHelperTheme.colors.defaultButtonColor
                        ),
                    items = items.itemSnapshotList.items, onListChanged = { item, selected ->
                        if (selected) {
                            viewModel.selectableItems.remove(item)
                        } else viewModel.selectableItems.add(item)
                    },
                    contentItem = { index, item, selected ->
                        itemListContent(index,item,selected)
                    })

            }
            Row(modifier = Modifier.align(Alignment.BottomCenter)) {
                Box(modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .clickAnimation {
                        onSelect(viewModel.selectableItems)
                    }) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.add),
                        style = FinanceHelperTheme.typography.h2,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .clickAnimation {
                            onDismiss()
                        }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.cancel),
                        style = FinanceHelperTheme.typography.h2,
                        color = Red,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun <T : Any> ItemVerticalList(
    modifier: Modifier = Modifier,
    items: List<T>,
    preselectedItems: List<T>? = null,
    onListChanged: (T, Boolean) -> Unit,
    contentItem: @Composable LazyItemScope.(Int, T, Boolean) -> Unit
) {
    LazyColumn(
        modifier,
        contentPadding = PaddingValues(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(items.size) { index ->
            val selected = rememberSaveable(preselectedItems?.size) {
                mutableStateOf(value = preselectedItems?.contains(items[index]) ?: false)
            }
            // при преселектед айтемах не убирает выделение
            Box(
                Modifier
                    .fillMaxWidth()
                    .clickAnimation { onListChanged(items[index], selected.value) }) {
                contentItem(index, items[index], selected.value)
            }
        }
    }
}

@Composable
fun GoodsListItem(
    modifier: Modifier,
    goodsUI: GoodsUI,
    selected: Boolean,
) {
    val checked by remember(selected, goodsUI) {
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
                color = FinanceHelperTheme.colors.primaryBackground,
                shape = FinanceHelperTheme.shape.shapeRoundMedium
            )
            .padding(5.dp)

    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = goodsUI.name,
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
