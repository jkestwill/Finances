package com.jk.category.select_category_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jk.category.CategoryVerticalList
import com.jk.category_common_ui.CategoryUI
import com.jk.common_ui.FinanceHelperTheme
import com.jk.common_ui.Red
import com.jk.common_ui.State
import com.jk.common_ui.clickAnimation
import com.jk.shared_res.R


@Composable
fun SelectCategoryDialog(
    viewModel: SelectCategoryDialogViewModel,
    selectedCategoryIdList: List<String>? = null,
    onSelectCategoryIds: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    val preselectedCategoryList by viewModel.selectedCategoryFlow.collectAsState()
    val bufferList = remember(preselectedCategoryList) {
        mutableStateListOf<CategoryUI>()
    }
    val categoryPagingItems = viewModel.allCategoryListFlow.collectAsLazyPagingItems()
    val dialogHeight = rememberSaveable() {
        mutableIntStateOf(350)
    }
    LaunchedEffect(key1 = selectedCategoryIdList) {
        if (selectedCategoryIdList != null) {
            viewModel.getCategoryListById(selectedCategoryIdList)
        }
    }
    LaunchedEffect(key1 = preselectedCategoryList) {
        when (preselectedCategoryList) {
            is State.Success -> {
                bufferList.clear()
                bufferList.addAll((preselectedCategoryList as State.Success<List<CategoryUI>>).data)
            }

            else -> {}
        }
    }
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Box(
            modifier = Modifier

                .height(dialogHeight.intValue.dp)

                .fillMaxWidth()

                .background(
                    color = FinanceHelperTheme.colors.primaryBackground,
                    shape = FinanceHelperTheme.shape.shape10
                )

                .border(
                    border = FinanceHelperTheme.shape.borderStroke,
                    shape = FinanceHelperTheme.shape.shape10
                )
                .padding(15.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center), verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(
                        modifier = Modifier
                            .weight(2f)
                            .align(Alignment.CenterVertically),
                        text = stringResource(id = R.string.add_new_category),
                        style = FinanceHelperTheme.typography.h1,
                        textAlign = TextAlign.Start
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickAnimation {
                                if (bufferList.isEmpty()) {
                                    bufferList.addAll(categoryPagingItems.itemSnapshotList.items)
                                } else
                                    bufferList.clear()
                            }
                            .background(
                                FinanceHelperTheme.colors.defaultButtonColor,
                                FinanceHelperTheme.shape.shape20
                            )
                            .border(
                                border = FinanceHelperTheme.shape.borderStroke,
                                shape = FinanceHelperTheme.shape.shape20
                            )
                            .align(Alignment.CenterVertically)
                            .padding(5.dp),
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = stringResource(id = R.string.select_all),
                            style = FinanceHelperTheme.typography.h3,
                            maxLines = 1
                        )
                    }
                }

                CategoryVerticalList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((dialogHeight.intValue/1.5).dp),
                    items = categoryPagingItems,
                    preSelectedItems = bufferList
                ) {
                    bufferList.clear()
                    bufferList.addAll(it)
                }
            }
            Row(modifier = Modifier.align(Alignment.BottomCenter)) {
                Box(modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .clickAnimation {
                        onSelectCategoryIds(bufferList.map { it.id })
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
fun CategoryVerticalList(
    modifier: Modifier,
    items: LazyPagingItems<CategoryUI>,
    preSelectedItems: List<CategoryUI>,
    onListChanged: (List<CategoryUI>) -> Unit
) {
    CategoryVerticalList(
        modifier = modifier,
        items = items.itemSnapshotList.items,
        preselectedItems = preSelectedItems,
        onListChanged = onListChanged
    )
    when (items.loadState.refresh) {
        is LoadState.Loading -> {

        }

        is LoadState.Error -> {

        }

        is LoadState.NotLoading -> {

        }
    }
}